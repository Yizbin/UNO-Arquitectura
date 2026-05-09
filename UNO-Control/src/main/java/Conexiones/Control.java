/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Conexiones;

import DTOs.ConexionJugadorDTO;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 * @param <T>
 */
public class Control<T> implements IFiltro<T,T> {

    private final List<ConexionJugadorDTO> listaJugadores;

    public Control() {
        this.listaJugadores = new ArrayList<>();
    }

    public void registrarJugador(ConexionJugadorDTO conexion) {
        this.listaJugadores.add(conexion);
    }

    public String getDireccion(int idJugador) {
        for (ConexionJugadorDTO conexion : listaJugadores) {
            if (conexion.getIdJugador() == idJugador) {
                return conexion.getIp() + ":" + conexion.getPuerto();
            }
        }
        return "Desconocido";
    }


    public List<ConexionJugadorDTO> getListaJugadores() {
        return listaJugadores;
    }

    @Override
    public ContextoPipeline<T> procesar(ContextoPipeline<T> contexto) throws Exception {
        
        T mensaje = contexto.getMensaje();
        
        if (mensaje == null) {
            contexto.detenerConError("El mensaje llegó nulo al filtro de Control.");
            return contexto;
        }

        //Logica del filtro
        
        return contexto;
    }

}
