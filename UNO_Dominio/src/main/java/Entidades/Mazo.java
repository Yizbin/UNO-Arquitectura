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
public class Mazo {

    private Stack<Carta> cartas;

    public Mazo() {
    }

    public Mazo(Stack<Carta> cartas) {
        this.cartas = cartas;
    }

    public Stack<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(Stack<Carta> cartas) {
        this.cartas = cartas;
    }

}
