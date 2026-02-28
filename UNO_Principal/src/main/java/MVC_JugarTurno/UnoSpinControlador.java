/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import Enums.TipoColor;

/**
 *
 * @author Abraham Coronel
 */
public class UnoSpinControlador {

    private final IControlModelo modelo;

    public UnoSpinControlador(IControlModelo modelo) {
        this.modelo = modelo;
    }

    public void robarCarta() {
        try {
            modelo.robarCarta();
        } catch (Exception ex) {
            modelo.notificarError(ex.getMessage());
        }
    }

    public void jugarCarta(CartaDTO carta) {
        try {
            modelo.jugarCarta(carta);
        } catch (Exception ex) {
            modelo.notificarError(ex.getMessage());
        }
    }

    public void seleccionarColor(TipoColor color) {
        try {
            modelo.seleccionarColor(color);
        } catch (Exception ex) {
            modelo.notificarError(ex.getMessage());
        }
    }
}
