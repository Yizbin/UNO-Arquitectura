/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author adell
 */
public class RespuestaFinalizacionDTO {
    private JugadorResumenDTO jugador;
    private Boolean acepta;

    public RespuestaFinalizacionDTO(JugadorResumenDTO jugador, Boolean acepta) {
        this.jugador = jugador;
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

    public Boolean getAcepta() {
        return acepta;
    }

    public void setAcepta(Boolean acepta) {
        this.acepta = acepta;
    }
    
    
    
    
}
