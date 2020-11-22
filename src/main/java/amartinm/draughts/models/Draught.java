package amartinm.draughts.models;

public class Draught extends Piece {

    Draught(Color color) {
        super(color);
    }

    @Override
    Error isCorrectDiagonalMovement(int amountBetweenDiagonalPieces, Movement movement) {
        if (amountBetweenDiagonalPieces > 1)
            return Error.TOO_MUCH_EATINGS;
        return null;
    }

    @Override
    public String getCode() {
        return super.getCode().toUpperCase();
    }

}