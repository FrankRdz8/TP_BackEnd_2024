package com.example.tpi_grupo58.Entidades;

import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "Posiciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID")
    private Vehiculo idVehiculo;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "LATITUD", nullable = false)
    private double latitud;

    @Column(name = "LONGITUD", nullable = false)
    private double longitud;


    public Posicion(VehiculoDto vehiculo, LocalDateTime fechaHora,
                            Double latitud, Double longitud)
    {
        this.idVehiculo = vehiculo.toVehiculo();
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
