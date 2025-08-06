package com.atm.buenas_practicas_java.mapper;

import com.atm.buenas_practicas_java.dtos.ComunidadDTO;
import com.atm.buenas_practicas_java.dtos.PublicacionDTO;
import com.atm.buenas_practicas_java.dtos.UsuarioDTO;
import com.atm.buenas_practicas_java.entities.ComentarioPublicacion;
import com.atm.buenas_practicas_java.entities.Comunidad;
import com.atm.buenas_practicas_java.entities.Publicacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicacionMapper {

    @Mapping(target = "numComentarios", ignore = true)
    @Mapping(target = "usuario", expression = "java(mapUsuario(publicacion))")
    PublicacionDTO toDto(Publicacion publicacion);

    default UsuarioDTO mapUsuario(Publicacion publicacion) {
        ComentarioPublicacion primerComentario = publicacion.getComentariosPublicacion().get(0);
        return new UsuarioDTO(
                primerComentario.getUsuario().getIdUsuario(),
                primerComentario.getUsuario().getNombreUsuario(),
                primerComentario.getUsuario().getAvatarUrl(),
                primerComentario.getUsuario().getRole(),
                primerComentario.getUsuario().getBaneado()
        );
    }

    List<PublicacionDTO> toDto(List<Publicacion> publicaciones);

}
