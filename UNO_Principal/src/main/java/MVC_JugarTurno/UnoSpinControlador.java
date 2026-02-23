/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

/**
 *
 * @author Abraham Coronel
 */
public class UnoSpinControlador {

    private final IControlModelo modelo;

    public UnoSpinControlador(IControlModelo modelo) {
        this.modelo = modelo;
    }

    public void robarCarta() throws Exception {
        modelo.robarCarta();
    }

}
