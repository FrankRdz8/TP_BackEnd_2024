package com.example.notificaciones.Controladores;


import com.example.notificaciones.Entidades.NotificacionAviso;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @PostMapping
    public ResponseEntity<NotificacionAviso> enviarMailFueraZona(){
        return null;
    }

    

}
