/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estado;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorEstadoSalaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import DTOs.RespuestaFinalizacionDTO;
import DTOs.ResultadoFinalizacionDTO;
import Enums.EstadoFinalizacion;
import Enums.EstadoRetoSpin;
import Enums.TipoColor;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class EstadoPartida implements IFiltro<PeticionJugadaDTO, PeticionJugadaDTO> {

    private int idJugador;
    private List<JugadorResumenDTO> jugadores;
    private CartaDTO cartaEnDescarte;
    private EstadoRetoSpin estadoReto;
    private boolean ruletaActiva;
    private List<CartaDTO> mazo;

    private boolean puedeRobar;
    private boolean puedeDecirUno;
    private TipoColor colorSeleccionado;
    private boolean inicioPermitido;
    private List<JugadorEstadoSalaDTO> estadosJugadoresSala;

    private String mensajeEstado;

    private EstadoFinalizacion estadoFinalizacion = EstadoFinalizacion.SIN_SOLICITUD;
    private ResultadoFinalizacionDTO resultadoFinalizacion;
    private List<RespuestaFinalizacionDTO> respuestasFinalizacion;

    @Override
    public ContextoPipeline<PeticionJugadaDTO> procesar(
            ContextoPipeline<PeticionJugadaDTO> contexto) {

        if (contexto == null || contexto.estaDetenido()) {
            return contexto;
        }

        PeticionJugadaDTO peticion = contexto.getMensaje();

        if (peticion == null) {
            return contexto;
        }

        if (peticion.getEstadoPartida() != null) {
            actualizarEstado(peticion.getEstadoPartida());
        }

        PeticionJugadaDTO peticionActualizada = crearDTOActual();

        // conservar datos originales
        peticionActualizada.setAccion(peticion.getAccion());
        peticionActualizada.setJugadorActualizar(
                peticion.getJugadorActualizar());

        peticionActualizada.setConfiguracionPartida(
                peticion.getConfiguracionPartida());

        return new ContextoPipeline<>(peticionActualizada);
    }

    private void actualizarEstado(EstadoPartidaDTO estadoRecibido) {
        this.idJugador = estadoRecibido.getIdJugador();
        this.jugadores = estadoRecibido.getJugadores();
        this.cartaEnDescarte = estadoRecibido.getCartaEnDescarte();
        this.estadoReto = estadoRecibido.getEstadoReto();
        this.ruletaActiva = estadoRecibido.isRuletaActiva();
        this.mazo = estadoRecibido.getMazo();

        this.puedeRobar = estadoRecibido.isPuedeRobar();
        this.puedeDecirUno = estadoRecibido.isPuedeDecirUno();
        this.colorSeleccionado = estadoRecibido.getColorSeleccionado();
        this.inicioPermitido = estadoRecibido.isInicioPermitido();
        this.estadosJugadoresSala = estadoRecibido.getEstadosJugadoresSala();

        this.mensajeEstado = estadoRecibido.getMensajeEstado();
        this.estadoFinalizacion = estadoRecibido.getEstadoFinalizacion();
        this.resultadoFinalizacion = estadoRecibido.getResultadoFinalizacion();
        this.respuestasFinalizacion = estadoRecibido.getRespuestasFinalizacion();
    }

    private PeticionJugadaDTO crearDTOActual() {
        PeticionJugadaDTO peticion = new PeticionJugadaDTO();
        EstadoPartidaDTO dto = new EstadoPartidaDTO();

        dto.setIdJugador(this.idJugador);
        dto.setJugadores(this.jugadores);
        dto.setCartaEnDescarte(this.cartaEnDescarte);
        dto.setEstadoReto(this.estadoReto);
        dto.setRuletaActiva(this.ruletaActiva);
        dto.setMazo(this.mazo);

        dto.setPuedeRobar(this.puedeRobar);
        dto.setPuedeDecirUno(this.puedeDecirUno);
        dto.setColorSeleccionado(this.colorSeleccionado);
        dto.setInicioPermitido(this.inicioPermitido);
        dto.setEstadosJugadoresSala(this.estadosJugadoresSala);

        dto.setMensajeEstado(this.mensajeEstado);
        dto.setEstadoFinalizacion(this.estadoFinalizacion);
        dto.setResultadoFinalizacion(this.resultadoFinalizacion);
        dto.setRespuestasFinalizacion(this.respuestasFinalizacion);

        peticion.setEstadoPartida(dto);
        
        return peticion;
    }
}
