package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Adapter.AdapterCliente;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Enums.TipoAccionPartida;

import Entidades.Partida;

import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Filtro.DominioFiltro;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Interfaces.SubDominioConcreto;
import MVC_JugarTurno.PantallaTurno;
import Serializador.Serializador;
import javax.swing.SwingUtilities;
import pipeline.CoordinadorFiltros;

public class Ensamblador {

    private static final String IP_SERVIDOR = "192.168.0.102";
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
                = new CoordinadorFiltros<>();

        pipelineSalida.agregarFiltro(new DominioFiltro(subDominio));
        pipelineSalida.agregarFiltro(new Serializador<EstadoPartidaDTO>());
        pipelineSalida.conectarDestino(adapterSink);

        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada
                = new CoordinadorFiltros<>();

        pipelineEntrada.agregarFiltro(
                new Deserializador<>(EstadoPartidaDTO.class)
        );

        PantallaTurno ventana = FabricaJugadorMVC.crearEntornoJugador(
                pipelineSalida,
                pipelineEntrada,
                1,
                "UNO Spin - Cliente Red",
                100,
                100
        );

        ventana.setVisible(true);

        ReceptorFactory.iniciarConexion(puertoServidor + 1, pipelineEntrada);

        registrarJugadorLocal(pipelineSalida, 1);
    }


    private static void registrarJugadorLocal(
            CoordinadorFiltros<PeticionJugadaDTO, byte[]> pipelineSalida,
            int idJugador) {
        try {
            JugadorResumenDTO jugador = new JugadorResumenDTO(idJugador, "Jugador " + idJugador);
            PeticionJugadaDTO peticion = new PeticionJugadaDTO(TipoAccionPartida.UNIRSE_PARTIDA, jugador);
            pipelineSalida.procesar(new Plantilla.ContextoPipeline<>(peticion));
            System.out.println("Jugador local registrado en partida: " + idJugador);
        } catch (Exception e) {
            System.err.println("No se pudo registrar el jugador local: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
