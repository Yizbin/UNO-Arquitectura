/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.EstadoFinalizacion;

/**
 *
 * @author adell
 */
public class ResultadoFinalizacionDTO {
    private EstadoFinalizacion estado;
    private TablaPosicionesDTO tablaPosicones;
    private String mensaje;
    private JugadorResumenDTO jugadorSolicitante;

    public ResultadoFinalizacionDTO(EstadoFinalizacion estado, TablaPosicionesDTO tablaPosicones, String mensaje) {
        this.estado = estado;
        this.tablaPosicones = tablaPosicones;
        this.mensaje = mensaje;
    }

    public ResultadoFinalizacionDTO(EstadoFinalizacion estado, TablaPosicionesDTO tablaPosicones, String mensaje, JugadorResumenDTO jugadorSolicitante) {
        this(estado, tablaPosicones, mensaje);
        this.jugadorSolicitante = jugadorSolicitante;
    }

    public ResultadoFinalizacionDTO() {
    }

    public EstadoFinalizacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoFinalizacion estado) {
        this.estado = estado;
    }

    public TablaPosicionesDTO getTablaPosicones() {
        return tablaPosicones;
    }

    public void setTablaPosicones(TablaPosicionesDTO tablaPosicones) {
        this.tablaPosicones = tablaPosicones;
    }

    public TablaPosicionesDTO getTablaPosiciones() {
        return tablaPosicones;
    }

    public void setTablaPosiciones(TablaPosicionesDTO tablaPosiciones) {
        this.tablaPosicones = tablaPosiciones;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public JugadorResumenDTO getJugadorSolicitante() {
        return jugadorSolicitante;
    }

    public void setJugadorSolicitante(JugadorResumenDTO jugadorSolicitante) {
        this.jugadorSolicitante = jugadorSolicitante;
    }
    
    
}
