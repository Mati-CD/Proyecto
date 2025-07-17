import org.example.CodigoLogico.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TestEstados {

    private Torneo torneo;
    private Participante p1, p2, p3, p4;

    @BeforeEach
    public void setUp() {
        torneo = new Torneo("Torneo Estados", "Futbol", "Individual", 4, 1);
        p1 = new ParticipanteIndividual("VINICIU", 25,"Brasil");
        p2 = new ParticipanteIndividual("Benja", 21, "Chile");
        p3 = new ParticipanteIndividual("Cristiano", 40,"Portugal");
        p4 = new ParticipanteIndividual("Antony",25,  "Brasil");
    }

    @Test
    public void testEstadosDelTorneo() {
        List<Participante> lista = Arrays.asList(p1, p2, p3, p4);
        torneo.iniciarTorneo(lista);

        // Verificar que se haya creado una fase
        List<FaseTorneo> fases = torneo.getFases();
        assertEquals(1, fases.size(), "Debe haber una fase creada al iniciar el torneo");

        // Verificar que la fase actual coincida
        FaseTorneo faseActual = torneo.getFaseActual();
        assertNotNull(faseActual, "Debe haber una fase actual");
        assertEquals(fases.get(0), faseActual, "La fase actual debe ser la única existente");

        // Verificar que existan partidos programados
        List<Partido> partidos = torneo.getPartidosActuales();
        assertEquals(2, partidos.size(), "Deben haberse creado 2 partidos con 4 participantes");

    }
}