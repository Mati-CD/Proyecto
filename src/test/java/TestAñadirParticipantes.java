
import org.example.CodigoLogico.Participante;
import org.example.CodigoLogico.ParticipanteIndividual;
import org.example.CodigoLogico.Torneo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestAñadirParticipantes {

    private Torneo torneo;
    private Participante p1, p2, p3, p4;

    @BeforeEach
    public void setUp() {
        torneo = new Torneo("Copa Test", "Fútbol", "Individual", 4, 1);
        p1 = new ParticipanteIndividual("Pedro", 21,"Chile");
        p2 = new ParticipanteIndividual("Juan", 21,"Chile");
        p3 = new ParticipanteIndividual("Diego",28, "Chile");
        p4 = new ParticipanteIndividual("Vini",25, "Brasil");
    }

    @Test
    public void testConstructor() {
        assertEquals("Copa Test", torneo.getNombre());
        assertEquals("Fútbol", torneo.getDisciplina());
        assertEquals("Individual", torneo.getTipoDeInscripcion());
        assertEquals(4, torneo.getNumParticipantes());
        assertEquals(1, torneo.getNumMiembrosEquipo());
        assertTrue(torneo.getParticipantes().isEmpty());
    }

    @Test
    public void testAddParticipante() {
        assertTrue(torneo.addParticipante(p1));
        assertTrue(torneo.getParticipantes().contains(p1));
        assertEquals(1, torneo.getParticipantes().size());
    }
}