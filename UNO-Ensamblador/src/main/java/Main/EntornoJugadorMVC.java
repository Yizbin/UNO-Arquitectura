package Main;

import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.UnoSpinControlador;

public class EntornoJugadorMVC {

    private final ModeloJuego modelo;
    private final UnoSpinControlador controlador;
    private final PantallaTurno vista;

    public EntornoJugadorMVC(ModeloJuego modelo, UnoSpinControlador controlador, PantallaTurno vista) {
        this.modelo = modelo;
        this.controlador = controlador;
        this.vista = vista;
    }

    public ModeloJuego getModelo() {
        return modelo;
    }

    public UnoSpinControlador getControlador() {
        return controlador;
    }

    public PantallaTurno getVista() {
        return vista;
    }
}
