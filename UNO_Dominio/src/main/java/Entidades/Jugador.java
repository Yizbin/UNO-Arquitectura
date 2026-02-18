/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

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

    public Jugador() {
    }

    public Jugador(String usuario, String avatar, List<Carta> mano, int puntos) {
        this.usuario = usuario;
        this.avatar = avatar;
        this.mano = mano;
        this.puntos = puntos;
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
        return mano;
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
    
    

}
