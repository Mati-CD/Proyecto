package org.example.CodigoLogico;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear un torneo
        Torneo torneo = new Torneo("Davis Cup", "Tennis", "Individiual", 8);

        // Registrar un observador simple que muestra notificaciones en consola
        torneo.registrarObserver(mensaje -> System.out.println("[NOTIFICACIÓN] " + mensaje));

        Inscripciones inscritos = new Inscripciones();
        inscritos.add(new ParticipanteIndividual("Rafael Nadal",36,"España"));
        inscritos.add(new ParticipanteIndividual("Novak Djokovic",35, "Serbia"));
        inscritos.add(new ParticipanteIndividual("Nicolás Massú", 43, "Chile"));
        inscritos.add(new ParticipanteIndividual("Carlos Alcaraz", 20, "España"));
        inscritos.add(new ParticipanteIndividual("Nicolás Jarry", 27, "Chile"));
        inscritos.add(new ParticipanteIndividual("Stefanos Tsitsipas", 24, "Grecia"));
        inscritos.add(new ParticipanteIndividual("Marcelo Ríos", 47, "Chile"));
        inscritos.add(new ParticipanteIndividual("Roger Federer", 41, "Suiza"));

        for (Participante p : inscritos.getInscritos()) {
            torneo.addParticipante(p);
        }

        // Iniciar el torneo
        System.out.println("\n=== INICIANDO TORNEO ===");
        torneo.iniciarTorneo(torneo.sorteoParticipantesRandom(torneo.getParticipantes()));

        // Mostrar estructura del torneo
        System.out.println(torneo.getEstructuraTexto());

        // Simular partidos con entrada de usuario
        Scanner scanner = new Scanner(System.in);
        while (!torneo.tieneCampeon()) {
            FaseTorneo faseActual = torneo.getFaseActual();
            System.out.println("\n=== " + faseActual.getNombre().toUpperCase() + " ===");

            for (Partido partido : torneo.getPartidosActuales()) {
                if (!partido.tieneResultado()) {
                    System.out.println("\nPartido: " + partido.getNombre());
                    System.out.print("Ingrese resultado (1 para " + partido.getJugador1() +
                            ", 2 para " + partido.getJugador2() + "): ");
                    String resultado = scanner.nextLine();
                    torneo.registrarResultado(partido, resultado);
                }
            }

            // Mostrar estado actual del torneo
            System.out.println("\n=== ESTADO ACTUAL DEL TORNEO ===");
            System.out.println(torneo.getEstructuraTexto());
        }

        // Mostrar campeón
        System.out.println("\n=== TORNEO FINALIZADO ===");
        System.out.println("¡El campeón es: " + torneo.toString().split("!!! ===\n")[1]);

        scanner.close();
    }
}