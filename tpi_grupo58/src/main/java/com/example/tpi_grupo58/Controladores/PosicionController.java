package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.Coordenadas.Agencia;
import com.example.tpi_grupo58.Entidades.Coordenadas.Coordenada;
import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.dtos.PruebaDto;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Servicios.PosicionService;
import com.example.tpi_grupo58.Servicios.PruebaService;
import com.example.tpi_grupo58.Servicios.VehiculoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posicion")
public class PosicionController {

    private VehiculoService vehiculoService;
    private PosicionService posicionService;

    private PruebaService pruebaService;

    public PosicionController(VehiculoService vehiculoService,
                              PruebaService pruebaService,
                              PosicionService posicionService) {
        this.vehiculoService = vehiculoService;
        this.pruebaService = pruebaService;
        this.posicionService = posicionService;
    }

    // Requerimiento d - Recibir posicion y avisar si se paso de limites
    @PostMapping("/aviso")
    public ResponseEntity<String> enviarMensajeVehiculoAlerta(@RequestParam Integer idVehiculo,
                                                              @RequestBody Coordenada coordenada) {

        Optional<VehiculoDto> vehiculo = vehiculoService.getById(idVehiculo);
        // Verificar si el vehiculo esta en una prueba si existe
        if (vehiculo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No existe el vehiculo ingresado. Ingrese un vehiculo válido.");
        }

        // Buscar la prueba activa con el vehiculo ingresa
        Optional<Prueba> pruebaVehiculo = pruebaService.getByVehiculo(idVehiculo).stream()
                .filter(prueba -> prueba.getFechaHoraFin() == null)
                .findFirst()
                .map(PruebaDto::toPrueba);
        if (pruebaVehiculo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El vehiculo ingresado no está realizando una prueba en este momento. ");
        }


        // Cargamos las latitudes y longitudes
        try {
            RestTemplate rest = new RestTemplate();

            ResponseEntity<Agencia> res = rest.getForEntity(
                    "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/", Agencia.class
            );
            if (res.getStatusCode().is2xxSuccessful()) {
                posicionService.setAgencia(res.getBody());
            }

        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
        }


        // Verificamos si se encuentra en una zona prohibida
        if (posicionService.isVehicleInAllowedArea(coordenada)) {
            // Guardar posición
            posicionService.addPosicion(vehiculo.get(), coordenada.getLat(), coordenada.getLon());
            return ResponseEntity.ok("El vehículo está en un área permitida.");
        } else {
            // Guardar posición
            posicionService.addPosicion(vehiculo.get(), coordenada.getLat(), coordenada.getLon());

            // Enviamos el mensaje (microservicio externo)
            Map<String, String> mapa = new HashMap<>();
            mapa.put("idprueba", pruebaVehiculo.get().getId().toString());
            mapa.put("fechahoraaviso", LocalDateTime.now().toString());
            mapa.put("mensaje", posicionService.getRazonAviso());

            try {
                RestTemplate rest = new RestTemplate();
                HttpEntity<Map<String, String>> entity = new HttpEntity<>(mapa);

                rest.postForEntity(
                        "http://localhost:8081/api/notificaciones/aviso", entity, Map.class
                );

                /*if (res.getStatusCode().is2xxSuccessful()){

                }*/

            } catch (HttpClientErrorException ex) {
                ex.printStackTrace();
            }

            return ResponseEntity.status(403).body("El vehículo está en una zona restringida o ha excedido el radio permitido.");
        }



    }
}
