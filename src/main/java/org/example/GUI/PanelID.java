package org.example.GUI;

import javax.swing.*;

public enum PanelID {
    INICIO("INICIO") {
        @Override
        public JPanel crearPanel() {
            return new PanelInicio();
        }
    },
    USUARIO("USUARIO") {
        @Override
        public JPanel crearPanel() {
            return new PanelUsuario();
        }
    },
    ORGANIZADOR("ORGANIZADOR") {
        @Override
        public JPanel crearPanel() {
            return new PanelOrganizador();
        }
    },
    CREAR_TORNEO("CREAR_TORNEO") {
        @Override
        public JPanel crearPanel() {
            return new PanelCrearTorneo();
        }
    },
    INSCRIBIR_PARTICIPANTES("INSCRIBIR_PARTICIPANTES") {
        @Override
        public JPanel crearPanel() {
            return new PanelInscribirParticipantes();
        }
    },
    ESTADO_ACTUAL("ESTADO_ACTUAL") {
        @Override
        public JPanel crearPanel() {
            return new PanelEstadoActualTorneo();
        }
    },
    INICIAR_TORNEO("INICIAR_TORNEO") {
        @Override
        public JPanel crearPanel() {
            return new PanelIniciarTorneo();
        }
    };

    private final String id;

    PanelID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public abstract JPanel crearPanel();

    public static PanelID getByID(String id) {
        for (PanelID panel : values()) {
            if (panel.id.equals(id)) {
                return panel;
            }
        }
        return null;
    }
}
