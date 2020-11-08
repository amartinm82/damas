package usantatecla.draughts.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    public BoardTest() {
        this.board = new Board();
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetPieceWithNullCoordinateThenThrowsAnAssertionError() {
        this.board.getPiece(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetPieceWithCoordinateOutOfBoardLimitsThenShouldThrowsAnAssertionError() {
        this.board.getPiece(new CoordinateBuilder().row(8).column(8).build());
    }

    @Test
    public void testGivenABoardWhenGetPieceWithCoordinateWhereThereIsNotAPieceThenReturnNull() {
        assertNull(this.board.getPiece(new CoordinateBuilder().build()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenPutWithNullCoordinateThenShouldThrowAnAssertionError() {
        this.board.put(null, null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenPutWithCoordinateOutOfBoardLimitsThenShouldThrowAnAssertionError() {
        this.board.put(new CoordinateBuilder().row(-1).build(), null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenPutPieceInCoordinateWhereAlreadyThereIsAPieceThenThrowsAssertionError() {
        Coordinate origin = new CoordinateBuilder().build();
        Piece piece = new Pawn(Color.BLACK);
        Piece coordinatePiece = new Pawn(Color.WHITE);
        this.board.put(origin, coordinatePiece);
        this.board.put(origin, piece);
    }

    @Test
    public void testGivenABoardWhenPutPieceOnCoordinateAndGetPieceWithCoordinateThenReturnPiece() {
        Coordinate coordinate = new CoordinateBuilder().build();
        Piece piece = new Pawn(Color.BLACK);
        this.board.put(coordinate, piece);
        assertEquals(piece, this.board.getPiece(coordinate));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenRemovePieceFromCoordinateWithoutPieceThenThrowsAssertionError() {
        this.board.remove(new CoordinateBuilder().build());
    }

    @Test
    public void testGivenABoardWhenRemovePieceFromCoordinateThenReturnPieceAndSetCoordinateToNull() {
        Coordinate coordinate = new CoordinateBuilder().build();
        Piece piece = new Pawn(Color.BLACK);
        this.board.put(coordinate, piece);
        assertEquals(piece, this.board.remove(coordinate));
        assertNull(this.board.getPiece(coordinate));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenMoveWithThereIsNotPieceInOriginCoordinateThenThrowsAssertionError() {
        this.board.move(new CoordinateBuilder().build(), null);
    }

    @Test
    public void testGivenABoardWhenMoveThenPieceIsMoved() {
        Coordinate origin = new CoordinateBuilder().build();
        Coordinate target = new CoordinateBuilder().row(1).column(6).build();
        Piece piece = new Pawn(Color.BLACK);
        this.board.put(origin, piece);
        this.board.move(origin, target);
        assertNull(this.board.getPiece(origin));
        assertEquals(piece, this.board.getPiece(target));
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithNullOriginThenThrowsAssertionError() {
        this.board.getBetweenDiagonalPieces(null, null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithNullTargetThenThrowsAssertionError() {
        this.board.getBetweenDiagonalPieces(new CoordinateBuilder().build(), null);
    }

    @Test
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithPiecesNotOnDiagonalThenReturnEmptyPiecesList() {
        assertTrue(this.board.getBetweenDiagonalPieces(
                new CoordinateBuilder().build(), new CoordinateBuilder().column(0).build()).isEmpty());
    }

    @Test
    public void testGivenABoardWhenGetBetweenDiagonalPiecesWithPiecesOnDiagonalThenReturnPiecesList() {
        int[] rows = {0, 1, 2};
        int[] columns = {7, 6, 5};
        Piece blackPawn = new Pawn(Color.BLACK);
        Piece whitePawn = new Pawn(Color.WHITE);
        for (int i = 0; i < rows.length; i++) {
            this.board.put(new CoordinateBuilder().row(rows[i]).column(columns[i]).build(), blackPawn);
            this.board.put(new CoordinateBuilder().row(columns[i]).column(rows[i]).build(), whitePawn);
        }
        assertArrayEquals(new Piece[]{blackPawn, blackPawn, whitePawn, whitePawn},
                this.board.getBetweenDiagonalPieces(
                        new CoordinateBuilder().build(),
                        new CoordinateBuilder().row(7).column(0).build()).toArray());
    }

    @Test
    public void testGivenABoardWhenGetColorWithNoPieceOnCoordinateThenReturnNull() {
        assertNull(this.board.getColor(new CoordinateBuilder().build()));
    }

    @Test
    public void testGivenABoardWhenGetColorWithPieceOnCoordinateThenReturnPieceColor() {
        Coordinate origin = new CoordinateBuilder().build();
        Piece piece = new Pawn(Color.BLACK);
        this.board.put(origin, piece);
        assertEquals(Color.BLACK, this.board.getColor(new CoordinateBuilder().build()));
    }

    @Test
    public void testGivenABoardWhenIsEmptyWithEmptyCoordinateThenReturnTrue() {
        assertTrue(this.board.isEmpty(new CoordinateBuilder().build()));
    }

    @Test
    public void testGivenABoardWhenIsEmptyWithNotEmptyCoordinateThenReturnFalse() {
        Coordinate coordinate = new CoordinateBuilder().build();
        Piece piece = new Pawn(Color.BLACK);
        this.board.put(coordinate, piece);
        assertFalse(this.board.isEmpty(coordinate));
    }

}
