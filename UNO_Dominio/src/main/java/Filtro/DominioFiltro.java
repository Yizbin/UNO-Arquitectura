/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Filtro;

import DTOs.EstadoPartidaDTO;
import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Interfaces.IFiltro;
import Interfaces.ISubDominio;
import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 */
public class DominioFiltro implements IFiltro<PeticionJugadaDTO, EstadoPartidaDTO> {

    private final ISubDominio subDominio;

    public DominioFiltro(ISubDominio subDominio) {
        this.subDominio = subDominio;
    }

    @Override
    public ContextoPipeline<EstadoPartidaDTO> procesar(ContextoPipeline<PeticionJugadaDTO> contexto) throws Exception {

        PeticionJugadaDTO peticion = contexto.getMensaje();

        if (peticion == null) {
            throw new IllegalStateException("La petición en el contexto es nula.");
        }

        TipoAccionPartida tipoAccion = peticion.getAccion();

        if (tipoAccion == null) {
            throw new IllegalStateException("El tipo de accion no ha sido definido en la peticion.");
        }

        switch (tipoAccion) {

            case UNIRSE_PARTIDA -> {
                subDominio.unirJugador(peticion.getJugador());
            }
            case SOLICITAR_INICIO_PARTIDA -> {
                subDominio.confirmarInicioPartida(peticion.getJugador());
            }
            case JUGAR_CARTA -> {
                subDominio.jugarCarta(peticion.getIdJugador(), peticion.getCartaAJugar());
            }
            case ROBAR_CARTA -> {
                subDominio.robarCarta(peticion.getIdJugador());
            }
            case ELEGIR_COLOR -> {
                subDominio.elegirColorComodin(peticion.getNuevoColor());
            }
            case TIRAR_RULETA -> {
                subDominio.tirarRuleta();
            }
            case GRITAR_UNO -> {
                subDominio.gritarUno(peticion.getIdJugador());
            }
            case TERMINAR_TURNO -> {
                subDominio.terminarTurno();
            }
            case ACTUALIZAR_PERFIL -> {
                subDominio.actualizarPerfilJugador(peticion.getJugador());
            }   
            default ->
                throw new UnsupportedOperationException("Tipo de accion no reconocido: " + tipoAccion);
        }

        EstadoPartidaDTO estado = subDominio.obtenerEstadoPartida();
        return new ContextoPipeline<>(estado);
    }

}
