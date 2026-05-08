/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Filtro;

import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import interfaces.ContextoPipeline;
import interfaces.IFiltro;

/**
 *
 * @author Abraham Coronel
 */
public class DominioFiltro implements IFiltro {

    private final ISubDominio subDominio = new SubDominioConcreto();
    private TipoAccionPartida tipoAccion;

    public DominioFiltro(TipoAccionPartida tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    @Override
    public ContextoPipeline procesar(ContextoPipeline contexto) throws Exception {

        if (this.tipoAccion == null) {
            throw new IllegalStateException("El tipo de accion no ha sido definido en el filtro.");
        }

        PeticionJugadaDTO peticion = (PeticionJugadaDTO) contexto.getMensaje();

        switch (this.tipoAccion) {

            case UNIRSE_PARTIDA -> {
                break;
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

            case TERMINAR_TURNO ->
                subDominio.terminarTurno();

            default ->
                throw new UnsupportedOperationException("Tipo de accion no reconocido: " + this.tipoAccion);
        }

        return contexto;
    }

    public void setTipoAccion(TipoAccionPartida tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

}
