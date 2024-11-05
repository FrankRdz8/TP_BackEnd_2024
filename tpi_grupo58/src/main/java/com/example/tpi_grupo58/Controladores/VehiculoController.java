package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.Coordenadas.Agencia;
import com.example.tpi_grupo58.Entidades.Coordenadas.Coordenada;
import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.Vehiculo;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Servicios.PruebaService;
import com.example.tpi_grupo58.Servicios.VehiculoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {

    private VehiculoService vehiculoService;

    private PruebaService pruebaService;

    public VehiculoController(VehiculoService vehiculoService,
                              PruebaService pruebaService){
        this.vehiculoService = vehiculoService;
        this.pruebaService = pruebaService;
    }

    @PostMapping("/mail")
    public ResponseEntity<String> findVehiculos(@RequestParam Integer idVehiculo,
                                                @RequestBody Coordenada coordenada){

        Vehiculo vehiculo = vehiculoService.getById(idVehiculo).get().toVehiculo();
        // Verificar si el vehiculo esta en una prueba si existe
        if (Objects.equals(vehiculo.getId(), idVehiculo)){
            // Buscar la prueba activa con el vehiculo ingresa
            Prueba pruebaVehiculo = pruebaService.getByVehiculo(idVehiculo).stream()
                    .filter(prueba -> prueba.getFechaHoraFin() == null)
                    .findFirst().get();

        } else {
            // Retornar un error diciendo que no existe el vehiculo
        }


        // Cargamos las latitudes y longitudes
        try {
            RestTemplate rest = new RestTemplate();

            ResponseEntity<Agencia> res = rest.getForEntity(
                    "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/", Agencia.class
            );
            if (res.getStatusCode().is2xxSuccessful()){
                vehiculoService.setAgencia(res.getBody());
            }

        } catch (HttpClientErrorException ex){
            ex.printStackTrace();
        }


        // Verificamos si se encuentra en una zona prohibida
        if (vehiculoService.isVehicleInAllowedArea(coordenada)) {
            return ResponseEntity.ok("El vehículo está en un área permitida.");
        } else {
            // Disparar las acciones necesarias aquí
            return ResponseEntity.status(403).body("El vehículo está en una zona restringida o ha excedido el radio permitido.");
        }



        // Enviamos el mail (microservicio externo)
        try {
            RestTemplate rest = new RestTemplate();
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(json);

            ResponseEntity<Map> res = rest.postForEntity(
                    "http://localhost:8081/api/convert", entity, Map.class
            );

            if (res.getStatusCode().is2xxSuccessful()){
                alquiler.setMonto((double)res.getBody().get("importe"));
            }
        } catch (HttpClientErrorException ex){

        }



    }
}
