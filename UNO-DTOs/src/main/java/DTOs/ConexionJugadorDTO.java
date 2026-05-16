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

    public ConexionJugadorDTO() {
    }

    public ConexionJugadorDTO(int idJugador, String ip, int puerto) {
        this.idJugador = idJugador;
        this.ip = ip;
        this.puerto = puerto;
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
    }

}
