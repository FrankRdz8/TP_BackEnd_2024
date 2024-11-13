package com.example.notificaciones.Entidades.dto;

import com.example.notificaciones.Entidades.NotificacionAviso;
import com.example.notificaciones.Entidades.NotificacionPromo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionAvisoDto {

    private Integer id;
    private Integer idPrueba;
    private LocalDateTime fechaHoraAviso;
    private String mensaje;

    public NotificacionAvisoDto(NotificacionAviso aviso){
        id = aviso.getIdNotificacionAviso();
        idPrueba = aviso.getIdPrueba();
        fechaHoraAviso = aviso.getFechaHoraAviso();
        mensaje = aviso.getMensaje();
    }

    public NotificacionAviso toNotificacionAviso(){
        return new NotificacionAviso(id,
                idPrueba, fechaHoraAviso, mensaje);
    }
}
