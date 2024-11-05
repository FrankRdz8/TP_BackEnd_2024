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

    @ManyToMany(mappedBy = "telefonosList")
    private List<NotificacionPromo> promoList;

    @ManyToMany(mappedBy = "telefonosList")
    private List<NotificacionAviso> avisosList;

}
