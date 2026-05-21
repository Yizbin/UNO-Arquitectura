/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Utilidades;

import Enums.TipoColor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class ColoresJugador {
    private static Map<TipoColor, TipoColor> coloresLocales = new HashMap<>();

    public static Map<TipoColor, TipoColor> getColores() {
        return coloresLocales;
    }

    public static void setColores(Map<TipoColor, TipoColor> nuevosColores) {
        if (nuevosColores != null) {
            coloresLocales = nuevosColores;
        }
    }
}
