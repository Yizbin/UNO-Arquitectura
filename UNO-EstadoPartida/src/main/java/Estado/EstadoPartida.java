/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Estado;

import DTOs.EstadoPartidaDTO;
import interfaces.ContextoPipeline;
import interfaces.IFiltro;

/**
 *
 * @author Abraham Coronel
 * @param <T>
 */
public class EstadoPartida<T> implements IFiltro<T, T> {

    private EstadoPartidaDTO estadoActual;

    public EstadoPartida() {
        this.estadoActual = new EstadoPartidaDTO();
    }

    public EstadoPartidaDTO getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoPartidaDTO estadoActual) {
        this.estadoActual = estadoActual;
    }

    @Override
    public ContextoPipeline<T> procesar(ContextoPipeline<T> contexto) throws Exception {
        T mensaje = contexto.getMensaje();

        if (mensaje == null) {
            return contexto;
        }

        if (mensaje instanceof EstadoPartidaDTO estadoPartidaDTO) {
            this.estadoActual = estadoPartidaDTO;
        }

        return contexto;
    }

}
