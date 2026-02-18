/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entidades;

import Enums.AccionesPosibles;

/**
 *
 * @author Abraham Coronel
 */
public class Ruleta {

    private AccionesPosibles accionesPosibles;

    public Ruleta() {
    }

    public Ruleta(AccionesPosibles accionesPosibles) {
        this.accionesPosibles = accionesPosibles;
    }

    public AccionesPosibles getAccionesPosibles() {
        return accionesPosibles;
    }

    public void setAccionesPosibles(AccionesPosibles accionesPosibles) {
        this.accionesPosibles = accionesPosibles;
    }
    
    
}
