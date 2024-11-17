package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.Vehiculo;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Repositorios.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoService {
    private VehiculoRepository vehiculoRepository;


    public VehiculoService(VehiculoRepository vehiculoRepository){
        this.vehiculoRepository = vehiculoRepository;
    }

    public Optional<VehiculoDto> getById(Integer id) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);

        return vehiculo.map(VehiculoDto::new);
    }



}
