package amartinm.draughts.views;

import amartinm.draughts.controllers.*;
import amartinm.draughts.models.Color;
import amartinm.draughts.models.Coordinate;
import amartinm.draughts.models.Error;
import amartinm.draughts.models.Piece;
import amartinm.draughts.utils.Console;
import amartinm.draughts.utils.YesNoDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class View implements InteractorControllersVisitor {

    private static final String TITTLE = "Draughts";
    private static final String COLOR_PARAM = "#color";
    private static final String[] COLOR_VALUES = {"blancas", "negras"};
    private static final String PROMPT = "Mueven las " + COLOR_PARAM + ": ";
    private static final String CANCEL_FORMAT = "-1";
    private static final String MOVEMENT_FORMAT = "[1-8]{2}(\\.[1-8]{2}){1,2}";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";
    private static final String RESUME_MESSAGE = "¿Queréis jugar otra";
    static final String[] ERROR_MESSAGES = {
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

    private Console console;
    private YesNoDialog yesNoDialog;
    private String string;

    public View() {
        this.console = new Console();
        this.yesNoDialog = new YesNoDialog();
    }

    public void interact(InteractorController controller) {
        assert controller != null;
        controller.accept(this);
    }

    @Override
    public void visit(StartController startController) {
        assert startController != null;
        this.console.writeln(TITTLE);
        this.write(startController);
        startController.start();
    }

    @Override
    public void visit(PlayController playController) {
        assert playController != null;
        Error error;
        do {
            error = null;
            this.string = this.read(playController.getColor());
            if (this.isCanceledFormat())
                playController.cancel();
            else if (!this.isMoveFormat()) {
                error = Error.BAD_FORMAT;
                this.writeError(error);
            } else {
                error = playController.move(this.getCoordinates());
                if (error != null)
                    this.writeError(error);
                else {
                    this.write(playController);
                    if (playController.isBlocked())
                        this.writeLost();
                }
            }
        } while (error != null);
    }

    @Override
    public void visit(ResumeController resumeController) {
        assert resumeController != null;
        if (this.yesNoDialog.read(RESUME_MESSAGE))
            resumeController.reset();
        else
            resumeController.next();
    }

    void write(InteractorController controller) {
        assert controller != null;
        final int DIMENSION = controller.getDimension();
        this.writeNumbersLine(DIMENSION);
        for (int i = 0; i < DIMENSION; i++)
            this.writePiecesRow(i, controller);
        this.writeNumbersLine(DIMENSION);
    }

    private void writeNumbersLine(final int DIMENSION) {
        this.console.write(" ");
        for (int i = 0; i < DIMENSION; i++)
            this.console.write((i + 1) + "");
        this.console.writeln();
    }

    private void writePiecesRow(final int row, InteractorController controller) {
        this.console.write((row + 1) + "");
        for (int j = 0; j < controller.getDimension(); j++) {
            Piece piece = controller.getPiece(new Coordinate(row, j));
            if (piece == null)
                this.console.write(" ");
            else
                this.console.write(piece.getCode());
        }
        this.console.writeln((row + 1) + "");
    }

    private String read(Color color) {
        final String titleColor = PROMPT.replace(COLOR_PARAM, COLOR_VALUES[color.ordinal()]);
        return this.console.readString(titleColor);
    }

    private boolean isCanceledFormat() {
        return string.equals(CANCEL_FORMAT);
    }

    private boolean isMoveFormat() {
        return Pattern.compile(MOVEMENT_FORMAT).matcher(string).find();
    }

    private void writeError(Error error) {
        this.console.writeln(ERROR_MESSAGES[error.ordinal()]);
    }

    private Coordinate[] getCoordinates() {
        assert this.isMoveFormat();
        List<Coordinate> coordinateList = new ArrayList<>();
        while (string.length() > 0) {
            coordinateList.add(Coordinate.getInstance(string.substring(0, 2)));
            string = string.substring(2);
            if (string.length() > 0 && string.charAt(0) == '.')
                string = string.substring(1);
        }
        Coordinate[] coordinates = new Coordinate[coordinateList.size()];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = coordinateList.get(i);
        }
        return coordinates;
    }

    private void writeLost() {
        this.console.writeln(LOST_MESSAGE);
    }
}
