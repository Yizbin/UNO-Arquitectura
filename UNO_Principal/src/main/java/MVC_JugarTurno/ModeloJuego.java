/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import Interfaces.ISubDominio;
import Interfaces.SubDominioConcreto;
import dtos.EstadoPartidaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloJuego implements IControlModelo, IModeloVista {
    
    private final List<ISuscriptor> suscriptores = new ArrayList<>();

    private final ISubDominio subDominio = new SubDominioConcreto();

    private EstadoPartidaDTO estado; 

    private int idJugadorLocal;

    public EstadoPartidaDTO getEstado() {
        return estado;
    }

    public int getIdJugadorLocal() {
        return idJugadorLocal;
    }

}
