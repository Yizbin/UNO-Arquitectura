/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.TipoAccionPartida;
import Enums.TipoColor;

/**
 *
 * @author Abraham Coronel
 */
public class PeticionJugadaDTO {

    private TipoAccionPartida accion;
    private int idJugador;
    private CartaDTO cartaAJugar;
    private TipoColor nuevoColor;

    public PeticionJugadaDTO() {
    }

    public PeticionJugadaDTO(TipoAccionPartida accion, int idJugador) {
        this.accion = accion;
        this.idJugador = idJugador;
    }

    public PeticionJugadaDTO(TipoAccionPartida accion, int idJugador, CartaDTO cartaAJugar, TipoColor nuevoColor) {
        this.accion = accion;
        this.idJugador = idJugador;
        this.cartaAJugar = cartaAJugar;
        this.nuevoColor = nuevoColor;
    }

    public TipoAccionPartida getAccion() {
        return accion;
    }

    public void setAccion(TipoAccionPartida accion) {
        this.accion = accion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public CartaDTO getCartaAJugar() {
        return cartaAJugar;
    }

    public void setCartaAJugar(CartaDTO cartaAJugar) {
        this.cartaAJugar = cartaAJugar;
    }

    public TipoColor getNuevoColor() {
        return nuevoColor;
    }

    public void setNuevoColor(TipoColor nuevoColor) {
        this.nuevoColor = nuevoColor;
    }

}
