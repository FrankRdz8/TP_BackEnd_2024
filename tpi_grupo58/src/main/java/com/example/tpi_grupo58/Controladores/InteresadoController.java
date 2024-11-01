package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.dtos.InteresadoDto;
import com.example.tpi_grupo58.Servicios.InteresadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/interesados")
public class InteresadoController {

    private InteresadoService interesadoService;

    public InteresadoController(InteresadoService interesadoService){
        this.interesadoService = interesadoService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<InteresadoDto> findInteresado(@PathVariable Integer id){
        Optional<InteresadoDto> interesado = interesadoService.getById(id);
        return interesado.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(interesado.get());
    }

    // @RequestBody recibe los parametros de un JSON
    @PostMapping
    public ResponseEntity<InteresadoDto> addInteresado(@Valid @RequestBody InteresadoDto interesado) {
        return new ResponseEntity<>(interesadoService.add(interesado), HttpStatus.CREATED);
    }





}
