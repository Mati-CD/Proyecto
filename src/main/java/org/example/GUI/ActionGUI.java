package org.example.GUI;

public enum ActionGUI {
    IR_A_INICIO("INICIO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelInicio();
        }
    },
    IR_A_ORGANIZADOR("ORGANIZADOR") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelOrganizador();
        }
    },
    IR_A_CREAR_TORNEO("CREAR_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelCrearTorneo();
        }
    },
    IR_A_INSCRIBIR_PARTICIPANTES("INSCRIBIR_PARTICIPANTES") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelInscribirParticipantes();
        }
    },
    IR_A_INICIAR_TORNEO("INICIAR_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelIniciarTorneo();
        }
    },
    IR_A_REGISTRAR_RESULTADOS("REGISTRAR_RESULTADOS") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelRegistrarResultados();
        }
    },
    IR_A_USUARIO("USUARIO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelUsuario();
        }
    },
    IR_A_ESTADO_ACTUAL_TORNEO("ESTADO_ACTUAL_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelEstadoActualTorneo();
        }
    },
    IR_A_PROXIMOS_ENCUENTROS("PROXIMOS_ENCUENTROS") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelProximosEncuentros();
        }
    },
    IR_A_ESTADISTICAS_GENERALES("ESTADISTICAS_GENERALES") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelEstadisticasGenerales();
        }
    },
    IR_A_VER_PARTICIPANTES("VER_PARTICIPANTES") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelVerParticipantes();
        }
    };

    private final String id;

    ActionGUI(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public abstract void ejecutar(Navegador navegador);

    public static ActionGUI getByID(String id) {
        for (ActionGUI action : values()) {
            if (action.id.equals(id)) {
                return action;
            }
        }
        return null;
    }
}