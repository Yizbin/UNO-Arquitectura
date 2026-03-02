package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.UnoSpinControlador;
import dtos.JugadorResumenDTO;
import java.util.List;
import javax.swing.JOptionPane;
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

        //bootstrap: simula "crear/configurar partida"
        try {
            controlador.iniciarPartida(List.of(
                new JugadorResumenDTO("Saul"),
                new JugadorResumenDTO("JP"),
                new JugadorResumenDTO("Pedro"),
                new JugadorResumenDTO("Chris")
            ));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al iniciar", JOptionPane.ERROR_MESSAGE);
        }

        return vista;
    }
    
    

}
