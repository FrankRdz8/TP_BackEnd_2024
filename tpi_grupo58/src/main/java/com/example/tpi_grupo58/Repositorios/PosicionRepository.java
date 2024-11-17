package com.example.tpi_grupo58.Repositorios;

import com.example.tpi_grupo58.Entidades.Posicion;
import com.example.tpi_grupo58.Entidades.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
    @Query(value = "SELECT * FROM Posiciones p WHERE p.ID_VEHICULO = :idVehiculo", nativeQuery = true)
    List<Posicion> findByVehiculo(Integer idVehiculo);
}
