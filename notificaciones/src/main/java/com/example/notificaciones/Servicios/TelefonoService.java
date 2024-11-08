package com.example.notificaciones.Servicios;

import com.example.notificaciones.Entidades.Telefono;
import com.example.notificaciones.Repositorios.TelefonoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelefonoService {

    private TelefonoRepository telefonoRepository;

    public TelefonoService(TelefonoRepository telefonoRepository){
        this.telefonoRepository = telefonoRepository;
    }


    public Optional<Telefono> getByNumero(Integer numeroTelefono){
        Optional<Telefono> telefono = telefonoRepository.findByNumero(numeroTelefono);

        return telefono.map(Telefono::new);
    }

    public Telefono addNumero(Telefono telefono){
        return telefonoRepository.save(telefono);
    }

}
