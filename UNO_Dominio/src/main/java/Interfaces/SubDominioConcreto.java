/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Entidades.Carta;
import Entidades.CartaAccion;
import Entidades.CartaComodin;
import Entidades.CartaNumero;
import Entidades.Jugador;
import Entidades.Mazo;
import Entidades.Partida;
import Enums.AccionesPosibles;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import dtos.CartaDTO;
import dtos.TipoCartaDTO;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private Partida partida;
    private TipoColor colorActual;

    public SubDominioConcreto() {
    }

    @Override
    public void prepararJuego(List<Jugador> jugadores) throws MazoVacioException {
        Stack<Carta> cartasIniciales = generarMazoCompleto();
        Mazo mazo = new Mazo(cartasIniciales);

        this.partida = new Partida(jugadores, mazo);
        this.partida.iniciarPartida();

        this.colorActual = TipoColor.NINGUNO;
    }

    @Override
    public void jugarCarta(Jugador jugador, Carta cartaAJugar) throws ValidarManoException, ValidarTurnoException, JugadaValidaException {
        if (!jugador.equals(partida.getJugadorActual())) {
            throw new ValidarTurnoException("No es el turno de este jugador.");
        }

        Carta cartaEnTope = partida.getDescarte().getTope();
        if (!cartaAJugar.esJugableSobre(cartaEnTope, this.colorActual)) {
            throw new JugadaValidaException("Jugada invalida. La carta no coincide en color o simbolo.");
        }

        Carta cartaJugada = jugador.jugarCarta(cartaAJugar);
        partida.getDescarte().apilarCarta(cartaJugada);
    }

    @Override
    public void robarCarta(Jugador jugador) throws MazoVacioException {
        if (!jugador.equals(partida.getJugadorActual())) {
            throw new IllegalStateException("No es el turno de este jugador.");
        }

        Carta robada = partida.getMazo().sacarCarta();
        jugador.robarCarta(robada);
    }

    @Override
    public void elegirColorComodin(TipoColor nuevoColor) {
        this.colorActual = nuevoColor;
    }

    @Override
    public AccionesPosibles tirarRuleta() {
        return partida.getRuleta().girar();
    }

    @Override
    public void gritarUno(Jugador jugador) {
        jugador.gritarUno();
    }

    @Override
    public void terminarTurno() {
        partida.avanzarTurno();
    }

    @Override
    public Jugador obtenerJugadorActual() {
        return partida.getJugadorActual();
    }

    @Override
    public Carta obtenerCartaEnTope() {
        return partida.getDescarte().getTope();
    }

    @Override
    public TipoColor obtenerColorActual() {
        return this.colorActual;
    }

    private Stack<Carta> generarMazoCompleto() {
        Stack<Carta> mazoNuevo = new Stack<>();
        return mazoNuevo;
    }
    
    

}
