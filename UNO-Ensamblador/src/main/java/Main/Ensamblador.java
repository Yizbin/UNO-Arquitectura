package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Adapter.Adapter;
import Conexiones.Control;
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
import Serializador.Serializador;
import javax.swing.SwingUtilities;
import pipeline.CoordinadorFiltros;

public class Ensamblador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configurarConexionRed(5000);
        });
    }

    private static void configurarConexionRed(int puertoServidor) {
        // --- 1. CONFIGURACIÓN DE SALIDA (CAPA FÍSICA Y ADAPTADOR) ---
        // El Dispatcher ahora es el gestor de conexiones (IConexionSalida)
        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();

        // El Adapter actúa como el Sink final de la tubería, recibiendo PaqueteRedDTO
        ISink<PaqueteRedDTO> adapterSink = new Adapter<>(dispatcher);

        // --- 2. PIPELINE DE SALIDA (MVC -> RED) ---
        // Entrada: PeticionJugadaDTO -> Salida: PaqueteRedDTO
        CoordinadorFiltros<PeticionJugadaDTO, PaqueteRedDTO> pipelineSalida = new CoordinadorFiltros<>();

        // El Serializador convierte de Objeto a byte[]
        pipelineSalida.agregarFiltro(new Serializador<PeticionJugadaDTO>());

        // El Filtro Control toma los bytes y les asigna IP/Puerto, devolviendo un PaqueteRedDTO
        // Nota: Asegúrate que tu clase Control implemente IFiltro<byte[], PaqueteRedDTO>
        Control<byte[]> filtroControl = new Control<>();
        // Aquí podrías registrar la IP/Puerto del servidor para el cliente
        pipelineSalida.agregarFiltro(filtroControl);

        // Conectamos el final de la tubería al Adapter
        pipelineSalida.conectarDestino(adapterSink);

        // --- 3. MODELO Y VISTA ---
        ModeloJuego modelo = new ModeloJuego();
        modelo.setIdJugadorLocal(1);
        modelo.conectarDestino(pipelineSalida);

        // --- 4. PIPELINE DE ENTRADA (RED -> MVC) ---
        // Entrada: byte[] -> Salida: EstadoPartidaDTO
        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        pipelineEntrada.agregarFiltro(new Deserializador<>(EstadoPartidaDTO.class));
        pipelineEntrada.conectarDestino(modelo);

        // Iniciamos la escucha de red usando el nuevo ReceptorFactory corregido
        ReceptorFactory.iniciarConexion(puertoServidor, pipelineEntrada);

        // --- 5. MOCK DEL ESTADO INICIAL ---
        EstadoPartidaDTO estadoInicial = crearEstadoMock(1);
        modelo.enviar(new Plantilla.ContextoPipeline<>(estadoInicial));

        // --- 6. LANZAR INTERFAZ ---
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
