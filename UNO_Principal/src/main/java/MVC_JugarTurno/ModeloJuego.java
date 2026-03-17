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
    private EstadoPantallaTurnoDTO estadoPantalla;

    private int idJugadorLocal;
    private String mensajePendiente;
    

    public ModeloJuego(ISubDominio subDominio) {
        this.subDominio = subDominio;
    }

    public void setIdJugadorLocal(int idJugadorLocal) {
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public EstadoPantallaTurnoDTO getEstadoPantalla() {
        refrescarEstado();
        return estadoPantalla;
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
        asignarMensajeLocal(mensaje);
        notificar();
    }

    @Override
    public void robarCarta() {
        String mensaje = null;

        try {
            subDominio.robarCarta(this.idJugadorLocal);
        } catch (MazoVacioException | ValidarTurnoException e) {
            mensaje = e.getMessage();
        }

        refrescarEstado();
        asignarMensajeLocal(mensaje);
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
        asignarMensajeLocal(mensaje);
        notificar();       
    }

    @Override
    public String consumirMensajePendiente() {
        String mensaje = this.mensajePendiente;
        this.mensajePendiente = null;
        return mensaje;
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
        asignarMensajeLocal(mensaje);
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
            this.estado.setManoJugadorActual(subDominio.obtenerManoJugador(this.idJugadorLocal));
            this.estado.setCartaEnDescarte(subDominio.obtenerCartaEnTope());
            this.estadoPantalla = construirEstadoPantalla(this.estado);
        }
    }

    private void asignarMensajeLocal(String mensaje) {
        if (mensaje != null && !mensaje.isBlank()) {
            this.mensajePendiente = mensaje;
        }
        if (mensaje != null && !mensaje.isBlank() && this.estado != null) {
            this.estado.setMensajeEstado(mensaje);
        }
    }

    private EstadoPantallaTurnoDTO construirEstadoPantalla(EstadoPartidaDTO estadoJuego) {
        EstadoPantallaTurnoDTO vista = new EstadoPantallaTurnoDTO();
        vista.setCartaEnDescarte(estadoJuego.getCartaEnDescarte());
        vista.setEsperandoColor(estadoJuego.isEsperandoColor());
        vista.setManoLocal(estadoJuego.getManoJugadorActual() != null ? estadoJuego.getManoJugadorActual() : List.of());
        vista.setTurnoLocal(estadoJuego.getIdJugadorEnTurno() == this.idJugadorLocal);

        List<JugadorResumenDTO> jugadores = estadoJuego.getJugadores() != null
                ? estadoJuego.getJugadores()
                : List.of();

        List<JugadorResumenDTO> remotos = new ArrayList<>();
        for (JugadorResumenDTO jugador : jugadores) {
            if (jugador.getId() == this.idJugadorLocal) {
                vista.setJugadorLocal(jugador);
            } else {
                remotos.add(jugador);
            }
        }

        if (!remotos.isEmpty()) {
            vista.setJugadorEste(remotos.get(0));
        }
        if (remotos.size() > 1) {
            vista.setJugadorNorte(remotos.get(1));
        }
        if (remotos.size() > 2) {
            vista.setJugadorOeste(remotos.get(2));
        }

        return vista;
    }

}
