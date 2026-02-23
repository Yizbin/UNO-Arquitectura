/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachade;

import Entidades.Carta;
import Entidades.Jugador;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import Interfaces.ICartaMapper;
import Interfaces.IJugadorMapper;
import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import dtos.CartaDTO;
import dtos.JugadorResumenDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Abraham Coronel
 */
public class JuegoAdapter implements IJuegoAdapter {

    private final ISubDominio subDominio;
    private final ICartaMapper cartaMapper;
    private final IJugadorMapper jugadorMapper;

    public JuegoAdapter(ICartaMapper cartaMapper, IJugadorMapper jugadorMapper) {
        this.subDominio = new SubDominioConcreto();
        this.cartaMapper = cartaMapper;
        this.jugadorMapper = jugadorMapper;
    }

    @Override
    public void iniciarPartida(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        List<Jugador> jugadores = jugadorMapper.toEntityList(jugadoresDTO);

        subDominio.prepararJuego(jugadores);
    }

    @Override
    public void robarCarta() throws Exception {
        Jugador jugador = subDominio.obtenerJugadorActual();

        subDominio.robarCarta(jugador);
    }

    @Override
    public List<CartaDTO> getManoJugadorActual() {
        Jugador jugador = subDominio.obtenerJugadorActual();

        if (jugador != null && jugador.getMano() != null) {
            return cartaMapper.toDTOList(jugador.getMano());
        }
        return new ArrayList<>();
    }

    @Override
    public void jugarCarta(CartaDTO cartaDTO) throws ValidarManoException, ValidarTurnoException, JugadaValidaException {
        Jugador jugadorActual = subDominio.obtenerJugadorActual();
        Carta cartaReal = null;

        for (Carta c : jugadorActual.getMano()) {
            CartaDTO dtoMapeado = cartaMapper.toDTO(c);

            if (dtoMapeado.getTipoCarta() == cartaDTO.getTipoCarta()
                    && dtoMapeado.getColor() == cartaDTO.getColor()
                    && Objects.equals(dtoMapeado.getNumero(), cartaDTO.getNumero())
                    && dtoMapeado.getAcciones() == cartaDTO.getAcciones()
                    && dtoMapeado.getComodines() == cartaDTO.getComodines()) {
                cartaReal = c;
                break;
            }
        }

        if (cartaReal == null) {
            throw new ValidarManoException("La carta seleccionada no está en tu mano.");
        }
        subDominio.jugarCarta(jugadorActual, cartaReal);
    }

    @Override
    public CartaDTO getCartaEnTope() {
        Carta tope = subDominio.obtenerCartaEnTope();
        return cartaMapper.toDTO(tope);
    }

    @Override
    public TipoColor getColorActual() {
        return subDominio.obtenerColorActual();
    }

}
