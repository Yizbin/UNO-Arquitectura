/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.JugadorResumenDTO;
import Entidades.Jugador;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Abraham Coronel
 */
public class JugadorMapper {

    public JugadorResumenDTO toDTO(Jugador entity) {
        if (entity == null) {
            return null;
        }

        JugadorResumenDTO dto = new JugadorResumenDTO();
        dto.setId(entity.getId());
        dto.setNombreUsuario(entity.getUsuario());
        dto.setRutaAvatar(entity.getAvatar());
        dto.setCantidadDeCartas(entity.getMano() != null ? entity.getMano().size() : 0);
        dto.setPuntos(entity.getPuntos());
        dto.setAceptado(entity.isAceptado());

        return dto;
    }

    public List<JugadorResumenDTO> toDTOList(List<Jugador> jugadores) {
        if (jugadores == null || jugadores.isEmpty()) {
            return new ArrayList<>();
        }

        return jugadores.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Jugador toEntity(JugadorResumenDTO dto) {
        if (dto == null) {
            return null;
        }

        Jugador jugador = new Jugador(
                dto.getId(),
                dto.getNombreUsuario(),
                dto.getRutaAvatar()
        );

        jugador.setPuntos(dto.getPuntos());
        if (dto.getMano() != null) {
            jugador.setMano(new CartaMapper().toEntityList(dto.getMano()));
        }
        jugador.setAceptado(dto.isAceptado());

        return jugador;
    }

    public List<Jugador> toEntityList(List<JugadorResumenDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
