package com.example.notificaciones.Controladores;


import com.example.notificaciones.Entidades.Notificacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifiaciones")
public class NotificacionController {


    @PostMapping
    public ResponseEntity<Notificacion> enviarMailFueraZona(){
        return null;
    }

}
