package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Repositorios.EmpleadoRepository;
import com.example.tpi_grupo58.Repositorios.InteresadoRepository;
import org.springframework.stereotype.Service;

@Service
public class InteresadoService {
    private InteresadoRepository interesadoRepository;

    public InteresadoService(InteresadoRepository interesadoRepository){
        this.interesadoRepository = interesadoRepository;
    }
}
