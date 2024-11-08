package com.example.notificaciones.Entidades.dto;

import com.example.notificaciones.Entidades.NotificacionPromo;
import com.example.notificaciones.Entidades.PromoTelefono;
import com.example.notificaciones.Entidades.Telefono;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionPromoDto {

    private Integer id;
    private LocalDateTime fechaHoraPromo;
    private LocalDateTime fechaHoraVencimiento;
    private String mensaje;

    public NotificacionPromoDto(NotificacionPromo promo){
        id = promo.getIdNotificacionPromocion();
        fechaHoraPromo = promo.getFechaHoraPromo();
        fechaHoraVencimiento = promo.getFechaHoraVencimiento();
        mensaje = promo.getMensaje();
    }

    public NotificacionPromo toNotificacionPromo(){
        return new NotificacionPromo(id, fechaHoraPromo,
                fechaHoraVencimiento, mensaje);
    }

}
