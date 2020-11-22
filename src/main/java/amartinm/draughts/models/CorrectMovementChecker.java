package amartinm.draughts.models;

import java.util.List;

public class CorrectMovementChecker extends BaseMovementChecker {

    public CorrectMovementChecker() {
        super();
    }

    public CorrectMovementChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, Movement movement) {
        List<Piece> betweenDiagonalPieces =
                board.getBetweenDiagonalPieces(movement.getCurrentCoordinate(), movement.getNextCoordinate());
        Error error = board.getPiece(movement.getFirstCoordinate())
                .isCorrectMovement(betweenDiagonalPieces, movement);
        if (error != null)
            return error;
        return super.check(board, turn, movement);
    }

}
