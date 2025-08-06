package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="objeto_usuario")
public class ObjetoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objeto_usuario", columnDefinition = "INTEGER")
    private Long idObjetoUsuario;
    private Boolean favorito = false;
    private Boolean estado = false;

    // Relación M:N entre la tabla usuario y objeto
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_objeto")
    private Objeto objeto;
}
