package com.example.notificaciones.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Notificaciones_Promociones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionPromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotificacionPromocion;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Promociones_Telefonos",
            joinColumns = @JoinColumn(name = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID")
    )
    private List<Telefono> telefonosList;

    @Column(name = "FECHA_HORA_VENCIMIENTO", nullable = false)
    private LocalDateTime fechaHoraVencimiento;
    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;



}
