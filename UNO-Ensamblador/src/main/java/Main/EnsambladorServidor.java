/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package Main;

import Adapter.Adapter;
import Conexiones.Control;
import DTOs.ConexionJugadorDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.PaqueteRedDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Estado.EstadoPartida;
import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Filtro.DominioFiltro;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Serializador.Serializador;
import java.util.List;
import pipeline.CoordinadorFiltros;

/**
 *
 * @author Abraham Coronel
 */
public class EnsambladorServidor {

    public static void main(String[] args) {
        System.out.println("Iniciando Servidor de UNO...");
        configurarServidor(5000);
    }

    private static void configurarServidor(int puertoEscucha) {
        // --- 1. CAPA DE SALIDA (Para enviar el estado a los jugadores) ---
        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        ISink<List<PaqueteRedDTO>> adapterSink = new Adapter(dispatcher);

        // Pipeline de Salida: EstadoPartidaDTO -> byte[] -> List<PaqueteRedDTO>
        CoordinadorFiltros<EstadoPartidaDTO, List<PaqueteRedDTO>> pipelineSalida = new CoordinadorFiltros<>();
        pipelineSalida.agregarFiltro(new Serializador<EstadoPartidaDTO>());
        
        // El filtro Control del servidor tendrá la lista de todos los jugadores conectados
        Control filtroControlServidor = new Control();
        
        // MOCK: Registramos a los jugadores que esperamos (en un caso real esto es dinámico)
        filtroControlServidor.registrarJugador(new ConexionJugadorDTO(1, "127.0.0.1", 5001));
        
        pipelineSalida.agregarFiltro(filtroControlServidor);
        pipelineSalida.conectarDestino(adapterSink);

        // --- 2. CAPA DE ENTRADA (Para recibir y procesar jugadas) ---
        // Pipeline de Entrada: byte[] -> PeticionJugadaDTO -> Procesamiento
        CoordinadorFiltros<byte[], PeticionJugadaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        
        // 1. Convertimos los bytes del socket a objeto
        pipelineEntrada.agregarFiltro(new Deserializador<>(PeticionJugadaDTO.class));
        
        // 2. Filtro de Dominio (Valida si la carta es legal)
        pipelineEntrada.agregarFiltro(new DominioFiltro());
        
        // 3. Filtro de Estado (Actualiza la mesa y genera la respuesta de salida)
        // Este filtro al final llama a pipelineSalida.enviar()
        EstadoPartida filtroEstado = new EstadoPartida(); 
        pipelineEntrada.agregarFiltro(filtroEstado);

        // --- 3. INICIO DE RED ---
        // El servidor abre el puerto 5000 y manda lo que reciba al pipeline de entrada
        ReceptorFactory.iniciarConexion(puertoEscucha, pipelineEntrada);
        
        System.out.println("Servidor escuchando en el puerto: " + puertoEscucha);
    }

}
