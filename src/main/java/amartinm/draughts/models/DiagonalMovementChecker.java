package amartinm.draughts.models;

public class DiagonalMovementChecker extends BaseMovementChecker {

    public DiagonalMovementChecker() {
        super();
    }

    public DiagonalMovementChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, int pair, Coordinate[] coordinates) {
        if (!coordinates[pair].isOnDiagonal(coordinates[pair + 1]))
            return Error.NOT_DIAGONAL;
        return super.check(board, turn, pair, coordinates);
    }

}
