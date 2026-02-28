/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.EstadoRetoSpin;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class EstadoPartidaDTO {

    private int idJugadorEnTurno;
    private List<JugadorResumenDTO> jugadores;
    private List<CartaDTO> manoJugadorActual;
    private CartaDTO cartaEnDescarte;
    private EstadoRetoSpin estadoReto;
    private boolean ruletaActiva;

    private boolean puedeTirarCarta;
    private boolean puedeRobar;
    private boolean puedeDecirUno;

    private String mensajeEstado;

    public EstadoPartidaDTO() {
    }

    public EstadoPartidaDTO(int idJugadorEnTurno, List<JugadorResumenDTO> jugadores, List<CartaDTO> manoJugadorActual, CartaDTO cartaEnDescarte, EstadoRetoSpin estadoReto, boolean ruletaActiva, boolean puedeTirarCarta, boolean puedeRobar, boolean puedeDecirUno, String mensajeEstado) {
        this.idJugadorEnTurno = idJugadorEnTurno;
        this.jugadores = jugadores;
        this.manoJugadorActual = manoJugadorActual;
        this.cartaEnDescarte = cartaEnDescarte;
        this.estadoReto = estadoReto;
        this.ruletaActiva = ruletaActiva;
        this.puedeTirarCarta = puedeTirarCarta;
        this.puedeRobar = puedeRobar;
        this.puedeDecirUno = puedeDecirUno;
        this.mensajeEstado = mensajeEstado;
    }

    public int getIdJugadorEnTurno() {
        return idJugadorEnTurno;
    }

    public void setIdJugadorEnTurno(int idJugadorEnTurno) {
        this.idJugadorEnTurno = idJugadorEnTurno;
    }

    public List<JugadorResumenDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorResumenDTO> jugadores) {
        this.jugadores = jugadores;
    }

    public List<CartaDTO> getManoJugadorActual() {
        return manoJugadorActual;
    }

    public void setManoJugadorActual(List<CartaDTO> manoJugadorActual) {
        this.manoJugadorActual = manoJugadorActual;
    }

    public CartaDTO getCartaEnDescarte() {
        return cartaEnDescarte;
    }

    public void setCartaEnDescarte(CartaDTO cartaEnDescarte) {
        this.cartaEnDescarte = cartaEnDescarte;
    }

    public EstadoRetoSpin getEstadoReto() {
        return estadoReto;
    }

    public void setEstadoReto(EstadoRetoSpin estadoReto) {
        this.estadoReto = estadoReto;
    }

    public boolean isRuletaActiva() {
        return ruletaActiva;
    }

    public void setRuletaActiva(boolean ruletaActiva) {
        this.ruletaActiva = ruletaActiva;
    }

    public boolean isPuedeTirarCarta() {
        return puedeTirarCarta;
    }

    public void setPuedeTirarCarta(boolean puedeTirarCarta) {
        this.puedeTirarCarta = puedeTirarCarta;
    }

    public boolean isPuedeRobar() {
        return puedeRobar;
    }

    public void setPuedeRobar(boolean puedeRobar) {
        this.puedeRobar = puedeRobar;
    }

    public boolean isPuedeDecirUno() {
        return puedeDecirUno;
    }

    public void setPuedeDecirUno(boolean puedeDecirUno) {
        this.puedeDecirUno = puedeDecirUno;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }

    public void setMensajeEstado(String mensajeEstado) {
        this.mensajeEstado = mensajeEstado;
    }
}
