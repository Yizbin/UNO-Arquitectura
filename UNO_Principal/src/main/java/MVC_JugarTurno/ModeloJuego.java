/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Enums.TipoColor;
import Interfaces.IPump;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloJuego implements IControlModelo, IModeloVista, ISink<EstadoPartidaDTO>, IPump<PeticionJugadaDTO> {

    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private EstadoPartidaDTO estadoActual;
    private ISink<PeticionJugadaDTO> destinoTuberias;
    private int idJugadorLocal;
    private String mensajePendiente;

    public void setIdJugadorLocal(int idJugadorLocal) {
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public void conectarDestino(ISink<PeticionJugadaDTO> destino) {
        this.destinoTuberias = destino;
    }

    public void realizarAccionJugador(PeticionJugadaDTO jugada) {
        if (destinoTuberias != null) {
            ContextoPipeline<PeticionJugadaDTO> contexto = new ContextoPipeline<>(jugada);
            try {
                destinoTuberias.enviar(contexto);
            } catch (Exception e) {
                System.err.println("Error al bombear la jugada a la tubería: " + e.getMessage());
            }
        }
    }

    @Override
    public void iniciarJuego(List<JugadorResumenDTO> jugadores) {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setIdJugador(idJugadorLocal);
        peticion.setAccion(TipoAccionPartida.INICIAR_PARTIDA);
        realizarAccionJugador(peticion);
    }

    @Override
    public void robarCarta() {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setIdJugador(idJugadorLocal);
        peticion.setAccion(TipoAccionPartida.ROBAR_CARTA);
        realizarAccionJugador(peticion);
    }

    @Override
    public void jugarCarta(CartaDTO carta) {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setIdJugador(idJugadorLocal);
        peticion.setAccion(TipoAccionPartida.JUGAR_CARTA);
        peticion.setCartaAJugar(carta);
        realizarAccionJugador(peticion);
    }

    @Override
    public void seleccionarColor(TipoColor color) {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setIdJugador(idJugadorLocal);
        peticion.setAccion(TipoAccionPartida.ELEGIR_COLOR);
        peticion.setNuevoColor(color);
        realizarAccionJugador(peticion);
    }

    @Override
    public void enviar(ContextoPipeline<EstadoPartidaDTO> contexto) {
        if (contexto != null && !contexto.estaDetenido()) {
            this.estadoActual = contexto.getMensaje();
            SwingUtilities.invokeLater(this::notificar);
        }
    }

    @Override
    public int getIdJugadorLocal() {
        return this.idJugadorLocal;
    }

    @Override
    public EstadoPantallaTurnoDTO getEstadoPantalla() {
        if (estadoActual == null) {
            return null;
        }

        EstadoPantallaTurnoDTO vista = new EstadoPantallaTurnoDTO();
        vista.setCartaEnDescarte(estadoActual.getCartaEnDescarte());
        vista.setManoLocal(estadoActual.getManoJugadorActual() != null ? estadoActual.getManoJugadorActual() : List.of());
        vista.setTurnoLocal(estadoActual.getIdJugadorEnTurno() == this.idJugadorLocal);

        List<JugadorResumenDTO> jugadores = estadoActual.getJugadores() != null
                ? estadoActual.getJugadores()
                : List.of();

        List<JugadorResumenDTO> remotos = new ArrayList<>();
        for (JugadorResumenDTO jugador : jugadores) {
            if (jugador.getId() == this.idJugadorLocal) {
                vista.setJugadorLocal(jugador);
            } else {
                remotos.add(jugador);
            }
        }

        if (!remotos.isEmpty()) {
            vista.setJugadorEste(remotos.get(0));
        }
        if (remotos.size() > 1) {
            vista.setJugadorNorte(remotos.get(1));
        }
        if (remotos.size() > 2) {
            vista.setJugadorOeste(remotos.get(2));
        }

        return vista;
    }

    @Override
    public String consumirMensajePendiente() {
        String msg = this.mensajePendiente;
        this.mensajePendiente = null;
        return msg;
    }

    public void suscribir(ISuscriptor suscriptor) {
        if (!suscriptores.contains(suscriptor)) {
            suscriptores.add(suscriptor);
        }
    }

    public void desuscribir(ISuscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public void notificar() {
        for (ISuscriptor s : suscriptores) {
            s.update(this);
        }
    }
}
