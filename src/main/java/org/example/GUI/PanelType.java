package org.example.GUI;

import javax.swing.*;

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
    };

    private final String id;

    PanelType(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public abstract JPanel crearPanel();

    public static PanelType getByID(String id) {
        for (PanelType panel : values()) {
            if (panel.id.equals(id)) {
                return panel;
            }
        }
        return null;
    }
}
