package com.example.notificaciones.Repositorios;

import com.example.notificaciones.Entidades.NotificacionAviso;
import com.example.notificaciones.Entidades.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificacionAvisoRepository extends JpaRepository<NotificacionAviso, Integer> {
    @Query(value = "SELECT * FROM NotificacionesAvisos n WHERE n.ID_PRUEBA = :idPrueba", nativeQuery = true)
    List<NotificacionAviso> findByIdPrueba(Integer idPrueba);
}
