/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package Interfaces;

import Entidades.Jugador;
import dtos.JugadorResumenDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IJugadorMapper {
    
    public JugadorResumenDTO toDTO(Jugador jugador);

    public List<JugadorResumenDTO> toDTOList(List<Jugador> jugadores);
    
    public Jugador toEntity(JugadorResumenDTO dto);
    
    public List<Jugador> toEntityList(List<JugadorResumenDTO> dtos);
}
