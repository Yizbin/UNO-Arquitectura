/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import DTOs.TablaPosicionesDTO;
import DTOs.TipoCartaDTO;
import Enums.EstadoFinalizacion;
import Enums.TipoAccionPartida;
import Enums.TipoColor;
import interfaces.IPump;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloJuego implements IControlModelo, IModeloVista, ISink<EstadoPartidaDTO> {

    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private IPump<PeticionJugadaDTO, byte[]> coordinador;
    private EstadoPartidaDTO estadoActual;
    private int idJugadorLocal;
    private String mensajePendiente;
    private boolean esperandoColor;

    public void setIdJugadorLocal(int idJugadorLocal) {
        this.idJugadorLocal = idJugadorLocal;
    }

    public void conectarDestino(IPump<PeticionJugadaDTO, byte[]> coordinador) {
        this.coordinador = coordinador;
    }

    public void realizarAccionJugador(PeticionJugadaDTO jugada) {
        if (coordinador != null) {
            ContextoPipeline<PeticionJugadaDTO> contexto = new ContextoPipeline<>(jugada);
            try {
                coordinador.procesar(contexto);
            } catch (Exception e) {
                System.err.println("Error al procesar" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void iniciarJuego(List<JugadorResumenDTO> jugadores) {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setAccion(TipoAccionPartida.INICIAR_PARTIDA);
        peticion.setEstadoPartida(crearEstadoPeticion());
        realizarAccionJugador(peticion);
    }

    @Override
    public void robarCarta() {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setAccion(TipoAccionPartida.ROBAR_CARTA);
        peticion.setEstadoPartida(crearEstadoPeticion());
        realizarAccionJugador(peticion);
    }

    @Override
    public void jugarCarta(CartaDTO carta) {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setAccion(TipoAccionPartida.JUGAR_CARTA);
        EstadoPartidaDTO estadoPeticion = crearEstadoPeticion();
        estadoPeticion.setCartaEnDescarte(carta);
        peticion.setEstadoPartida(estadoPeticion);
        this.esperandoColor = carta != null && carta.getTipoCarta() == TipoCartaDTO.COMODIN;
        realizarAccionJugador(peticion);
    }

    @Override
    public void seleccionarColor(TipoColor color) {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setAccion(TipoAccionPartida.ELEGIR_COLOR);
        EstadoPartidaDTO estadoPeticion = crearEstadoPeticion();
        estadoPeticion.setColorSeleccionado(color);
        peticion.setEstadoPartida(estadoPeticion);
        this.esperandoColor = false;
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
        vista.setTurnoLocal(estadoActual.getIdJugador() == this.idJugadorLocal);
        vista.setEsperandoColor(esperandoColor);
        vista.setEstadoFinalizacion(estadoActual.getEstadoFinalizacion());
        vista.setResultadoFinalizacion(estadoActual.getResultadoFinalizacion());
        vista.setTablaPosiciones(estadoActual.getResultadoFinalizacion() != null
                ? estadoActual.getResultadoFinalizacion().getTablaPosiciones()
                : null);

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

    @Override
    public EstadoFinalizacion getEstadoFinalizacion() {
        return estadoActual != null ? estadoActual.getEstadoFinalizacion() : EstadoFinalizacion.SIN_SOLICITUD;
    }

    @Override
    public TablaPosicionesDTO getTablaPosiciones() {
        return estadoActual != null && estadoActual.getResultadoFinalizacion() != null
                ? estadoActual.getResultadoFinalizacion().getTablaPosiciones()
                : null;
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

    // caso finalizar partida
    @Override
    public boolean solicitarFinalizacion(JugadorResumenDTO jugadorDTO) {
        JugadorResumenDTO solicitante = jugadorDTO != null ? jugadorDTO : obtenerJugadorLocal();
        if (solicitante == null) {
            mensajePendiente = "No se pudo solicitar la finalizacion: jugador local no disponible.";
            notificar();
            return false;
        }

        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setAccion(TipoAccionPartida.SOLICITAR_FINALIZACION);
        peticion.setJugadorActualizar(solicitante);

        System.out.println("Enviando solicitud de finalizacion del jugador " + solicitante.getId());
        realizarAccionJugador(peticion);
        return true;
    }

    @Override
    public boolean responderFinalizacion(JugadorResumenDTO jugadorDTO, boolean acepta) {
        JugadorResumenDTO jugador = jugadorDTO != null ? jugadorDTO : obtenerJugadorLocal();
        if (jugador == null) {
            mensajePendiente = "No se pudo responder la finalizacion: jugador local no disponible.";
            notificar();
            return false;
        }

        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        peticion.setAccion(acepta
                ? TipoAccionPartida.ACEPTAR_FINALIZACION
                : TipoAccionPartida.RECHAZAR_FINALIZACION);
        peticion.setJugadorActualizar(jugador);

        System.out.println("Enviando respuesta de finalizacion del jugador " + jugador.getId() + ": " + acepta);
        realizarAccionJugador(peticion);
        return true;
    }

    private EstadoPartidaDTO crearEstadoPeticion() {
        EstadoPartidaDTO estadoPeticion = new EstadoPartidaDTO();
        estadoPeticion.setIdJugador(idJugadorLocal);
        return estadoPeticion;
    }

    private JugadorResumenDTO obtenerJugadorLocal() {
        if (estadoActual == null || estadoActual.getJugadores() == null) {
            return null;
        }

        for (JugadorResumenDTO jugador : estadoActual.getJugadores()) {
            if (jugador.getId() == idJugadorLocal) {
                return jugador;
            }
        }
        return null;
    }
}
