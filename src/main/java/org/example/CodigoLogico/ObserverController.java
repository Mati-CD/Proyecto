package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que implementa el patrón Observer.
 * Permite registrar, remover y notificar observadores asociados a eventos del torneo.
 */
public abstract class ObserverController {

    protected final List<TorneoObserver> observers = new ArrayList<>();

    /**
     * Registra un nuevo observador si no ha sido registrado previamente.
     *
     * @param observer el observador a registrar
     */
    public void registrarObserver(TorneoObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Elimina un observador previamente registrado.
     *
     * @param observer el observador a remover
     */
    public void removerObserver(TorneoObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores registrados con un mensaje.
     *
     * @param mensaje el mensaje a enviar a los observadores
     */
    protected void notificarObservers(String mensaje) {
        for (TorneoObserver observer : new ArrayList<>(observers)) {
            observer.actualizar(mensaje);
        }
    }
}