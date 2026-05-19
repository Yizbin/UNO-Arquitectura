/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package MVC_Sala;

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Enums.EstadoJugadorSala;
import Plantilla.ContextoPipeline;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Main {

    public static void main(String[] args) throws IOException, Exception {
        ModeloSala modelo = new ModeloSala();
        ControladorSala control = new ControladorSala(modelo);

        SalaEspera sala = new SalaEspera(control);
        modelo.suscribir(sala);
        sala.setVisible(true);

        JugadorResumenDTO jugador1 = new JugadorResumenDTO();
        jugador1.setId(1);
        jugador1.setNombreUsuario("dreammywiw0123");
        jugador1.setRutaAvatar("src/main/resources/default-profile-picture-icon-high-resolution-high-resolution-default-profile-picture-icon-symbolizing-no-display-picture-360167031.png");
        jugador1.setEstadoSala(EstadoJugadorSala.CONFIRMADO);

        JugadorResumenDTO jugador2 = new JugadorResumenDTO();
        jugador2.setId(2);
        jugador2.setNombreUsuario("Adel");
        jugador2.setRutaAvatar("src/main/resources/default-profile-picture-icon-high-resolution-high-resolution-default-profile-picture-icon-symbolizing-no-display-picture-360167031.png");
        jugador2.setEstadoSala(EstadoJugadorSala.CONFIRMADO);

        JugadorResumenDTO jugador3 = new JugadorResumenDTO();
        jugador3.setId(3);
        jugador3.setNombreUsuario("Norma");
        jugador3.setRutaAvatar("src/main/resources/default-profile-picture-icon-high-resolution-high-resolution-default-profile-picture-icon-symbolizing-no-display-picture-360167031.png");
        jugador3.setEstadoSala(EstadoJugadorSala.ESPERANDO);

        JugadorResumenDTO jugador4 = new JugadorResumenDTO();
        jugador4.setId(4);
        jugador4.setNombreUsuario("Angel");
        jugador4.setRutaAvatar("src/main/resources/default-profile-picture-icon-high-resolution-high-resolution-default-profile-picture-icon-symbolizing-no-display-picture-360167031.png");
        jugador4.setEstadoSala(EstadoJugadorSala.CONFIRMADO);

        EstadoPartidaDTO estadoMock = new EstadoPartidaDTO();
        estadoMock.setJugadores(List.of(jugador1, jugador2, jugador3, jugador4));

        modelo.enviar(new ContextoPipeline<>(estadoMock));
    }
}
