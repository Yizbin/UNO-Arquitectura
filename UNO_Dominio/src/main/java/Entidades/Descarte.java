/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.Stack;

/**
 *
 * @author Abraham Coronel
 */
public class Descarte {

    private Carta tope;
    private Stack<Carta> historial;

    public Descarte() {
        this.historial = new Stack<>();
    }

    //Recibe la carta jugada y pasa la carta anterior al historial y agrega la nueva encima
    public void apilarCarta(Carta carta) {
        if (this.tope != null) {
            this.historial.push(this.tope);
        }
        this.tope = carta;
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
    }

    public Stack<Carta> getHistorial() {
        return new Stack<>();
    }

    public void setHistorial(Stack<Carta> historial) {
        this.historial = historial;
    }

}
