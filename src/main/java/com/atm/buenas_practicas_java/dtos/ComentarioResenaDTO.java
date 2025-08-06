package com.atm.buenas_practicas_java.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ComentarioResenaDTO(
        Long idComentarioResena,
        LocalDateTime fecha,
        String contenido,
        UsuarioDTO usuario,
        List<ReaccionDTO> reacciones
) {
}
