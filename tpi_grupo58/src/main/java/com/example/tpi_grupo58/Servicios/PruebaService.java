package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Repositorios.ModeloRepository;
import com.example.tpi_grupo58.Repositorios.PruebaRepository;
import org.springframework.stereotype.Service;

@Service
public class PruebaService {
    private PruebaRepository pruebaRepository;

    public PruebaService(PruebaRepository pruebaRepository){
        this.pruebaRepository = pruebaRepository;
    }
}
