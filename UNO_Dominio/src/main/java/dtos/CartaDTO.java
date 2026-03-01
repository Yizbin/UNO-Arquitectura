/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Enums.Acciones;
import Enums.Comodines;
import Enums.TipoColor;

/**
 *
 * @author Abraham Coronel
 */
public class CartaDTO {

    /**
     * Si el tipo es numero, usa color+nunmero Si el tipo es accion, usa
     * color+accion Si el tipo es comodin, usa comodin+color(null antes de
     * elegir y una vez electo es el que se cambiará)
     */
    private TipoCartaDTO tipoCarta;
    private TipoColor color;    //puede ser null si es comodin antes de elegir
    private Integer numero;
    private Acciones acciones;
    private Comodines comodines;
    private String rutaImagen;

    public CartaDTO() {
    }

    public CartaDTO(TipoCartaDTO tipoCarta, TipoColor color, Integer numero, Acciones acciones, Comodines comodines, String rutaImagen) {
        this.tipoCarta = tipoCarta;
        this.color = color;
        this.numero = numero;
        this.acciones = acciones;
        this.comodines = comodines;
        this.rutaImagen = rutaImagen;
    }

    public TipoCartaDTO getTipoCarta() {
        return tipoCarta;
    }

    public void setTipoCarta(TipoCartaDTO tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public TipoColor getColor() {
        return color;
    }

    public void setColor(TipoColor color) {
        this.color = color;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Acciones getAcciones() {
        return acciones;
    }

    public void setAcciones(Acciones acciones) {
        this.acciones = acciones;
    }

    public Comodines getComodines() {
        return comodines;
    }

    public void setComodines(Comodines comodines) {
        this.comodines = comodines;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CartaDTO{");
        sb.append("tipoCarta=").append(tipoCarta);
        sb.append(", color=").append(color);
        sb.append(", numero=").append(numero);
        sb.append(", acciones=").append(acciones);
        sb.append(", comodines=").append(comodines);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
