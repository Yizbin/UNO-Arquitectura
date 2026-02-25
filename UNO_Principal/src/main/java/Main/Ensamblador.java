package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.UnoSpinControlador;
import javax.swing.SwingUtilities;

public class Ensamblador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            iniciarAplicacion();
        });
    }

    private static void iniciarAplicacion() {
        ISubDominio subDominio = new SubDominioConcreto();

        PantallaTurno ventanaPrincipal = ensamblarMVC(subDominio);

        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);
    }

    private static PantallaTurno ensamblarMVC(ISubDominio subDominio) {
        ModeloJuego modelo = new ModeloJuego(subDominio);

        UnoSpinControlador controlador = new UnoSpinControlador(modelo);

        PantallaTurno vista = new PantallaTurno(modelo, controlador);

        modelo.agregarSuscriptor(vista);

        return vista;
    }

}
