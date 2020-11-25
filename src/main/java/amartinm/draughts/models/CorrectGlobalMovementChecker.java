package amartinm.draughts.models;

public class CorrectGlobalMovementChecker extends BaseMovementChecker {

    private int piecesToEat;

    public CorrectGlobalMovementChecker() {
        super();
    }

    public CorrectGlobalMovementChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, int pair, Coordinate[] coordinates) {
        this.piecesToEat += board.getBetweenDiagonalPieces(coordinates[pair], coordinates[pair + 1]).size();
        if (pair + 1 == coordinates.length - 1
                && coordinates.length > 2
                && coordinates.length > this.piecesToEat + 1)
            return Error.TOO_MUCH_JUMPS;
        return super.check(board, turn, pair, coordinates);
    }

}
