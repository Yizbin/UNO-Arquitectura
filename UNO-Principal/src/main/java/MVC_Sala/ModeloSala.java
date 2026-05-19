/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import Enums.EstadoJugadorSala;
import Enums.TipoAccionPartida;
import Enums.TipoColor;
import Plantilla.ContextoPipeline;
import interfaces.IPump;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloSala implements IControlModeloSala, IModeloSalaVista {

    private final List<ISuscriptorSala> suscriptores;
    private IPump<PeticionJugadaDTO, ?> coordinador;
    private List<JugadorResumenDTO> jugadoresEnSala;
    private JugadorResumenDTO jugadorLocal;
    private Map<TipoColor, TipoColor> coloresLocales;
    private boolean cambiarFrame = false;
    private boolean partidaListaParaIniciar;

    public ModeloSala() {
        this.suscriptores = new ArrayList<>();
        this.jugadoresEnSala = new ArrayList<>();
        this.partidaListaParaIniciar = false;
    }

    public ModeloSala(IPump<PeticionJugadaDTO, ?> coordinador) {
        this();
        this.coordinador = coordinador;
    }

    public void conectarCoordinador(IPump<PeticionJugadaDTO, ?> coordinador) {
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
                    TipoAccionPartida.SOLICITAR_UNIRSE_PARTIDA,
                    jugadorLocal,
                    null
            );

            enviarPeticion(peticion);
            return true;
        } catch (Exception e) {
            System.err.println("Error al unirse: " + e.getMessage());
        }

        return false;
    }

    public boolean aceptarSolicitudUnion(int idJugadorSolicitante) {
        if (coordinador == null || jugadorLocal == null) {
            return false;
        }

        try {
            EstadoPartidaDTO estado = new EstadoPartidaDTO();
            estado.setIdJugador(idJugadorSolicitante);

            PeticionJugadaDTO peticion = new PeticionJugadaDTO(
                    TipoAccionPartida.ACEPTAR_SOLICITUD_UNION,
                    estado
            );

            enviarPeticion(peticion);
            return true;
        } catch (Exception e) {
            System.err.println("Error al aceptar solicitud: " + e.getMessage());
            return false;
        }
    }

    public boolean rechazarSolicitudUnion(int idJugadorSolicitante) {
        if (coordinador == null || jugadorLocal == null) {
            return false;
        }

        try {
            EstadoPartidaDTO estado = new EstadoPartidaDTO();
            estado.setIdJugador(idJugadorSolicitante);

            PeticionJugadaDTO peticion = new PeticionJugadaDTO(
                    TipoAccionPartida.RECHAZAR_SOLICITUD_UNION,
                    estado
            );

            enviarPeticion(peticion);
            return true;
        } catch (Exception e) {
            System.err.println("Error al rechazar solicitud: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean iniciarPartida(JugadorResumenDTO jugadorDTO) {
//        JugadorResumenDTO jugadorSolicitud = jugadorDTO != null ? jugadorDTO : jugadorLocal;
//
//        if (coordinador == null || jugadorSolicitud == null) {
//            return false;
//        }
//
//        jugadorSolicitud.setEstadoSala(EstadoJugadorSala.CONFIRMADO);
//        this.jugadorLocal = jugadorSolicitud;
//
//        try {
//            PeticionJugadaDTO peticion = new PeticionJugadaDTO(
//                    TipoAccionPartida.SOLICITAR_INICIO_PARTIDA,
//                    jugadorSolicitud
//            );
//            enviarPeticion(peticion);
//            return true;
//        } catch (Exception e) {
//            System.err.println("Error al solicitar inicio de partida: " + e.getMessage());
//        }
        return false;
    }

    @Override
    public void establecerJugadorLocal(JugadorResumenDTO datos) {
        if (datos == null) {
            return;
        }

        this.jugadorLocal = datos;
    }

    private void enviarPeticion(PeticionJugadaDTO peticion) throws Exception {
        ContextoPipeline<PeticionJugadaDTO> contexto = new ContextoPipeline<>(peticion);
        coordinador.procesar(contexto);
    }

    @Override
    public List<JugadorResumenDTO> getJugadoresEnSala() {
        return this.jugadoresEnSala;
    }

    @Override
    public void abrirSalaEspera() {
        this.cambiarFrame = true;
        notificar();
    }

    @Override
    public boolean isCambiarFrame() {
        return cambiarFrame;
    }

    @Override
    public JugadorResumenDTO getJugadorLocal() {
        return this.jugadorLocal;
    }

    @Override
    public List<JugadorResumenDTO> getJugadoresConfirmados() {
        List<JugadorResumenDTO> jugadoresConfirmados = new ArrayList<>();

        for (JugadorResumenDTO jugador : jugadoresEnSala) {
            if (jugador.getEstadoSala() == EstadoJugadorSala.CONFIRMADO) {
                jugadoresConfirmados.add(jugador);
            }
        }

        return jugadoresConfirmados;
    }

    @Override
    public boolean isPartidaListaParaIniciar() {
        return this.partidaListaParaIniciar;
    }

    @Override
    public void actualizarDatosJugador(JugadorResumenDTO datos, Map<TipoColor, TipoColor> misColores) {
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
                    jugadorLocal,
                    null
            );
            this.coloresLocales = misColores;
            enviarPeticion(peticion);
        } catch (Exception e) {
            System.err.println("Error al actualizar perfil: " + e.getMessage());
        }
    }

}
