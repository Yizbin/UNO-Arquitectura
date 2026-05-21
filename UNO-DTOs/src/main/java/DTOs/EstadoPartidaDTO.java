/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.EstadoFinalizacion;
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
    private CartaDTO cartaEnDescarte;
    private EstadoRetoSpin estadoReto;
    private boolean ruletaActiva;
    private List<CartaDTO> mazo;

    private boolean puedeRobar;
    private boolean puedeDecirUno;
    private TipoColor colorSeleccionado;

    private boolean inicioPermitido;

    private String mensajeEstado;

    private EstadoFinalizacion estadoFinalizacion = EstadoFinalizacion.SIN_SOLICITUD;
    private ResultadoFinalizacionDTO resultadoFinalizacion;
    private List<RespuestaFinalizacionDTO> respuestasFinalizacion;

    private List<JugadorEstadoSalaDTO> estadosJugadoresSala;

    public EstadoPartidaDTO() {
    }

    public EstadoPartidaDTO(List<JugadorEstadoSalaDTO> estadosJugadoresSala) {
        this.estadosJugadoresSala = estadosJugadoresSala;
    }

    public EstadoPartidaDTO(int idJugador, List<JugadorResumenDTO> jugadores, CartaDTO cartaEnDescarte, EstadoRetoSpin estadoReto, boolean ruletaActiva, List<CartaDTO> mazo, boolean puedeRobar, boolean puedeDecirUno, TipoColor colorSeleccionado, String mensajeEstado, boolean inicioPermitido) {
        this.idJugador = idJugador;
        this.jugadores = jugadores;
        this.cartaEnDescarte = cartaEnDescarte;
        this.estadoReto = estadoReto;
        this.ruletaActiva = ruletaActiva;
        this.mazo = mazo;
        this.puedeRobar = puedeRobar;
        this.puedeDecirUno = puedeDecirUno;
        this.colorSeleccionado = colorSeleccionado;
        this.mensajeEstado = mensajeEstado;
        this.inicioPermitido = inicioPermitido;
    }

    public boolean isInicioPermitido() {
        return inicioPermitido;
    }

    public void setInicioPermitido(boolean inicioPermitido) {
        this.inicioPermitido = inicioPermitido;
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

    public TipoColor getColorSeleccionado() {
        return colorSeleccionado;
    }

    public void setColorSeleccionado(TipoColor colorSeleccionado) {
        this.colorSeleccionado = colorSeleccionado;
    }

    public EstadoFinalizacion getEstadoFinalizacion() {
        return estadoFinalizacion;
    }

    public void setEstadoFinalizacion(EstadoFinalizacion estadoFinalizacion) {
        this.estadoFinalizacion = estadoFinalizacion != null ? estadoFinalizacion : EstadoFinalizacion.SIN_SOLICITUD;
    }

    public ResultadoFinalizacionDTO getResultadoFinalizacion() {
        return resultadoFinalizacion;
    }

    public void setResultadoFinalizacion(ResultadoFinalizacionDTO resultadoFinalizacion) {
        this.resultadoFinalizacion = resultadoFinalizacion;
    }

    public List<RespuestaFinalizacionDTO> getRespuestasFinalizacion() {
        return respuestasFinalizacion;
    }

    public void setRespuestasFinalizacion(List<RespuestaFinalizacionDTO> respuestasFinalizacion) {
        this.respuestasFinalizacion = respuestasFinalizacion;
    }

    public List<CartaDTO> getMazo() {
        return mazo;
    }

    public void setMazo(List<CartaDTO> mazo) {
        this.mazo = mazo;
    }

    public List<JugadorEstadoSalaDTO> getEstadosJugadoresSala() {
        return estadosJugadoresSala;
    }

    public void setEstadosJugadoresSala(List<JugadorEstadoSalaDTO> estadosJugadoresSala) {
        this.estadosJugadoresSala = estadosJugadoresSala;
    }

}
