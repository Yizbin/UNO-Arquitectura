/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Abraham Coronel
 */
public abstract class Carta {

    protected int puntuacion;
    protected boolean esSpin;

    public Carta() {
    }

    public Carta(int puntuacion, boolean esSpin) {
        this.puntuacion = puntuacion;
        this.esSpin = esSpin;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public boolean isEsSpin() {
        return esSpin;
    }

    public void setEsSpin(boolean esSpin) {
        this.esSpin = esSpin;
    }

}
