/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Excepciones.MazoVacioException;
import factorys.MazoFactory;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author Abraham Coronel
 */
public class Mazo {

    private Stack<Carta> cartas;

    public Mazo() {
        this.cartas = new Stack<>();
    }

    public Mazo(Stack<Carta> cartas) {
        this.cartas = cartas;
        mezclar();
    }

    public static Mazo crear() {
        return MazoFactory.crear();
    }

    //Mezcla la pila de manera aleatoria
    public final void mezclar() {
        Collections.shuffle(this.cartas);
    }

    //Saca y devuelve la carta en el tope para ser robada
    public Carta sacarCarta() throws MazoVacioException {
        if (cartas.isEmpty()) {
            throw new MazoVacioException("El mazo esta vacio, necesita ser rellenado");
        }
        return cartas.pop();
    }

    //Recibe todas las cartas del descarte menos la del tope
    public void rellenar(Stack<Carta> cartasDescarte) {
        this.cartas.addAll(cartasDescarte);
        mezclar();
    }

    //Verifica si no quedan cartas xd
    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    public Stack<Carta> getCartas() {
        return (Stack<Carta>) cartas.clone();
    }

    public void setCartas(Stack<Carta> cartas) {
        this.cartas = (cartas == null) ? new Stack<>() : (Stack<Carta>) cartas.clone();
    }

}
