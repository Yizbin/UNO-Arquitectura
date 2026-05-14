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
        System.out.println("=====================================");
        System.out.println("  Iniciando Servidor de UNO Spin...  ");
        System.out.println("=====================================");
        configurarServidor(5000);
    }

    private static void configurarServidor(int puertoEscucha) {
        SubDominioConcreto subDominio = new SubDominioConcreto();

        simularFinDeSalaDeEsperaMock(subDominio); //Metodo Mock

        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        ISink<List<PaqueteRedDTO>> adapterSink = new Adapter(dispatcher);

        CoordinadorFiltros<EstadoPartidaDTO, List<PaqueteRedDTO>> pipelineSalida = new CoordinadorFiltros<>();
        pipelineSalida.agregarFiltro(new Serializador<EstadoPartidaDTO>());

        Control filtroControlServidor = new Control();
        pipelineSalida.agregarFiltro(filtroControlServidor);
        pipelineSalida.conectarDestino(adapterSink);

        CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada = new CoordinadorFiltros<>();
        pipelineEntrada.agregarFiltro(new Deserializador<>(PeticionJugadaDTO.class));
        pipelineEntrada.agregarFiltro(new DominioFiltro(subDominio));
        pipelineEntrada.agregarFiltro(new EstadoPartida());
        pipelineEntrada.conectarDestino(pipelineSalida);

        int[] idJugadorActual = {1};
        int puertoRespuestaCliente = puertoEscucha + 1;
        List<String> ipsConectadas = new ArrayList<>();

        ReceptorFactory.iniciarConexion(puertoEscucha, pipelineEntrada, (String ipCliente) -> {
            if (!ipsConectadas.contains(ipCliente)) {

                ipsConectadas.add(ipCliente);

                int id = idJugadorActual[0]++;
                filtroControlServidor.registrarJugador(new ConexionJugadorDTO(id, ipCliente, puertoRespuestaCliente));

                System.out.println("==================================================");
                System.out.println(">>> NUEVO JUGADOR CONECTADO Y REGISTRADO <<<");
                System.out.println("    Jugador ID: " + id);
                System.out.println("    IP Origen: " + ipCliente);
                System.out.println("    Puerto Asignado: " + puertoRespuestaCliente);
                System.out.println("==================================================");
            }
        });

        System.out.println("\n[ESTADO] Servidor escuchando peticiones en el puerto: " + puertoEscucha);
        System.out.println("[ESTADO] Listo para jugar.");
    }

    private static void simularFinDeSalaDeEsperaMock(SubDominioConcreto subDominio) {
        int cantidadJugadores = 2;
        List<JugadorResumenDTO> jugadoresIniciales = new ArrayList<>();
        for (int i = 1; i <= cantidadJugadores; i++) {
            jugadoresIniciales.add(new JugadorResumenDTO(i, "Jugador " + i));
        }
        try {
            subDominio.prepararJuego(jugadoresIniciales);
        } catch (MazoVacioException e) {
            System.err.println("Error al preparar la partida (Mock): " + e.getMessage());
        }
    }
}
