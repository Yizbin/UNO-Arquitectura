/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import dtos.CartaDTO;
import dtos.JugadorResumenDTO;
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

    public void iniciarPartida(List<JugadorResumenDTO> jugadores) throws Exception {
        modelo.iniciarJuego(jugadores);
    }
    
    public void robarCarta() throws Exception {
        modelo.robarCarta();
    }

    public void jugarCarta(CartaDTO carta) throws Exception {
        modelo.jugarCarta(carta);
    }
}
