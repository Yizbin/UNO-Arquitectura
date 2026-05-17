/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.TablaPosicionesDTO;
import Enums.EstadoFinalizacion;

/**
 *
 * @author Abraham Coronel
 */
public interface IModeloVista {

    public int getIdJugadorLocal();

    public EstadoPantallaTurnoDTO getEstadoPantalla();

    public String consumirMensajePendiente();

    public EstadoFinalizacion getEstadoFinalizacion();

    public TablaPosicionesDTO getTablaPosiciones();
    
}
