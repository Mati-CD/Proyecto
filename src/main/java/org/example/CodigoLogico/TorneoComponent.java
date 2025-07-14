package org.example.CodigoLogico;

import java.util.List;

/**
 * Clase abstracta que define la interfaz común para todos los componentes del patrón Composite
 * en la estructura del torneo. Puede representar tanto fases como partidos individuales.
 */
public abstract class TorneoComponent {

    /**
     * Retorna el nombre del componente del torneo.
     *
     * @return nombre del componente
     */
    public abstract String getNombre();

    /**
     * Retorna la lista de subcomponentes del componente actual.
     * Si el componente es una hoja (como un partido), puede retornar una lista vacía.
     *
     * @return lista de subcomponentes
     */
    public abstract List<TorneoComponent> getComponentes();

    /**
     * Agrega un subcomponente al componente actual.
     * En componentes hoja, puede lanzar una excepción si no está permitido.
     *
     * @param component componente a agregar
     */
    public abstract void agregar(TorneoComponent component);
}