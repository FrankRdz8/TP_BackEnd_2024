package com.example.tpi_grupo58.Entidades.dtos;

import com.example.tpi_grupo58.Entidades.Interesado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteresadoDto {

    private Integer id;
    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private Integer restringido;
    private Integer nroLicencia;
    private LocalDateTime fechaVencimientoLicencia;

    public InteresadoDto(Interesado interesado){
        id = interesado.getId();
        tipoDocumento = interesado.getTipoDocumento();
        documento = interesado.getDocumento();
        nombre = interesado.getNombre();
        apellido = interesado.getApellido();
        restringido = interesado.getRestringido();
        nroLicencia = interesado.getNroLicencia();
        fechaVencimientoLicencia = interesado.getFechaVencimientoLicencia();
    }

    public Interesado toInteresado(){
        return new Interesado(id, tipoDocumento, documento, nombre,
                apellido, restringido, nroLicencia, fechaVencimientoLicencia);
    }
}
