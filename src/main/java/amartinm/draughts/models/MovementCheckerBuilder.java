package amartinm.draughts.models;

public class MovementCheckerBuilder {

    MovementCheckerBuilder() {
    }

    public MovementChecker build() {
        MovementChecker correctGlobalMovementChecker = new CorrectGlobalMovementChecker();
        MovementChecker correctMovementChecker = new CorrectMovementChecker(correctGlobalMovementChecker);
        MovementChecker targetChecker = new TargetChecker(correctMovementChecker);
        MovementChecker diagonalMovementChecker = new DiagonalMovementChecker(targetChecker);
        MovementChecker pieceChecker = new PieceChecker(diagonalMovementChecker);
        return new OriginChecker(pieceChecker);
    }
}
