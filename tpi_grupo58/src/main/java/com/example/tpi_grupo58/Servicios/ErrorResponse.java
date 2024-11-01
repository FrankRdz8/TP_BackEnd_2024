package com.example.tpi_grupo58.Servicios;

import lombok.Data;

@Data
public class ErrorResponse {

    private String error;

    private String message;

    public ErrorResponse(String message) {
        this.error = "Not Found";
        this.message = message;
    }

        // Getters y setters

}
