package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import MVC_JugarTurno.PantallaTurno;
import javax.swing.SwingUtilities;

public class Ensamblador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            iniciarAplicacion();
        });
    }

    private static void iniciarAplicacion() {
        ISubDominio subDominio = new SubDominioConcreto();

        PantallaTurno ventanaJ1 = FabricaJugadorMVC.crearEntornoJugador(
                subDominio, 1, "Ventana - Jugador 1", 100, 200
        );

        PantallaTurno ventanaJ2 = FabricaJugadorMVC.crearEntornoJugador(
                subDominio, 2, "Ventana - Jugador 2", 700, 200
        );

        GestorPartidaLocal gestor = new GestorPartidaLocal();
        gestor.iniciarSimulacion(subDominio, ventanaJ1, ventanaJ2);
    }
}
