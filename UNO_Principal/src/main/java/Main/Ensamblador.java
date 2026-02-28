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

        ModeloJuego modelo = new ModeloJuego(subDominio);

        PantallaTurno ventanaPrincipal = ensamblarMVC(modelo);

        simularPartidaPrevia(modelo);

        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);
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
            jugadores.add(new JugadorResumenDTO("Jugador 1", 0, 0, true));
            jugadores.add(new JugadorResumenDTO("Jugador 2", 0, 0, false));
            jugadores.add(new JugadorResumenDTO("Jugador 3", 0, 0, false));
            jugadores.add(new JugadorResumenDTO("Jugador 4", 0, 0, false));

            modelo.iniciarPartida(jugadores);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
