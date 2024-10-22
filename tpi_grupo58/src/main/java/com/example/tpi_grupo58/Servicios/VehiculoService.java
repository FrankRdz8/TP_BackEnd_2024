package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Repositorios.ModeloRepository;
import com.example.tpi_grupo58.Repositorios.VehiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {
    private VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository){
        this.vehiculoRepository = vehiculoRepository;
    }
}
