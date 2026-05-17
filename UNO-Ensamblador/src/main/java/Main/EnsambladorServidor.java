/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Adapter.AdapterServidor;
import Conexiones.Control;
import DTOs.ConexionJugadorDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.PaqueteRedDTO;
import Deserializador.Deserializador;
import Estado.EstadoPartida;
import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Serializador.Serializador;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        IConexionSalida dispatcher = DispatcherFactory.crearDispatcher();
        ISink<List<PaqueteRedDTO>> adapterSink =
                new AdapterServidor(dispatcher);

        Control filtroControlServidor = new Control();

        CoordinadorFiltros<byte[], List<PaqueteRedDTO>> pipelineServidor =
                new CoordinadorFiltros<>();

        pipelineServidor.agregarFiltro(
                new Deserializador<>(EstadoPartidaDTO.class)
        );
        pipelineServidor.agregarFiltro(new EstadoPartida());
        pipelineServidor.agregarFiltro(new Serializador<EstadoPartidaDTO>());
        pipelineServidor.agregarFiltro(filtroControlServidor);
        pipelineServidor.conectarDestino(adapterSink);

        int[] idJugadorActual = {1};
        int puertoRespuestaCliente = puertoEscucha + 1;
        Set<String> ipsConectadas = new HashSet<>();

        ReceptorFactory.iniciarConexion(
                puertoEscucha,
                pipelineServidor,
                (String ipCliente) -> {
                    if (ipsConectadas.add(ipCliente)) {
                        int id = idJugadorActual[0]++;
                        filtroControlServidor.registrarJugador(
                                new ConexionJugadorDTO(
                                        id,
                                        ipCliente,
                                        puertoRespuestaCliente
                                )
                        );

                        System.out.println("==================================================");
                        System.out.println(">>> NUEVO JUGADOR CONECTADO Y REGISTRADO <<<");
                        System.out.println("    Jugador ID: " + id);
                        System.out.println("    IP Origen: " + ipCliente);
                        System.out.println("    Puerto Asignado: " + puertoRespuestaCliente);
                        System.out.println("==================================================");
                    }
                }
        );

        System.out.println(
                "\n[ESTADO] Servidor escuchando peticiones en el puerto: "
                + puertoEscucha
        );
        System.out.println("[ESTADO] Listo para jugar.");
    }
}