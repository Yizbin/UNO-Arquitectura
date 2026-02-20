/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Carta;
import Entidades.Jugador;
import Enums.AccionesPosibles;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface ISubDominio {

    public void prepararJuego(List<Jugador> jugadores) throws MazoVacioException;

    //Intenta jugar una carta de la mano del jugador actual hacia el descarte
    public void jugarCarta(Jugador jugador, Carta cartaAJugar) throws ValidarManoException, ValidarTurnoException, JugadaValidaException;

    //Hace que el jugador actual robe una carta del mazo
    public void robarCarta(Jugador jugador) throws MazoVacioException;

    //Define el nuevo color en juego cuando un jugador tira un comodín
    public void elegirColorComodin(TipoColor nuevoColor);

    //Gira la ruleta y devuelve la acción que debe realizarse (cuando se tira una carta Spin)
    public AccionesPosibles tirarRuleta();

    //Permite a un jugador protegerse gritando "¡UNO!"
    public void gritarUno(Jugador jugador);

    //Pasa el turno al siguiente jugador según el sentido actual del juego
    public void terminarTurno();

    // Devuelve quién es el jugador que debe tirar en este momento
    public Jugador obtenerJugadorActual();

    // Devuelve la carta que está visible en el tope del descarte
    public Carta obtenerCartaEnTope();

    // Devuelve el color que está activo en el juego (útil si hay un comodín en el tope)
    public TipoColor obtenerColorActual();
}
