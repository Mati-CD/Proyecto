package org.example.GUI;

import javax.swing.*;

/**
 * Enum que representa los distintos tipos de paneles disponibles en la aplicación.
 * Cada tipo sabe cómo instanciar su propio panel correspondiente.
 */
public enum PanelType {
    INICIO("INICIO") {
        @Override
        public PanelInicio crearPanel() {
            return new PanelInicio();
        }
    },
    USUARIO("USUARIO") {
        @Override
        public PanelUsuario crearPanel() {
            return new PanelUsuario();
        }
    },
    ORGANIZADOR("ORGANIZADOR") {
        @Override
        public PanelOrganizador crearPanel() {
            return new PanelOrganizador();
        }
    },
    CREAR_TORNEO("CREAR_TORNEO") {
        @Override
        public PanelCrearTorneo crearPanel() {
            return new PanelCrearTorneo();
        }
    },
    ELIMINAR_TORNEO("ELIMINAR_TORNEO") {
        @Override
        public PanelEliminarTorneo crearPanel() { return new PanelEliminarTorneo();}
    },
    INSCRIBIR_PARTICIPANTES("INSCRIBIR_PARTICIPANTES") {
        @Override
        public PanelInscribirParticipantes crearPanel() {
            return new PanelInscribirParticipantes();
        }
    },
    ESTADO_ACTUAL("ESTADO_ACTUAL") {
        @Override
        public PanelEstadoActualTorneo crearPanel() {
            return new PanelEstadoActualTorneo();
        }
    },
    INICIAR_TORNEO("INICIAR_TORNEO") {
        @Override
        public PanelIniciarTorneo crearPanel() {
            return new PanelIniciarTorneo();
        }
    },
    REGISTRAR_RESULTADOS("REGISTRAR_RESULTADOS") {
        @Override
        public PanelRegistrarResultados crearPanel() {
            return new PanelRegistrarResultados();
        }
    },
    PROXIMOS_ENCUENTROS("PROXIMOS_ENCUENTROS") {
        @Override
        public PanelProximosEncuentros crearPanel() {
            return new PanelProximosEncuentros();
        }
    },
    ESTADISTICAS_GENERALES("ESTADISTICAS_GENERALES") {
        @Override
        public PanelEstadisticasGenerales crearPanel() {
            return new PanelEstadisticasGenerales();
        }
    },
    VER_PARTICIPANTES("VER_PARTICIPANTES") {
        @Override
        public PanelVerParticipantes crearPanel() {
            return new PanelVerParticipantes();
        }
    };

    private final String id;

    /**
     * Constructor que asigna un identificador único al tipo de panel.
     *
     * @param id identificador textual del panel.
     */
    PanelType(String id) {
        this.id = id;
    }

    /**
     * Retorna el ID asociado a este tipo de panel.
     *
     * @return identificador del panel.
     */
    public String getID() {
        return id;
    }

    /**
     * Crea una instancia del panel asociado a este tipo.
     *
     * @return una instancia del panel correspondiente.
     */
    public abstract JPanel crearPanel();

    /**
     * Busca un PanelType según su ID.
     *
     * @param id el identificador a buscar.
     * @return el PanelType correspondiente, o null si no se encuentra.
     */
    public static PanelType getByID(String id) {
        for (PanelType panel : values()) {
            if (panel.id.equals(id)) {
                return panel;
            }
        }
        return null;
    }
}