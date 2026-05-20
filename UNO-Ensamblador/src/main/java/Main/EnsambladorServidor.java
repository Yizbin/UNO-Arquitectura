/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Adapter.AdapterServidor;
import Conexiones.Broadcast;
import DTOs.ConexionJugadorDTO;
import DTOs.PaqueteRedDTO;
import DTOs.PeticionJugadaDTO;
import Deserializador.Deserializador;
import Entidades.Partida;
import Factory.DispatcherFactory;
import Factory.ReceptorFactory;
import Filtro.DominioFiltro;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Interfaces.SubDominioConcreto;
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

        Broadcast filtroBroadcastServidor = new Broadcast();
        SubDominioConcreto subDominio = new SubDominioConcreto(new Partida());

        CoordinadorFiltros<byte[], List<PaqueteRedDTO>> pipelineServidor =
                new CoordinadorFiltros<>(
                        List.of(
                                new Deserializador<>(PeticionJugadaDTO.class),
                                new DominioFiltro(subDominio),
                                new Serializador<PeticionJugadaDTO>(),
                                filtroBroadcastServidor
                        ),
                        adapterSink
        );

        int[] idJugadorActual = {1};
        int puertoCliente = puertoEscucha + 1;
        Set<String> ipsConectadas = new HashSet<>();

        ReceptorFactory.iniciarConexion(
                puertoEscucha,
                pipelineServidor,
                (String ipCliente) -> {
                    if (ipsConectadas.add(ipCliente)) {
                        int id = idJugadorActual[0]++;
                        filtroBroadcastServidor.registrarJugador(
                                new ConexionJugadorDTO(
                                        id,
                                        ipCliente,
                                        puertoCliente
                                )
                        );

                        System.out.println("==================================================");
                        System.out.println(">>> NUEVO JUGADOR CONECTADO Y REGISTRADO <<<");
                        System.out.println("    Jugador ID: " + id);
                        System.out.println("    IP Origen: " + ipCliente);
                        System.out.println("    Puerto Cliente: " + puertoCliente);
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
