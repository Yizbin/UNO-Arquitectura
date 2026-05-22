/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_Sala;

import DTOs.CartaDTO;
import DTOs.JugadorEstadoSalaDTO;
import DTOs.JugadorResumenDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IModeloSalaVista {

    List<JugadorResumenDTO> getJugadoresEnSala();
    
    void suscribir(ISuscriptorSala suscriptor);
    
    void desuscribir(ISuscriptorSala suscriptor);
    
    void notificar();
    
    boolean isCambiarFrame();
    
    JugadorResumenDTO getJugadorLocal();

    List<JugadorEstadoSalaDTO> getEstadosJugadoresSala();

    boolean isPartidaListaParaIniciar();
    
    boolean puedeResponderSolicitudUnion(int idJugador);
    
    CartaDTO getC1();
    
    CartaDTO getC2();
    
    CartaDTO getC3();

    CartaDTO getC4();

}
