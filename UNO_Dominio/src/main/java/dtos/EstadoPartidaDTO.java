package dtos;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020

import Enums.EstadoRetoSpin;
import java.util.List;


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

    public EstadoPartidaDTO(int idJugadorEnTurno, List<JugadorResumenDTO> jugadores, List<CartaDTO> manoJugadorActual, CartaDTO cartaEnDescarte, EstadoRetoSpin estadoReto, boolean ruletaActiva) {
        this.idJugadorEnTurno = idJugadorEnTurno;
        this.jugadores = jugadores;
        this.manoJugadorActual = manoJugadorActual;
        this.cartaEnDescarte = cartaEnDescarte;
        this.estadoReto = estadoReto;
        this.ruletaActiva = ruletaActiva;
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
    
    
    
}
