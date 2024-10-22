package com.example.tpi_grupo58.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PATENTE", nullable = false)
    private String patente;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO", referencedColumnName = "ID")
    private Modelo idModelo;

    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<Prueba> pruebas;

    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<Posicion> posiciones;


    public Vehiculo(Integer id, String patente, Modelo modelo) {
        this.id = id;
        this.patente = patente;
        this.idModelo = modelo;
    }
}
