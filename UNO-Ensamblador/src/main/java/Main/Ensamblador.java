package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Adapter.AdapterCliente;
import DTOs.EstadoPartidaDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Entidades.Partida;
import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Filtro.DominioFiltro;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Interfaces.SubDominioConcreto;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.ModeloJuego;
import MVC_Sala.ControladorSala;
import MVC_Sala.MenuPrincipal;
import MVC_Sala.ModeloSala;
import Serializador.Serializador;
import java.awt.EventQueue;
import java.util.List;
import javax.swing.SwingUtilities;
import pipeline.CoordinadorFiltros;

public class Ensamblador {

    private static final String IP_SERVIDOR = "192.168.1.72";
    private static final int PUERTO_SERVIDOR = 5000;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configurarConexionRed(IP_SERVIDOR, PUERTO_SERVIDOR);
        });
    }

    private static void configurarConexionRed(String ipServidor, int puertoServidor) {

        Partida partida = new Partida();
        SubDominioConcreto subDominio = new SubDominioConcreto(partida);

        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        dispatcher.preConectar(ipServidor, puertoServidor);

        ISink<byte[]> adapterSink
                = new AdapterCliente(ipServidor, puertoServidor, dispatcher);

        CoordinadorFiltros<PeticionJugadaDTO, byte[]> pipelineSalida
                = new CoordinadorFiltros<>(
                        List.of(
                                new DominioFiltro(subDominio),
                                new Serializador<EstadoPartidaDTO>()
                        ),
                        adapterSink
                );

        ModeloJuego modeloJuego = new ModeloJuego();

        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada
                = new CoordinadorFiltros<>(
                        List.of(new Deserializador<>(EstadoPartidaDTO.class)),
                        modeloJuego
                );

        PantallaTurno ventana = FabricaJugadorMVC.crearEntornoJugador(
                pipelineSalida,
                modeloJuego,
                0,
                "UNO Spin - Cliente Red",
                100,
                100
        );

        ventana.setVisible(false);
        
        //agrege esto para poder probar la conexión
        System.out.println("Se conectó al servidor");
        ModeloSala modelo = new ModeloSala(pipelineSalida);

        ControladorSala controlador = new ControladorSala(modelo);
        EventQueue.invokeLater(() -> {
            try {
                MenuPrincipal ventanaMenu = new MenuPrincipal(controlador);
                ventanaMenu.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        ReceptorFactory.iniciarConexion(puertoServidor + 1, pipelineEntrada);
    }
}
