package com.example.notificaciones.Repositorios;

import com.example.notificaciones.Entidades.NotificacionPromo;
import com.example.notificaciones.Entidades.dto.NotificacionPromoDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionPromoRepository extends JpaRepository<NotificacionPromo, Integer> {

}
