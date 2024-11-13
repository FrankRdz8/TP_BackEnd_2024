package com.example.notificaciones.Servicios;

import com.example.notificaciones.Entidades.NotificacionAviso;
import com.example.notificaciones.Entidades.NotificacionPromo;
import com.example.notificaciones.Entidades.PromoTelefono;
import com.example.notificaciones.Entidades.dto.NotificacionAvisoDto;
import com.example.notificaciones.Entidades.dto.NotificacionPromoDto;
import com.example.notificaciones.Entidades.dto.PromoTelefonoDto;
import com.example.notificaciones.Repositorios.NotificacionAvisoRepository;
import com.example.notificaciones.Repositorios.NotificacionPromoRepository;
import com.example.notificaciones.Repositorios.PromoTelefonoRepository;
import jakarta.websocket.OnClose;
import org.springframework.stereotype.Service;

import java.rmi.MarshalledObject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

        return promo;

    }

    public List<Map<String, Object>> getIncidentesMapa(List<Integer> idsPruebas){

        List<Map<String, Object>> listaMapas = new ArrayList<>();
        // Es un doble for porque una prueba puede tener varios incidentes
        // Ej: Que entre en 2 zonas peligrosas
        for (int i=0; i<idsPruebas.size(); i++){
            List<NotificacionAviso> avisos = notificacionAvisoRepository.findByIdPrueba(idsPruebas.get(i));
            for (int j=0; j<avisos.size(); j++){
                Map<String, Object> mapa = new HashMap<>();

                mapa.put("ID", avisos.get(j).getIdNotificacionAviso());
                mapa.put("ID Prueba", avisos.get(j).getIdPrueba());
                mapa.put("Fecha Hora Aviso", avisos.get(j).getFechaHoraAviso());
                mapa.put("Mensaje", avisos.get(j).getMensaje());

                listaMapas.add(mapa);
            }

        }

        return listaMapas;
    }

    public List<NotificacionAvisoDto> getAll(){
        return notificacionAvisoRepository.findAll().stream().map(NotificacionAvisoDto::new).toList();
    }


    // Requerimiento 5
    public List<PromoTelefonoDto> addPromosTelefonos(List<PromoTelefonoDto> promosTelefonos){
        // Convertir los DTOs a entidades
        List<PromoTelefono> promoTelefonosEntities = promosTelefonos.stream()
                .map(PromoTelefonoDto::toPromoTelefono) // Convertir a entidad
                .collect(Collectors.toList());

        // Guardar las entidades en la base de datos
        List<PromoTelefono> savedPromoTelefonos = promoTelefonoRepository.saveAll(promoTelefonosEntities);

        // Convertir las entidades guardadas nuevamente a DTOs
        return savedPromoTelefonos.stream()
                .map(PromoTelefonoDto::new) // Convertir entidad a DTO
                .collect(Collectors.toList());
    }

}
