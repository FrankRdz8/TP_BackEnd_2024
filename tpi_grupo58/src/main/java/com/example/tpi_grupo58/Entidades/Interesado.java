package com.example.tpi_grupo58.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Interesados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interesado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TIPO_DOCUMENTO", nullable = false)
    private String tipoDocumento;

    @Column(name = "DOCUMENTO", nullable = false, length = 10)
    private String documento;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "RESTRINGIDO", nullable = false, columnDefinition = "boolean default 'false'")
    private Integer restringido;

    @Column(name = "NRO_LICENCIA", nullable = false, unique = true)
    private Integer nroLicencia;

    @Column(name = "FECHA_VENCIMIENTO_LICENCIA", nullable = false)
    private LocalDateTime fechaVencimientoLicencia;


    @OneToMany(mappedBy = "idInteresado", fetch = FetchType.LAZY)
    private List<Prueba> pruebas;

    public Interesado(Integer id, String tipoDocumento, String documento,
                      String nombre, String apellido, Integer restringido,
                      Integer nroLicencia, LocalDateTime fechaVencimientoLicencia){
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.restringido = restringido;
        this.nroLicencia = nroLicencia;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    @PrePersist
    public void prePersist() {
        if (this.tipoDocumento == null) {
            this.tipoDocumento = "DNI";
        }
    }

}
