package amartinm.draughts.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private Coordinate origin;
    private Piece piece;

    @Before
    public void before() {
        this.board = new Board();
        this.origin = new CoordinateBuilder().build();
        this.piece = blackPawn();
        this.board.put(origin, piece);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetPieceWithNullCoordinateThenThrowsAnAssertionError() {
        this.board.getPiece(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetPieceWithCoordinateOutOfBoardLimitsThenShouldThrowsAnAssertionError() {
        this.board.getPiece(outOfBoardLimitsCoordinate());
    }

    @Test
    public void testGivenABoardWhenGetPieceWithCoordinateWhereThereIsNotAPieceThenReturnNull() {
        assertNull(this.board.getPiece(emptyCoordinate()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenPutWithNullCoordinateThenShouldThrowAnAssertionError() {
        this.board.put(null, null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenPutWithCoordinateOutOfBoardLimitsThenShouldThrowAnAssertionError() {
        this.board.put(outOfBoardLimitsCoordinate(), null);
    }

    @Test
    public void testGivenABoardWhenPutPieceInCoordinateWhereAlreadyThereIsAPieceThenOverridePiece() {
        assertEquals(blackPawn(), this.board.getPiece(this.origin));
        this.board.put(origin, whitePawn());
        assertEquals(whitePawn(), this.board.getPiece(this.origin));
    }

    @Test
    public void testGivenABoardWhenPutPieceOnCoordinateAndGetPieceWithCoordinateThenReturnPiece() {
        assertEquals(this.piece, this.board.getPiece(this.origin));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenRemovePieceFromCoordinateWithoutPieceThenThrowsAssertionError() {
        this.board.remove(emptyCoordinate());
    }

    @Test
    public void testGivenABoardWhenRemovePieceFromCoordinateThenReturnPieceAndSetCoordinateToNull() {
        assertEquals(this.piece, this.board.remove(this.origin));
        assertNull(this.board.getPiece(this.origin));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenMoveWithThereIsNotPieceInOriginCoordinateThenThrowsAssertionError() {
        this.board.move(emptyCoordinate(), null);
    }

    @Test
    public void testGivenABoardWhenMoveThenPieceIsMoved() {
        Coordinate target = new CoordinateBuilder().row(1).column(6).build();
        this.board.move(this.origin, target);
        assertNull(this.board.getPiece(this.origin));
        assertEquals(this.piece, this.board.getPiece(target));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithNullOriginThenThrowsAssertionError() {
        this.board.getBetweenDiagonalPieces(null, null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithNullTargetThenThrowsAssertionError() {
        this.board.getBetweenDiagonalPieces(this.origin, null);
    }

    @Test
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithPiecesNotOnDiagonalThenReturnEmptyPiecesList() {
        assertTrue(this.board.getBetweenDiagonalPieces(
                this.origin, new CoordinateBuilder().column(0).build()).isEmpty());
    }

    @Test
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithPiecesOnDiagonalThenReturnPiecesList() {
        this.fillBoard();
        assertArrayEquals(new Piece[]{blackPawn(), blackPawn(), whitePawn(), whitePawn()},
                this.board.getBetweenDiagonalPieces(this.origin, oppositeCornerCoordinate()).toArray());
    }

    @Test
    public void testGivenABoardWhenGetColorWithNoPieceOnCoordinateThenReturnNull() {
        assertNull(this.board.getColor(emptyCoordinate()));
    }

    @Test
    public void testGivenABoardWhenGetColorWithPieceOnCoordinateThenReturnPieceColor() {
        assertEquals(Color.BLACK, this.board.getColor(this.origin));
    }

    @Test
    public void testGivenABoardWhenIsEmptyWithEmptyCoordinateThenReturnTrue() {
        assertTrue(this.board.isEmpty(emptyCoordinate()));
    }

    @Test
    public void testGivenABoardWhenIsEmptyWithNotEmptyCoordinateThenReturnFalse() {
        assertFalse(this.board.isEmpty(this.origin));
    }

    private static Pawn whitePawn() {
        return new Pawn(Color.WHITE);
    }

    private static Pawn blackPawn() {
        return new Pawn(Color.BLACK);
    }

    private void fillBoard() {
        int[] rows = {0, 1, 2};
        int[] columns = {7, 6, 5};
        this.board = new Board();
        for (int i = 0; i < rows.length; i++) {
            this.board.put(new CoordinateBuilder().row(rows[i]).column(columns[i]).build(), blackPawn());
            this.board.put(new CoordinateBuilder().row(columns[i]).column(rows[i]).build(), whitePawn());
        }
    }

    private static Coordinate oppositeCornerCoordinate() {
        return new CoordinateBuilder().row(7).column(0).build();
    }

    private static Coordinate outOfBoardLimitsCoordinate() {
        return new CoordinateBuilder().row(8).column(8).build();
    }

    private static Coordinate emptyCoordinate() {
        return new CoordinateBuilder().column(5).build();
    }
}
