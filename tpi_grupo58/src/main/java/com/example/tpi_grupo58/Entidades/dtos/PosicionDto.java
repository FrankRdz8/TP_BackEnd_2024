package com.example.tpi_grupo58.Entidades.dtos;

import com.example.tpi_grupo58.Entidades.Posicion;
import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.Vehiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosicionDto {
    private Integer id;
    private VehiculoDto idVehiculo;
    private LocalDateTime fechaHora;
    private double latitud;
    private double longitud;

    public PosicionDto(Posicion posicion) {
        id = posicion.getId();
        idVehiculo = new VehiculoDto(posicion.getIdVehiculo());
        fechaHora = posicion.getFechaHora();
        latitud = posicion.getLatitud();
        longitud = posicion.getLongitud();
    }

    public Posicion toPosicion(){
        return new Posicion(id, idVehiculo.toVehiculo(), fechaHora, latitud, longitud);
    }
}
