package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Repositorios.EmpleadoRepository;
import com.example.tpi_grupo58.Repositorios.MarcaRepository;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {
    private MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository){
        this.marcaRepository = marcaRepository;
    }
}
