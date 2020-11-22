package amartinm.draughts.models;

public class OriginChecker extends BaseMovementChecker {

    public OriginChecker() {
        super();
    }

    public OriginChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, Movement movement) {
        if (board.isEmpty(movement.getCurrentCoordinate()))
            return Error.EMPTY_ORIGIN;
        return super.check(board, turn, movement);
    }

}
