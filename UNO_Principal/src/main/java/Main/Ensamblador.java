package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import DTOs.JugadorResumenDTO;
import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.UnoSpinControlador;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class Ensamblador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            iniciarAplicacion();
        });
    }

    private static void iniciarAplicacion() {
        ISubDominio subDominio = new SubDominioConcreto();

        ModeloJuego modeloJ1 = new ModeloJuego(subDominio);
        modeloJ1.setIdJugadorLocal(1);
        PantallaTurno ventanaJ1 = ensamblarMVC(modeloJ1);
        ventanaJ1.setTitle("Ventana - Jugador 1");

        ModeloJuego modeloJ2 = new ModeloJuego(subDominio);
        modeloJ2.setIdJugadorLocal(2);
        PantallaTurno ventanaJ2 = ensamblarMVC(modeloJ2);
        ventanaJ2.setTitle("Ventana - Jugador 2");

        simularPartidaPrevia(modeloJ1);

        ventanaJ2.update();

        ventanaJ1.setLocation(100, 200);
        ventanaJ2.setLocation(700, 200);

        ventanaJ1.setVisible(true);
        ventanaJ2.setVisible(true);
    }

    private static PantallaTurno ensamblarMVC(ModeloJuego modelo) {
        UnoSpinControlador controlador = new UnoSpinControlador(modelo);

        PantallaTurno vista = new PantallaTurno(modelo, controlador);

        modelo.agregarSuscriptor(vista);

        return vista;
    }

    private static void simularPartidaPrevia(ModeloJuego modelo) {
        try {
            List<JugadorResumenDTO> jugadores = new ArrayList<>();
            jugadores.add(new JugadorResumenDTO(1, "Jugador 1", 0, 0, true));
            jugadores.add(new JugadorResumenDTO(2, "Jugador 2", 0, 0, false));
            jugadores.add(new JugadorResumenDTO(3, "Jugador 3", 0, 0, false));
            jugadores.add(new JugadorResumenDTO(4, "Jugador 4", 0, 0, false));

            modelo.iniciarPartida(jugadores);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
