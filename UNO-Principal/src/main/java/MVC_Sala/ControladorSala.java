/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import java.util.Map;
import MVC_ConfigurarPartida.IControlConfgPartida;

/**
 *
 * @author Abraham Coronel
 */
public class ControladorSala {

    private final IControlModeloSala modelo;
    private final IControlConfgPartida controlConfigPartida;

    public ControladorSala(IControlModeloSala modelo) {
        this(modelo, null);
    }

    public ControladorSala(IControlModeloSala modelo, IControlConfgPartida controlConfigPartida) {
        this.modelo = modelo;
        this.controlConfigPartida = controlConfigPartida;
    }

    public boolean solicitarUnirsePartida() {
        return modelo.solicitarUnirsePartida();
    }

    public boolean actualizarEstadoJugadorSala() {
        return modelo.actualizarEstadoJugadorSala();
    }

    void actualizarDatosJugador(JugadorResumenDTO datos, Map<TipoColor, TipoColor> misColores) {
        modelo.actualizarDatosJugador(datos, misColores);
    }

    public void abrirSalaEspera() {
        modelo.abrirSalaEspera();
    }

    public void establecerJugadorLocal(JugadorResumenDTO datos) {
        modelo.establecerJugadorLocal(datos);
    }
    
    public void abrirConfigurarPartida() {
        if (controlConfigPartida == null) {
            throw new IllegalStateException("No se ha configurado el controlador de Configurar Partida.");
        }

        controlConfigPartida.mostrarPantallaConfigurarPartida();
    }
}
