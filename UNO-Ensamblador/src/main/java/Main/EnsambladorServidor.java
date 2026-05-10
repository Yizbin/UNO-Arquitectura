/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Adapter.Adapter;
import Conexiones.Control;
import DTOs.ConexionJugadorDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PaqueteRedDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Estado.EstadoPartida;
import Excepciones.MazoVacioException;
import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Filtro.DominioFiltro;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Interfaces.SubDominioConcreto;
import Serializador.Serializador;
import java.util.ArrayList;
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
        // --- 0. DOMINIO COMPARTIDO ---
        // ✅ Una sola instancia compartida entre todos los filtros que la necesiten
        SubDominioConcreto subDominio = new SubDominioConcreto();

        // ✅ Preparar la partida con los jugadores conocidos antes de abrir el puerto
        List<JugadorResumenDTO> jugadoresIniciales = new ArrayList<>();
        jugadoresIniciales.add(new JugadorResumenDTO(1, "Jugador1"));
        // Agregar los demás jugadores según tu lógica de lobby...
        try {
            subDominio.prepararJuego(jugadoresIniciales);
        } catch (MazoVacioException e) {
            System.err.println("Error al preparar la partida: " + e.getMessage());
            return;
        }

        // --- 1. CAPA DE SALIDA ---
        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        ISink<List<PaqueteRedDTO>> adapterSink = new Adapter(dispatcher);

        CoordinadorFiltros<EstadoPartidaDTO, List<PaqueteRedDTO>> pipelineSalida = new CoordinadorFiltros<>();
        pipelineSalida.agregarFiltro(new Serializador<EstadoPartidaDTO>());

        Control filtroControlServidor = new Control();
        filtroControlServidor.registrarJugador(new ConexionJugadorDTO(1, "127.0.0.1", 5001));
        pipelineSalida.agregarFiltro(filtroControlServidor);
        pipelineSalida.conectarDestino(adapterSink);

        // --- 2. CAPA DE ENTRADA ---
        CoordinadorFiltros<byte[], PeticionJugadaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        pipelineEntrada.agregarFiltro(new Deserializador<>(PeticionJugadaDTO.class));

        // ✅ Se pasa la instancia compartida al filtro
        pipelineEntrada.agregarFiltro(new DominioFiltro(subDominio));

        EstadoPartida filtroEstado = new EstadoPartida();
        pipelineEntrada.agregarFiltro(filtroEstado);

        // --- 3. RED ---
        ReceptorFactory.iniciarConexion(puertoEscucha, pipelineEntrada);
        System.out.println("Servidor escuchando en el puerto: " + puertoEscucha);
    }

}
