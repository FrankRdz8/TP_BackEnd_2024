package com.example.notificaciones.Repositorios;

import com.example.notificaciones.Entidades.Telefono;
import com.example.notificaciones.Entidades.dto.TelefonoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TelefonoRepository extends JpaRepository<Telefono, Integer> {

    @Query(value = "SELECT * FROM Telefonos t WHERE t.NUMERO = :numero", nativeQuery = true)
    Optional<Telefono> findByNumero(Integer numero);
}
