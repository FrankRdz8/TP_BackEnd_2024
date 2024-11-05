package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.Coordenadas.Agencia;
import com.example.tpi_grupo58.Entidades.Coordenadas.Coordenada;
import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.Vehiculo;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Servicios.PruebaService;
import com.example.tpi_grupo58.Servicios.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {

    private VehiculoService vehiculoService;
    private PruebaService pruebaService;

    @Value("${notification.service.url}") // URL del microservicio de notificaciones
    private String notificationServiceUrl;

    public VehiculoController(VehiculoService vehiculoService,
                              PruebaService pruebaService){
        this.vehiculoService = vehiculoService;
        this.pruebaService = pruebaService;
    }

    @PostMapping("/mail")
    public ResponseEntity<String> findVehiculos(@RequestParam Integer idVehiculo,
                                                @RequestBody Coordenada coordenada){
        try {
            //Vehiculo vehiculo = vehiculoService.getById(idVehiculo).get().toVehiculo();
            //Si encuentra el vehiculo sigue ejecutando hacia abajo, sino tira la excepcion de no entrado.
            vehiculoService.getById(idVehiculo)
                    .orElseThrow(() -> new NoSuchElementException("Vehículo no encontrado"));

            // Verificar si el vehiculo esta en una prueba si existe
            Optional<Prueba> pruebaOpt = pruebaService.getByVehiculo(idVehiculo).stream()
                    .filter(prueba -> prueba.getFechaHoraFin() == null)
                    .findFirst();

            if (pruebaOpt.isPresent()) {
                //Prueba pruebaVehiculo = pruebaOpt.get();
                /*if (Objects.equals(vehiculo.getId(), idVehiculo)){
                    // Buscar la prueba activa con el vehiculo ingresa
                    Prueba pruebaVehiculo = pruebaService.getByVehiculo(idVehiculo).stream()
                            .filter(prueba -> prueba.getFechaHoraFin() == null)
                            .findFirst().get();
                } else {
                    // Retornar un error diciendo que no existe el vehiculo
                }*/

                // Cargamos las latitudes y longitudes
                try {
                    RestTemplate rest = new RestTemplate();
                    ResponseEntity<Agencia> res = rest.getForEntity(
                            "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/", Agencia.class
                    );
                    if (res.getStatusCode().is2xxSuccessful()) {
                        vehiculoService.setAgencia(res.getBody());
                    }

                } catch (HttpClientErrorException ex) {
                    ex.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al cargar configuración de la agencia");
                }

                // Verificamos si se encuentra en una zona prohibida
                if (!vehiculoService.isVehicleInAllowedArea(coordenada)) {
                    //return ResponseEntity.ok("El vehículo está en un área permitida.");
                    String message = "Vehículo con ID " + idVehiculo + " ha excedido los límites permitidos.";
                    enviarNotificacion("paredesgenaro99@gmail.com", "Alerta de Vehículo", message);
                    return ResponseEntity.status(403).body("Vehículo en zona restringida o fuera de los límites permitidos.");
                } else {
                    return ResponseEntity.ok("Vehículo está en un área permitida.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prueba activa no encontrada para este vehículo.");
            }


        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al procesar la solicitud.");
        }
    }

    private void enviarNotificacion(String destinatario, String asunto, String cuerpo) {
        // Crear el mapa con los datos de la notificación
        Map<String, String> notificacionData = new HashMap<>();
        notificacionData.put("destinatario", destinatario);
        notificacionData.put("asunto", asunto);
        notificacionData.put("cuerpo", cuerpo);

        // Configurar los headers para la solicitud HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear la entidad HTTP con los datos y headers
        HttpEntity<Map<String, String>> request = new HttpEntity<>(notificacionData, headers);

        // Hacer la llamada al microservicio de notificaciones
        try {
            RestTemplate rest = new RestTemplate();
            ResponseEntity<Void> response = rest.postForEntity(
                    notificationServiceUrl + "/api/notificaciones/enviar",
                    request,
                    Void.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error al enviar notificación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar con el servicio de notificaciones", e);
        }
    }
}

