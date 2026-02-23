/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Carta;
import dtos.CartaDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface ICartaMapper {

    public CartaDTO toDTO(Carta carta);

    public List<CartaDTO> toDTOList(List<Carta> cartas);
}
