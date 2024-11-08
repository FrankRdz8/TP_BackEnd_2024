package com.example.notificaciones.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Telefonos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NUMERO", nullable = false)
    private Integer numero;

    /*@ManyToMany(mappedBy = "telefonosList")
    private List<NotificacionPromo> promoList;*/

    @OneToMany(mappedBy = "idTelefono", cascade = CascadeType.ALL)
    private List<PromoTelefono> promosTelefonos;


    public Telefono(Integer numero){
        this.numero = numero;
    }

}
