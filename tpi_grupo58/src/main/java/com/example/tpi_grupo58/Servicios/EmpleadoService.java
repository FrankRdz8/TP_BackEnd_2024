package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.Empleado;
import com.example.tpi_grupo58.Entidades.Vehiculo;
import com.example.tpi_grupo58.Entidades.dtos.EmpleadoDto;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Repositorios.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository){
        this.empleadoRepository = empleadoRepository;
    }


    public List<EmpleadoDto> getAll(){
        return empleadoRepository.findAll().stream().map(empleado -> new EmpleadoDto(empleado)).toList();
    }

    public Optional<EmpleadoDto> getById(Integer id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);

        return empleado.isEmpty()
                ? Optional.empty()
                : Optional.of(new EmpleadoDto(empleado.get()));
    }





}
