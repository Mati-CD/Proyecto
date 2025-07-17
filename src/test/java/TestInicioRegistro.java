import org.example.CodigoLogico.Participante;
import org.example.CodigoLogico.ParticipanteIndividual;
import org.example.CodigoLogico.Partido;
import org.example.CodigoLogico.Torneo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TestInicioRegistro{

    private Torneo torneo;
    private Participante p1, p2, p3, p4;

    @BeforeEach
    public void setUp() {
        torneo = new Torneo("Torneo Resultados", "Tenis", "Individual", 4, 1);
        p1 = new ParticipanteIndividual("Cristiano",40, "Portugal");
        p2 = new ParticipanteIndividual("Ney",21 ,"Chile");
        p3 = new ParticipanteIndividual("Joshua",30, "Alemania");
        p4 = new ParticipanteIndividual("Tyler",31, "USA");
    }

    @Test
    public void testInicioYRegistroDeResultados() {
        //Verificar inicio de torneo
        List<Participante> lista = Arrays.asList(p1, p2, p3, p4);
        torneo.iniciarTorneo(lista);

        assertEquals(4, torneo.getParticipantes().size(), "El torneo debe tener 4 participantes");
        assertEquals(1, torneo.getFases().size(), "Debe haberse creado una fase inicial");
        assertEquals(2, torneo.getPartidosActuales().size(), "Debe haber 2 partidos con 4 participantes");

        //REgistro de resultados
        List<Partido> partidos = torneo.getPartidosActuales();
        Partido partido1 = partidos.get(0);
        Partido partido2 = partidos.get(1);

        //Asegurar que los partidos aún no están finalizados
        assertFalse(partido1.tieneResultado());
        assertFalse(partido2.tieneResultado());

        //Registrar ganadores
        torneo.registrarResultado(partido1, partido1.getJugador1());
        torneo.registrarResultado(partido2, partido2.getJugador2());

        //Verificar que estén finalizados
        assertTrue(partido1.tieneResultado(), "El partido 1 debe estar finalizado");
        assertTrue(partido2.tieneResultado(), "El partido 2 debe estar finalizado");

        assertEquals(partido1.getJugador1(), partido1.getGanador(), "El ganador del partido 1 debe ser el jugador 1");
        assertEquals(partido2.getJugador2(), partido2.getGanador(), "El ganador del partido 2 debe ser el jugador 2");

        //Verificar que todos los partidos estén completos
        assertTrue(torneo.todosLosPartidosCompletados(), "Todos los partidos deben estar finalizados");
    }
}
