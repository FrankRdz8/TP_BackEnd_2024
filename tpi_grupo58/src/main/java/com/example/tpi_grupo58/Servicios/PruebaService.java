package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.dtos.EmpleadoDto;
import com.example.tpi_grupo58.Entidades.dtos.InteresadoDto;
import com.example.tpi_grupo58.Entidades.dtos.PruebaDto;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Repositorios.PruebaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.tpi_grupo58.Servicios.ErrorResponse;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PruebaService {
    private PruebaRepository pruebaRepository;

    private InteresadoService interesadoService;
    private VehiculoService vehiculoService;
    private EmpleadoService empleadoService;

    public PruebaService(PruebaRepository pruebaRepository,
                         InteresadoService interesadoService,
                         VehiculoService vehiculoService,
                         EmpleadoService empleadoService){
        this.pruebaRepository = pruebaRepository;
        this.interesadoService = interesadoService;
        this.vehiculoService = vehiculoService;
        this.empleadoService = empleadoService;
    }

    // Requerimiento 1
    public PruebaDto add(PruebaDto pruebaDto) {
        // Buscamos el interesado, empleado y vehiculo
        Optional<InteresadoDto> interesado = interesadoService.getById(pruebaDto.getIdInteresado().getId());
        Optional<EmpleadoDto> empleado = empleadoService.getById(pruebaDto.getIdEmpleado().getLegajo());
        Optional<VehiculoDto> vehiculo = vehiculoService.getById(pruebaDto.getIdVehiculo().getId());

        // Buscar interesado sin licencia vencida y no restringido
        /*if (interesado.get().getFechaVencimientoLicencia().isAfter(LocalDateTime.now())){
            // Return licencia vencida
            return null;
        }

        // Verificar que el vehiculo que le pasamos no esté en una prueba
        List<PruebaDto> pruebasVehiculo = getByVehiculo(vehiculo.get().getId());
        boolean pruebaVehiculoActivo = pruebasVehiculo.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin() == null);

        // Verificar que el empleado no este asignado en una prueba actual
        List<PruebaDto> pruebasEmpleado = getByEmpleado(empleado.get().getLegajo());
        boolean pruebaEmpleadoActivo = pruebasEmpleado.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin() == null);
        if (pruebaEmpleadoActivo || pruebaVehiculoActivo){
            // Retornar que el empleado o vehiculo esta haciendo una prueba
            return null;
        }


        // Verificar que el cliente no este asignado en una prueba actual
        List<PruebaDto> pruebasCliente = getByCliente(interesado.get().getId());
        boolean pruebaClienteActivo = pruebasCliente.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin() == null);
        if (pruebaClienteActivo){
            // Retornar que el cliente esta haciendo una prueba
            return null;
        }*/

        // Seteo
        VehiculoDto vehiculoDto = new VehiculoDto(vehiculo.get().getId(),
                vehiculo.get().getPatente(), vehiculo.get().getIdModelo());
        pruebaDto.setIdVehiculo(vehiculoDto);

        InteresadoDto interesadoDto = new InteresadoDto(interesado.get().getId(),
                interesado.get().getTipoDocumento(), interesado.get().getDocumento(),
                interesado.get().getNombre(), interesado.get().getApellido(),
                interesado.get().getRestringido(), interesado.get().getNroLicencia(),
                interesado.get().getFechaVencimientoLicencia());
        pruebaDto.setIdInteresado(interesadoDto);

        EmpleadoDto empleadoDto = new EmpleadoDto(empleado.get().getLegajo(),
                empleado.get().getNombre(), empleado.get().getApellido(),
                empleado.get().getTelefonoContacto());
        pruebaDto.setIdEmpleado(empleadoDto);

        pruebaDto.setFechaHoraInicio(LocalDateTime.now());
        pruebaDto.setFechaHoraFin(null);
        pruebaDto.setComentarios(null);


        return new PruebaDto(pruebaRepository.save(pruebaDto.toPrueba()));
    }

    public List<PruebaDto> getByVehiculo(Integer idVehiculo){
        return pruebaRepository.findByVehiculo(idVehiculo);
    }

    public List<PruebaDto> getByEmpleado(Integer idEmpleado){
        return pruebaRepository.findByEmpleado(idEmpleado);
    }

    public List<PruebaDto> getByCliente(Integer idCliente){
        return pruebaRepository.findByCliente(idCliente);
    }

}
