package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Adapter.AdapterCliente;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Enums.TipoAccionPartida;

import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import MVC_JugarTurno.PantallaTurno;
import Serializador.Serializador;
import javax.swing.SwingUtilities;
import pipeline.CoordinadorFiltros;

public class Ensamblador {

    private static final String IP_SERVIDOR = "192.168.0.102";
    private static final int PUERTO_SERVIDOR = 5000;

    public static void main(String[] args) {
        int idJugador = obtenerIdJugador(args);
        SwingUtilities.invokeLater(() -> {
            configurarConexionRed(IP_SERVIDOR, PUERTO_SERVIDOR, idJugador);
        });
    }

    private static int obtenerIdJugador(String[] args) {
        if (args == null || args.length == 0) {
            return 1;
        }

        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private static void configurarConexionRed(String ipServidor, int puertoServidor, int idJugador) {

        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        dispatcher.preConectar(ipServidor, puertoServidor);

        ISink<byte[]> adapterSink
                = new AdapterCliente(ipServidor, puertoServidor, dispatcher);

        CoordinadorFiltros<PeticionJugadaDTO, byte[]> pipelineSalida
                = new CoordinadorFiltros<>();

        pipelineSalida.agregarFiltro(new Serializador<PeticionJugadaDTO>());
        pipelineSalida.conectarDestino(adapterSink);

        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada
                = new CoordinadorFiltros<>();

        pipelineEntrada.agregarFiltro(
                new Deserializador<>(EstadoPartidaDTO.class)
        );

        PantallaTurno ventana = FabricaJugadorMVC.crearEntornoJugador(
                pipelineSalida,
                pipelineEntrada,
                idJugador,
                "UNO Spin - Cliente Red",
                100,
                100
        );

        ventana.setVisible(true);

        ReceptorFactory.iniciarConexion(puertoServidor + 1, pipelineEntrada);

        registrarJugadorLocal(pipelineSalida, idJugador);
    }


    private static void registrarJugadorLocal(
            CoordinadorFiltros<PeticionJugadaDTO, byte[]> pipelineSalida,
            int idJugador) {
        try {
            JugadorResumenDTO jugador = new JugadorResumenDTO(idJugador, "Jugador " + idJugador);
            EstadoPartidaDTO estado = new EstadoPartidaDTO();
            estado.setIdJugador(jugador.getId());
            PeticionJugadaDTO peticion = new PeticionJugadaDTO(TipoAccionPartida.UNIRSE_PARTIDA, estado);
            pipelineSalida.procesar(new Plantilla.ContextoPipeline<>(peticion));
            System.out.println("Jugador local registrado en partida: " + idJugador);
        } catch (Exception e) {
            System.err.println("No se pudo registrar el jugador local: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
