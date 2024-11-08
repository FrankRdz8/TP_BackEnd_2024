package com.example.notificaciones.Repositorios;

import com.example.notificaciones.Entidades.PromoTelefono;
import com.example.notificaciones.Entidades.compositekey.PromoTelefonoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoTelefonoRepository extends JpaRepository<PromoTelefono, PromoTelefonoId> {
}
