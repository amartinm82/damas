package amartinm.draughts.models;

public class PieceChecker extends BaseMovementChecker {

    public PieceChecker() {
        super();
    }

    public PieceChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, int pair, Coordinate[] coordinates) {
        if (pair == 0 && turn.getOppositeColor() == board.getColor(coordinates[pair]))
            return Error.OPPOSITE_PIECE;
        return super.check(board, turn, pair, coordinates);
    }

}
