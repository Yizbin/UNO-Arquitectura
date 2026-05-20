/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import MVC_ConfigurarPartida.ControlConfgPartida;
import MVC_JugarTurno.UnoSpinControlador;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public class ControladorSala {

    private final IControlModeloSala modelo;
    private final ControlConfgPartida controlConfigPartida;
    private final UnoSpinControlador controlJuego;

    public ControladorSala(IControlModeloSala modelo) {
        this(modelo, null, null);
    }

    public ControladorSala(IControlModeloSala modelo, ControlConfgPartida controlConfigPartida) {
        this(modelo, controlConfigPartida, null);
    }

    public ControladorSala(IControlModeloSala modelo, ControlConfgPartida controlConfigPartida, UnoSpinControlador controlJuego) {
        this.modelo = modelo;
        this.controlConfigPartida = controlConfigPartida;
        this.controlJuego = controlJuego;
    }

    public boolean solicitarUnirsePartida() {
        return modelo.solicitarUnirsePartida();
    }

    public boolean actualizarEstadoJugadorSala() {
        return modelo.actualizarEstadoJugadorSala();
    }

    public boolean iniciarPartida(JugadorResumenDTO jugadorDTO) {
        return modelo.iniciarPartida(jugadorDTO);
    }

    public boolean notificarInicio(IModeloSalaVista modeloVista) {
        if (modeloVista == null || !modeloVista.isPartidaListaParaIniciar()) {
            return false;
        }

        JugadorResumenDTO jugadorLocal = modeloVista.getJugadorLocal();

        if (jugadorLocal == null || jugadorLocal.getId() != 1 || controlJuego == null) {
            return false;
        }

        controlJuego.iniciarPartida(modeloVista.getJugadoresEnSala(), jugadorLocal);
        return true;
    }

    public void abrirConfigurarPartida() {
        if (controlConfigPartida == null) {
            throw new IllegalStateException("No se ha configurado el controlador de Configurar Partida.");
        }

        controlConfigPartida.mostrarPantallaConfigurarPartida();
    }

    void registrarJugador(JugadorResumenDTO datos, Map<TipoColor, TipoColor> misColores) {
        modelo.registrarJugador(datos, misColores);
    }

    public void abrirSalaEspera() {
        modelo.abrirSalaEspera();
    }

    public void establecerJugadorLocal(JugadorResumenDTO datos) {
        modelo.establecerJugadorLocal(datos);
    }
    
    public void abrirMenu() {

    }
}
