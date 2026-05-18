/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;

/**
 *
 * @author adell
 */
public class RespuestaFinalizacionDTO {
    private JugadorResumenDTO jugador;
    private Date fecha;
    private Boolean acepta;

    public RespuestaFinalizacionDTO(JugadorResumenDTO jugador, Date fecha, Boolean acepta) {
        this.jugador = jugador;
        this.fecha = fecha;
        this.acepta = acepta;
    }

    public RespuestaFinalizacionDTO() {
    }
    
    
    public JugadorResumenDTO getJugador() {
        return jugador;
    }

    public void setJugador(JugadorResumenDTO jugador) {
        this.jugador = jugador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getAcepta() {
        return acepta;
    }

    public void setAcepta(Boolean acepta) {
        this.acepta = acepta;
    }
    
    
    
    
}
