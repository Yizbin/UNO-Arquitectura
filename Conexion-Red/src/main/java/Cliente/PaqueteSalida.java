/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

/**
 *
 * @author Abraham Coronel
 */
public class PaqueteSalida {

    private Object mensaje;
    private String ip;
    private int puerto;

    public PaqueteSalida() {
    }

    public PaqueteSalida(Object mensaje, String ip, int puerto) {
        this.mensaje = mensaje;
        this.ip = ip;
        this.puerto = puerto;
    }

    public Object getMensaje() {
        return mensaje;
    }

    public void setMensaje(Object mensaje) {
        this.mensaje = mensaje;
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
