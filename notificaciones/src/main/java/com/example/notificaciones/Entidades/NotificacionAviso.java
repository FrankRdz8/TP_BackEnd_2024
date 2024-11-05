package com.example.notificaciones.Entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Notificaciones_Avisos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionAviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotificacionAviso;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Avisos_Telefonos",
            joinColumns = @JoinColumn(name = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID")
    )
    private List<Telefono> telefonosList;

    @Column(name = "ID_VEHICULO", nullable = false)
    private Integer idVehiculo;

    @Column(name = "NOMBRE_CLIENTE", nullable = false)
    private String nombreCliente;

    @Column(name = "FECHA_HORA_AVISO", nullable = false)
    private LocalDateTime fechaHoraAviso;

    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

    @Column(name = "MOTIVO", nullable = false)
    private String motivo;



}
