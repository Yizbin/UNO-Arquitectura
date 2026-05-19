/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.List;

/**
 *
 * @author adell
 */
public class TablaPosicionesDTO {
    private List<JugadorResumenDTO> posiciones;

    public TablaPosicionesDTO(List<JugadorResumenDTO> posiciones) {
        this.posiciones = posiciones;
    }

    public TablaPosicionesDTO() {
    }

    public List<JugadorResumenDTO> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<JugadorResumenDTO> posiciones) {
        this.posiciones = posiciones;
    }

}
