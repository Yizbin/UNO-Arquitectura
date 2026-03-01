/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Entidades.Carta;
import Entidades.CartaComodin;
import Entidades.CartaNumero;
import Entidades.Jugador;
import Entidades.Mazo;
import Entidades.Partida;
import Enums.AccionesPosibles;
import Enums.Comodines;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import Mappers.CartaMapper;
import Mappers.JugadorMapper;
import java.util.ArrayList;
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
    public void elegirColorComodin(TipoColor nuevoColor) {
        this.partida.setColorActual(nuevoColor);
    }

    @Override
    public AccionesPosibles tirarRuleta() {
        return partida.getRuleta().girar();
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

        for (int i = 0; i < 8; i++) {
            mazoNuevo.push(new CartaNumero(i + 1, TipoColor.ROJO, false));
        }
        mazoNuevo.push(new CartaNumero(9, TipoColor.ROJO, true));
        for (int i = 0; i < 8; i++) {
            mazoNuevo.push(new CartaNumero(i + 1, TipoColor.AMARILLO, false));
        }
        mazoNuevo.push(new CartaNumero(9, TipoColor.AMARILLO, true));
        for (int i = 0; i < 8; i++) {
            mazoNuevo.push(new CartaNumero(i + 1, TipoColor.AZUL, false));
        }
        mazoNuevo.push(new CartaNumero(9, TipoColor.AZUL, true));
        for (int i = 0; i < 8; i++) {
            mazoNuevo.push(new CartaNumero(i + 1, TipoColor.VERDE, false));
        }
        mazoNuevo.push(new CartaNumero(9, TipoColor.VERDE, true));

        mazoNuevo.push(new CartaComodin(Comodines.CAMBIO_COLOR));
        mazoNuevo.push(new CartaComodin(Comodines.CAMBIO_COLOR));
        mazoNuevo.push(new CartaComodin(Comodines.TOMA_CUATRO));
        mazoNuevo.push(new CartaComodin(Comodines.TOMA_CUATRO));

        return mazoNuevo;
    }

    @Override
    public List<CartaDTO> obtenerManoJugador(int idJugador) {
        for (Jugador jugador : partida.getJugadores()) {
            if (jugador.getId() == idJugador) {
                return cartaMapper.toDTOList(jugador.getMano());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public EstadoPartidaDTO obtenerEstadoPartida() {
        EstadoPartidaDTO estadoDTO = new EstadoPartidaDTO();
        if (this.partida != null) {
            estadoDTO.setEsperandoColor(this.partida.isEsperandoColor());
        }
        return estadoDTO;
    }

    private Jugador obtenerJugadorPorId(int idJugador) {
        for (Jugador j : partida.getJugadores()) {
            if (j.getId() == idJugador) {
                return j;
            }
        }
        throw new IllegalArgumentException("Jugador no encontrado con ID: " + idJugador);
    }

    @Override
    public void jugarCarta(int idJugador, CartaDTO cartaAJugarDTO) throws ValidarManoException, ValidarTurnoException, JugadaValidaException {
        Jugador jugador = obtenerJugadorPorId(idJugador); 
        Carta cartaAJugar = cartaMapper.toEntity(cartaAJugarDTO);

        partida.jugarCarta(jugador, cartaAJugar);

        if (!partida.isEsperandoColor()) {
            partida.avanzarTurno();
        }
    }

    @Override
    public void robarCarta(int idJugador) throws MazoVacioException {
        Jugador jugador = obtenerJugadorPorId(idJugador);
        partida.robarCarta(jugador);
        partida.avanzarTurno();
    }

    @Override
    public void gritarUno(int idJugador) {
        Jugador jugador = obtenerJugadorPorId(idJugador);
        partida.gritarUno(jugador);
    }

}
