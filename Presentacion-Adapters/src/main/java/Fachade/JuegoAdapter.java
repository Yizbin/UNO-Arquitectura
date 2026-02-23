/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachade;

import Entidades.Jugador;
import Excepciones.MazoVacioException;
import Interfaces.ICartaMapper;
import Interfaces.IJugadorMapper;
import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import dtos.CartaDTO;
import dtos.JugadorResumenDTO;
import java.util.ArrayList;
import java.util.List;

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

}
