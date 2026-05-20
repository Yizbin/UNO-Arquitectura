/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import MVC_ConfigurarPartida.IControlConfgPartida;
import MVC_JugarTurno.UnoSpinControlador;
import java.io.IOException;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Abraham Coronel
 */
public class ControladorSala {

    private final IControlModeloSala modelo;
    private final IModeloSalaVista modeloVista;
    private final IControlConfgPartida controlConfigPartida;
    private final UnoSpinControlador controlJuego;

    public ControladorSala(IControlModeloSala modelo) {
        this(modelo, null, null);
    }

    public ControladorSala(IControlModeloSala modelo, IControlConfgPartida controlConfigPartida) {
        this(modelo, controlConfigPartida, null);
    }

    public ControladorSala(IControlModeloSala modelo, IControlConfgPartida controlConfigPartida, UnoSpinControlador controlJuego) {
        this.modelo = modelo;
        this.modeloVista = modelo instanceof IModeloSalaVista vista ? vista : null;
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

    public boolean notificarInicio() {
        if (modeloVista == null || !modeloVista.isPartidaListaParaIniciar()) {
            return false;
        }

        JugadorResumenDTO jugadorLocal = modeloVista.getJugadorLocal();
        if (jugadorLocal == null || jugadorLocal.getId() != 1) {
            return false;
        }

        if (controlJuego != null) {
            controlJuego.iniciarPartida(modeloVista.getJugadoresEnSala(), jugadorLocal);
            return true;
        }

        return false;
    }

    public void suscribirVista(ISuscriptorSala suscriptor) {
        if (modeloVista != null) {
            modeloVista.suscribir(suscriptor);
        }
    }

    public void mostrarSalaEspera() {
        SalaEspera sala = new SalaEspera(this);
        suscribirVista(sala);
        if (modeloVista != null) {
            sala.update(modeloVista);
        }
        sala.setVisible(true);
    }

    public void mostrarConfiguracionJugador() {
        if (modeloVista == null) {
            throw new IllegalStateException("No se ha configurado el modelo de vista de sala.");
        }

        try {
            new ConfiguracionJugador(this, modeloVista, 1);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo abrir el registro del jugador: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void actualizarDatosJugador(JugadorResumenDTO datos, Map<TipoColor, TipoColor> misColores) {
        //modelo.actualizarDatosJugador(datos, misColores);
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

    public void abrirConfigurarPartida() {
        if (controlConfigPartida == null) {
            throw new IllegalStateException("No se ha configurado el controlador de Configurar Partida.");
        }
        controlConfigPartida.mostrarPantallaConfigurarPartida();
    }

    public void abrirMenu() {

    }
}
