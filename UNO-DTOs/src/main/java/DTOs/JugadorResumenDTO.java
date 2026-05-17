/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.EstadoJugadorSala;
import Enums.TipoColor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public class JugadorResumenDTO {

    private int id;
    private String nombreUsuario;
    private String rutaAvatar;
    private int cantidadDeCartas;
    private int puntos;
    private boolean enTurno;
    private Map<TipoColor, TipoColor> preferenciasColor = new HashMap<>();
    private EstadoJugadorSala estadoSala = EstadoJugadorSala.ESPERANDO;

    public JugadorResumenDTO() {
        preferenciasColor.put(TipoColor.ROJO, TipoColor.ROJO);
        preferenciasColor.put(TipoColor.AZUL, TipoColor.AZUL);
        preferenciasColor.put(TipoColor.VERDE, TipoColor.VERDE);
        preferenciasColor.put(TipoColor.AMARILLO, TipoColor.AMARILLO);
    }

    public JugadorResumenDTO(int id, String nombreUsuario) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
    }

    public JugadorResumenDTO(int id, String nombreUsuario, int cantidadDeCartas, int puntos, boolean enTurno) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.cantidadDeCartas = cantidadDeCartas;
        this.puntos = puntos;
        this.enTurno = enTurno;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCantidadDeCartas() {
        return cantidadDeCartas;
    }

    public void setCantidadDeCartas(int cantidadDeCartas) {
        this.cantidadDeCartas = cantidadDeCartas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isEnTurno() {
        return enTurno;
    }

    public void setEnTurno(boolean enTurno) {
        this.enTurno = enTurno;
    }

    public String getRutaAvatar() {
        return rutaAvatar;
    }

    public void setRutaAvatar(String rutaAvatar) {
        this.rutaAvatar = rutaAvatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<TipoColor, TipoColor> getPreferenciasColor() {
        return preferenciasColor;
    }

    public void setPreferenciasColor(Map<TipoColor, TipoColor> preferenciasColor) {
        this.preferenciasColor = preferenciasColor;
    }

    public EstadoJugadorSala getEstadoSala() {
        return estadoSala;
    }

    public void setEstadoSala(EstadoJugadorSala estadoSala) {
        this.estadoSala = estadoSala != null ? estadoSala : EstadoJugadorSala.ESPERANDO;
    }

}
