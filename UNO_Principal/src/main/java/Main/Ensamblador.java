package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Fachade.IJuegoAdapter;
import Fachade.JuegoAdapter;
import Interfaces.ICartaMapper;
import Interfaces.IJugadorMapper;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.UnoSpinControlador;
import Mappers.CartaMapper;
import Mappers.JugadorMapper;
import javax.swing.SwingUtilities;

public class Ensamblador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            iniciarAplicacion();
        });
    }

    private static void iniciarAplicacion() {
        IJuegoAdapter adaptador = configurarAdaptador();

        PantallaTurno ventanaPrincipal = ensamblarMVC(adaptador);

        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);
    }

    private static IJuegoAdapter configurarAdaptador() {
        ICartaMapper cartaMapper = new CartaMapper();
        IJugadorMapper jugadorMapper = new JugadorMapper();

        return new JuegoAdapter(cartaMapper, jugadorMapper);
    }

    private static PantallaTurno ensamblarMVC(IJuegoAdapter adaptador) {
        ModeloJuego modelo = new ModeloJuego(adaptador);

        UnoSpinControlador controlador = new UnoSpinControlador(modelo);

        PantallaTurno vista = new PantallaTurno(modelo, controlador);

        return vista;
    }

}
