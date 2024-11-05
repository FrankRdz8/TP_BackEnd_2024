package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.dtos.PruebaDto;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Servicios.PruebaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Object> getPruebasFechaActual(@RequestParam LocalDateTime fecha){
        List<PruebaDto> pruebas = pruebaService.getPruebasFechaDada(fecha);

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
