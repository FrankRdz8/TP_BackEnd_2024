package com.example.notificaciones.Servicios;

import com.example.notificaciones.Entidades.NotificacionAviso;
import com.example.notificaciones.Entidades.NotificacionPromo;
import com.example.notificaciones.Entidades.PromoTelefono;
import com.example.notificaciones.Repositorios.NotificacionAvisoRepository;
import com.example.notificaciones.Repositorios.NotificacionPromoRepository;
import com.example.notificaciones.Repositorios.PromoTelefonoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private NotificacionAvisoRepository notificacionAvisoRepository;
    private NotificacionPromoRepository notificacionPromoRepository;
    private PromoTelefonoRepository promoTelefonoRepository;

    public NotificacionService(NotificacionAvisoRepository notificacionAvisoRepository,
                               NotificacionPromoRepository notificacionPromoRepository,
                               PromoTelefonoRepository promoTelefonoRepository){
        this.notificacionAvisoRepository = notificacionAvisoRepository;
        this.notificacionPromoRepository = notificacionPromoRepository;
        this.promoTelefonoRepository = promoTelefonoRepository;
    }

    public NotificacionAviso guardarNotificacion(NotificacionAviso notificacionAviso){
        return new NotificacionAviso(notificacionAvisoRepository.save(notificacionAviso));
    }

    public Optional<NotificacionPromo> getById(Integer idPromo){
        Optional<NotificacionPromo> promo = notificacionPromoRepository.findById(idPromo);

        return promo.map(NotificacionPromo::new);
    }

    public List<PromoTelefono> addPromosTelefonos(List<PromoTelefono> promosTelefonos){
        return promoTelefonoRepository.saveAll(promosTelefonos);
    }

}
