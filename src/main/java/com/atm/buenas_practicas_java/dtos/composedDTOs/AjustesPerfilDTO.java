package com.atm.buenas_practicas_java.dtos.composedDTOs;

import com.atm.buenas_practicas_java.entities.Genero;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AjustesPerfilDTO(
        Long idUsuario,
        String nombreUsuario,
        String contrasena,
        String biografia,
        MultipartFile avatar
) {}

