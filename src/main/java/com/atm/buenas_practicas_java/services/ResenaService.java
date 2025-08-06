package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.dtos.ResenaDTO;
import com.atm.buenas_practicas_java.entities.Resena;
import com.atm.buenas_practicas_java.entities.Usuario;
import com.atm.buenas_practicas_java.mapper.ResenaMapper;
import com.atm.buenas_practicas_java.repositories.ObjetoRepository;
import com.atm.buenas_practicas_java.repositories.ReaccionRepository;
import com.atm.buenas_practicas_java.repositories.ResenaRepository;
import com.atm.buenas_practicas_java.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    private final ReaccionRepository reaccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ResenaRepository resenaRepository;
    private final ResenaMapper resenaMapper;

    public ResenaService(ResenaRepository resenaRepository,
                         ResenaMapper resenaMapper,
                         ReaccionRepository reaccionRepository,
                         UsuarioRepository usuarioRepository) {
        this.resenaRepository = resenaRepository;
        this.resenaMapper = resenaMapper;
        this.reaccionRepository = reaccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Resena findById(Long idResena) {
        return resenaRepository.findById(idResena).orElseThrow();
    }

    // Metodos auxiliares para rellenar el DTO de Reseña con el numero de likes
    private ResenaDTO rellenarResenaDTO(ResenaDTO resenaDTO, String nombreUsuario) {

        Boolean likeUsuario = false;
        if (nombreUsuario != null && !nombreUsuario.isBlank()) {
            Optional<Usuario> optUsuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

            if (optUsuario.isPresent()) {
                Usuario usuario = optUsuario.get();
                likeUsuario = reaccionRepository
                        .findByResena_IdResenaAndUsuario_IdUsuario(resenaDTO.idResena(), usuario.getIdUsuario())
                        .isPresent();
            }
        }

        return new ResenaDTO(
                resenaDTO.idResena(),
                resenaDTO.titulo(),
                resenaDTO.contenido(),
                resenaDTO.puntuacion(),
                resenaDTO.spoiler(),
                resenaDTO.autor(),
                resenaDTO.comentariosResena(),
                resenaDTO.fechaPublicacion(),
                reaccionRepository.countByResena_IdResenaAndMeGustaEquals(resenaDTO.idResena(), true),
                likeUsuario
        );
    }

    private List<ResenaDTO> rellenarListaResenaDTO(List<ResenaDTO> resenasIncompletas, String nombreUsuario) {
        return resenasIncompletas.stream()
                .map(resenaDTO -> rellenarResenaDTO(resenaDTO, nombreUsuario)).toList();
    }

    @Transactional
    public List<ResenaDTO> findResenasByObjeto(Long idObjeto, String nombreUsuario) {
        List<ResenaDTO> resenasDTO =  resenaMapper.toDtoList(resenaRepository.findResenasByObjeto_IdObjetoOrderByFechaPublicacionDesc(idObjeto));
        return rellenarListaResenaDTO(resenasDTO, nombreUsuario);
    }

    @Transactional
    public List<ResenaDTO> obtenerResenasUsuario(Long idUsuario, String nombreUsuario) {
        List<ResenaDTO> resenasDTO = resenaMapper.toDtoList(resenaRepository.findResenasByUsuario_IdUsuario(idUsuario));
        return rellenarListaResenaDTO(resenasDTO, nombreUsuario);
    }

    public List<ResenaDTO> obtenerResenasReaccionadasUsuario(Long idUsuario, String nombreUsuario) {
        List<ResenaDTO> resenasDTO = resenaMapper.toDtoList(resenaRepository.obtenerResenasReaccionadasUsuario(idUsuario));
        return rellenarListaResenaDTO(resenasDTO, nombreUsuario);
    }

    public Long contarResenasUsuario(Long idUsuario) {
        return resenaRepository.countByUsuario_IdUsuario(idUsuario);
    }

    @Transactional
    public List<ResenaDTO> obtenerResenasConAbuso(String nombreUsuario) {
        List<ResenaDTO> resenasReportadas = resenaMapper.toDtoList(resenaRepository.findResenasByAbusoEquals(true));
        return rellenarListaResenaDTO(resenasReportadas, nombreUsuario);
    }

    @Transactional
    public ResenaDTO obtenerUltimaResena(String nombreUsuario) {
        ResenaDTO ultimaResena = resenaMapper.toDto(resenaRepository.findTopByOrderByFechaPublicacionDesc());
        return rellenarResenaDTO(ultimaResena, nombreUsuario);
    }

    @Transactional
    public Resena save(Resena resena) {
        return resenaRepository.save(resena);
    }

    @Transactional
    public void reportarResena(Long idResena) {
        resenaRepository.reportarResena(idResena);
    }

    @Transactional
    public void reportarSpoilerResena(Long idResena) {
        resenaRepository.reportarSpoilerResena(idResena);
    }

    @Transactional
    public void eliminarResena(Long idResena) {
        resenaRepository.borrarResenaPorIdResena(idResena);
    }

    @Transactional
    public void aprobarResena(Long idResena) {
        resenaRepository.aprobarResena(idResena);
    }

    public Long contarResenasConAbuso() {
        return resenaRepository.countResenaByAbusoEquals(true);
    }
}
