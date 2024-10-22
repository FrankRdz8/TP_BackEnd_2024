package com.example.tpi_grupo58.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Modelos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_MARCA", referencedColumnName = "ID")
    private Marca idMarca;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "idModelo", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculos;


}
