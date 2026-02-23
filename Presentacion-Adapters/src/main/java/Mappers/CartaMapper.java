/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Entidades.Carta;
import Entidades.CartaAccion;
import Entidades.CartaComodin;
import Entidades.CartaNumero;
import Interfaces.ICartaMapper;
import dtos.CartaDTO;
import dtos.TipoCartaDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Abraham Coronel
 */
public class CartaMapper implements ICartaMapper {

    @Override
    public CartaDTO toDTO(Carta carta) {
        if (carta == null) {
            return null;
        }

        CartaDTO dto = new CartaDTO();

        if (carta instanceof CartaNumero cartaNum) {
            dto.setTipoCarta(TipoCartaDTO.NUMERO);
            dto.setNumero(cartaNum.getNumero());
            dto.setColor(cartaNum.getColor());

        } else if (carta instanceof CartaAccion cartaAcc) {
            dto.setTipoCarta(TipoCartaDTO.ACCION);
            dto.setAcciones(cartaAcc.getTipoAccion());
            dto.setColor(cartaAcc.getColor());

        } else if (carta instanceof CartaComodin cartaCom) {
            dto.setTipoCarta(TipoCartaDTO.COMODIN);
            dto.setComodines(cartaCom.getTipoComodin());
            dto.setColor(cartaCom.getColorElegido());
        }

        return dto;
    }

    @Override
    public List<CartaDTO> toDTOList(List<Carta> cartas) {
        if (cartas == null || cartas.isEmpty()) {
            return new ArrayList<>();
        }

        return cartas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
