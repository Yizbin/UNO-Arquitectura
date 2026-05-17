/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;


/**
 *
 * @author Abraham Coronel
 */
public class ControladorSala {

    private final IControlModeloSala modelo;

    public ControladorSala(IControlModeloSala modelo) {
        this.modelo = modelo;
    }

    public boolean solicitarUnirsePartida() {
        return modelo.solicitarUnirsePartida();
    }

    public boolean iniciarPartida(JugadorResumenDTO jugadorDTO) {
        return modelo.iniciarPartida(jugadorDTO);
    }
    
    public void actualizarPerfil(JugadorResumenDTO datos) {
        modelo.actualizarDatosJugador(datos);
    }
}