/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import javax.swing.Icon;

/**
 *
 * @author Abraham Coronel
 */
public class MenuDTO {

    private String nombreJugador;
    private Icon avatar;

    public MenuDTO() {
    }

    public MenuDTO(String nombreJugador, Icon avatar) {
        this.nombreJugador = nombreJugador;
        this.avatar = avatar;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Icon getAvatar() {
        return avatar;
    }

    public void setAvatar(Icon avatar) {
        this.avatar = avatar;
    }

}
