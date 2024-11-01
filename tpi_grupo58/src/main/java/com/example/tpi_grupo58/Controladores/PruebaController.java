package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.dtos.PruebaDto;
import com.example.tpi_grupo58.Servicios.PruebaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService){
        this.pruebaService = pruebaService;
    }

    @PostMapping
    public ResponseEntity<PruebaDto> addPrueba(@Valid @RequestBody PruebaDto prueba){
        /*try {
            this.pruebaService.add(prueba);
            return new ResponseEntity<>(prueba, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }*/


        return new ResponseEntity<>(pruebaService.add(prueba), HttpStatus.CREATED);
    }





}
