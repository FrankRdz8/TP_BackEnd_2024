package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.dtos.EmpleadoDto;
import com.example.tpi_grupo58.Entidades.dtos.InteresadoDto;
import com.example.tpi_grupo58.Entidades.dtos.PruebaDto;
import com.example.tpi_grupo58.Entidades.dtos.VehiculoDto;
import com.example.tpi_grupo58.Entidades.exception.ResourceNotFoundException;
import com.example.tpi_grupo58.Repositorios.PruebaRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PruebaService {
    private PruebaRepository pruebaRepository;

    private InteresadoService interesadoService;
    private VehiculoService vehiculoService;
    private EmpleadoService empleadoService;
    private PosicionService posicionService;


    public PruebaService(PruebaRepository pruebaRepository,
                         InteresadoService interesadoService,
                         VehiculoService vehiculoService,
                         EmpleadoService empleadoService,
                         PosicionService posicionService){
        this.pruebaRepository = pruebaRepository;
        this.interesadoService = interesadoService;
        this.vehiculoService = vehiculoService;
        this.empleadoService = empleadoService;
        this.posicionService = posicionService;
    }

    // Requerimiento 1
    public Map<String, Object> add(PruebaDto pruebaDto) {
        // Buscamos el interesado, empleado y vehiculo
        Optional<InteresadoDto> interesado = interesadoService.getById(pruebaDto.getIdInteresado().getId());
        Optional<EmpleadoDto> empleado = empleadoService.getById(pruebaDto.getIdEmpleado().getLegajo());
        Optional<VehiculoDto> vehiculo = vehiculoService.getById(pruebaDto.getIdVehiculo().getId());

        // Aca guardamos el objeto pruebaDto y el mensaje de la petición (200 o 400)
        Map<String, Object> mapa = new HashMap<>();

        if (interesado.isEmpty() || empleado.isEmpty() || vehiculo.isEmpty()) {
            mapa.put("prueba", null);
            mapa.put("mensaje", "Uno de los IDs proporcionados no corresponde a un registro existente.");
            return mapa;
        }

        // Buscar interesado sin licencia vencida y no restringido
        if (interesado.get().getFechaVencimientoLicencia().isBefore(LocalDateTime.now()) || interesado.get().getRestringido() == 1){
            mapa.put("prueba", null);
            mapa.put("mensaje", "El interesado tiene la licencia vencida o está restringido para hacer pruebas.");
            return mapa;
        }

        // Verificar que el vehiculo que le pasamos no esté en una prueba
        List<Prueba> pruebasVehiculo = getByVehiculo(vehiculo.get().getId());
        boolean pruebaVehiculoActivo = pruebasVehiculo.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin() == null);

        // Verificar que el empleado no este asignado en una prueba actual
        List<Prueba> pruebasEmpleado = getByEmpleado(empleado.get().getLegajo());
        boolean pruebaEmpleadoActivo = pruebasEmpleado.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin() == null);

        if (pruebaEmpleadoActivo || pruebaVehiculoActivo){
            mapa.put("prueba", null);
            mapa.put("mensaje", "El empleado o vehiculo ingresado se encuentra haciendo una prueba en este momento.");
            return mapa;
        }

        // Verificar que el cliente no este asignado en una prueba actual
        List<Prueba> pruebasCliente = getByCliente(interesado.get().getId());
        boolean pruebaClienteActivo = pruebasCliente.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin() == null);
        if (pruebaClienteActivo){
            mapa.put("prueba", null);
            mapa.put("mensaje", "El cliente se encuentra haciendo una prueba en este momento.");
            return mapa;
        }

        // Seteo
        pruebaDto.setIdVehiculo(vehiculo.get());
        pruebaDto.setIdInteresado(interesado.get());
        pruebaDto.setIdEmpleado(empleado.get());

        pruebaDto.setFechaHoraInicio(LocalDateTime.now());
        pruebaDto.setFechaHoraFin(null);
        pruebaDto.setComentarios(null);

        mapa.put("prueba", pruebaDto);
        pruebaRepository.save(pruebaDto.toPrueba());

        return mapa;
    }

    // Requerimiento 2
    // 2024-11-02T13:37:31.882 -> Formato de fecha
    public List<PruebaDto> getPruebasFechaDada(LocalDateTime fecha) {
        List<Prueba> pruebasActuales = pruebaRepository.findByPruebaEnCurso();

        return pruebasActuales.stream()
                .filter(prueba -> prueba.getFechaHoraInicio().isBefore(fecha))
                .map(PruebaDto::new)
                .toList();
    }

    // Requerimiento 3
    public PruebaDto finalizarPrueba(Integer id, PruebaDto pruebaDto){
        Prueba prueba = pruebaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Prueba [%d] inexistente", id))
        );
        prueba.setFechaHoraFin(LocalDateTime.now());
        prueba.setComentarios(pruebaDto.getComentarios());
        return new PruebaDto(pruebaRepository.saveAndFlush(prueba.finalizarPrueba(pruebaDto.toPrueba())));
    }


    public List<Prueba> getByVehiculo(Integer idVehiculo){
        return pruebaRepository.findByVehiculo(idVehiculo);
    }

    public List<Prueba> getByEmpleado(Integer idEmpleado){
        return pruebaRepository.findByEmpleado(idEmpleado);
    }

    public List<Prueba> getByCliente(Integer idCliente){
        return pruebaRepository.findByCliente(idCliente);
    }

}
