/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.AccionesPosibles;
import java.util.Random;

/**
 *
 * @author Abraham Coronel
 */
public class Ruleta {

    private AccionesPosibles[] acciones;
    private Random random;

    public Ruleta() {
        this.acciones = AccionesPosibles.values();
        this.random = new Random();
    }

    //De manera random selecciona una casilla
    public AccionesPosibles girar() {
        int index = random.nextInt(acciones.length);
        return acciones[index];
    }

    public AccionesPosibles[] getAcciones() {
        return acciones;
    }

    public void setAcciones(AccionesPosibles[] acciones) {
        this.acciones = acciones;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

}
