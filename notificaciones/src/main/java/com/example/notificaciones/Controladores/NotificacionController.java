package com.example.notificaciones.Controladores;


import com.example.notificaciones.Entidades.NotificacionAviso;
import com.example.notificaciones.Entidades.dto.TelefonoDto;
import com.example.notificaciones.Servicios.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/aviso")
    public ResponseEntity<NotificacionAviso> guardarNotificacionAviso(
            @RequestBody Map<String, String> mapa) {

        NotificacionAviso notificacionAviso = new NotificacionAviso(
                null,
                Integer.parseInt(mapa.get("idprueba")),
                LocalDateTime.parse(mapa.get("fechahoraaviso")),
                mapa.get("mensaje"));


        return new ResponseEntity<>(notificacionService.guardarNotificacion(notificacionAviso), HttpStatus.CREATED);
    }

    @PostMapping("/promo")
    public ResponseEntity<String> enviarPromo(@RequestParam Integer idPromo,
                                              @RequestBody List<TelefonoDto> telefonos) {

        return null;

    }
}