package com.example.tpi_grupo58.Entidades;

import com.example.tpi_grupo58.Entidades.dtos.MarcaDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Marcas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "idMarca", fetch = FetchType.LAZY)
    private List<Modelo> modelos;

    public Marca(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
