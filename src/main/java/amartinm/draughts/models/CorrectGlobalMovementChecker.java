package amartinm.draughts.models;

import java.util.List;

public class CorrectGlobalMovementChecker extends BaseMovementChecker {

    public CorrectGlobalMovementChecker() {
        super();
    }

    public CorrectGlobalMovementChecker(MovementChecker movementChecker) {
        super(movementChecker);
    }

    @Override
    public Error check(Board board, Turn turn, Movement movement) {
        Coordinate forRemoving = this.getBetweenDiagonalPiece(board, movement);
        if (forRemoving != null) {
            movement.addCoordinateToRemove(forRemoving);
        }
        if (movement.getCoordinates().length > 2 && movement.getCoordinates().length > movement.getCoordinatesToRemove().size() + 1)
            return Error.TOO_MUCH_JUMPS;
        return super.check(board, turn, movement);
    }

    private Coordinate getBetweenDiagonalPiece(Board board, Movement movement) {
        assert movement.getCurrentCoordinate().isOnDiagonal(movement.getNextCoordinate());
        List<Coordinate> betweenCoordinates = movement.getCurrentCoordinate().getBetweenDiagonalCoordinates(movement.getNextCoordinate());
        if (betweenCoordinates.isEmpty())
            return null;
        for (Coordinate coordinate : betweenCoordinates) {
            if (board.getPiece(coordinate) != null)
                return coordinate;
        }
        return null;
    }


}
