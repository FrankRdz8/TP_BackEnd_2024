package com.example.notificaciones.Entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "NotificacionesAvisos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionAviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotificacionAviso;

    @Column(name = "ID_PRUEBA", nullable = false)
    private Integer idPrueba;

    @Column(name = "FECHA_HORA_AVISO", nullable = false)
    private LocalDateTime fechaHoraAviso;

    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

    public NotificacionAviso(NotificacionAviso notificacionAviso){
        this.idNotificacionAviso = null;
        this.idPrueba = notificacionAviso.getIdPrueba();
        this.fechaHoraAviso = notificacionAviso.getFechaHoraAviso();
        this.mensaje = notificacionAviso.getMensaje();
    }

}
