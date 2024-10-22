package com.example.tpi_grupo58.Entidades.dtos;

import com.example.tpi_grupo58.Entidades.Vehiculo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculoDto {
    private Integer id;
    private String patente;
    private ModeloDto idModelo;

    public VehiculoDto(Vehiculo vehiculo) {
        id = vehiculo.getId();
        patente = vehiculo.getPatente();
        idModelo = new ModeloDto(vehiculo.getIdModelo());
    }
}
