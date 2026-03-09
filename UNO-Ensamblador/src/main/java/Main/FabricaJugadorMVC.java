/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Interfaces.ISubDominio;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.UnoSpinControlador;

/**
 *
 * @author Abraham Coronel
 */
public class FabricaJugadorMVC {

    public static PantallaTurno crearEntornoJugador(ISubDominio subDominio, int idJugador, String tituloVista, int posX, int posY) {
        ModeloJuego modelo = new ModeloJuego(subDominio);
        modelo.setIdJugadorLocal(idJugador);

        UnoSpinControlador controlador = new UnoSpinControlador(modelo);

        PantallaTurno vista = new PantallaTurno(modelo, controlador);
        modelo.agregarSuscriptor(vista);

        vista.setTitle(tituloVista);
        vista.setLocation(posX, posY);

        return vista;
    }
}
