/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.TipoColor;
import java.util.Stack;

/**
 *
 * @author Abraham Coronel
 */
public class Descarte {

    private Carta tope;
    private TipoColor colorActual;
    private Stack<Carta> historial;

    public Descarte() {
        this.colorActual = TipoColor.NINGUNO;
        this.historial = new Stack<>();
    }

    public boolean puedeApilar(Carta carta) {
        return carta != null && carta.esJugableSobre(tope, colorActual);
    }

    //Recibe la carta jugada y pasa la carta anterior al historial y agrega la nueva encima
    public void apilarCarta(Carta carta) {
        if (this.tope != null) {
            this.historial.push(this.tope);
        }
        this.tope = carta;
        actualizarColorActual(carta);
    }

    public void elegirColor(TipoColor color) {
        if (color == null || color == TipoColor.NINGUNO) {
            throw new IllegalArgumentException("El color elegido para el comodin no es valido.");
        }

        if (!(tope instanceof CartaComodin comodin)) {
            throw new IllegalStateException("Solo se puede elegir color cuando el tope del descarte es un comodin.");
        }

        this.colorActual = color;
        comodin.setColorElegido(color);
    }

    //Devuelve todas las cartas menos el tope para rellenar el mazo principal
    public Stack<Carta> vaciarParaRellenarMazo() {
        Stack<Carta> cartasDevueltas = this.historial;
        this.historial = new Stack<>();
        return cartasDevueltas;
    }

    public Carta getTope() {
        return tope;
    }

    public void setTope(Carta tope) {
        this.tope = tope;
        actualizarColorActual(tope);
    }

    public TipoColor getColorActual() {
        return colorActual;
    }

    public Stack<Carta> getHistorial() {
        return (Stack<Carta>) historial.clone();
    }

    public void setHistorial(Stack<Carta> historial) {
        this.historial = (historial == null) ? new Stack<>() : (Stack<Carta>) historial.clone();
    }

    private void actualizarColorActual(Carta carta) {
        if (carta instanceof CartaNumero cartaNumero) {
            this.colorActual = cartaNumero.getColor();
        } else if (carta instanceof CartaAccion cartaAccion) {
            this.colorActual = cartaAccion.getColor();
        } else if (carta instanceof CartaComodin cartaComodin && cartaComodin.getColorElegido() != null) {
            this.colorActual = cartaComodin.getColorElegido();
        }
    }

}
