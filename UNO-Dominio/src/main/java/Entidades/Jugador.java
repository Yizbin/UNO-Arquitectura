/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Enums.EstadoJugadorSala;
import Excepciones.ValidarManoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Jugador {

    private static final int ID_ANFITRION = 1;

    private int id;
    private String usuario;
    private String avatar; //Url imgaen
    private List<Carta> mano;
    private int puntos;
    private boolean dijoUno;
    private boolean aceptado;
    private EstadoJugadorSala estadoSala;

    public Jugador() {
        this.mano = new ArrayList<>();
        this.aceptado = false;
    }

    public Jugador(int id) {
        this();
        this.id = id;
    }

    public Jugador(int id, String usuario, String avatar) {
        this.id = id;
        this.usuario = usuario;
        this.avatar = avatar;
        this.mano = new ArrayList<>();
        this.puntos = 0;
        this.dijoUno = false;
        this.aceptado = false;
    }

    public void robarCarta(Carta carta) {
        this.mano.add(carta);
        this.dijoUno = false;
    }

    public Carta jugarCarta(Carta cartaCopia) throws ValidarManoException {
        if (cartaCopia == null) {
            throw new ValidarManoException("La carta a jugar no puede ser nula.");
        }

        for (Carta carta : this.mano) {
            if (carta.equals(cartaCopia)) {
                this.mano.remove(carta);
                return carta;
            }
        }

        throw new ValidarManoException("El jugador no tiene esa carta en la mano.");
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

    public boolean esAnfitrion() {
        return id == ID_ANFITRION;
    }

    public void confirmarInicioPartida() {
        this.estadoSala = EstadoJugadorSala.CONFIRMADO;
    }

    public boolean estaConfirmadoParaIniciar() {
        return this.estadoSala == EstadoJugadorSala.CONFIRMADO;
    }

    public void actualizarPerfil(String usuario, String avatar) {
        this.usuario = usuario;
        this.avatar = avatar;
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

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
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

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public EstadoJugadorSala getEstadoSala() {
        return estadoSala;
    }

    public void setEstadoSala(EstadoJugadorSala estadoSala) {
        this.estadoSala = estadoSala;
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
