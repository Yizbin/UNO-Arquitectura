/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Entidades.Carta;
import Entidades.CartaAccion;
import Entidades.CartaComodin;
import Entidades.CartaNumero;
import dtos.CartaDTO;
import dtos.TipoCartaDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Abraham Coronel
 */
public class CartaMapper {

    public CartaDTO toDTO(Carta carta) {
        if (carta == null) {
            return null;
        }

        CartaDTO dto = new CartaDTO();

        switch (carta) {
            case CartaNumero cartaNum -> {
                dto.setTipoCarta(TipoCartaDTO.NUMERO);
                dto.setNumero(cartaNum.getNumero());
                dto.setColor(cartaNum.getColor());
                
            }
            case CartaAccion cartaAcc -> {
                dto.setTipoCarta(TipoCartaDTO.ACCION);
                dto.setAcciones(cartaAcc.getTipoAccion());
                dto.setColor(cartaAcc.getColor());
                
            }
            case CartaComodin cartaCom -> {
                dto.setTipoCarta(TipoCartaDTO.COMODIN);
                dto.setComodines(cartaCom.getTipoComodin());
                dto.setColor(cartaCom.getColorElegido());
            }
            default -> {
            }
        }

        return dto;
    }

    public List<CartaDTO> toDTOList(List<Carta> cartas) {
        if (cartas == null || cartas.isEmpty()) {
            return new ArrayList<>();
        }

        return cartas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Carta toEntity(CartaDTO dto) {
        if (dto == null) {
            return null;
        }

        switch (dto.getTipoCarta()) {
            case NUMERO -> {
                return new CartaNumero(dto.getNumero(), dto.getColor(), false);
            }

            case ACCION -> {
                return new CartaAccion(dto.getAcciones(), dto.getColor());
            }

            case COMODIN -> {
                CartaComodin comodin = new CartaComodin(dto.getComodines());

                if (dto.getColor() != null) {
                    comodin.setColorElegido(dto.getColor());
                }
                return comodin;
            }

        }
        return null;
    }

    public List<Carta> toEntityList(List<CartaDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
