package com.example.notificaciones.Controladores;


import com.example.notificaciones.Entidades.Notificacion;
import com.example.notificaciones.Servicios.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private NotificacionService notificacionService;
    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarNotificacion(@RequestBody Map<String, String> notificacionData) {
        String destinatario = notificacionData.get("destinatario");
        String asunto = notificacionData.get("asunto");
        String cuerpo = notificacionData.get("cuerpo");

        // Verificar que no sean nulos
        if (destinatario == null || asunto == null || cuerpo == null) {
            return ResponseEntity.badRequest().build();
        }
        notificacionService.enviarCorreo(destinatario, asunto, cuerpo);
        return ResponseEntity.ok().build();
    }
}
