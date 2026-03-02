/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import Enums.TipoColor;
import Interfaces.ISubDominio;
import DTOs.JugadorResumenDTO;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloJuego implements IControlModelo, IModeloVista {

    private final List<ISuscriptor> suscriptores = new ArrayList<>();

    private final ISubDominio subDominio;

    private EstadoPartidaDTO estado;

    private int idJugadorLocal;
    

    public ModeloJuego(ISubDominio subDominio) {
        this.subDominio = subDominio;
    }

    public void setIdJugadorLocal(int idJugadorLocal) {
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public EstadoPartidaDTO getEstado() {
        return estado;
    }

    @Override
    public int getIdJugadorLocal() {
        return idJugadorLocal;
    }

    @Override
    public void iniciarJuego(List<JugadorResumenDTO> jugadores) {
        String mensaje = null;

        try {
            subDominio.prepararJuego(jugadores);
        } catch (MazoVacioException e) {
            mensaje = e.getMessage();
        }

        refrescarEstado();
        if (mensaje != null) {
            estado.setMensajeEstado(mensaje);
        }
        notificar();
    }

    @Override
    public void robarCarta() {
        String mensaje = null;

        try {
            subDominio.robarCarta(this.idJugadorLocal);
        } catch (MazoVacioException e) {
            mensaje = e.getMessage();
        }

        refrescarEstado();
        if (mensaje != null) {
            estado.setMensajeEstado(mensaje);
        }
        notificar();
    }


    public void agregarSuscriptor(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    @Override
    public void jugarCarta(CartaDTO carta) {
        String mensaje = null;

        try {
            subDominio.jugarCarta(this.idJugadorLocal, carta);
        } catch (ValidarManoException | ValidarTurnoException | JugadaValidaException | MazoVacioException e) {
            mensaje = e.getMessage();
        }

        refrescarEstado(); //refrescar siempre
        if (mensaje != null) {
            estado.setMensajeEstado(mensaje);
        }
        notificar();       
    }

    @Override
    public CartaDTO getCartaEnTope() {
        System.out.println(subDominio.obtenerCartaEnTope());
        return subDominio.obtenerCartaEnTope();
    }

    @Override
    public TipoColor getColorActual() {
        return subDominio.obtenerColorActual();
    }

    @Override
    public List<CartaDTO> getManoJugadorLocal() {
        return subDominio.obtenerManoJugador(this.idJugadorLocal);
    }

    @Override
    public void seleccionarColor(TipoColor color){
        String mensaje = null;
        
        try{
            subDominio.elegirColorComodin(color);
        } catch (MazoVacioException e) {
            mensaje = e.getMessage();
        }
        
        refrescarEstado();
        if (mensaje != null){
            estado.setMensajeEstado(mensaje);
        }
        notificar();
    }

    //Metodo Privados
    private void notificar() {
        for (ISuscriptor s : suscriptores) {
            s.update();
        }
    }

    private void refrescarEstado() {
        if (subDominio != null) {
            this.estado = subDominio.obtenerEstadoPartida();
            if (this.estado == null) {
                this.estado = new EstadoPartidaDTO();
            }
        }
    }

}
