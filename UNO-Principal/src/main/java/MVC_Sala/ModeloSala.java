/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorEstadoSalaDTO;
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
import javax.swing.SwingUtilities;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloSala implements IControlModeloSala, IModeloSalaVista {

    private final List<ISuscriptorSala> suscriptores;
    private IPump<PeticionJugadaDTO, ?> coordinador;
    private List<JugadorResumenDTO> jugadoresEnSala;
    private List<JugadorEstadoSalaDTO> estadosJugadoresSala;
    private JugadorResumenDTO jugadorLocal;
    private Map<TipoColor, TipoColor> coloresLocales;
    private boolean cambiarFrame = false;
    private boolean partidaListaParaIniciar;

    public ModeloSala() {
        this.suscriptores = new ArrayList<>();
        this.jugadoresEnSala = new ArrayList<>();
        this.estadosJugadoresSala = new ArrayList<>();
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

            enviarPeticionSegura(peticion, "Error al solicitar union");
            return true;
        } catch (Exception e) {
            System.err.println("Error al unirse: " + e.getMessage());
        }

        return false;
    }

    @Override
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

            enviarPeticionSegura(peticion, "Error al aceptar union");
            return true;
        } catch (Exception e) {
            System.err.println("Error al aceptar solicitud: " + e.getMessage());
            return false;
        }
    }

    @Override
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

            enviarPeticionSegura(peticion, "Error al rechazar la union");
            return true;
        } catch (Exception e) {
            System.err.println("Error al rechazar solicitud: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean iniciarPartida(JugadorResumenDTO jugadorDTO) {

        if (coordinador == null || !partidaListaParaIniciar || jugadorDTO == null || jugadorDTO.getId() != 1) {
            return false;
        }

        try {
            EstadoPartidaDTO estado = new EstadoPartidaDTO();
            estado.setJugadores(jugadoresEnSala);

            PeticionJugadaDTO peticion = new PeticionJugadaDTO(
                    TipoAccionPartida.INICIAR_PARTIDA,
                    jugadorDTO,
                    estado
            );
            enviarPeticion(peticion);

            this.partidaListaParaIniciar = true;
            notificar();
            return true;
        } catch (Exception e) {
            System.err.println("Error al solicitar inicio de partida: " + e.getMessage());
        }
        return false;
    }

    public void agregarSolicitudUnion(JugadorResumenDTO jugador) {
        if (jugador == null) {
            return;
        }

        if (!contieneJugador(jugador.getId())) {
            jugadoresEnSala.add(jugador);
        }

        setearJugadoresEsperando();
        notificar();
    }

    public void registrarUnionAceptada(JugadorResumenDTO jugador) {
        if (jugador == null) {
            return;
        }

        if (!contieneJugador(jugador.getId())) {
            jugadoresEnSala.add(jugador);
        }

        setearJugadoresEsperando();
        notificar();
    }

    public void registrarUnionRechazada(JugadorResumenDTO jugador) {
        if (jugador == null) {
            return;
        }

        jugadoresEnSala.removeIf(j -> j.getId() == jugador.getId());
        setearJugadoresEsperando();
        notificar();
    }

    private boolean contieneJugador(int idJugador) {
        for (JugadorResumenDTO jugador : jugadoresEnSala) {
            if (jugador.getId() == idJugador) {
                return true;
            }

        }
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

    private boolean enviarPeticionSegura(PeticionJugadaDTO peticion, String mensajeError) {
        try {
            enviarPeticion(peticion);
            return true;
        } catch (Exception e) {
            System.err.println(mensajeError + ": " + e.getMessage());
            return false;
        }
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
    @Override
    public void validarCondicionInicio(EstadoPartidaDTO estadoPartidaDTO) {
        if (estadoPartidaDTO == null) {
            return;
        }

        if (estadoPartidaDTO.getJugadores() != null) {
            this.jugadoresEnSala = estadoPartidaDTO.getJugadores();
        }

        if (estadoPartidaDTO.getEstadosJugadoresSala() != null) {
            this.estadosJugadoresSala = estadoPartidaDTO.getEstadosJugadoresSala();
        }

        this.partidaListaParaIniciar = estadoPartidaDTO.isInicioPermitido();
        SwingUtilities.invokeLater(this::notificar);
    }

    private void setearJugadoresEsperando() {
        List<JugadorEstadoSalaDTO> estadosActualizados = new ArrayList<>();

        for (JugadorResumenDTO jugador : jugadoresEnSala) {
            EstadoJugadorSala estadoActual = obtenerEstadoJugador(jugador.getId());

            if (estadoActual == null) {
                estadoActual = EstadoJugadorSala.ESPERANDO;
            }

            estadosActualizados.add(new JugadorEstadoSalaDTO(
                    jugador.getId(),
                    estadoActual
            ));
        }

        this.estadosJugadoresSala = estadosActualizados;
    }

    private EstadoJugadorSala obtenerEstadoJugador(int idJugador) {
        for (JugadorEstadoSalaDTO estadoJugador : estadosJugadoresSala) {
            if (estadoJugador.getId() == idJugador) {
                return estadoJugador.getEstadoSala();
            }
        }

        return null;
    }

    @Override
    public List<JugadorEstadoSalaDTO> getEstadosJugadoresSala() {
        return this.estadosJugadoresSala;
    }

    @Override
    public boolean actualizarEstadoJugadorSala() {
        JugadorResumenDTO jugadorLocal = this.jugadorLocal;
        if (coordinador == null || jugadorLocal == null) {
            return false;
        }
        try {
            EstadoJugadorSala estadoActual = obtenerEstadoJugador(jugadorLocal.getId());
            EstadoJugadorSala nuevoEstado = estadoActual
                    == EstadoJugadorSala.CONFIRMADO
                            ? EstadoJugadorSala.CANCELADO
                            : EstadoJugadorSala.CONFIRMADO;
            JugadorEstadoSalaDTO jugadorEstado = new JugadorEstadoSalaDTO(
                    jugadorLocal.getId(),
                    nuevoEstado
            );
            EstadoPartidaDTO estado = new EstadoPartidaDTO();
            estado.setEstadosJugadoresSala(List.of(jugadorEstado));

            PeticionJugadaDTO peticion = new PeticionJugadaDTO(
                    TipoAccionPartida.CAMBIAR_INICIO_PARTIDA,
                    estado
            );

            enviarPeticion(peticion);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar estado del jugador en sala: " + e.getMessage());
        }

        return false;
    }

}
