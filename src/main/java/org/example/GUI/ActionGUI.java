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
    },
    INICIAR_TORNEO("INICIAR_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelIniciarTorneo();
        }
    },
    REGISTRAR_RESULTADOS("REGISTRAR_RESULTADOS") {
      @Override
      public void ejecutar(Navegador navegador) {

      }
    },
    IR_A_USUARIO("IR_A_USUARIO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelUsuario();
        }
    },
    ESTADO_ACTUAL_TORNEO("ESTADO_ACTUAL_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelEstadoActualTorneo();
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
