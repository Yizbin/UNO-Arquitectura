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
import javax.swing.SwingUtilities;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloSala implements IControlModeloSala, IModeloSalaVista, ISink<EstadoPartidaDTO> {

    private final List<ISuscriptorSala> suscriptores;
    private IPump<PeticionJugadaDTO> coordinador;
    private List<JugadorResumenDTO> jugadoresEnSala;
    private JugadorResumenDTO jugadorLocal;

    public ModeloSala() {
        this.suscriptores = new ArrayList<>();
        this.jugadoresEnSala = new ArrayList<>();
    }

    public ModeloSala(IPump<PeticionJugadaDTO> coordinador) {
        this();
        this.coordinador = coordinador;
    }

    public void conectarCoordinador(IPump<PeticionJugadaDTO> coordinador) {
        this.coordinador = coordinador;
    }

    @Override
    public void suscribir(ISuscriptorSala suscriptor) {
        if (!suscriptores.contains(suscriptor)) {
            suscriptores.add(suscriptor);
        }
    }

    @Override
    public void desuscribir(ISuscriptorSala suscriptor) {
        suscriptores.remove(suscriptor);
    }

    @Override
    public void notificar() {
        for (ISuscriptorSala s : suscriptores) {
            s.update(this);
        }
    }

    @Override
    public boolean solicitarUnirsePartida() {
        if (coordinador == null || jugadorLocal == null) {
            return false;
        }

        try {
            PeticionJugadaDTO peticion = new PeticionJugadaDTO(
                    TipoAccionPartida.UNIRSE_PARTIDA,
                    jugadorLocal.getId()
            );
            enviarPeticion(peticion);
            return true;
        } catch (Exception e) {
            System.err.println("Error al unirse: " + e.getMessage());
        }

        return false;
    }

    @Override
    public void actualizarDatosJugador(JugadorResumenDTO datos) {
        if (datos == null) {
            return;
        }

        this.jugadorLocal = datos;

        if (coordinador == null) {
            return;
        }

        try {
            PeticionJugadaDTO peticion = new PeticionJugadaDTO(
                    TipoAccionPartida.ACTUALIZAR_PERFIL,
                    jugadorLocal.getId()
            );
            enviarPeticion(peticion);
        } catch (Exception e) {
            System.err.println("Error al actualizar perfil: " + e.getMessage());
        }
    }

    @Override
    public void enviar(ContextoPipeline<EstadoPartidaDTO> contexto) throws Exception {
        if (contexto == null || contexto.estaDetenido()) {
            return;
        }

        EstadoPartidaDTO estado = contexto.getMensaje();
        if (estado == null) {
            return;
        }

        this.jugadoresEnSala = estado.getJugadores() != null
                ? estado.getJugadores()
                : List.of();
        SwingUtilities.invokeLater(this::notificar);
    }

    private void enviarPeticion(PeticionJugadaDTO peticion) throws Exception {
        coordinador.procesar(new ContextoPipeline<>(peticion));
    }

    @Override
    public List<JugadorResumenDTO> getJugadoresEnSala() {
        return this.jugadoresEnSala;
    }

}
