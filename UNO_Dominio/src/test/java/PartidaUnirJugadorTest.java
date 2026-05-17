/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Entidades.Partida;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Abraham Coronel Bringas
 */
class PartidaUnirJugadorTest {

    @Test
    void debeUnirJugadorValidoALaPartida() {
        Partida partida = new Partida();
        JugadorResumenDTO jugadorDTO = new JugadorResumenDTO(1, "Abraham");

        partida.unirJugador(jugadorDTO);

        assertEquals(1, partida.getJugadores().size());
        assertEquals(1, partida.getJugadores().get(0).getId());
        assertEquals("Abraham", partida.getJugadores().get(0).getUsuario());
    }

    @Test
    void noDebePermitirUnirElMismoJugadorDosVeces() {
        Partida partida = new Partida();
        JugadorResumenDTO jugadorDTO = new JugadorResumenDTO(1, "Abraham");

        partida.unirJugador(jugadorDTO);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.unirJugador(jugadorDTO)
        );

        assertEquals("El jugador ya esta unido a la partida.", excepcion.getMessage());
        assertEquals(1, partida.getJugadores().size());
    }

    @Test
    void noDebePermitirUnirJugadorNulo() {
        Partida partida = new Partida();

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.unirJugador(null)
        );

        assertEquals("El jugador a unir no puede ser nulo.", excepcion.getMessage());
        assertTrue(partida.getJugadores().isEmpty());
    }

    @Test
    void debeMostrarAlJugadorEnElEstadoDespuesDeUnirse() {
        Partida partida = new Partida();
        JugadorResumenDTO jugadorDTO = new JugadorResumenDTO(1, "Abraham");

        partida.unirJugador(jugadorDTO);

        EstadoPartidaDTO estado = partida.obtenerEstadoPartidaDTO();

        assertNotNull(estado.getJugadores());
        assertEquals(1, estado.getJugadores().size());
        assertEquals(1, estado.getJugadores().get(0).getId());
        assertEquals("Abraham", estado.getJugadores().get(0).getNombreUsuario());
    }
}
