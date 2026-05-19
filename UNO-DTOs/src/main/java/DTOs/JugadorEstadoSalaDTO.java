/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.EstadoJugadorSala;

/**
 *
 * @author angel
 */
public class JugadorEstadoSalaDTO {

    private int id;
    private EstadoJugadorSala estadoSala;

    public JugadorEstadoSalaDTO() {

    }

    public JugadorEstadoSalaDTO(int id, EstadoJugadorSala estadoSala) {
        this.id = id;
        this.estadoSala = estadoSala;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstadoJugadorSala getEstadoSala() {
        return estadoSala;
    }

    public void setEstadoSala(EstadoJugadorSala estadoSala) {
        this.estadoSala = estadoSala;
    }

}
