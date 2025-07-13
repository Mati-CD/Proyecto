package org.example.CodigoLogico;

import java.util.ArrayList;
import java.util.List;

public abstract class ObserverController {
    protected final List<TorneoObserver> observers = new ArrayList<>();

    public void registrarObserver(TorneoObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removerObserver(TorneoObserver observer) {
        observers.remove(observer);
    }

    protected void notificarObservers(String mensaje) {
        for (TorneoObserver observer : new ArrayList<>(observers)) {
            observer.actualizar(mensaje);
        }
    }
}
