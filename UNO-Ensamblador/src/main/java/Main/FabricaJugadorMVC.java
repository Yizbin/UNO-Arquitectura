/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DTOs.EstadoPartidaDTO;
import DTOs.PeticionJugadaDTO;
import Interfaces.ISink;
import MVC_JugarTurno.ModeloJuego;
import MVC_JugarTurno.PantallaTurno;
import MVC_JugarTurno.UnoSpinControlador;
import pipeline.CoordinadorFiltros;

/**
 *
 * @author Abraham Coronel
 */
public class FabricaJugadorMVC {

    /**
     * Crea el entorno MVC y lo conecta a la tubería de salida.
     *
     * @param pipelineSalida
     * @param pipelineEntrada
     * @param idJugador ID asignado al jugador local.
     * @param tituloVista Título de la ventana.
     * @param posX Posición X inicial.
     * @param posY Posición Y inicial.
     * @return Instancia de la vista (JFrame) configurada.
     */
    public static PantallaTurno crearEntornoJugador(
            ISink<PeticionJugadaDTO> pipelineSalida,
            CoordinadorFiltros<byte[], EstadoPartidaDTO> pipelineEntrada,
            int idJugador,
            String tituloVista,
            int posX,
            int posY) {

        ModeloJuego modelo = new ModeloJuego();
        modelo.setIdJugadorLocal(idJugador);

        if (pipelineEntrada != null) {
            pipelineEntrada.conectarDestino(modelo);
        }

        UnoSpinControlador controlador = new UnoSpinControlador(modelo);
        PantallaTurno vista = new PantallaTurno(modelo, controlador);
        modelo.suscribir(vista);

        vista.setTitle(tituloVista);
        vista.setLocation(posX, posY);

        return vista;
    }
}
