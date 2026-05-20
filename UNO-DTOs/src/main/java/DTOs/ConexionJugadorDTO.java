/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Abraham Coronel
 */
public class ConexionJugadorDTO {

    private int idJugador; 
    private String ip;
    private int puerto;
    private int puertoInicioPartida;
    private int puertoCargarPartida;

    public ConexionJugadorDTO() {
    }

    public ConexionJugadorDTO(int idJugador, String ip, int puerto) {
        this.idJugador = idJugador;
        this.ip = ip;
        this.puerto = puerto;
        this.puertoInicioPartida = puerto;
        this.puertoCargarPartida = puerto;
    }

    public ConexionJugadorDTO(int idJugador, String ip, int puertoInicioPartida, int puertoCargarPartida) {
        this.idJugador = idJugador;
        this.ip = ip;
        this.puerto = puertoInicioPartida;
        this.puertoInicioPartida = puertoInicioPartida;
        this.puertoCargarPartida = puertoCargarPartida;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
        this.puertoInicioPartida = puerto;
        this.puertoCargarPartida = puerto;
    }

    public int getPuertoInicioPartida() {
        return puertoInicioPartida;
    }

    public void setPuertoInicioPartida(int puertoInicioPartida) {
        this.puertoInicioPartida = puertoInicioPartida;
    }

    public int getPuertoCargarPartida() {
        return puertoCargarPartida;
    }

    public void setPuertoCargarPartida(int puertoCargarPartida) {
        this.puertoCargarPartida = puertoCargarPartida;
    }

}
