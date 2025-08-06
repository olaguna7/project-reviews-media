package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="publicaciones")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion", columnDefinition = "INTEGER")
    private Long idPublicacion;

    @Column(columnDefinition = "VARCHAR(200)")
    private String titulo;
    private Boolean abuso = false;

    @OneToMany(mappedBy = "publicacion", fetch = FetchType.LAZY)
    private List<ComentarioPublicacion> comentariosPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_comunidad")
    private Comunidad comunidad;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
