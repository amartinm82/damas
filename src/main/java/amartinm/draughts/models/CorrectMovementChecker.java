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
    public Error check(Board board, Turn turn, int pair, Coordinate[] coordinates) {
        List<Piece> betweenDiagonalPieces =
                board.getBetweenDiagonalPieces(coordinates[pair], coordinates[pair + 1]);
        Error error = board.getPiece(coordinates[0])
                .isCorrectMovement(betweenDiagonalPieces, pair, coordinates);
        if (error != null)
            return error;
        return super.check(board, turn, pair, coordinates);
    }

}
