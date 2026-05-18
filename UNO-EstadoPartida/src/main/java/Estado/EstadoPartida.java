/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estado;

import DTOs.EstadoPartidaDTO;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class EstadoPartida implements IFiltro<EstadoPartidaDTO, EstadoPartidaDTO> {

    private final EstadoPartidaDTO estadoActual;

    public EstadoPartida() {
        this.estadoActual = new EstadoPartidaDTO();
        this.estadoActual.setJugadores(List.of());
        this.estadoActual.setManoJugadorActual(List.of());
        this.estadoActual.setSolicitudesPendientes(List.of());
        this.estadoActual.setMensajeEstado("");
    }

    @Override
    public ContextoPipeline<EstadoPartidaDTO> procesar(ContextoPipeline<EstadoPartidaDTO> contexto) {

        if (contexto == null || contexto.estaDetenido()) {
            return contexto;
        }

        EstadoPartidaDTO estadoRecibido = contexto.getMensaje();

        if (estadoRecibido == null) {
            return new ContextoPipeline<>(estadoActual);
        }

        actualizarEstado(estadoRecibido);

        return new ContextoPipeline<>(estadoActual);
    }

    private void actualizarEstado(EstadoPartidaDTO estadoRecibido) {
        estadoActual.setIdJugador(estadoRecibido.getIdJugador());
        estadoActual.setJugadores(
                estadoRecibido.getJugadores() != null
                ? estadoRecibido.getJugadores()
                : List.of()
        );
        estadoActual.setManoJugadorActual(
                estadoRecibido.getManoJugadorActual() != null
                ? estadoRecibido.getManoJugadorActual()
                : List.of()
        );
        estadoActual.setCartaEnDescarte(estadoRecibido.getCartaEnDescarte());
        estadoActual.setEstadoReto(estadoRecibido.getEstadoReto());
        estadoActual.setRuletaActiva(estadoRecibido.isRuletaActiva());
        estadoActual.setPuedeTirarCarta(estadoRecibido.isPuedeTirarCarta());
        estadoActual.setPuedeRobar(estadoRecibido.isPuedeRobar());
        estadoActual.setPuedeDecirUno(estadoRecibido.isPuedeDecirUno());
        estadoActual.setEsperandoColor(estadoRecibido.isEsperandoColor());
        estadoActual.setColorSeleccionado(estadoRecibido.getColorSeleccionado());
        estadoActual.setMensajeEstado(
                estadoRecibido.getMensajeEstado() != null
                ? estadoRecibido.getMensajeEstado()
                : ""
        );
        estadoActual.setPartidaListaParaIniciar(estadoRecibido.isPartidaListaParaIniciar());
        estadoActual.setIdAnfitrion(estadoRecibido.getIdAnfitrion());
        estadoActual.setSolicitudesPendientes(
                estadoRecibido.getSolicitudesPendientes() != null
                ? estadoRecibido.getSolicitudesPendientes()
                : List.of()
        );
    }
}
