package com.example.tpi_grupo58.Entidades.dtos;

import com.example.tpi_grupo58.Entidades.Marca;
import com.example.tpi_grupo58.Entidades.Modelo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModeloDto {

    private Integer id;
    private MarcaDto idMarca;
    private String descripcion;

    public ModeloDto(Modelo modelo){
        id = modelo.getId();
        idMarca = new MarcaDto(modelo.getIdMarca());
        descripcion = modelo.getDescripcion();
    }

    public Modelo toModelo(){
        return new Modelo(id, idMarca.toMarca(), descripcion);
    }


}
