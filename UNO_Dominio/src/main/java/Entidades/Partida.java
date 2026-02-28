/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import java.util.List;
import java.util.Objects;

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
    private boolean sentidoHorario;
    private TipoColor colorActual;
    private boolean esperandoColor;

    public Partida(List<Jugador> jugadores, Mazo mazo) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        this.descarte = new Descarte();
        this.ruleta = new Ruleta();
        this.indiceTurnoActual = 0;
        this.sentidoHorario = true;
        this.colorActual = TipoColor.NINGUNO;
        this.esperandoColor = false;
    }

    //Crea la partida
    public void iniciarPartida() throws MazoVacioException {
        for (int i = 0; i < 7; i++) {
            for (Jugador jugador : jugadores) {
                jugador.robarCarta(mazo.sacarCarta());
            }
        }
        descarte.apilarCarta(mazo.sacarCarta());
        this.colorActual = TipoColor.NINGUNO;
    }

    public void jugarCarta(Jugador jugador, Carta cartaAJugar) throws ValidarManoException, ValidarTurnoException, JugadaValidaException {
        if (!jugador.equals(this.getJugadorActual())) {
            throw new ValidarTurnoException("No es el turno de este jugador.");
        }

        Carta cartaEnTope = this.descarte.getTope();
        if (!cartaAJugar.esJugableSobre(cartaEnTope, this.colorActual)) {
            throw new JugadaValidaException("Jugada invalida. La carta no coincide en color o simbolo.");
        }

        Jugador jugadorReal = this.getJugadorActual();
        Carta cartaJugada = jugadorReal.jugarCarta(cartaAJugar);
        this.descarte.apilarCarta(cartaJugada);

        if (cartaJugada instanceof CartaNumero cartaNumero) {
            this.colorActual = cartaNumero.getColor();
        } else if (cartaJugada instanceof CartaAccion cartaAccion) {
            this.colorActual = cartaAccion.getColor();
        } else if (cartaJugada instanceof CartaComodin) {
            this.esperandoColor = true;
        }
    }

    public void robarCarta(Jugador jugador) throws MazoVacioException {
        if (!jugador.equals(this.getJugadorActual())) {
            throw new IllegalStateException("No es el turno de este jugador.");
        }
        Carta robada = mazo.sacarCarta();
        this.getJugadorActual().robarCarta(robada);
    }

    public void gritarUno(Jugador jugador) {
        for (Jugador j : jugadores) {
            if (j.equals(jugador)) {
                j.gritarUno();
                break;
            }
        }
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

    public TipoColor getColorActual() {
        return colorActual;
    }

    public void setColorActual(TipoColor colorActual) {
        this.colorActual = colorActual;
        this.esperandoColor = false;
    }

    public boolean isEsperandoColor() {
        return esperandoColor;
    }

    public void setEsperandoColor(boolean esperandoColor) {
        this.esperandoColor = esperandoColor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Partida other = (Partida) obj;
        return Objects.equals(this.jugadores, other.jugadores);
    }
    
    

}
