package amartinm.draughts.models;

public class PieceChecker extends BaseMovementChecker {

    public PieceChecker() {
        super();
    }

    public PieceChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, Movement movement) {
        if (turn.getOppositeColor() == board.getColor(movement.getCurrentCoordinate()))
            return Error.OPPOSITE_PIECE;
        return super.check(board, turn, movement);
    }

}
