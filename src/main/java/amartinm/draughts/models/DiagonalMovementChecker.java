package amartinm.draughts.models;

public class DiagonalMovementChecker extends BaseMovementChecker {

    public DiagonalMovementChecker() {
        super();
    }

    public DiagonalMovementChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, Movement movement) {
        if (!movement.getCurrentCoordinate().isOnDiagonal(movement.getNextCoordinate()))
            return Error.NOT_DIAGONAL;
        return super.check(board, turn, movement);
    }

}
