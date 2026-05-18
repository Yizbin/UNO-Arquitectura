/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;
import java.util.List;

/**
 *
 * @author adell
 */
public class TablaPosicionesDTO {
    private List<JugadorResumenDTO> posiciones;
    private Date fechaGeneracion;

    public TablaPosicionesDTO(List<JugadorResumenDTO> posiciones, Date fechaGeneracion) {
        this.posiciones = posiciones;
        this.fechaGeneracion = fechaGeneracion;
    }

    public TablaPosicionesDTO() {
    }

    public List<JugadorResumenDTO> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<JugadorResumenDTO> posiciones) {
        this.posiciones = posiciones;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
    
}
