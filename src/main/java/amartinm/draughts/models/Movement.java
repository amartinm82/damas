package amartinm.draughts.models;

import java.util.ArrayList;
import java.util.List;

public class Movement {

    private Coordinate[] coordinates;
    private List<Coordinate> coordinatesToRemove;
    private MovementChecker movementChecker;

    Movement(Coordinate[] coordinates) {
        assert coordinates != null && coordinates.length > 1;
        for (Coordinate coordinate : coordinates) {
            assert coordinate != null;
        }
        this.coordinates = coordinates;
        this.coordinatesToRemove = new ArrayList<>();
        MovementChecker correctGlobalMovementChecker = new CorrectGlobalMovementChecker();
        MovementChecker correctMovementChecker = new CorrectMovementChecker(correctGlobalMovementChecker);
        MovementChecker targetChecker = new TargetChecker(correctMovementChecker);
        MovementChecker diagonalMovementChecker = new DiagonalMovementChecker(targetChecker);
        MovementChecker pieceChecker = new PieceChecker(diagonalMovementChecker);
        this.movementChecker = new OriginChecker(pieceChecker);
    }

    public List<Coordinate> getCoordinatesToRemove() {
        return this.coordinatesToRemove;
    }

    Error isCorrect(Board board, Turn turn) {
        Error error;
        int pair = 0;
        do {
            error = this.movementChecker.check(board, turn, pair, this.coordinates);
            if (error != null)
                return error;
            Coordinate forRemoving = this.getBetweenDiagonalPiece(board, pair);
            if (forRemoving != null) {
                this.coordinatesToRemove.add(forRemoving);
            }
            pair++;
        } while (pair < this.coordinates.length - 1);
        return null;
    }

    private Coordinate getBetweenDiagonalPiece(Board board, int pair) {
        assert this.coordinates[pair].isOnDiagonal(this.coordinates[pair + 1]);
        List<Coordinate> betweenCoordinates = this.coordinates[pair].getBetweenDiagonalCoordinates(this.coordinates[pair + 1]);
        if (betweenCoordinates.isEmpty())
            return null;
        for (Coordinate coordinate : betweenCoordinates) {
            if (board.getPiece(coordinate) != null)
                return coordinate;
        }
        return null;
    }

}
