/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.JugadorResumenDTO;
import Entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Abraham Coronel
 */
public class JugadorMapper {

    public JugadorResumenDTO toDTO(Jugador jugador) {
        if (jugador == null) {
            return null;
        }

        JugadorResumenDTO dto = new JugadorResumenDTO();

        dto.setId(jugador.getId());

        dto.setNombreUsuario(jugador.getUsuario());

        int cantidadCartas = (jugador.getMano() != null) ? jugador.getMano().size() : 0;
        dto.setCantidadDeCartas(cantidadCartas);

        dto.setPuntos(jugador.calcularPuntosRestantes());

        dto.setEnTurno(false);

        dto.setEstadoSala(jugador.getEstadoSala());

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

        Jugador jugador = new Jugador(dto.getId(), dto.getNombreUsuario(), "avatar_por_defecto.png");

        jugador.setEstadoSala(dto.getEstadoSala());

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