package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.dtos.InteresadoDto;
import com.example.tpi_grupo58.Repositorios.ModeloRepository;
import com.example.tpi_grupo58.Repositorios.PruebaRepository;
import org.springframework.stereotype.Service;

@Service
public class PruebaService {
    private PruebaRepository pruebaRepository;

    public PruebaService(PruebaRepository pruebaRepository){
        this.pruebaRepository = pruebaRepository;
    }

    // Requerimiento 1
    public InteresadoDto add(InteresadoDto interesadoDto) {
        // Buscar interesado sin licencia vencida y no restringido

        // Verificar que el vehiculo que le pasamos no esté en una prueba
        // fechahorafin = null

        // Verificar que el empleado no este asignado en una prueba actual
        // fechahorafin = null


        // Agregar

        return null;
        //return new InteresadoDto(interesadoRepository.save(interesadoDto.toInteresado()));
    }

}
