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
        SubDominioConcreto subDominio = new SubDominioConcreto();

        int cantidadJugadores = 1;

        List<JugadorResumenDTO> jugadoresIniciales = new ArrayList<>();
        for (int i = 1; i <= cantidadJugadores; i++) {
            jugadoresIniciales.add(new JugadorResumenDTO(i, "Jugador" + i));
        }

        try {
            subDominio.prepararJuego(jugadoresIniciales);
        } catch (MazoVacioException e) {
            System.err.println("Error al preparar la partida: " + e.getMessage());
            return;
        }

        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        ISink<List<PaqueteRedDTO>> adapterSink = new Adapter(dispatcher);

        CoordinadorFiltros<EstadoPartidaDTO, List<PaqueteRedDTO>> pipelineSalida = new CoordinadorFiltros<>();
        pipelineSalida.agregarFiltro(new Serializador<EstadoPartidaDTO>());

        Control filtroControlServidor = new Control();

        for (int i = 1; i <= cantidadJugadores; i++) {
            filtroControlServidor.registrarJugador(
                    new ConexionJugadorDTO(i, "127.0.0.1", puertoEscucha + i)
            );
        }

        pipelineSalida.agregarFiltro(filtroControlServidor);
        pipelineSalida.conectarDestino(adapterSink);

        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        pipelineEntrada.agregarFiltro(new Deserializador<>(PeticionJugadaDTO.class));
        pipelineEntrada.agregarFiltro(new DominioFiltro(subDominio));
        pipelineEntrada.agregarFiltro(new EstadoPartida());
        pipelineEntrada.conectarDestino(pipelineSalida); // respuesta va a los clientes

        ReceptorFactory.iniciarConexion(puertoEscucha, pipelineEntrada);
        System.out.println("Servidor escuchando en el puerto: " + puertoEscucha);
    }

}
