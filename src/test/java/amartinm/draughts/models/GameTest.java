package amartinm.draughts.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Board board;
    private Coordinate origin;

    @Before
    public void before() {
        this.board = new Board();
        this.game = new Game(board);
        this.origin = new CoordinateBuilder().row(5).column(2).build();
        this.board.put(origin, new Pawn(Color.WHITE));
    }

    @Test(expected = AssertionError.class)
    public void testGivenAGameWhenMoveWithNullCoordinatesThenThrowsAssertionError() {
        this.game.move(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenAGameWhenMoveWithEmptyCoordinatesThenThrowsAssertionError() {
        this.game.move(new Coordinate[]{});
    }

    @Test(expected = AssertionError.class)
    public void testGivenAGameWhenMoveWithCoordinatesWithNullElementThenThrowAssertionError() {
        assertEquals(Error.EMPTY_ORIGIN, this.game.move(new Coordinate[]{null}));
    }

    @Test
    public void testGivenAGameWhenMoveWithEmptyOriginThenReturnEmptyOriginError() {
        Coordinate[] coordinates = new Coordinate[]{this.origin, this.onDiagonalNECoordinate()};
        this.board.put(this.origin, null);
        assertEquals(Error.EMPTY_ORIGIN, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveOppositeColorThenReturnOppositePieceError() {
        Coordinate targetCoordinate = this.onDiagonalNECoordinate();
        this.board.put(this.origin, new Pawn(Color.BLACK));
        Coordinate[] coordinates = new Coordinate[]{this.origin, targetCoordinate};
        assertEquals(Error.OPPOSITE_PIECE, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveWithCoordinatesNotOnDiagonalThenReturnNotDiagonalError() {
        assertEquals(Error.NOT_DIAGONAL, this.game.move(this.notOnDiagonalMovementCoordinates()));
    }

    @Test
    public void testGivenAGameWhenMoveToNotEmptyCoordinateThenReturnNotEmptyTargetError() {
        Coordinate targetCoordinate = this.onDiagonalNECoordinate();
        this.board.put(targetCoordinate, new Pawn(Color.BLACK));
        Coordinate[] coordinates = new Coordinate[]{this.origin, targetCoordinate};
        assertEquals(Error.NOT_EMPTY_TARGET, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveNotOnDiagonalThenReturnNotDiagonalError() {
        assertEquals(Error.NOT_DIAGONAL, this.game.move(this.notOnDiagonalMovementCoordinates()));
    }

    @Test
    public void testGivenAGameWhenMoveEatingAColleagueThenReturnColleagueEatingError() {
        Coordinate[] coordinates = this.setBoardToEatAColleague();
        assertEquals(Error.COLLEAGUE_EATING, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveWithIncorrectPieceMovementThenReturnError() {
        assertEquals(Error.NOT_ADVANCED, this.game.move(this.backwardMovementCoordinates()));
    }

    @Test
    public void testGivenAGameWhenMoveAndReachLimitWithPawnThenConvertPawnToDraught() {
        Coordinate[] reachWhiteLimitMovementCoordinates = this.reachWhiteLimitMovementCoordinates();
        assertNull(this.game.move(reachWhiteLimitMovementCoordinates));
        assertNull(this.board.getPiece(reachWhiteLimitMovementCoordinates[0]));
        assertTrue(this.board.getPiece(reachWhiteLimitMovementCoordinates[1]) instanceof Draught);
        assertEquals(Color.BLACK, this.game.getTurnColor());
    }

    @Test
    public void testGivenAGameWhenMoveAndIsIncorrectGlobalMovementThenUndoMovementsAndReturnError() {
        Coordinate[] movementToUndoCoordinates = this.prepareBoardToMoveWithMovementsToUndo();
        assertEquals(Error.TOO_MUCH_JUMPS, this.game.move(movementToUndoCoordinates));
        this.checkMovementsAreUndo(movementToUndoCoordinates);
    }

    @Test
    public void testGivenAGameWhenCheckIsBlockedThenReturnTrue() {
        this.setBoardToBlockWhitePieces();
        assertTrue(this.game.isBlocked());
    }

    @Test
    public void testGivenAGameWhenCancelThenRemovePiecesAndChangeTurn() {
        this.game.cancel();
        assertNull(this.game.getPiece(this.origin));
        assertEquals(Color.BLACK, this.game.getTurnColor());
    }

    private Coordinate onDiagonalNECoordinate() {
        return new CoordinateBuilder().row(4).column(3).build();
    }

    private Coordinate[] notOnDiagonalMovementCoordinates() {
        Coordinate targetCoordinate = new CoordinateBuilder().row(this.origin.getRow()).column(7).build();
        return new Coordinate[]{this.origin, targetCoordinate};
    }

    private Coordinate[] setBoardToEatAColleague() {
        Coordinate targetCoordinate = new CoordinateBuilder().row(this.origin.getRow() - 2).column(this.origin.getColumn() + 2).build();
        this.board.put(new CoordinateBuilder().row(this.origin.getRow() - 1).column(this.origin.getColumn() + 1).build(), new Pawn(Color.WHITE));
        return new Coordinate[]{this.origin, targetCoordinate};
    }

    private Coordinate[] backwardMovementCoordinates() {
        return new Coordinate[]{this.origin,
                new CoordinateBuilder().row(this.origin.getRow() + 1).column(this.origin.getColumn() - 1).build()};
    }

    private Coordinate[] reachWhiteLimitMovementCoordinates() {
        Coordinate whiteLimitCoordinate = new CoordinateBuilder().build();
        Coordinate previousWhiteLimitCoordinate = new CoordinateBuilder()
                .row(whiteLimitCoordinate.getRow() + 1)
                .column(whiteLimitCoordinate.getColumn() - 1)
                .build();
        this.board.put(previousWhiteLimitCoordinate, new Pawn(Color.WHITE));
        return new Coordinate[]{previousWhiteLimitCoordinate, whiteLimitCoordinate};
    }

    private Coordinate[] prepareBoardToMoveWithMovementsToUndo() {
        Coordinate secondCoordinate = new CoordinateBuilder().row(3).column(4).build();
        Coordinate thirdCoordinate = new CoordinateBuilder().row(1).column(6).build();
        Coordinate targetCoordinate = new CoordinateBuilder().build();

        Coordinate firstOppositePieceCoordinate = new CoordinateBuilder().row(4).column(3).build();
        Coordinate secondOppositePieceCoordinate = new CoordinateBuilder().row(2).column(5).build();
        this.board.put(firstOppositePieceCoordinate, new Pawn(Color.BLACK));
        this.board.put(secondOppositePieceCoordinate, new Draught(Color.BLACK));

        return new Coordinate[]{this.origin, secondCoordinate, thirdCoordinate, targetCoordinate};
    }

    private void checkMovementsAreUndo(Coordinate[] movementToUndoCoordinates) {
        assertTrue(this.game.getPiece(this.origin) instanceof Pawn);
        assertNull(this.game.getPiece(movementToUndoCoordinates[3]));
        assertTrue(this.game.getPiece(new CoordinateBuilder()
                .row(movementToUndoCoordinates[1].getRow() + 1)
                .column(movementToUndoCoordinates[1].getColumn() - 1)
                .build()) instanceof Pawn);
        assertTrue(this.game.getPiece(new CoordinateBuilder()
                .row(movementToUndoCoordinates[2].getRow() + 1)
                .column(movementToUndoCoordinates[2].getColumn() - 1)
                .build()) instanceof Draught);
    }

    private void setBoardToBlockWhitePieces() {
        Coordinate firstOppositePieceCoordinate = new CoordinateBuilder().row(this.origin.getRow() - 1).column(this.origin.getColumn() - 1).build();
        Coordinate secondOppositePieceCoordinate = new CoordinateBuilder().row(this.origin.getRow() - 1).column(this.origin.getColumn() + 1).build();
        Coordinate thirdOppositePieceCoordinate = new CoordinateBuilder().row(this.origin.getRow() - 2).column(this.origin.getColumn() - 2).build();
        Coordinate fourOppositePieceCoordinate = new CoordinateBuilder().row(this.origin.getRow() - 2).column(this.origin.getColumn() + 2).build();
        this.board.put(firstOppositePieceCoordinate, new Pawn(Color.BLACK));
        this.board.put(secondOppositePieceCoordinate, new Draught(Color.BLACK));
        this.board.put(thirdOppositePieceCoordinate, new Pawn(Color.BLACK));
        this.board.put(fourOppositePieceCoordinate, new Draught(Color.BLACK));
    }

}
