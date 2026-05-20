/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.TipoAccionPartida;

/**
 *
 * @author Abraham Coronel
 */
public class PeticionJugadaDTO {

    private TipoAccionPartida accion;
    private JugadorResumenDTO jugadorLocal;
    private EstadoPartidaDTO estadoPartida;
    private ConfiguracionPartidaDTO configuracionPartida;

    public PeticionJugadaDTO() {
    }

    public PeticionJugadaDTO(TipoAccionPartida accion, EstadoPartidaDTO estadoPartida) {
        this.accion = accion;
        this.estadoPartida = estadoPartida;
    }

    public PeticionJugadaDTO(TipoAccionPartida accion, JugadorResumenDTO jugadorLocal, EstadoPartidaDTO estadoPartida) {
        this.accion = accion;
        this.jugadorLocal = jugadorLocal;
        this.estadoPartida = estadoPartida;
    }

    public TipoAccionPartida getAccion() {
        return accion;
    }

    public void setAccion(TipoAccionPartida accion) {
        this.accion = accion;
    }

    public EstadoPartidaDTO getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(EstadoPartidaDTO estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    public JugadorResumenDTO getJugadorLocal() {
        return jugadorLocal;
    }

    public void setJugadorLocal(JugadorResumenDTO jugadorLocal) {
        this.jugadorLocal = jugadorLocal;
    }
    
    public ConfiguracionPartidaDTO getConfiguracionPartida() {
        return configuracionPartida;
    }

    public void setConfiguracionPartida(ConfiguracionPartidaDTO configuracionPartida) {
        this.configuracionPartida = configuracionPartida;
    }
}
