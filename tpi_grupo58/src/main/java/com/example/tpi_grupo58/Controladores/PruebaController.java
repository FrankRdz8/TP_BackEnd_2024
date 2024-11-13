package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.dtos.PruebaDto;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Servicios.PruebaService;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService){
        this.pruebaService = pruebaService;
    }

    @PostMapping
    public ResponseEntity<Object> addPrueba(@Valid @RequestBody PruebaDto prueba){

        Map<String, Object> mapa = pruebaService.add(prueba);
        if (mapa.get("prueba") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(mapa.get("mensaje"));
        }

        return new ResponseEntity<>(mapa.get("prueba"), HttpStatus.CREATED);
    }

    @GetMapping("/incidentesByEmpleado")
    public ResponseEntity<List<Map<String, Object>>> getIncidentesByEmpleado(
            @RequestParam Integer idEmpleado
    ){
        // Buscar pruebas por empleado
        List<Prueba> listaPruebas = pruebaService.getByEmpleado(idEmpleado);
        List<Integer> listaIds = listaPruebas.stream().map(Prueba::getId).toList();



        // Buscar incidentes de las pruebas
        try {
            RestTemplate rest = new RestTemplate();
            HttpEntity<List<Integer>> entity = new HttpEntity<>(listaIds);

            // Usar ParameterizedTypeReference para deserializar correctamente
            ResponseEntity<List<Map<String, Object>>> res = rest.exchange(
                    "http://localhost:8081/api/notificaciones/empleado",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            if (res.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<>(res.getBody(), HttpStatus.OK);
            }
        } catch (HttpClientErrorException ex) {
            // Manejo de excepciones
        }

        return ResponseEntity.noContent().build();

    }


    @GetMapping("/byFecha")
    public ResponseEntity<Object> getPruebasFechaActual(@RequestParam LocalDateTime fecha){
        List<PruebaDto> pruebas = pruebaService.getPruebasFechaDada(fecha);

        return pruebas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pruebas);
    }

    @GetMapping("/byVehiculo")
    public ResponseEntity<List<PruebaDto>> getPruebasByVehiculo(@RequestParam Integer idVehiculo){
        List<PruebaDto> pruebas = pruebaService.getByVehiculo(idVehiculo);

        return pruebas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pruebas);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PruebaDto> finalizarPrueba(
            @PathVariable Integer id,
            @RequestBody PruebaDto pruebaDto
    ){
        return new ResponseEntity<>(pruebaService.finalizarPrueba(id, pruebaDto), HttpStatus.ACCEPTED);
    }


}
