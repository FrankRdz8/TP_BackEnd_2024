package com.example.notificaciones.Entidades.dto;

import com.example.notificaciones.Entidades.Telefono;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoDto {

    private Integer numero;

    public TelefonoDto(Telefono telefono){
        numero = telefono.getNumero();
    }

    public Telefono toTelefono(){
        return new Telefono(numero);
    }
}