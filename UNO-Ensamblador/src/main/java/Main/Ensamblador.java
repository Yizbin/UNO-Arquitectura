package Main;

import Adapter.AdapterCliente;
import Adapter.AdapterCargarPartida;
import Adapter.AdapterIniciarPartida;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Entidades.Partida;
import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Filtro.DominioFiltro;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Interfaces.SubDominioConcreto;
import MVC_ConfigurarPartida.ControlConfgPartida;
import MVC_ConfigurarPartida.ModeloConfgPartida;
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

    private static final String IP_SERVIDOR = "192.168.1.71";
    private static final int PUERTO_SERVIDOR = 5000;
    private static final int DESPLAZAMIENTO_PUERTO_INICIO_PARTIDA = 1;
    private static final int DESPLAZAMIENTO_PUERTO_CARGAR_PARTIDA = 2;
    private static final int ID_JUGADOR_ANFITRION = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> configurarConexionRed(IP_SERVIDOR, PUERTO_SERVIDOR));
    }

    private static void configurarConexionRed(String ipServidor, int puertoServidor) {
        Partida partida = new Partida();
        SubDominioConcreto subDominio = new SubDominioConcreto(partida);

        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        dispatcher.preConectar(ipServidor, puertoServidor);

        ISink<byte[]> adapterSalida = new AdapterCliente(ipServidor, puertoServidor, dispatcher);
        CoordinadorFiltros<PeticionJugadaDTO, byte[]> pipelineSalida
                = new CoordinadorFiltros<>(
                        List.of(
                                new DominioFiltro(subDominio),
                                new Serializador<PeticionJugadaDTO>()
                        ),
                        adapterSalida
                );

        ModeloJuego modeloJuego = new ModeloJuego();
        ModeloSala modeloSala = new ModeloSala(pipelineSalida);

        EntornoJugadorMVC entornoJuego = FabricaJugadorMVC.crearEntornoJugadorCompleto(
                pipelineSalida,
                modeloJuego,
                ID_JUGADOR_ANFITRION,
                "UNO Spin - Cliente Red",
                100,
                100
        );
        entornoJuego.getVista().setVisible(false);

        ISink<PeticionJugadaDTO> adapterIniciarPartida = new AdapterIniciarPartida(modeloSala);
        CoordinadorFiltros<byte[], PeticionJugadaDTO> pipelineEntradaIniciarPartida
                = new CoordinadorFiltros<>(
                        List.of(
                                new Deserializador<>(PeticionJugadaDTO.class),
                                new DominioFiltro(subDominio)
                        ),
                        adapterIniciarPartida
                );

        ISink<PeticionJugadaDTO> adapterCargarPartida = new AdapterCargarPartida(modeloJuego);
        CoordinadorFiltros<byte[], PeticionJugadaDTO> pipelineEntradaCargarPartida
                = new CoordinadorFiltros<>(
                        List.of(
                                new Deserializador<>(PeticionJugadaDTO.class),
                                new DominioFiltro(subDominio)
                        ),
                        adapterCargarPartida
                );

        ModeloConfgPartida modeloConfigPartida = new ModeloConfgPartida(pipelineSalida);
        ControlConfgPartida controlConfigPartida = new ControlConfgPartida(modeloConfigPartida);
        ControladorSala controladorSala = new ControladorSala(
                modeloSala,
                controlConfigPartida,
                entornoJuego.getControlador()
        );

        System.out.println("Se conecto al servidor");

        EventQueue.invokeLater(() -> {
            MenuPrincipal ventanaMenu = new MenuPrincipal(controladorSala);
            ventanaMenu.setVisible(true);
        });

        ReceptorFactory.iniciarConexion(
                puertoServidor + DESPLAZAMIENTO_PUERTO_INICIO_PARTIDA,
                pipelineEntradaIniciarPartida
        );
        ReceptorFactory.iniciarConexion(
                puertoServidor + DESPLAZAMIENTO_PUERTO_CARGAR_PARTIDA,
                pipelineEntradaCargarPartida
        );
    }
}
