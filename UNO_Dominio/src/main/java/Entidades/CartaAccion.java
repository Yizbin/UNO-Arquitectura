/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entidades;

import Enums.AccionesPosibles;
import Enums.TipoColor;

/**
 *
 * @author Abraham Coronel
 */
public class CartaAccion extends Carta {
    
    private AccionesPosibles tipoAccion;
    private TipoColor color;

    public CartaAccion() {
    }

    public CartaAccion(AccionesPosibles tipoAccion, TipoColor color) {
        this.tipoAccion = tipoAccion;
        this.color = color;
    }

    public AccionesPosibles getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(AccionesPosibles tipoAccion) {
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
    
    

}
