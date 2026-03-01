/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import Interfaces.ISubDominio;
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

    @Override
    public EstadoPartidaDTO getEstado() {
        return estado;
    }

    @Override
    public int getIdJugadorLocal() {
        return idJugadorLocal;
    }

    @Override
    public void iniciarPartida(List<JugadorResumenDTO> jugadores) throws Exception {
        subDominio.prepararJuego(jugadores);
        this.notificar();
    }

    @Override
    public void robarCarta() throws Exception {
        JugadorResumenDTO jugadorActual = subDominio.obtenerJugadorActual();
        subDominio.robarCarta(jugadorActual);
        this.notificar();
    }

    @Override
    public void agregarSuscriptor(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    @Override
    public void jugarCarta(CartaDTO carta) throws Exception {
        JugadorResumenDTO jugadorActual = subDominio.obtenerJugadorActual();
        subDominio.jugarCarta(jugadorActual, carta);
        this.notificar();
    }

    @Override
    public CartaDTO getCartaEnTope() {
        return subDominio.obtenerCartaEnTope();
    }

    @Override
    public TipoColor getColorActual() {
        return subDominio.obtenerColorActual();
    }

    @Override
    public List<CartaDTO> getManoJugadorActual() {
        System.out.println( subDominio.obtenerManoJugadorActual());
        return subDominio.obtenerManoJugadorActual();
    }

    @Override
    public void notificarError(String mensaje) {
        for (ISuscriptor s : suscriptores) {
            s.notificarError(mensaje);
        }
    }

    //Metodo Privados
    private void notificar() {
        if (subDominio != null) {
            this.estado = subDominio.obtenerEstadoPartida();
        }
        for (ISuscriptor s : suscriptores) {
            s.update();
        }
    }

    @Override
    public void seleccionarColor(TipoColor color) throws Exception {
        subDominio.elegirColorComodin(color);
        this.notificar();
    }

}
