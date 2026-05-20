package Entidades;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020

import Excepciones.ValidarTurnoException;
import java.util.List;


public class Turno {
    
    /**
     * Indice del jugador en turno
     */
    private int indiceTurnoActual;
    /**
     * Sentido horario de los turnos (izq false der true)
     */
    private boolean sentidoHorario;
    /**
     * Lista de jugadores en juego
     */
    private List<Jugador> jugadores;

    /**
     * 
     */
    public Turno() {
        this.indiceTurnoActual = 0;
        this.sentidoHorario = true;
        this.jugadores = List.of();
    }
    
    /**
     * 
     * @param indiceTurnoActual
     * @param jugadores 
     */
    public Turno(int indiceTurnoActual, List<Jugador> jugadores) {
        this.sentidoHorario = true;
        setJugadores(jugadores);
        setIndiceTurnoActual(indiceTurnoActual);
    }

    /**
     * Obtener el jugador actual
     * @return 
     */
    public Jugador obtenerJugadorActual(){
        validarJugadoresDisponibles();
        return jugadores.get(indiceTurnoActual);
    }
    
    /**
     * 
     */
    public void avanzar(){
        validarJugadoresDisponibles();

        if (sentidoHorario) {
            indiceTurnoActual = (indiceTurnoActual + 1) % jugadores.size();
        } else {
            indiceTurnoActual = (indiceTurnoActual - 1 + jugadores.size()) % jugadores.size();
        }
    }
    
    /**
     * 
     */
    public void saltar(){
        avanzar();
        avanzar();
    }

    public void aplicarReversa() {
        invertirSentido();
        avanzar();
    }

    public void aplicarSalto() {
        saltar();
    }

    public Jugador avanzarYObtenerJugadorActual() {
        avanzar();
        return obtenerJugadorActual();
    }

    /**
     * 
     */
    public void invertirSentido(){
        this.sentidoHorario = !sentidoHorario;
    }
    
    public boolean esTurnoDe(Jugador jugador){
        return jugador != null && jugador.equals(obtenerJugadorActual());
    }

    public void validarTurno(Jugador jugador) throws ValidarTurnoException {
        if (!esTurnoDe(jugador)) {
            throw new ValidarTurnoException("No es el turno de este jugador.");
        }
    }
    
    public void setIndiceTurnoActual(int indiceTurnoActual) {
        if (jugadores == null || jugadores.isEmpty()) {
            this.indiceTurnoActual = 0;
            return;
        }

        this.indiceTurnoActual = Math.floorMod(indiceTurnoActual, jugadores.size());
    }

    public void setSentidoHorario(boolean sentidoHorario) {
        this.sentidoHorario = sentidoHorario;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores == null ? List.of() : List.copyOf(jugadores);
        setIndiceTurnoActual(indiceTurnoActual);
    }

    public int getIndiceTurnoActual() {
        return indiceTurnoActual;
    }

    public boolean isSentidoHorario() {
        return sentidoHorario;
    }

    public List<Jugador> getJugadores() {
        return List.copyOf(jugadores);
    }

    private void validarJugadoresDisponibles() {
        if (jugadores == null || jugadores.isEmpty()) {
            throw new IllegalStateException("No hay jugadores disponibles para gestionar el turno.");
        }
    }
    
}
