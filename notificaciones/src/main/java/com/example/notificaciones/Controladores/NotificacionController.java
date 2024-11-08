package com.example.notificaciones.Controladores;


import com.example.notificaciones.Entidades.NotificacionAviso;
import com.example.notificaciones.Entidades.NotificacionPromo;
import com.example.notificaciones.Entidades.PromoTelefono;
import com.example.notificaciones.Entidades.Telefono;
import com.example.notificaciones.Entidades.dto.NotificacionPromoDto;
import com.example.notificaciones.Entidades.dto.TelefonoDto;
import com.example.notificaciones.Servicios.NotificacionService;
import com.example.notificaciones.Servicios.TelefonoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private NotificacionService notificacionService;
    private TelefonoService telefonoService;

    public NotificacionController(NotificacionService notificacionService,
                                  TelefonoService telefonoService) {
        this.notificacionService = notificacionService;
        this.telefonoService = telefonoService;
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
    public ResponseEntity<List<PromoTelefono>> enviarPromo(@RequestParam Integer idPromo,
                                              @RequestBody List<TelefonoDto> telefonos) {

        // Buscar promo
        Optional<NotificacionPromo> promo = notificacionService.getById(idPromo);
        if (promo.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ArrayList<PromoTelefono>());
        }
        NotificacionPromoDto promoDto = new NotificacionPromoDto(promo.get());

        // Buscar telefonos
        List<Telefono> listaIntegral = new ArrayList<>();

        for (TelefonoDto telefonoDto : telefonos) {
            Optional<Telefono> telefono = telefonoService.getByNumero(telefonoDto.getNumero());
            if (telefono.isEmpty()) {
                // Creamos y subimos a BD el numero nuevo
                Telefono telefono1 = telefonoService.addNumero(telefonoDto.toTelefono());
                listaIntegral.add(telefono1);
            } else {
                listaIntegral.add(telefono.get());
            }
        }

        // Crear y subir PromoTelefono (Tabla intermedia)
        List<PromoTelefono> listaPromosTelefonos = new ArrayList<>();

        LocalDateTime fechaHoraPromo = LocalDateTime.now();

        for (Telefono telefono : listaIntegral){
            PromoTelefono promoTemporal = new PromoTelefono(
                    promo.get(),
                    telefono,
                    fechaHoraPromo);
            listaPromosTelefonos.add(promoTemporal);
        }

        return new ResponseEntity<>(notificacionService.addPromosTelefonos(listaPromosTelefonos), HttpStatus.CREATED);

    }
}