package amartinm.draughts.models;

public class TargetChecker extends BaseMovementChecker {

    public TargetChecker() {
        super();
    }

    public TargetChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, int pair, Coordinate[] coordinates) {
        if (!board.isEmpty(coordinates[pair + 1]))
            return Error.NOT_EMPTY_TARGET;
        return super.check(board, turn, pair, coordinates);
    }

}
