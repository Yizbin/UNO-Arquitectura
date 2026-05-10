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
import Interfaces.ISink;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import Plantilla.ContextoPipeline;
import Serializador.Serializador;
import java.util.List;
import javax.swing.SwingUtilities;
import pipeline.CoordinadorFiltros;

public class Ensamblador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configurarConexionRed("127.0.0.1", 5000);
        });
    }

    private static void configurarConexionRed(String ipServidor, int puertoServidor) {
        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();

        ISink<List<PaqueteRedDTO>> adapterSink = new Adapter(dispatcher);

        CoordinadorFiltros<PeticionJugadaDTO, List<PaqueteRedDTO>> pipelineSalida = new CoordinadorFiltros<>();

        pipelineSalida.agregarFiltro(new Serializador<PeticionJugadaDTO>());

        Control filtroControl = new Control();

        filtroControl.registrarJugador(new ConexionJugadorDTO(0, ipServidor, puertoServidor));

        pipelineSalida.agregarFiltro(filtroControl);

        pipelineSalida.conectarDestino(adapterSink);

        ModeloJuego modelo = new ModeloJuego();
        modelo.setIdJugadorLocal(1);
        modelo.conectarDestino(pipelineSalida);

        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        pipelineEntrada.agregarFiltro(new Deserializador<>(EstadoPartidaDTO.class));
        pipelineEntrada.conectarDestino(modelo);

        ReceptorFactory.iniciarConexion(puertoServidor + 1, pipelineEntrada);

        EstadoPartidaDTO estadoInicial = crearEstadoMock(1);
        modelo.enviar(new ContextoPipeline<>(estadoInicial));

        PantallaTurno ventana = FabricaJugadorMVC.crearEntornoJugador(
                pipelineSalida, 1, "UNO Spin - Cliente Red", 100, 100
        );
        ventana.setVisible(true);
    }

    private static EstadoPartidaDTO crearEstadoMock(int idLocal) {
        EstadoPartidaDTO mock = new EstadoPartidaDTO();
        mock.setIdJugadorEnTurno(idLocal);
        return mock;
    }
}
