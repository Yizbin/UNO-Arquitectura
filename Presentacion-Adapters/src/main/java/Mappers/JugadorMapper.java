/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Entidades.Jugador;
import Interfaces.IJugadorMapper;
import dtos.JugadorResumenDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Abraham Coronel
 */
public class JugadorMapper implements IJugadorMapper {

    @Override
    public JugadorResumenDTO toDTO(Jugador jugador) {
        if (jugador == null) {
            return null;
        }

        JugadorResumenDTO dto = new JugadorResumenDTO();

        dto.setNombreUsuario(jugador.getUsuario());

        int cantidadCartas = (jugador.getMano() != null) ? jugador.getMano().size() : 0;
        dto.setCantidadDeCartas(cantidadCartas);

        dto.setPuntos(jugador.calcularPuntosRestantes());

        dto.setEnTurno(false);

        return dto;
    }

    @Override
    public List<JugadorResumenDTO> toDTOList(List<Jugador> jugadores) {
        if (jugadores == null || jugadores.isEmpty()) {
            return new ArrayList<>();
        }

        return jugadores.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Jugador toEntity(JugadorResumenDTO dto) {
        if (dto == null) {
            return null;
        }
        Jugador jugador = new Jugador(dto.getNombreUsuario(), "avatar_por_defecto.png");

        return jugador;
    }

    @Override
    public List<Jugador> toEntityList(List<JugadorResumenDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
