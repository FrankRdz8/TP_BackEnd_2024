package com.example.tpi_grupo58.Entidades.dtos;

import com.example.tpi_grupo58.Entidades.Empleado;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class EmpleadoDto {

    private Integer legajo;
    private String nombre;
    private String apellido;
    private BigInteger telefonoContacto;

    public EmpleadoDto(Empleado empleado){
        legajo = empleado.getLegajo();
        nombre = empleado.getNombre();
        apellido = empleado.getApellido();
        telefonoContacto = empleado.getTelefonoContacto();
    }

    public Empleado toEmpleado(){
        return new Empleado(legajo, nombre, apellido, telefonoContacto);
    }

}
