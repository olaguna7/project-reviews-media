package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.ComentarioResena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentarioResenaRepository extends JpaRepository<ComentarioResena, Long> {
    List<ComentarioResena> findComentarioResenasByAbusoEquals(boolean b);

    List<ComentarioResena> findComentarioResenasByUsuario_IdUsuario(Long idUsuario);

    @Modifying
    @Query("UPDATE ComentarioResena cr SET cr.abuso = true WHERE cr.idComentarioResena = :idComentarioResena")
    void reportarComentarioResena(@Param("idComentarioResena") Long idComentarioResena);

    @Modifying
    @Query("UPDATE ComentarioResena cr SET cr.spoiler = true WHERE cr.idComentarioResena = :idComentarioResena")
    void reportarSpoilerComentarioResena(@Param("idComentarioResena") Long idComentarioResena);

    @Modifying
    @Query("DELETE FROM ComentarioResena cr WHERE cr.idComentarioResena = :idComentarioResena")
    void borrarComentarioResena(@Param("idComentarioResena") Long idComentarioResena);

    @Modifying
    @Query("UPDATE ComentarioResena cr SET cr.abuso = false WHERE cr.idComentarioResena = :idComentarioResena")
    void aprobarComentarioResena(@Param("idComentarioResena") Long idComentarioResena);

    Long countComentarioResenasByAbusoEquals(boolean b);
}
