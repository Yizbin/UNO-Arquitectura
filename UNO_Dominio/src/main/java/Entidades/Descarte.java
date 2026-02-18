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
    }

    public Descarte(Carta tope, Stack<Carta> historial) {
        this.tope = tope;
        this.historial = historial;
    }

    public Carta getTope() {
        return tope;
    }

    public void setTope(Carta tope) {
        this.tope = tope;
    }

    public Stack<Carta> getHistorial() {
        return historial;
    }

    public void setHistorial(Stack<Carta> historial) {
        this.historial = historial;
    }
    
    

}
