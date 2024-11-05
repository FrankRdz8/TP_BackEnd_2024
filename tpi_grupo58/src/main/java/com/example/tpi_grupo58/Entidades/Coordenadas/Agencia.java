package com.example.tpi_grupo58.Entidades.Coordenadas;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agencia {

    private Coordenada coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    // Constructor, getters y setters
    public Coordenada getCoordenadasAgencia() {
        return coordenadasAgencia;
    }

    public void setCoordenadasAgencia(Coordenada coordenadasAgencia) {
        this.coordenadasAgencia = coordenadasAgencia;
    }

    public double getRadioAdmitidoKm() {
        return radioAdmitidoKm;
    }

    public void setRadioAdmitidoKm(double radioAdmitidoKm) {
        this.radioAdmitidoKm = radioAdmitidoKm;
    }

    public List<ZonaRestringida> getZonasRestringidas() {
        return zonasRestringidas;
    }

    public void setZonasRestringidas(List<ZonaRestringida> zonasRestringidas) {
        this.zonasRestringidas = zonasRestringidas;
    }
}
