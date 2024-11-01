package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.Interesado;
import com.example.tpi_grupo58.Entidades.dtos.InteresadoDto;
import com.example.tpi_grupo58.Repositorios.EmpleadoRepository;
import com.example.tpi_grupo58.Repositorios.InteresadoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InteresadoService {
    private InteresadoRepository interesadoRepository;

    public InteresadoService(InteresadoRepository interesadoRepository){
        this.interesadoRepository = interesadoRepository;
    }

    public InteresadoDto add(InteresadoDto interesadoDto) {
        return new InteresadoDto(interesadoRepository.save(interesadoDto.toInteresado()));
    }

    public Optional<InteresadoDto> getById(Integer id) {
        Optional<Interesado> interesado = interesadoRepository.findById(id);

        return interesado.isEmpty()
                ? Optional.empty()
                : Optional.of(new InteresadoDto(interesado.get()));
    }


}
