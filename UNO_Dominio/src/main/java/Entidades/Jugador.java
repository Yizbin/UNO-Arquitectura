/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.EstadoJugadorSala;
import Enums.TipoColor;
import Excepciones.ValidarManoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Abraham Coronel
 */
public class Jugador {

    private int id;
    private String usuario;
    private String avatar; //Url imgaen
    private List<Carta> mano;
    private int puntos;
    private boolean dijoUno;
    private EstadoJugadorSala estadoSala;
    private Map<TipoColor, TipoColor> preferenciasColor = new HashMap<>();

    public Jugador() {
        this.mano = new ArrayList<>();
        this.estadoSala = EstadoJugadorSala.ESPERANDO;
    }

    public Jugador(int id) {
        this.id = id;
    }

    public Jugador(int id, String usuario, String avatar) {
        this.id = id;
        this.usuario = usuario;
        this.avatar = avatar;
        this.mano = new ArrayList<>();
        this.puntos = 0;
        this.dijoUno = false;
        this.estadoSala = EstadoJugadorSala.ESPERANDO;
    }

    public void robarCarta(Carta carta) {
        this.mano.add(carta);
        this.dijoUno = false;
    }

    public Carta jugarCarta(Carta cartaCopia) throws ValidarManoException {
        Carta cartaReal = null;

        for (Carta c : this.mano) {

            if (c instanceof CartaNumero cn && cartaCopia instanceof CartaNumero cnCopia) {
                if (cn.getColor() == cnCopia.getColor() && cn.getNumero() == cnCopia.getNumero()) {
                    cartaReal = c;
                    break;
                }
            } else if (c instanceof CartaAccion ca && cartaCopia instanceof CartaAccion caCopia) {
                if (ca.getColor() == caCopia.getColor() && ca.getTipoAccion() == caCopia.getTipoAccion()) {
                    cartaReal = c;
                    break;
                }
            } else if (c instanceof CartaComodin cc && cartaCopia instanceof CartaComodin ccCopia) {
                if (cc.getTipoComodin() == ccCopia.getTipoComodin()) {
                    cartaReal = c;
                    break;
                }
            }
        }

        if (cartaReal == null) {
            throw new ValidarManoException("El jugador no tiene esa carta en la mano.");
        }

        this.mano.remove(cartaReal);
        return cartaReal;
    }

    //Para cuando queda una carta nomas
    public void gritarUno() {
        if (mano.size() == 1) {
            this.dijoUno = true;
        }
    }

    //Calcula los puntos
    public int calcularPuntosRestantes() {
        return mano.stream().mapToInt(Carta::getPuntuacion).sum();
    }

    //Nose si este iria tengo mis dudas de como lo manejaremos.
    public boolean esVulnerableAlCastigo() {
        return mano.size() == 1 && !dijoUno;
    }

    //Solo comprueba si el jugador no tiene cartas y gano
    public boolean haGanado() {
        return mano.isEmpty();
    }

    public void actualizarPerfil(String usuario, String avatar, Map<TipoColor, TipoColor> nuevasPreferencias) {
        this.usuario = usuario;
        this.avatar = avatar;
        if (nuevasPreferencias != null) {
            this.preferenciasColor = nuevasPreferencias;
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Carta> getMano() {
        return List.copyOf(mano);
    }

    public void setMano(List<Carta> mano) {
        this.mano = (mano == null) ? new ArrayList<>() : new ArrayList<>(mano);
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isDijoUno() {
        return dijoUno;
    }

    public void setDijoUno(boolean dijoUno) {
        this.dijoUno = dijoUno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstadoJugadorSala getEstadoSala() {
        return estadoSala;
    }

    public void setEstadoSala(EstadoJugadorSala estadoSala) {
        this.estadoSala = estadoSala != null ? estadoSala : EstadoJugadorSala.ESPERANDO;
    }

    public Map<TipoColor, TipoColor> getPreferenciasColor() {
        return preferenciasColor;
    }

    public void setPreferenciasColor(Map<TipoColor, TipoColor> preferenciasColor) {
        this.preferenciasColor = preferenciasColor;
    }
    
    

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugador other = (Jugador) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.usuario, other.usuario);
    }

}
