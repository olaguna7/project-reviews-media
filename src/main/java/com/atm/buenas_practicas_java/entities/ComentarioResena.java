package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comentarios_resenas")
public class ComentarioResena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INTEGER")
    private Long idComentarioResena;

    private LocalDateTime fecha = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String contenido;
    private Boolean abuso = false;
    private Boolean spoiler = false;
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_resena", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resena resena;
}
