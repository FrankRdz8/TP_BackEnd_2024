package com.example.gateway.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configureRoutes(RouteLocatorBuilder builder,
                                        @Value("${gateway.url.microservicio.notificaciones}") String uriNotificaciones,
                                        @Value("${gateway.url.microservicio.tpi_grupo58}") String uriTpiGrupo58) {
        return builder.routes()
                // Rutas hacia el microservicio de notificaciones
                .route("notificaciones_route", r -> r.path("/api/notificaciones/").uri(uriNotificaciones))
                // Rutas hacia el microservicio principal (tpi_grupo58)
                .route("principal_route", r -> r.path("/api/").uri(uriTpiGrupo58))
                .build();
    }
}
