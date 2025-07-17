
import org.example.CodigoLogico.Inscripciones;
import org.example.CodigoLogico.Participante;
import org.example.CodigoLogico.ParticipanteIndividual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TestInformacionBasica {
    private Inscripciones inscripciones;
    private Participante p1;
    private Participante p2;

    @BeforeEach
    public void setUp() {
        inscripciones = new Inscripciones();
        p1 = new ParticipanteIndividual("Vini", 25, "Brasil");
        p2 = new ParticipanteIndividual("Alexis",36, "Chile" );
    }

    @Test
    public void testAgregarParticipante() {
        inscripciones.add(p1);
        inscripciones.add(p2);

        List<Participante> lista = inscripciones.getInscritos();

        assertEquals(2, lista.size());
        assertTrue(lista.contains(p1));
        assertTrue(lista.contains(p2));
    }

    @Test
    public void testListaInicialVacia() {
        assertTrue(inscripciones.getInscritos().isEmpty());
    }
}