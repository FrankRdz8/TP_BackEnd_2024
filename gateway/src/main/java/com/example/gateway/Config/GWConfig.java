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
                                        @Value("${gateway.url-microservicio-notificaciones}") String uriNotificaciones,
                                        @Value("${gateway.url-microservicio-tpi_grupo58}") String uriTpiGrupo58) {
        return builder.routes()
                .route(r -> r.path("/api/notificaciones/**").uri(uriNotificaciones))
                .route(r -> r.path("/api/tpi_grupo58/**").uri(uriTpiGrupo58))
                .build();
    }
}
