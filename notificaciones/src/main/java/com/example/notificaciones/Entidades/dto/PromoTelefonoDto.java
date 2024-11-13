package com.example.notificaciones.Entidades.dto;

import com.example.notificaciones.Entidades.NotificacionPromo;
import com.example.notificaciones.Entidades.PromoTelefono;
import com.example.notificaciones.Entidades.Telefono;
import com.example.notificaciones.Entidades.compositekey.PromoTelefonoId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoTelefonoDto {

    private PromoTelefonoId idPromoTelefono;
    private NotificacionPromoDto idPromocion;
    private TelefonoDto telefono;
    private LocalDateTime fechaHoraPromocion;

    public PromoTelefonoDto(PromoTelefono promoTelefono){
        idPromoTelefono = promoTelefono.getIdPromoTelefono();
        idPromocion = new NotificacionPromoDto(promoTelefono.getIdPromocion());
        telefono = new TelefonoDto(promoTelefono.getTelefono());
        fechaHoraPromocion = promoTelefono.getFechaHoraPromocion();
    }

    public PromoTelefono toPromoTelefono(){
        return new PromoTelefono(idPromoTelefono,
                idPromocion, telefono, fechaHoraPromocion);
    }
}
