package amartinm.draughts.views;

import amartinm.draughts.models.Error;

public class ErrorView extends SubView {

    static final String[] MESSAGES = {
            "Error!!! No te entiendo: <d><d>{,<d><d>}[0-2]",
            "Error!!! No hay ficha que mover",
            "Error!!! No es una de tus fichas",
            "Error!!! No vas en diagonal",
            "Error!!! No está vacío el destino",
            "Error!!! No avanzas",
            "Error!!! No comes contrarias",
            "Error!!! No puedes comer tus fichas",
            "Error!!! No respetas la distancia",
            "Error!!! No se puede comer tantas en un movimiento",
            "Error!!! No se puede comer tantas en un salto"
    };

    ErrorView() {
        super();
    }

    void write(Error error) {
        this.console.writeln(MESSAGES[error.ordinal()]);
    }
}
