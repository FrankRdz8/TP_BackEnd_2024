package com.example.tpi_grupo58.Entidades.dtos;

import com.example.tpi_grupo58.Entidades.Marca;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarcaDto {

    private Integer id;
    private String nombre;

    public MarcaDto(Marca marca){
        id = marca.getId();
        nombre = marca.getNombre();
    }

    public Marca toMarca(){
        return new Marca(id, nombre);
    }
}
