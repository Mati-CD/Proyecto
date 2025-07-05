package org.example.CodigoLogico;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear un torneo
        Torneo torneo = new Torneo("Davis Cup", "Tenis");

        // Registrar un observador simple que muestra notificaciones en consola
        torneo.registrarObserver(mensaje -> System.out.println("[NOTIFICACIÓN] " + mensaje));

        // Agregar participantes
        torneo.agregarParticipante("Rafael Nadal");
        torneo.agregarParticipante("Novak Djokovic");
        torneo.agregarParticipante("Roger Federer");
        torneo.agregarParticipante("Nicolás Massú");
        torneo.agregarParticipante("Carlos Alcaraz");
        torneo.agregarParticipante("Nicolás Jarry");
        torneo.agregarParticipante("Stefanos Tsitsipas");
        torneo.agregarParticipante("Marcelo Ríos");

        // Iniciar el torneo
        System.out.println("\n=== INICIANDO TORNEO ===");
        torneo.iniciarTorneo();

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