/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Entidades.Partida;
import Enums.EstadoJugadorSala;
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

    @Test
    void debePermitirUnirJugadorSinPerfilCompleto() {
        Partida partida = new Partida();
        JugadorResumenDTO jugadorDTO = new JugadorResumenDTO();

        partida.unirJugador(jugadorDTO);

        assertEquals(1, partida.getJugadores().size());
        assertEquals(0, partida.getJugadores().get(0).getId());
        assertNull(partida.getJugadores().get(0).getUsuario());
    }

    @Test
    void jugadorSinPerfilDebeQuedarEnEstadoEsperando() {
        Partida partida = new Partida();
        JugadorResumenDTO jugadorDTO = new JugadorResumenDTO();

        partida.unirJugador(jugadorDTO);

        assertEquals(
                EstadoJugadorSala.ESPERANDO,
                partida.getJugadores().get(0).getEstadoSala()
        );
    }

    @Test
    void debeConservarLosDatosCuandoJugadorLlegaConPerfilCompleto() {
        Partida partida = new Partida();
        JugadorResumenDTO jugadorDTO = new JugadorResumenDTO(10, "Abraham");

        partida.unirJugador(jugadorDTO);

        assertEquals(10, partida.getJugadores().get(0).getId());
        assertEquals("Abraham", partida.getJugadores().get(0).getUsuario());
    }

    @Test
    void dosJugadoresSinPerfilActualmenteSeConsideranDuplicados() {
        Partida partida = new Partida();

        JugadorResumenDTO jugador1 = new JugadorResumenDTO();
        JugadorResumenDTO jugador2 = new JugadorResumenDTO();

        partida.unirJugador(jugador1);

        assertThrows(
                IllegalArgumentException.class,
                () -> partida.unirJugador(jugador2)
        );
    }

    @Test
    void debePermitirUnirJugadoresSinNombreSiTienenIdsDistintos() {
        Partida partida = new Partida();

        JugadorResumenDTO jugador1 = new JugadorResumenDTO();
        jugador1.setId(1);

        JugadorResumenDTO jugador2 = new JugadorResumenDTO();
        jugador2.setId(2);

        partida.unirJugador(jugador1);
        partida.unirJugador(jugador2);

        assertEquals(2, partida.getJugadores().size());
    }

    @Test
    void debeActualizarJugadorProvisionalCuandoSeCompletaSuPerfil() {
        Partida partida = new Partida();

        JugadorResumenDTO jugadorVacio = new JugadorResumenDTO();
        jugadorVacio.setId(1);

        partida.unirJugador(jugadorVacio);

        JugadorResumenDTO perfilActualizado = new JugadorResumenDTO();
        perfilActualizado.setId(1);
        perfilActualizado.setNombreUsuario("Abraham");

        partida.actualizarPerfilJugador(perfilActualizado);

        assertEquals(1, partida.getJugadores().size());
        assertEquals(1, partida.getJugadores().get(0).getId());
        assertEquals("Abraham", partida.getJugadores().get(0).getUsuario());
    }
}
