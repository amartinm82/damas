package amartinm.draughts.models;

public class OriginChecker extends BaseMovementChecker {

    public OriginChecker() {
        super();
    }

    public OriginChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, int pair, Coordinate[] coordinates) {
        if (pair == 0 && board.isEmpty(coordinates[pair]))
            return Error.EMPTY_ORIGIN;
        return super.check(board, turn, pair, coordinates);
    }

}
