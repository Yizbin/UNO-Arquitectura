/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.TipoColor;

/**
 *
 * @author Abraham Coronel
 */
public class CartaNumero extends Carta {

    private int numero;
    private TipoColor color;

    public CartaNumero() {
    }

    public CartaNumero(int numero, TipoColor color, boolean esSpin) {
        super(numero, esSpin);
        this.numero = numero;
        this.color = color;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public TipoColor getColor() {
        return color;
    }

    public void setColor(TipoColor color) {
        this.color = color;
    }

    @Override
    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public boolean isEsSpin() {
        return esSpin;
    }

    @Override
    public void setEsSpin(boolean esSpin) {
        this.esSpin = esSpin;
    }

    @Override
    public boolean esJugableSobre(Carta cartaEnTope, TipoColor colorEnJuego) {
        if (this.color == colorEnJuego) {
            return true;
        }

        if (cartaEnTope instanceof CartaNumero topeNumerico) {
            return this.numero == topeNumerico.getNumero();
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CartaNumero that = (CartaNumero) obj;
        return numero == that.numero && color == that.color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(numero, color);
    }

}
