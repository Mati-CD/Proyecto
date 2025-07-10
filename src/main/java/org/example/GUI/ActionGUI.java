package org.example.GUI;

public enum ActionGUI {
    IR_A_INICIO("IR_A_INICIO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelInicio();
        }
    },
    IR_A_ORGANIZADOR("IR_A_ORGANIZADOR") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelOrganizador();
        }
    },
    IR_A_USUARIO("IR_A_USUARIO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelUsuario();
        }
    },
    CREAR_TORNEO("CREAR_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelCrearTorneo();
        }
    },
    INSCRIBIR_PARTICIPANTES("INSCRIBIR_PARTICIPANTES") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelInscribirParticipantes();
        }
    };

    private final String ID;

    ActionGUI(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public abstract void ejecutar(Navegador navegador);

    public static ActionGUI getById(String id) {
        for (ActionGUI action : values()) {
            if (action.ID.equals(id)) {
                return action;
            }
        }
        return null;
    }
}
