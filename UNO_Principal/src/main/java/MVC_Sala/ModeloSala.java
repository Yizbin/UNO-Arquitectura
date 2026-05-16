/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Interfaces.IPump;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloSala implements IControlModeloSala, IModeloSalaVista, ISink<EstadoPartidaDTO>, IPump<PeticionJugadaDTO> {

    private final List<ISuscriptorSala> suscriptores;

    private List<JugadorResumenDTO> jugadoresEnSala;

    private ISink<PeticionJugadaDTO> destino;

    private JugadorResumenDTO jugadorLocal;

    public ModeloSala() {
        this.suscriptores = new ArrayList<>();
        this.jugadoresEnSala = new ArrayList<>();
    }

    public void suscribir(ISuscriptorSala suscriptor) {
        if (!suscriptores.contains(suscriptor)) {
            suscriptores.add(suscriptor);
        }
    }

    public void desuscribir(ISuscriptorSala suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public void notificar() {
        for (ISuscriptorSala s : suscriptores) {
            s.update(this);
        }
    }

    @Override
    public boolean solicitarUnirsePartida() {
        if (destino != null) {
            try {
                PeticionJugadaDTO peticion = new PeticionJugadaDTO(TipoAccionPartida.UNIRSE_PARTIDA, jugadorLocal.getId());
                destino.enviar(new ContextoPipeline<>(peticion));
                return true;
            } catch (Exception e) {
                System.err.println("Error al unirse: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public void actualizarDatosJugador(JugadorResumenDTO datos) {
        if (destino != null && datos != null) {
            try {
                this.jugadorLocal = datos;
                PeticionJugadaDTO peticion = new PeticionJugadaDTO(TipoAccionPartida.ACTUALIZAR_PERFIL, jugadorLocal.getId());
                destino.enviar(new ContextoPipeline<>(peticion));
            } catch (Exception e) {
                System.err.println("Error al actualizar perfil: " + e.getMessage());
            }
        }
    }

    @Override
    public void enviar(ContextoPipeline<EstadoPartidaDTO> contexto) throws Exception {
        EstadoPartidaDTO estado = contexto.getMensaje();

        if (estado != null) {
            this.jugadoresEnSala = estado.getJugadores();
            notificar();
        }
    }

    @Override
    public void conectarDestino(ISink<PeticionJugadaDTO> destino) {
        this.destino = destino;
    }

    @Override
    public List<JugadorResumenDTO> getJugadoresEnSala() {
        return this.jugadoresEnSala;
    }

}
