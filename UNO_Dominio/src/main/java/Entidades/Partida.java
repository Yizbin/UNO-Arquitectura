/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Excepciones.MazoVacioException;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class Partida {

    private List<Jugador> jugadores;
    private Mazo mazo;
    private Descarte descarte;
    private Ruleta ruleta;
    private int indiceTurnoActual;
    private boolean sentidoHorario; //Con esto se determina que jugador sigue en el turno

    public Partida(List<Jugador> jugadores, Mazo mazo) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        this.descarte = new Descarte();
        this.ruleta = new Ruleta();
        this.indiceTurnoActual = 0;
        this.sentidoHorario = true;
    }

    //Crea la partida
    public void iniciarPartida() throws MazoVacioException {
        for (int i = 0; i < 7; i++) {
            for (Jugador jugador : jugadores) {
                jugador.robarCarta(mazo.sacarCarta());
            }
        }
        descarte.apilarCarta(mazo.sacarCarta());
    }

    //Retorna el jugador con el turno actual
    public Jugador getJugadorActual() {
        return jugadores.get(indiceTurnoActual);
    }

    //Calcula el siguente turno, vi un ejemplo y creo que es asi xd
    public void avanzarTurno() {
        if (sentidoHorario) {
            indiceTurnoActual = (indiceTurnoActual + 1) % jugadores.size();
        } else {
            indiceTurnoActual = (indiceTurnoActual - 1 + jugadores.size()) % jugadores.size();
        }
    }

    //Esto esta por la carta que cambia el sentido de los turnos
    public void invertirSentido() {
        this.sentidoHorario = !this.sentidoHorario;
    }

    //Este metodo esta por verse porque depende de como lo manejemos, pero basicamente lo castiga si no grita uno
    public void penalizarJugador(Jugador jugador) throws MazoVacioException {
        if (jugador.esVulnerableAlCastigo()) {
            jugador.robarCarta(obtenerCartaDelMazo());
            jugador.robarCarta(obtenerCartaDelMazo());
        }
    }

    //Metodos Privados
    private Carta obtenerCartaDelMazo() throws MazoVacioException {
        if (mazo.estaVacio()) {
            mazo.rellenar(descarte.vaciarParaRellenarMazo());
        }
        return mazo.sacarCarta();
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public Descarte getDescarte() {
        return descarte;
    }

    public void setDescarte(Descarte descarte) {
        this.descarte = descarte;
    }

    public Ruleta getRuleta() {
        return ruleta;
    }

    public void setRuleta(Ruleta ruleta) {
        this.ruleta = ruleta;
    }

    public int getIndiceTurnoActual() {
        return indiceTurnoActual;
    }

    public void setIndiceTurnoActual(int indiceTurnoActual) {
        this.indiceTurnoActual = indiceTurnoActual;
    }

    public boolean isSentidoHorario() {
        return sentidoHorario;
    }

    public void setSentidoHorario(boolean sentidoHorario) {
        this.sentidoHorario = sentidoHorario;
    }

}
