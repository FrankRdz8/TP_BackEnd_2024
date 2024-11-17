package com.example.tpi_grupo58.Servicios;

import com.example.tpi_grupo58.Entidades.Coordenadas.Agencia;
import com.example.tpi_grupo58.Entidades.Coordenadas.Coordenada;
import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.dtos.*;
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
        List<Prueba> pruebasVehiculo = getByVehiculo(vehiculo.get().getId()).stream().map(PruebaDto::toPrueba).toList();
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

    public List<Prueba> getPruebasByVehiculoEnPeriodo(Integer idVehiculo,
                                         LocalDateTime fechaDesde,
                                         LocalDateTime fechaHasta){

        List<Prueba> pruebasSinFiltrar = pruebaRepository.findByVehiculo(idVehiculo).stream()
                .filter(prueba -> prueba.getFechaHoraFin() != null)
                .toList();


        return pruebasSinFiltrar.stream()
                .filter(prueba -> fechaDesde.isBefore(prueba.getFechaHoraInicio()) && fechaHasta.isAfter(prueba.getFechaHoraFin()))
                .toList();
    }

    public Double calcularKmVehiculoDesdeHasta(List<Prueba> pruebas){

        double kmTotales = 0.0;

        Coordenada coordenadaAgencia = posicionService.getCoordenadaAgencia();

        Integer idVehiculo = pruebas.get(0).getIdVehiculo().getId();

        // En prueba -> Buscar posiciones del vehiculo entre el inicio y fin de esa prueba
        // -> Tendriamos los km totales de esa prueba
        for (int i=0; i < pruebas.size(); i++){
            List<PosicionDto> posiciones = posicionService.getPosicionByVehiculo(idVehiculo);
            int finalI = i;
            List<PosicionDto> posicionesEnIntervalo = posiciones.stream()
                    .filter(posicionDto ->
                            posicionDto.getFechaHora().isAfter(pruebas.get(finalI).getFechaHoraInicio())
                                    && posicionDto.getFechaHora().isBefore(pruebas.get(finalI).getFechaHoraFin()))
                    .toList();

            // Buscamos todas las posiciones
            for (int j=0; j < posicionesEnIntervalo.size(); j++){
                Coordenada coordenada = new Coordenada(posicionesEnIntervalo.get(j).getLatitud(), posicionesEnIntervalo.get(j).getLongitud());
                // Primera se calcula con agencia
                if (j == 0){
                    kmTotales += posicionService.calcularDistancia(coordenadaAgencia, coordenada);
                } // Intermedios entre ellos
                else if (j<posicionesEnIntervalo.size()-1){
                    Coordenada cordAux = new Coordenada(posicionesEnIntervalo.get(j-1).getLatitud(), posicionesEnIntervalo.get(j-1).getLongitud());
                    kmTotales += posicionService.calcularDistancia(cordAux, coordenada);
                } else if(j == posicionesEnIntervalo.size()-1) { // Final con agencia
                    kmTotales += posicionService.calcularDistancia(coordenada, coordenadaAgencia);
                }
                // Si no tiene se mantiene en 0
                }
            }


        return kmTotales;

    }

    public void setearAgencia(Agencia agencia){
        posicionService.setAgencia(agencia);
    }

    public List<PruebaDto> getByVehiculo(Integer idVehiculo){
        return pruebaRepository.findByVehiculo(idVehiculo).stream().map(PruebaDto::new).toList();
    }

    public List<Prueba> getByEmpleado(Integer idEmpleado){
        return pruebaRepository.findByEmpleado(idEmpleado);
    }

    public List<Prueba> getByCliente(Integer idCliente){
        return pruebaRepository.findByCliente(idCliente);
    }

}
