package amartinm.draughts.models;

public class BaseMovementChecker implements MovementChecker {

    private MovementChecker next;

    protected BaseMovementChecker() {
    }

    protected BaseMovementChecker(MovementChecker movementChecker) {
        assert movementChecker != null;
        this.next = movementChecker;
    }

    @Override
    public Error check(Board board, Turn turn, Movement movement) {
        if (next != null) {
            return next.check(board, turn, movement);
        }
        return null;
    }

}
