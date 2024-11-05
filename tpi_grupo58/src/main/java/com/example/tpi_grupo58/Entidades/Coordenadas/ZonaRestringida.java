package com.example.tpi_grupo58.Entidades.Coordenadas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonaRestringida {

    private Coordenada noroeste;
    private Coordenada sureste;
}
