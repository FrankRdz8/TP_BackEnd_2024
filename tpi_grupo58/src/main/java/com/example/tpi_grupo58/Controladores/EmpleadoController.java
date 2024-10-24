package com.example.tpi_grupo58.Controladores;

import com.example.tpi_grupo58.Entidades.Empleado;
import com.example.tpi_grupo58.Entidades.dtos.EmpleadoDto;
import com.example.tpi_grupo58.Servicios.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService){
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDto>> findEmpleados(){
        List<EmpleadoDto> empleados = empleadoService.getAll();
        return empleados.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(empleados);
    }




}
