package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Adapter.Adapter;
import Conexiones.Control;
import DTOs.ConexionJugadorDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.PaqueteRedDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Interfaces.IConexionSalida;
import Interfaces.IPump;
import Interfaces.ISink;
import Serializador.Serializador;
import java.util.List;
import javax.swing.SwingUtilities;
import pipeline.CoordinadorFiltros;

public class Ensamblador {

    private static final String IP_SERVIDOR = "192.168.1.67";
    private static final int PUERTO_SERVIDOR = 5000;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configurarConexionRed(IP_SERVIDOR, PUERTO_SERVIDOR);
        });
    }

    private static void configurarConexionRed(String ipServidor, int puertoServidor) {

        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        dispatcher.preConectar(ipServidor, puertoServidor);
        ISink<List<PaqueteRedDTO>> adapterSink = new Adapter(dispatcher);

        CoordinadorFiltros<PeticionJugadaDTO, List<PaqueteRedDTO>> pipelineSalida = new CoordinadorFiltros<>();
        pipelineSalida.agregarFiltro(new Serializador<PeticionJugadaDTO>());

        Control filtroControl = new Control();
        filtroControl.registrarJugador(new ConexionJugadorDTO(0, ipServidor, puertoServidor));
        pipelineSalida.agregarFiltro(filtroControl);

        pipelineSalida.conectarDestino(adapterSink);

        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        pipelineEntrada.agregarFiltro(new Deserializador<>(EstadoPartidaDTO.class));

        ReceptorFactory.iniciarConexion(puertoServidor + 1, pipelineEntrada);

        probarCasoUsoJugarTurnoMock(pipelineSalida, pipelineEntrada); //Mock
    }

    private static void probarCasoUsoJugarTurnoMock(
            ISink<PeticionJugadaDTO> pipelineSalida,
            IPump<EstadoPartidaDTO> pipelineEntrada) {

        int idJugadorLocal = 1;

        MVC_JugarTurno.PantallaTurno ventana = FabricaJugadorMVC.crearEntornoJugador(
                pipelineSalida,
                pipelineEntrada,
                idJugadorLocal,
                "UNO Spin - Cliente Red (Jugador " + idJugadorLocal + ")",
                100, 100
        );
        ventana.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(500);
                PeticionJugadaDTO peticionMock = new PeticionJugadaDTO();
                peticionMock.setIdJugador(idJugadorLocal);
                peticionMock.setAccion(Enums.TipoAccionPartida.UNIRSE_PARTIDA);

                pipelineSalida.enviar(new Plantilla.ContextoPipeline<>(peticionMock));
                System.out.println("Peticion inicial enviada.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
