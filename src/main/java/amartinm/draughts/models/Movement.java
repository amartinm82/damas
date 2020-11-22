package amartinm.draughts.models;

import java.util.ArrayList;
import java.util.List;

public class Movement {

    private int pair;
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

    public Coordinate[] getCoordinates() {
        return this.coordinates;
    }

    public List<Coordinate> getCoordinatesToRemove() {
        return this.coordinatesToRemove;
    }

    Coordinate getCurrentCoordinate() {
        return this.coordinates[pair];
    }

    Coordinate getNextCoordinate() {
        return this.coordinates[pair + 1];
    }

    void next() {
        assert this.pair < coordinates.length - 1;
        this.pair++;
    }

    Coordinate getFirstCoordinate() {
        return this.coordinates[0];
    }

    Coordinate getLastCoordinate() {
        return this.coordinates[this.coordinates.length - 1];
    }

    void addCoordinateToRemove(Coordinate coordinate) {
        this.coordinatesToRemove.add(coordinate);
    }

    boolean areCheckedAllCoordinates() {
        return pair == this.coordinates.length - 1;
    }

    Error isCorrect(Board board, Turn turn) {
        Error error;
        do {
            error = this.movementChecker.check(board, turn, this);
            if (error != null)
                return error;
            this.next();
        } while (!this.areCheckedAllCoordinates());
        return null;
    }

}
