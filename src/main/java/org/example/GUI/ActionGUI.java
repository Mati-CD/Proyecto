package org.example.GUI;

/**
 * Representa las distintas acciones de navegación disponibles en la interfaz gráfica (GUI) de la aplicación.
 * Cada acción está asociada a un panel que debe mostrarse en el Navegador.
 */
public enum ActionGUI {

    /** Usado para mostrar el panel de inicio. */
    IR_A_INICIO("INICIO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelInicio();
        }
    },

    /** Usado para mostrar el panel del organizador. */
    IR_A_ORGANIZADOR("ORGANIZADOR") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelOrganizador();
        }
    },

    /** Usado para mostrar el panel de creación de torneo. */
    IR_A_CREAR_TORNEO("CREAR_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelCrearTorneo();
        }
    },

    /** Usado para mostrar el panel de inscripción de participantes. */
    IR_A_INSCRIBIR_PARTICIPANTES("INSCRIBIR_PARTICIPANTES") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelInscribirParticipantes();
        }
    },

    /** Usado para mostrar el panel de inicio del torneo. */
    IR_A_INICIAR_TORNEO("INICIAR_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelIniciarTorneo();
        }
    },

    /** Usado para mostrar el panel de registro de resultados. */
    IR_A_REGISTRAR_RESULTADOS("REGISTRAR_RESULTADOS") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelRegistrarResultados();
        }
    },

    /** Usado para mostrar el panel de usuario. */
    IR_A_USUARIO("USUARIO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelUsuario();
        }
    },

    /** Usado para mostrar el panel del estado actual del torneo. */
    IR_A_ESTADO_ACTUAL_TORNEO("ESTADO_ACTUAL_TORNEO") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelEstadoActualTorneo();
        }
    },

    /** Usado para mostrar el panel de próximos encuentros. */
    IR_A_PROXIMOS_ENCUENTROS("PROXIMOS_ENCUENTROS") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelProximosEncuentros();
        }
    },

    /** Usado para mostrar el panel de estadísticas generales. */
    IR_A_ESTADISTICAS_GENERALES("ESTADISTICAS_GENERALES") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelEstadisticasGenerales();
        }
    },

    /** Usado para mostrar el panel de participantes registrados. */
    IR_A_VER_PARTICIPANTES("VER_PARTICIPANTES") {
        @Override
        public void ejecutar(Navegador navegador) {
            navegador.mostrarPanelVerParticipantes();
        }
    };

    /** Identificador único de la acción. */
    private final String id;

    /**
     * @param id Identificador de la acción.
     */
    ActionGUI(String id) {
        this.id = id;
    }

    /**
     * Devuelve el identificador asociado a esta acción.
     *
     * @return El identificador de la acción.
     */
    public String getID() {
        return id;
    }

    /**
     * Ejecuta la acción correspondiente sobre el Navegador
     *
     * @param navegador El objeto navegador sobre el cual se aplica la acción.
     */
    public abstract void ejecutar(Navegador navegador);

    /**
     * Busca una acción a partir de su identificador.
     *
     * @param id El identificador de la acción.
     * @return La acción correspondiente, o {@code null} si no se encuentra.
     */
    public static ActionGUI getByID(String id) {
        for (ActionGUI action : values()) {
            if (action.id.equals(id)) {
                return action;
            }
        }
        return null;
    }
}