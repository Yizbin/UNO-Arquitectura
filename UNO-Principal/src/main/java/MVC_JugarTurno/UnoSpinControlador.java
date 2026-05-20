/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class UnoSpinControlador {

    private final IControlModelo modelo;

    public UnoSpinControlador(IControlModelo modelo) {
        this.modelo = modelo;
    }

    public void iniciarPartida(List<JugadorResumenDTO> jugadores) {
        modelo.iniciarJuego(jugadores);
    }

    public void iniciarPartida(List<JugadorResumenDTO> jugadores, JugadorResumenDTO jugadorSolicitante) {
        modelo.iniciarJuego(jugadores, jugadorSolicitante);
    }

    public void robarCarta() {
        modelo.robarCarta();
    }

    public void jugarCarta(CartaDTO carta) {
        modelo.jugarCarta(carta);
    }

    public void seleccionarColor(TipoColor color) {
        modelo.seleccionarColor(color);
    }

    public boolean solicitarFinalizacion(JugadorResumenDTO jugadorDTO) {
        return modelo.solicitarFinalizacion(jugadorDTO);
    }

    public boolean responderFinalizacion(JugadorResumenDTO jugadorDTO, boolean acepta) {
        return modelo.responderFinalizacion(jugadorDTO, acepta);
    }
}
