package com.example.gateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfig {
    // Empleado crea pruebas y manda notificaciones
    // Vehiculo envia posiciones
    // Administrador ver reportes
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize

                    // Requerimiento a - Crear prueba
                    .requestMatchers(HttpMethod.POST, "/api/pruebas/**")
                    .hasRole("EMPLEADO")
                    // Requerimiento b - Obtener pruebas en fecha
                    .requestMatchers(HttpMethod.GET, "/api/pruebas/byFecha/**")
                    .hasRole("EMPLEADO")
                    // Requerimiento c - Finalizar prueba
                    .requestMatchers(HttpMethod.PATCH, "/api/pruebas/{id}/**")
                    .hasRole("EMPLEADO")
                    // Requerimientos d - Ingresar posicion y avisar limites
                    .requestMatchers(HttpMethod.POST, "/api/posicion/aviso/**")
                    .hasRole("VEHICULO")
                    // Requerimiento e - Enviar notificaciones de promo
                    .requestMatchers(HttpMethod.POST, "/api/notificaciones/promo/**")
                    .hasRole("EMPLEADO")
                    // Requerimiento fi - Obtener todos los incidentes
                    .requestMatchers(HttpMethod.GET, "/api/notificaciones/incidentes")
                    .hasRole("ADMIN")
                    // Requerimiento fii - Obtener incidente para un empleado
                    .requestMatchers(HttpMethod.POST, "/api/pruebas/incidentesByEmpleado/**")
                    .hasRole("ADMIN")
                    // Requerimiento fiii - Obtener km de prueba de un vehiculo en periodo
                    .requestMatchers(HttpMethod.GET, "/api/pruebas/kmByVehiculoEnPeriodo/**")
                    .hasRole("ADMIN")
                    // Requerimiento fiv - Pruebas realizadas por un vehiculo
                    .requestMatchers(HttpMethod.GET, "/api/pruebas/byVehiculo/**")
                    .hasRole("ADMIN")

                    // Cualquier otra petición...
                    .anyRequest()
                    .authenticated()

                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Se especifica el nombre del claim a analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        // Se agrega este prefijo en la conversión por una convención de Spring
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        // Se asocia el conversor de Authorities al Bean que convierte el token JWT a un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        // También se puede cambiar el claim que corresponde al nombre que luego se utilizará en el objeto
        // Authorization
        // jwtAuthenticationConverter.setPrincipalClaimName("user_name");

        return jwtAuthenticationConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Configura el JwtDecoder usando un URI de JWK Set
        String jwkSetUri = "https://labsys.frc.utn.edu.ar/aim/realms/backend/";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

}



