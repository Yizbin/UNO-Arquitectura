/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.Acciones;
import Enums.TipoColor;

/**
 *
 * @author Abraham Coronel
 */
public class CartaAccion extends Carta {

    private Acciones tipoAccion;
    private TipoColor color;

    public CartaAccion() {
    }

    public CartaAccion(Acciones tipoAccion, TipoColor color) {
        super(20, false);
        this.tipoAccion = tipoAccion;
        this.color = color;
    }

    public Acciones getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(Acciones tipoAccion) {
        this.tipoAccion = tipoAccion;
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
        if (cartaEnTope instanceof CartaAccion topeAccion) {
            return this.tipoAccion == topeAccion.getTipoAccion();
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
        CartaAccion that = (CartaAccion) obj;
        return tipoAccion == that.tipoAccion && color == that.color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(tipoAccion, color);
    }

}
