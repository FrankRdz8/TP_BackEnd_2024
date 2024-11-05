package com.example.notificaciones.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    private String destinatario;
    private String asunto;
    private String cuerpo;
}
