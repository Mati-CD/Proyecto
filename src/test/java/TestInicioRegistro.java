import org.example.CodigoLogico.Participante;
import org.example.CodigoLogico.ParticipanteIndividual;
import org.example.CodigoLogico.Partido;
import org.example.CodigoLogico.Torneo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class TestInicioRegistro {

    private Torneo torneo;
    private Participante jugadorA, jugadorB, jugadorC, jugadorD;

    @BeforeEach
    public void setUp() {
        // Configuración inicial con nombres más descriptivos
        torneo = new Torneo("Torneo de Prueba", "Tenis", "Individual", 4, 1);
        jugadorA = new ParticipanteIndividual("Jugador A", 25, "España");
        jugadorB = new ParticipanteIndividual("Jugador B", 30, "Francia");
        jugadorC = new ParticipanteIndividual("Jugador C", 28, "Italia");
        jugadorD = new ParticipanteIndividual("Jugador D", 22, "Alemania");
    }

    @Test
    public void testInicioTorneoCorrecto() {
        // Arrange
        List<Participante> participantes = Arrays.asList(jugadorA, jugadorB, jugadorC, jugadorD);

        // Act
        torneo.iniciarTorneo(participantes);

        // Assert
        assertEquals(4, torneo.getParticipantes().size(),
                "El torneo debe tener exactamente 4 participantes");

        assertNotNull(torneo.getFaseActual(),
                "Debe existir una fase actual después de iniciar el torneo");

        assertEquals("Semifinales", torneo.getFaseActual().getNombre(),
                "Para 4 participantes la fase inicial debe ser Semifinales");

        assertEquals(2, torneo.getPartidosActuales().size(),
                "Deben crearse 2 partidos para 4 participantes");

        assertFalse(torneo.tieneCampeon(),
                "No debe haber campeón al inicio del torneo");
    }

    @Test
    public void testRegistroResultadosYAvanceFases() {
        // Arrange - Iniciar torneo
        List<Participante> participantes = Arrays.asList(jugadorA, jugadorB, jugadorC, jugadorD);
        torneo.iniciarTorneo(participantes);

        // Verificar emparejamientos correctos
        List<Partido> partidosIniciales = torneo.getPartidosActuales();
        assertEquals(2, partidosIniciales.size());

        // Verificar que los emparejamientos son los esperados (A vs B, C vs D)
        Partido partido1 = partidosIniciales.get(0);
        Partido partido2 = partidosIniciales.get(1);

        assertTrue((partido1.getJugador1().equals("Jugador A (España)") && partido1.getJugador2().equals("Jugador B (Francia)")) ||
                        (partido1.getJugador1().equals("Jugador B (Francia)") && partido1.getJugador2().equals("Jugador A (España)")),
                "Emparejamiento incorrecto en semifinal 1");

        assertTrue((partido2.getJugador1().equals("Jugador C (Italia)") && partido2.getJugador2().equals("Jugador D (Alemania)")) ||
                        (partido2.getJugador1().equals("Jugador D (Alemania)") && partido2.getJugador2().equals("Jugador C (Italia)")),
                "Emparejamiento incorrecto en semifinal 2");

        // Resto del test...
    }

    @Test
    public void testInicioTorneoConParticipantesIncorrectos() {
        // Arrange - Lista con solo 3 participantes (se necesitan 4)
        List<Participante> participantes = Arrays.asList(jugadorA, jugadorB, jugadorC);

        // Act
        torneo.iniciarTorneo(participantes);

        // Assert
        assertTrue(torneo.getParticipantes().isEmpty(),
                "No se deben aceptar participantes si no se alcanza el número requerido");
        assertTrue(torneo.getFases().isEmpty(),
                "No se deben crear fases si no se alcanza el número requerido de participantes");
    }

    @Test
    public void testRegistroResultadoInvalido() {
        // Arrange - Iniciar torneo válido
        List<Participante> participantes = Arrays.asList(jugadorA, jugadorB, jugadorC, jugadorD);
        torneo.iniciarTorneo(participantes);
        Partido partido = torneo.getPartidosActuales().get(0);

        // Act & Assert - Intentar registrar un jugador que no está en el partido
        assertThrows(IllegalArgumentException.class, () -> {
            torneo.registrarResultado(partido, "Jugador Inexistente");
        }, "Debe lanzar excepción al intentar registrar un jugador que no está en el partido");

        // Registrar resultado válido
        torneo.registrarResultado(partido, partido.getJugador1());

        // Intentar registrar resultado nuevamente (ya está registrado)
        assertThrows(IllegalStateException.class, () -> {
            torneo.registrarResultado(partido, partido.getJugador2());
        }, "Debe lanzar excepción al intentar modificar un resultado ya registrado");
    }
}