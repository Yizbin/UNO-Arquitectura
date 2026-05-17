/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.EstadoRetoSpin;
import Enums.TipoColor;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class EstadoPartidaDTO {

    private int idJugador;
    private List<JugadorResumenDTO> jugadores;
    private List<CartaDTO> manoJugadorActual;
    private CartaDTO cartaEnDescarte;
    private EstadoRetoSpin estadoReto;
    private boolean ruletaActiva;

    private boolean puedeTirarCarta;
    private boolean puedeRobar;
    private boolean puedeDecirUno;
    private boolean esperandoColor;
    private TipoColor colorSeleccionado;

    private String mensajeEstado;

    private boolean partidaListaParaIniciar;

    private int idAnfitrion;
    private List<Integer> solicitudesPendientes;

    public EstadoPartidaDTO() {
    }

    public EstadoPartidaDTO(int idJugador, List<JugadorResumenDTO> jugadores, List<CartaDTO> manoJugadorActual, CartaDTO cartaEnDescarte, EstadoRetoSpin estadoReto, boolean ruletaActiva, boolean puedeTirarCarta, boolean puedeRobar, boolean puedeDecirUno, String mensajeEstado) {
        this.idJugador = idJugador;
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

    public EstadoPartidaDTO(int idJugador, List<JugadorResumenDTO> jugadores, List<CartaDTO> manoJugadorActual, CartaDTO cartaEnDescarte, EstadoRetoSpin estadoReto, boolean ruletaActiva, boolean puedeTirarCarta, boolean puedeRobar, boolean puedeDecirUno, boolean esperandoColor, TipoColor colorSeleccionado, String mensajeEstado, boolean partidaListaParaIniciar) {
        this.idJugador = idJugador;
        this.jugadores = jugadores;
        this.manoJugadorActual = manoJugadorActual;
        this.cartaEnDescarte = cartaEnDescarte;
        this.estadoReto = estadoReto;
        this.ruletaActiva = ruletaActiva;
        this.puedeTirarCarta = puedeTirarCarta;
        this.puedeRobar = puedeRobar;
        this.puedeDecirUno = puedeDecirUno;
        this.esperandoColor = esperandoColor;
        this.colorSeleccionado = colorSeleccionado;
        this.mensajeEstado = mensajeEstado;
        this.partidaListaParaIniciar = partidaListaParaIniciar;
    }

    public EstadoPartidaDTO(int idJugador, List<JugadorResumenDTO> jugadores, List<CartaDTO> manoJugadorActual, CartaDTO cartaEnDescarte, EstadoRetoSpin estadoReto, boolean ruletaActiva, boolean puedeTirarCarta, boolean puedeRobar, boolean puedeDecirUno, boolean esperandoColor, TipoColor colorSeleccionado, String mensajeEstado, boolean partidaListaParaIniciar, int idAnfitrion, List<Integer> solicitudesPendientes) {
        this.idJugador = idJugador;
        this.jugadores = jugadores;
        this.manoJugadorActual = manoJugadorActual;
        this.cartaEnDescarte = cartaEnDescarte;
        this.estadoReto = estadoReto;
        this.ruletaActiva = ruletaActiva;
        this.puedeTirarCarta = puedeTirarCarta;
        this.puedeRobar = puedeRobar;
        this.puedeDecirUno = puedeDecirUno;
        this.esperandoColor = esperandoColor;
        this.colorSeleccionado = colorSeleccionado;
        this.mensajeEstado = mensajeEstado;
        this.partidaListaParaIniciar = partidaListaParaIniciar;
        this.idAnfitrion = idAnfitrion;
        this.solicitudesPendientes = solicitudesPendientes;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
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

    public boolean isEsperandoColor() {
        return esperandoColor;
    }

    public void setEsperandoColor(boolean esperandoColor) {
        this.esperandoColor = esperandoColor;
    }

    public boolean isPartidaListaParaIniciar() {
        return partidaListaParaIniciar;
    }

    public void setPartidaListaParaIniciar(boolean partidaListaParaIniciar) {
        this.partidaListaParaIniciar = partidaListaParaIniciar;
    }

    public TipoColor getColorSeleccionado() {
        return colorSeleccionado;
    }

    public void setColorSeleccionado(TipoColor colorSeleccionado) {
        this.colorSeleccionado = colorSeleccionado;
    }

    public int getIdAnfitrion() {
        return idAnfitrion;
    }

    public void setIdAnfitrion(int idAnfitrion) {
        this.idAnfitrion = idAnfitrion;
    }

    public List<Integer> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public void setSolicitudesPendientes(List<Integer> solicitudesPendientes) {
        this.solicitudesPendientes = solicitudesPendientes;
    }

}
