package com.example.notificaciones.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "NotificacionesPromociones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionPromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer idNotificacionPromocion;

    @Column(name = "FECHA_HORA_PROMO", nullable = false)
    private LocalDateTime fechaHoraPromo;
    @Column(name = "FECHA_HORA_VENCIMIENTO", nullable = false)
    private LocalDateTime fechaHoraVencimiento;
    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

    @OneToMany(mappedBy = "idPromocion", cascade = CascadeType.ALL)
    private List<PromoTelefono> promoTelefonos;

    public NotificacionPromo(NotificacionPromo notificacionPromo){
        this.fechaHoraPromo = notificacionPromo.getFechaHoraPromo();
        this.fechaHoraVencimiento = notificacionPromo.getFechaHoraVencimiento();
        this.mensaje = notificacionPromo.getMensaje();
    }

}
