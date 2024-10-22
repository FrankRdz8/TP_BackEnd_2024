package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Repositorios.EmpleadoRepository;
import com.example.tpi_grupo58.Repositorios.ModeloRepository;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {
    private ModeloRepository modeloRepository;

    public ModeloService(ModeloRepository modeloRepository){
        this.modeloRepository = modeloRepository;
    }
}
