/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;


/**
 *
 * @author Abraham Coronel
 */
public class MenuDTO {

    private String nombreJugador;
    private String avatar;

    public MenuDTO() {
    }

    public MenuDTO(String nombreJugador, String avatar) {
        this.nombreJugador = nombreJugador;
        this.avatar = avatar;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
