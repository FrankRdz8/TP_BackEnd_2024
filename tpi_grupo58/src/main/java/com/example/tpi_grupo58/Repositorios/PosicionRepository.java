package com.example.tpi_grupo58.Repositorios;

import com.example.tpi_grupo58.Entidades.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {

}
