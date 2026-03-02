/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
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
import factorys.MazoFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private Partida partida;
    private TipoColor colorActual;
    private final CartaMapper cartaMapper;
    private final JugadorMapper jugadorMapper;
    private final MazoFactory mazoFactory;

    public SubDominioConcreto() {
        this.cartaMapper = new CartaMapper();
        this.jugadorMapper = new JugadorMapper();
        this.mazoFactory = new MazoFactory();
    }

    @Override
    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        List<Jugador> jugadores = jugadorMapper.toEntityList(jugadoresDTO);

        Mazo mazo = generarMazoCompleto();

        this.partida = new Partida(jugadores, mazo);
        this.partida.iniciarPartida();
    }

    @Override
    public void elegirColorComodin(TipoColor nuevoColor) {
        this.partida.setColorActual(nuevoColor);
        this.partida.avanzarTurno();
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

    private Mazo generarMazoCompleto() {
         return mazoFactory.crear();
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
            if (this.partida.getJugadorActual() != null) {
                estadoDTO.setIdJugadorEnTurno(this.partida.getJugadorActual().getId());
            }
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
    }

    @Override
    public void gritarUno(int idJugador) {
        Jugador jugador = obtenerJugadorPorId(idJugador);
        partida.gritarUno(jugador);
    }

}
