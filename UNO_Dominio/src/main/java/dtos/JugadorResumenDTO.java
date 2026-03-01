/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Abraham Coronel
 */
public class JugadorResumenDTO {

    private int id;
    private String nombreUsuario;
    private int cantidadDeCartas;
    private int puntos;
    private boolean enTurno;

    public JugadorResumenDTO() {
    }

    public JugadorResumenDTO(int id, String nombreUsuario, int cantidadDeCartas, int puntos, boolean enTurno) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.cantidadDeCartas = cantidadDeCartas;
        this.puntos = puntos;
        this.enTurno = enTurno;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCantidadDeCartas() {
        return cantidadDeCartas;
    }

    public void setCantidadDeCartas(int cantidadDeCartas) {
        this.cantidadDeCartas = cantidadDeCartas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isEnTurno() {
        return enTurno;
    }

    public void setEnTurno(boolean enTurno) {
        this.enTurno = enTurno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
