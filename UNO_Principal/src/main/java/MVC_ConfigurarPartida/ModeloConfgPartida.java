/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_ConfigurarPartida;

import DTOs.ConfiguracionPartidaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Plantilla.ContextoPipeline;
import interfaces.IPump;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pride Factor Black
 */
public class ModeloConfgPartida implements IModeloConfgPartida{
    private final IPump<PeticionJugadaDTO> coordinador;
    private final List<ISuscriptor> suscriptores;

    private boolean configuracionExitosa;
    private String mensajeError;

    public ModeloConfgPartida(IPump<PeticionJugadaDTO> coordinador) {
        this.coordinador = coordinador;
        this.suscriptores = new ArrayList<>();
        this.configuracionExitosa = false;
        this.mensajeError = "";
    }

    @Override
    public void configurarPartida(ConfiguracionPartidaDTO dto) {
        try {
            PeticionJugadaDTO peticion = new PeticionJugadaDTO();
            peticion.setAccion(TipoAccionPartida.CONFIGURAR_PARTIDA);
            peticion.setConfiguracionPartida(dto);

            ContextoPipeline<PeticionJugadaDTO> contexto = new ContextoPipeline<>(peticion);
            coordinador.procesar(contexto);

            configuracionExitosa = true;
            mensajeError = "";
        } catch (Exception ex) {
            configuracionExitosa = false;
            mensajeError = ex.getMessage();
        }

        notificar();
    }

    @Override
    public boolean isConfiguracionExitosa() {
        return configuracionExitosa;
    }

    @Override
    public String getMensajeError() {
        return mensajeError;
    }

    @Override
    public void agregarSuscriptor(ISuscriptor suscriptor) {
        if (suscriptor != null) {
            suscriptores.add(suscriptor);
        }
    }

    @Override
    public void notificar() {
        for (ISuscriptor suscriptor : suscriptores) {
            suscriptor.update(this);
        }
    }
}
