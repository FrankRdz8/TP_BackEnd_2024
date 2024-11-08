package com.example.notificaciones.Entidades;


import com.example.notificaciones.Entidades.compositekey.PromoTelefonoId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PromocionesTelefonos")
@NoArgsConstructor
@Data
public class PromoTelefono {

    @EmbeddedId
    private PromoTelefonoId idPromoTelefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPromo")  // Mapea el atributo de la clave primaria compuesta
    @JoinColumn(name = "ID_PROMOCION", referencedColumnName = "ID")
    private NotificacionPromo idPromocion;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTelefono")  // Mapea el atributo de la clave primaria compuesta
    @JoinColumn(name = "ID_TELEFONO", referencedColumnName = "ID")
    private Telefono telefono;

    @Column(name = "FECHA_HORA_PROMO", insertable = false, updatable = false)
    private LocalDateTime fechaHoraPromocion;

    public PromoTelefono(NotificacionPromo notificacionPromo,
                         Telefono idTelefono,
                         LocalDateTime fechaHoraPromocion){
        this.idPromocion = notificacionPromo;
        this.telefono = idTelefono;
        this.fechaHoraPromocion = fechaHoraPromocion;
        this.idPromoTelefono = new PromoTelefonoId(
                idPromocion.getIdNotificacionPromocion(),
                telefono.getId(),
                this.fechaHoraPromocion);
    }
}
