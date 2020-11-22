package amartinm.draughts.models;

public class TargetChecker extends BaseMovementChecker {

    public TargetChecker() {
        super();
    }

    public TargetChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, Movement movement) {
        if (!board.isEmpty(movement.getNextCoordinate()))
            return Error.NOT_EMPTY_TARGET;
        return super.check(board, turn, movement);
    }

}
