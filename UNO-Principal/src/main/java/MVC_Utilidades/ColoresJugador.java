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
        if (coloresLocales == null) {
            coloresLocales = new HashMap<>();
        }
        if (coloresLocales.isEmpty()) {
            coloresLocales.put(TipoColor.ROJO, TipoColor.ROJO);
            coloresLocales.put(TipoColor.AZUL, TipoColor.AZUL);
            coloresLocales.put(TipoColor.VERDE, TipoColor.VERDE);
            coloresLocales.put(TipoColor.AMARILLO, TipoColor.AMARILLO);
        }
        return coloresLocales;
    }

    public static void setColores(Map<TipoColor, TipoColor> nuevosColores) {
        if (nuevosColores != null && !nuevosColores.isEmpty()) {
            coloresLocales = nuevosColores;
        }
    }
}
