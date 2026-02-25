/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Entidades.Carta;
import Entidades.Jugador;
import Entidades.Mazo;
import Entidades.Partida;
import Enums.AccionesPosibles;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import Mappers.CartaMapper;
import Mappers.JugadorMapper;
import dtos.CartaDTO;
import dtos.JugadorResumenDTO;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private Partida partida;
    private TipoColor colorActual;
    private final CartaMapper cartaMapper;
    private final JugadorMapper jugadorMapper;

    public SubDominioConcreto() {
        this.cartaMapper = new CartaMapper();
        this.jugadorMapper = new JugadorMapper();
    }

    @Override
    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        List<Jugador> jugadores = jugadorMapper.toEntityList(jugadoresDTO);

        Stack<Carta> cartasIniciales = generarMazoCompleto();
        Mazo mazo = new Mazo(cartasIniciales);

        this.partida = new Partida(jugadores, mazo);
        this.partida.iniciarPartida();
    }

    @Override
    public void jugarCarta(JugadorResumenDTO jugadorDTO, CartaDTO cartaAJugarDTO) throws ValidarManoException, ValidarTurnoException, JugadaValidaException {
        Jugador jugador = jugadorMapper.toEntity(jugadorDTO);
        Carta cartaAJugar = cartaMapper.toEntity(cartaAJugarDTO);

        partida.jugarCarta(jugador, cartaAJugar);
    }

    @Override
    public void robarCarta(JugadorResumenDTO jugadorDTO) throws MazoVacioException {
        Jugador jugador = jugadorMapper.toEntity(jugadorDTO);
        partida.robarCarta(jugador);
    }

    @Override
    public void elegirColorComodin(TipoColor nuevoColor) {
        this.partida.setColorActual(colorActual);
    }

    @Override
    public AccionesPosibles tirarRuleta() {
        return partida.getRuleta().girar();
    }

    @Override
    public void gritarUno(JugadorResumenDTO jugadorDTO) {
        Jugador jugador = jugadorMapper.toEntity(jugadorDTO);
        partida.gritarUno(jugador);
    }

    @Override
    public void terminarTurno() {
        partida.avanzarTurno();
    }

    @Override
    public JugadorResumenDTO obtenerJugadorActual() {
        Jugador jugadorActual = partida.getJugadorActual();
        JugadorResumenDTO dto = jugadorMapper.toDTO(jugadorActual);
        dto.setEnTurno(true);
        return dto;
    }

    @Override
    public CartaDTO obtenerCartaEnTope() {
        Carta tope = partida.getDescarte().getTope();
        return cartaMapper.toDTO(tope);
    }

    @Override
    public TipoColor obtenerColorActual() {
        return this.partida.getColorActual();
    }

    private Stack<Carta> generarMazoCompleto() {
        Stack<Carta> mazoNuevo = new Stack<>();
        return mazoNuevo;
    }

    @Override
    public List<CartaDTO> obtenerManoJugadorActual() {
        Jugador jugadorActual = partida.getJugadorActual();

        List<Carta> manoEntidad = jugadorActual.getMano();

        return cartaMapper.toDTOList(manoEntidad);
    }

}
