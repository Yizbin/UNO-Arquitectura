/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DTOs.JugadorResumenDTO;
import Interfaces.ISubDominio;
import MVC_JugarTurno.PantallaTurno;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Abraham Coronel
 */
public class GestorPartidaLocal {

    public void iniciarSimulacion(ISubDominio subDominio, PantallaTurno vistaJ1, PantallaTurno vistaJ2) {
        // Mostrar ventanas
        vistaJ1.setVisible(true);
        vistaJ2.setVisible(true);

        try {
            List<JugadorResumenDTO> jugadores = new ArrayList<>();
            jugadores.add(new JugadorResumenDTO(1, "Jugador 1", 0, 0, true));
            jugadores.add(new JugadorResumenDTO(2, "Jugador 2", 0, 0, false));

            subDominio.prepararJuego(jugadores);

            vistaJ1.update();
            vistaJ2.update();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        iniciarSincronizador(vistaJ1, vistaJ2);
    }

    private void iniciarSincronizador(PantallaTurno vistaJ1, PantallaTurno vistaJ2) {
        Timer sincronizador = new Timer(500, e -> {
            vistaJ1.update();
            vistaJ2.update();
        });
        sincronizador.start();
    }
}
