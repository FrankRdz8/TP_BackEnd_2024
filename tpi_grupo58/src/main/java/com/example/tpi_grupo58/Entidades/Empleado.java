package com.example.tpi_grupo58.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "Empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer legajo;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false)
    private String apellido;

    @Column(name = "TELEFONO_CONTACTO", nullable = false)
    private BigInteger telefonoContacto;

    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<Prueba> pruebas;

    public Empleado(Integer legajo, String nombre, String apellido, BigInteger telefonoContacto){
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonoContacto = telefonoContacto;
    }


}
