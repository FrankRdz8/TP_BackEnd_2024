package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.Coordenadas.Agencia;
import com.example.tpi_grupo58.Entidades.Coordenadas.Coordenada;
import com.example.tpi_grupo58.Entidades.Coordenadas.ZonaRestringida;
import com.example.tpi_grupo58.Entidades.Interesado;
import com.example.tpi_grupo58.Entidades.Vehiculo;
import com.example.tpi_grupo58.Entidades.dtos.InteresadoDto;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Repositorios.ModeloRepository;
import com.example.tpi_grupo58.Repositorios.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoService {
    private VehiculoRepository vehiculoRepository;

    private Agencia agencia;
    private String razon;

    public VehiculoService(VehiculoRepository vehiculoRepository){
        this.vehiculoRepository = vehiculoRepository;
    }

    public Optional<VehiculoDto> getById(Integer id) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);

        return vehiculo.map(VehiculoDto::new);
    }


    public boolean isVehicleInAllowedArea(Coordenada posicionVehiculo) {
        return isWithinRadius(posicionVehiculo) && !isInRestrictedZone(posicionVehiculo);
    }

    private boolean isWithinRadius(Coordenada posicionVehiculo) {
        double distancia = calcularDistancia(agencia.getCoordenadasAgencia(), posicionVehiculo);
        setRazonAviso("¡Atención!\n" +
                "El vehículo ha superado el radio de 5 km de la agencia.\n" +
                "Por razones de seguridad y para evitar inconvenientes, le solicitamos que regrese a la agencia lo antes posible.\n" +
                "\n" +
                "Por favor, tome acción inmediata para evitar cualquier inconveniente.");
        return distancia <= agencia.getRadioAdmitidoKm();
    }

    private boolean isInRestrictedZone(Coordenada posicionVehiculo) {
        for (ZonaRestringida zona : agencia.getZonasRestringidas()) {
            if (isWithinBounds(posicionVehiculo, zona)) {
                setRazonAviso("¡Atención!\n" +
                        "El vehículo se encuentra actualmente en una zona restringida.\n" +
                        "Por motivos de seguridad y regulación, le solicitamos que regrese lo antes posible a la agencia para evitar sanciones o problemas adicionales.\n" +
                        "\n" +
                        "Por favor, tome acción inmediata.");
                return true; // Está dentro de una zona restringida
            }
        }
        return false; // No está en ninguna zona restringida
    }

    private boolean isWithinBounds(Coordenada posicion, ZonaRestringida zona) {
        // Verificar si la latitud del vehículo está dentro de los límites de latitud de la zona
        boolean latitudDentro = posicion.getLat() >= Math.min(zona.getNoroeste().getLat(), zona.getSureste().getLat()) &&
                posicion.getLat() <= Math.max(zona.getNoroeste().getLat(), zona.getSureste().getLat());

        // Verificar si la longitud del vehículo está dentro de los límites de longitud de la zona
        boolean longitudDentro = posicion.getLon() >= Math.min(zona.getNoroeste().getLon(), zona.getSureste().getLon()) &&
                posicion.getLon() <= Math.max(zona.getNoroeste().getLon(), zona.getSureste().getLon());

        // El vehículo está dentro de los límites de la zona si ambas condiciones se cumplen
        return latitudDentro && longitudDentro;
    }


    private double calcularDistancia(Coordenada coord1, Coordenada coord2) {
        // Implementar fórmula de Haversine o similar para calcular la distancia entre dos puntos
        // en la superficie de la tierra
        final double R = 6371.0;
        // Convertir las latitudes y longitudes de grados a radianes
        double lat1Rad = Math.toRadians(coord1.getLat());
        double lon1Rad = Math.toRadians(coord1.getLon());
        double lat2Rad = Math.toRadians(coord2.getLat());
        double lon2Rad = Math.toRadians(coord2.getLon());

        // Diferencias de latitudes y longitudes
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Fórmula de Haversine
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        double distancia = R * c;

        return distancia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    private void setRazonAviso(String razon){
        this.razon = razon;
    }

    public String getRazonAviso(){
        return razon;
    }
}
