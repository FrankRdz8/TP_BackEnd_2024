package com.example.notificaciones.Servicios;

import com.example.notificaciones.Entidades.NotificacionAviso;
import com.example.notificaciones.Repositorios.NotificacionAvisoRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private NotificacionAvisoRepository notificacionAvisoRepository;

    public NotificacionService(NotificacionAvisoRepository notificacionAvisoRepository){
        this.notificacionAvisoRepository = notificacionAvisoRepository;
    }

    public NotificacionAviso guardarNotificacion(NotificacionAviso notificacionAviso){
        return new NotificacionAviso(notificacionAvisoRepository.save(notificacionAviso));
    }
}
