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

    /*@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "PromocionesTelefonos",
            joinColumns = @JoinColumn(name = "ID_PROMOCION", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_TELEFONO", referencedColumnName = "ID")
    )
    private List<Telefono> telefonosList;*/

    @Column(name = "FECHA_HORA_PROMO", nullable = false)
    private LocalDateTime fechaHoraPromo;
    @Column(name = "FECHA_HORA_VENCIMIENTO", nullable = false)
    private LocalDateTime fechaHoraVencimiento;
    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

    @OneToMany(mappedBy = "idPromocion", cascade = CascadeType.ALL)
    private List<PromoTelefono> promoTelefonos;
}
