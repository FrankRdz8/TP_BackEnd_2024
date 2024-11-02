package com.example.tpi_grupo58.Repositorios;

import com.example.tpi_grupo58.Entidades.Prueba;
import com.example.tpi_grupo58.Entidades.dtos.PruebaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Integer> {

    @Query(value = "SELECT * FROM Pruebas p WHERE p.ID_VEHICULO = :idVehiculo", nativeQuery = true)
    List<Prueba> findByVehiculo(Integer idVehiculo);
    @Query(value = "SELECT * FROM Pruebas p WHERE p.ID_EMPLEADO = :idEmpleado", nativeQuery = true)
    List<Prueba> findByEmpleado(Integer idEmpleado);
    @Query(value = "SELECT * FROM Pruebas p WHERE p.ID_INTERESADO = :idInteresado", nativeQuery = true)
    List<Prueba> findByCliente(Integer idInteresado);
    @Query(value = "SELECT * FROM Pruebas p WHERE p.FECHA_HORA_FIN IS NULL", nativeQuery = true)
    List<Prueba> findByPruebaEnCurso();
}
