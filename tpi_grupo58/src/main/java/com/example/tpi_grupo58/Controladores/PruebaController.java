package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Servicios.PruebaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private PruebaService pruebaService;
    public PruebaController(PruebaService pruebaService){
        this.pruebaService = pruebaService;
    }
}
