package amartinm.draughts.models;

public class Pawn extends Piece {

    private static final int MAX_DISTANCE = 2;

    Pawn(Color color) {
        super(color);
    }

    @Override
    Error isCorrectDiagonalMovement(int amountBetweenDiagonalPieces, Movement movement) {
        if (!this.isAdvanced(movement.getCurrentCoordinate(), movement.getNextCoordinate()))
            return Error.NOT_ADVANCED;
        int distance = movement.getCurrentCoordinate().getDiagonalDistance(movement.getNextCoordinate());
        if (distance > Pawn.MAX_DISTANCE)
            return Error.TOO_MUCH_ADVANCED;
        if (distance == Pawn.MAX_DISTANCE && amountBetweenDiagonalPieces != 1)
            return Error.WITHOUT_EATING;
        return null;
    }

}