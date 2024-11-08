package com.example.notificaciones.Entidades.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoTelefonoId implements Serializable {

    @Column(name = "ID_PROMOCION")
    private Integer idPromo;
    @Column(name = "ID_TELEFONO")
    private Integer idTelefono;
    @Column(name = "FECHA_HORA_PROMO")
    private LocalDateTime fechaHoraPromo;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoTelefonoId that = (PromoTelefonoId) o;
        return Objects.equals(idPromo, that.idPromo) && Objects.equals(idTelefono, that.idTelefono) && Objects.equals(fechaHoraPromo, that.fechaHoraPromo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPromo, idTelefono, fechaHoraPromo);
    }
}
