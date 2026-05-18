/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import MVC_ConfigurarPartida.IControlConfgPartida;
import MVC_ConfigurarPartida.PantallaConfigurarPartida;


/**
 *
 * @author Abraham Coronel
 */
public class ControladorSala {

    private final IControlModeloSala modelo;
    private IControlConfgPartida controlConfigPartida;

    public ControladorSala(IControlModeloSala modelo, IControlConfgPartida controlConfigPartida) {
        this.modelo = modelo;
        this.controlConfigPartida=controlConfigPartida;
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
    
    public void abrirConfigurarPartida() {
        controlConfigPartida.mostrarPantallaConfigurarPartida();
    }
}