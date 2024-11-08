package com.example.notificaciones.Entidades;


import com.example.notificaciones.Entidades.compositekey.PromoTelefonoId;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PromocionesTelefonos")
@NoArgsConstructor
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
    private Telefono idTelefono;

    //@MapsId("fechaHoraPromo") // Asocia el campo fechaHoraPromocion en la clave compuesta
    @Column(name = "FECHA_HORA_PROMO")
    private LocalDateTime fechaHoraPromocion;

    public PromoTelefono(NotificacionPromo notificacionPromo,
                         Telefono idTelefono,
                         LocalDateTime fechaHoraPromocion){
        this.idPromocion = notificacionPromo;
        this.idTelefono = idTelefono;
        this.fechaHoraPromocion = fechaHoraPromocion;
        this.idPromoTelefono = new PromoTelefonoId(
                idPromocion.getIdNotificacionPromocion(),
                this.idTelefono.getId(),
                this.fechaHoraPromocion);
    }


}
