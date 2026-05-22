/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import Enums.EstadoJugadorSala;
import Enums.TipoColor;
import MVC_ConfigurarPartida.ControlConfgPartida;
import MVC_JugarTurno.UnoSpinControlador;

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

    public EstadoJugadorSala obtenerEstadoJugador(int idJugador) {
        return modelo.obtenerEstadoJugador(idJugador);
    }

    public boolean iniciarPartida(JugadorResumenDTO jugadorDTO) {
        return modelo.iniciarPartida(jugadorDTO);
    }

    public boolean aceptarSolicitudUnion(int idJugadorSolicitante) {
        return modelo.aceptarSolicitudUnion(idJugadorSolicitante);
    }

    public boolean rechazarSolicitudUnion(int idJugadorSolicitante) {
        return modelo.rechazarSolicitudUnion(idJugadorSolicitante);
    }

    public boolean notificarInicio(IModeloSalaVista modeloVista) {
        if (modeloVista == null || !modeloVista.isPartidaListaParaIniciar()) {
            return false;
        }

        JugadorResumenDTO jugadorLocal = modeloVista.getJugadorLocal();

        if (jugadorLocal == null || jugadorLocal.getId() != 1) {
            return false;
        }

        return modelo.iniciarPartida(jugadorLocal);
    }

    public void abrirConfigurarPartida() {
        if (controlConfigPartida == null) {
            throw new IllegalStateException("No se ha configurado el controlador de Configurar Partida.");
        }

        controlConfigPartida.mostrarPantallaConfigurarPartida();
    }

    void registrarJugador(JugadorResumenDTO datos) {
        modelo.registrarJugador(datos);
    }

    public void abrirSalaEspera() {
        modelo.abrirSalaEspera();
    }

    public void establecerJugadorLocal(JugadorResumenDTO datos) {
        modelo.establecerJugadorLocal(datos);
    }
    
    public void actualizarNombreJugador(String nombre) {
        this.modelo.getJugadorLocal().setNombreUsuario(nombre);
    }

    public void actualizarAvatarJugador(String rutaAvatar) {
        this.modelo.getJugadorLocal().setRutaAvatar(rutaAvatar);
    }

    public void cambiarColorBase(TipoColor base, TipoColor nuevo) {
        this.modelo.getMisColores().put(base, nuevo);
        if (null != base) switch (base) {
            case ROJO -> this.modelo.getC1().setColor(nuevo);
            case AZUL -> this.modelo.getC2().setColor(nuevo);
            case VERDE -> this.modelo.getC3().setColor(nuevo);
            case AMARILLO -> this.modelo.getC4().setColor(nuevo);
            default -> {
            }
        }
    }
}
