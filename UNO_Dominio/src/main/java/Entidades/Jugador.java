/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Excepciones.ValidarManoException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class Jugador {

    private String usuario;
    private String avatar; //Url imgaen
    private List<Carta> mano;
    private int puntos;
    private boolean dijoUno;

    public Jugador() {
    }

    public Jugador(String usuario, String avatar) {
        this.usuario = usuario;
        this.avatar = avatar;
        this.mano = new ArrayList<>();
        this.puntos = 0;
        this.dijoUno = false;
    }

    //Como dice el nombre roba una carta al jugador
    public void robarCarta(Carta carta) {
        this.mano.add(carta);
        this.dijoUno = false;
    }

    //Busca la carta en la mano, la elimina y la devuelve para ponerla en el descarte
    public Carta jugarCarta(Carta cartaAJugar) throws ValidarManoException {
        if (mano.remove(cartaAJugar)) {
            return cartaAJugar;
        }
        throw new ValidarManoException("El jugador no tiene esa carta en la mano");
    }

    //Para cuando queda una carta nomas
    public void gritarUno() {
        if (mano.size() == 1) {
            this.dijoUno = true;
        }
    }

    //Calcula los puntos
    public int calcularPuntosRestantes() {
        return mano.stream().mapToInt(Carta::getPuntuacion).sum();
    }

    //Nose si este iria tengo mis dudas de como lo manejaremos.
    public boolean esVulnerableAlCastigo() {
        return mano.size() == 1 && !dijoUno;
    }

    //Solo comprueba si el jugador no tiene cartas y gano
    public boolean haGanado() {
        return mano.isEmpty();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Carta> getMano() {
        return new ArrayList<>();
    }

    public void setMano(List<Carta> mano) {
        this.mano = mano;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isDijoUno() {
        return dijoUno;
    }

    public void setDijoUno(boolean dijoUno) {
        this.dijoUno = dijoUno;
    }

}
