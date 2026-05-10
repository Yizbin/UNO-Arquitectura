package Main;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import DTOs.EstadoPartidaDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Factory.DispatcherFactory;
import Interfaces.ISink;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import Serializador.Serializador;
import java.net.Socket;
import javax.swing.SwingUtilities;
import pipeline.CoordinadorFiltros;

public class Ensamblador {

    public static void main(String[] args) {
        // En un caso real, aquí obtendrías el socket de un login o diálogo de conexión
        // Socket socket = new Socket("127.0.0.1", 5000); 
        
        SwingUtilities.invokeLater(() -> {
            // Pasamos un socket (puedes mockear el socket para pruebas de compilación)
            configurarConexionRed(null); 
        });
    }

    private static void configurarConexionRed(Socket socket) {
        // --- 1. CONFIGURACIÓN DE RED (CAPA FÍSICA) ---
        // Obtenemos el sumidero de bytes para enviar al servidor
        ISink<byte[]> dispatcher = DispatcherFactory.crearDispatcher("127.0.0.1", 5000);

        // --- 2. PIPELINE DE SALIDA (MVC -> RED) ---
        // Entrada: PeticionJugadaDTO -> Salida: byte[]
        CoordinadorFiltros<PeticionJugadaDTO, byte[]> pipelineSalida = new CoordinadorFiltros<>();
        pipelineSalida.agregarFiltro(new Serializador<PeticionJugadaDTO>()); // Convierte DTO a JSON bytes
        pipelineSalida.conectarDestino(dispatcher); // Al final, envía por el socket

        // --- 3. MODELO Y VISTA ---
        ModeloJuego modelo = new ModeloJuego();
        modelo.setIdJugadorLocal(1); // Esto vendría de la respuesta del servidor al unirse
        modelo.conectarDestino(pipelineSalida); // El modelo bombea al pipeline de salida

        // --- 4. PIPELINE DE ENTRADA (RED -> MVC) ---
        // Entrada: byte[] -> Salida: EstadoPartidaDTO
        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        pipelineEntrada.agregarFiltro(new Deserializador<>(EstadoPartidaDTO.class));
        pipelineEntrada.conectarDestino(modelo); // El resultado final actualiza el modelo

        // Iniciamos la escucha de red y conectamos la bomba (Receptor) al inicio del pipeline
        // ReceptorFactory.iniciarConexion(socket, pipelineEntrada);

        // --- 5. MOCK DEL ESTADO INICIAL ---
        // Para que la pantalla no aparezca vacía antes de que el servidor responda
        EstadoPartidaDTO estadoInicial = crearEstadoMock(1);
        modelo.enviar(new Plantilla.ContextoPipeline<>(estadoInicial));

        // --- 6. LANZAR INTERFAZ ---
        PantallaTurno ventana = FabricaJugadorMVC.crearEntornoJugador(
                pipelineSalida, 1, "UNO Spin - Cliente Red", 100, 100
        );
        ventana.setVisible(true);
    }

    /**
     * Crea un estado ficticio para que la vista cargue inicialmente 
     * mientras esperamos datos del servidor.
     */
    private static EstadoPartidaDTO crearEstadoMock(int idLocal) {
        EstadoPartidaDTO mock = new EstadoPartidaDTO();
        mock.setIdJugadorEnTurno(idLocal);
        return mock;
    }
}
