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
public class SolicitudFinalizacionDTO {
    private JugadorResumenDTO jugador;
    private Date fecha;
    private String mensaje;

    public SolicitudFinalizacionDTO(JugadorResumenDTO jugador, Date fecha, String mensaje) {
        this.jugador = jugador;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public SolicitudFinalizacionDTO() {
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
