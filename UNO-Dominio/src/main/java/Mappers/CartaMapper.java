/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.CartaDTO;
import DTOs.TipoCartaDTO;
import Entidades.Carta;
import Entidades.CartaAccion;
import Entidades.CartaComodin;
import Entidades.CartaNumero;
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

        if (carta instanceof CartaNumero numero) {
            return new CartaDTO(TipoCartaDTO.NUMERO,
                    numero.getColor(), numero.getNumero(),
                    null, null, null); //CAMBIAR LA RUTA DESPUES
        }
        if (carta instanceof CartaAccion accion) {
            return new CartaDTO(TipoCartaDTO.ACCION,
                    accion.getColor(), null,
                    accion.getTipoAccion(), null, null); //CAMBIAR LA RUTA DESPUES
        }
        if (carta instanceof CartaComodin comodin) {
            return new CartaDTO(TipoCartaDTO.COMODIN, comodin.getColorElegido(),
                    null, null, comodin.getTipoComodin(),
                    null); //CAMBIAR LA RUTA DESPUES
        }

        throw new IllegalArgumentException("EL TIPO DE CARTA NO FUE IDENTIFICADO");
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
