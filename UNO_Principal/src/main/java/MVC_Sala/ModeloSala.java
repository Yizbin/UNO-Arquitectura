/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package MVC_Sala;

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import Interfaces.IPump;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloSala implements IControlModeloSala, IModeloSalaVista, ISink<EstadoPartidaDTO>, IPump<PeticionJugadaDTO> {
    
    private List<ISuscriptorSala> suscriptores;
    
    public void suscribir(ISuscriptorSala suscriptor) {
        if (!suscriptores.contains(suscriptor)) {
            suscriptores.add(suscriptor);
        }
    }

    public void desuscribir(ISuscriptorSala suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public void notificar() {
        for (ISuscriptorSala s : suscriptores) {
            s.update(this);
        }
    }

    @Override
    public void enviar(ContextoPipeline<EstadoPartidaDTO> contexto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void conectarDestino(ISink<PeticionJugadaDTO> destino) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<JugadorResumenDTO> getJugadoresEnSala() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
