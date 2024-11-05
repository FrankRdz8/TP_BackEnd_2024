package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.Posicion;
import com.example.tpi_grupo58.Entidades.Vehiculo;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Repositorios.ModeloRepository;
import com.example.tpi_grupo58.Repositorios.PosicionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PosicionService {
    private PosicionRepository posicionRepository;

    public PosicionService(PosicionRepository posicionRepository){
        this.posicionRepository = posicionRepository;
    }

    public void addPosicion(VehiculoDto idVehiculo, Double latitud, Double longitud) {

        Posicion posicion = new Posicion(null, idVehiculo.toVehiculo(), LocalDateTime.now(), latitud, longitud);
        posicionRepository.save(posicion);

    }
}
