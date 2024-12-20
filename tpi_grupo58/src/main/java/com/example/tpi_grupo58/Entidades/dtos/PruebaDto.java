package com.example.tpi_grupo58.Entidades.dtos;

import com.example.tpi_grupo58.Entidades.Modelo;
import com.example.tpi_grupo58.Entidades.Prueba;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PruebaDto {
    private Integer id;
    private VehiculoDto idVehiculo;
    private InteresadoDto idInteresado;
    private EmpleadoDto idEmpleado;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String comentarios;

    public PruebaDto(Prueba prueba){
        id = prueba.getId();
        idVehiculo = new VehiculoDto(prueba.getIdVehiculo());
        idInteresado = new InteresadoDto(prueba.getIdInteresado());
        idEmpleado = new EmpleadoDto(prueba.getIdEmpleado());
        fechaHoraInicio = prueba.getFechaHoraInicio();
        fechaHoraFin = prueba.getFechaHoraFin();
        comentarios = prueba.getComentarios();
    }

    public Prueba toPrueba(){
        return new Prueba(id, idVehiculo.toVehiculo(), idInteresado.toInteresado(),
                idEmpleado.toEmpleado(), fechaHoraInicio, fechaHoraFin, comentarios);
    }


}
