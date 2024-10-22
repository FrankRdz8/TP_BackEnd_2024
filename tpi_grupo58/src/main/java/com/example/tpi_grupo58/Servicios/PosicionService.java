package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Repositorios.ModeloRepository;
import com.example.tpi_grupo58.Repositorios.PosicionRepository;
import org.springframework.stereotype.Service;

@Service
public class PosicionService {
    private PosicionRepository posicionRepository;

    public PosicionService(PosicionRepository posicionRepository){
        this.posicionRepository = posicionRepository;
    }
}
