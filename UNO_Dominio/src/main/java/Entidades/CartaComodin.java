/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.Comodines;
import Enums.TipoColor;

/**
 *
 * @author Abraham Coronel
 */
public class CartaComodin extends Carta {

    private Comodines tipoComodin;
    private TipoColor colorElegido;

    public CartaComodin() {
    }

    public CartaComodin(Comodines tipoComodin) {
        super(50, false);
        this.tipoComodin = tipoComodin;
    }

    public Comodines getTipoComodin() {
        return tipoComodin;
    }

    public void setTipoComodin(Comodines tipoComodin) {
        this.tipoComodin = tipoComodin;
    }

    public TipoColor getColorElegido() {
        return colorElegido;
    }

    public void setColorElegido(TipoColor colorElegido) {
        this.colorElegido = colorElegido;
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
        return true;
    }

}
