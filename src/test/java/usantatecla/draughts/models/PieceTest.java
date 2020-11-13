package usantatecla.draughts.models;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public abstract class PieceTest {

    private static final int MAX_ROW = Coordinate.getDimension() - 1;

    protected Piece piece;

    protected abstract void before();

    protected abstract Piece createPiece(Color color);

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsCorrectMovementWithNullCoordinatesThenThrowAssertionError() {
        this.piece.isCorrectMovement(Collections.EMPTY_LIST, 0, null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsCorrectMovementWithPairOutOfLimitsThenThrowAssertionError() {
        this.piece.isCorrectMovement(Collections.EMPTY_LIST, 2, createSameRowCoordinates());
    }

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsCorrectMovementWithFirstCoordinateIsNullThenThrowAssertionError() {
        this.piece.isCorrectMovement(Collections.EMPTY_LIST, 0, new Coordinate[]{null});
    }

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsCorrectMovementWithSecondCoordinateIsNullThenThrowAssertionError() {
        this.piece.isCorrectMovement(Collections.EMPTY_LIST, 0, new Coordinate[]{new CoordinateBuilder().build(), null});
    }

    @Test
    public void testGivenAPieceWhenCheckIsCorrectMovementWithCoordinatesNotOnDiagonalThenReturnNotDiagonalError() {
        assertEquals(Error.NOT_DIAGONAL,
                this.piece.isCorrectMovement(Collections.EMPTY_LIST, 0, createSameRowCoordinates()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsCorrectMovementWithNullDiagonalPiecesThenThrowsAssertionError() {
        this.piece.isCorrectMovement(null, 0, createOnDiagonalCoordinates(Color.BLACK, 1));
    }

    @Test
    public void testGivenAPieceWhenCheckIsCorrectMovementWithEatingColleaguePiecesThenReturnColleagueEatingError() {
        assertEquals(Error.COLLEAGUE_EATING,
                this.piece.isCorrectMovement(Collections.singletonList(this.createPiece(this.piece.getColor())), 0,
                        createOnDiagonalCoordinates(this.piece.getColor(), 1)));
    }

    @Test
    public void testGivenAPieceWhenCheckIsCorrectMovementWithCorrectMovementThenReturnNull() {
        assertNull(this.createPiece(Color.BLACK).isCorrectMovement(Collections.EMPTY_LIST, 0,
                createOnDiagonalCoordinates(Color.BLACK, 1)));
    }

    @Test
    public void testGivenAPieceWhenCheckIsCorrectMovementWithCorrectEatingMovementThenReturnNull() {
        assertNull(this.createPiece(Color.BLACK).isCorrectMovement(Collections.singletonList(this.createPiece(Color.WHITE)), 0,
                createOnDiagonalCoordinates(Color.BLACK, 2)));
    }

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsLimitWithNullCoordinateThenThrowAssertionError() {
        this.piece.isLimit(null);
    }

    @Test
    public void testGivenAPieceWhenCheckIsLimitWithLastRowCoordinateAndWhitePieceThenReturnFalse() {
        assertFalse(this.createPiece(Color.WHITE).isLimit(new CoordinateBuilder().row(MAX_ROW).build()));
    }

    @Test
    public void testGivenAPieceWhenCheckIsLimitWithFirstRowCoordinateAndBlackPieceThenReturnFalse() {
        assertFalse(this.createPiece(Color.BLACK).isLimit(new CoordinateBuilder().build()));
    }

    @Test
    public void testGivenAPieceWhenCheckIsLimitWithFirstRowCoordinateAndBlackPieceThenReturnTrue() {
        assertTrue(this.createPiece(Color.BLACK).isLimit(new CoordinateBuilder().row(MAX_ROW).build()));
    }

    @Test
    public void testGivenAPieceWhenCheckIsLimitWithLastRowCoordinateAndWhitePieceThenReturnTrue() {
        assertTrue(this.createPiece(Color.WHITE).isLimit(new CoordinateBuilder().build()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsAdvancedWithOriginCoordinateNullThenThrowsAssertionError() {
        this.piece.isAdvanced(null, null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenAPieceWhenCheckIsAdvancedWithTargetCoordinateNullThenThrowsAssertionError() {
        this.piece.isAdvanced(new CoordinateBuilder().build(), null);
    }

    @Test
    public void testGivenAPieceWhenCheckIsAdvancedWithCoordinatesInSameRowThenReturnFalse() {
        Coordinate[] coordinates = createSameRowCoordinates();
        assertFalse(this.piece.isAdvanced(coordinates[0], coordinates[1]));
    }

    @Test
    public void testGivenAPieceWhenCheckIsAdvancedAndPieceIsWhiteAndNotAdvanceThenReturnFalse() {
        Coordinate[] coordinates = createOnDiagonalCoordinates(Color.WHITE, 1);
        assertFalse(this.createPiece(Color.WHITE).isAdvanced(coordinates[1], coordinates[0]));
    }

    @Test
    public void testGivenAPieceWhenCheckIsAdvancedAndPieceIsBlackAndNotAdvanceThenReturnFalse() {
        Coordinate[] coordinates = createOnDiagonalCoordinates(Color.BLACK, 1);
        assertFalse(this.createPiece(Color.BLACK).isAdvanced(coordinates[1], coordinates[0]));
    }

    @Test
    public void testGivenAPieceWhenCheckIsAdvancedAndPieceIsWhiteAndAdvanceThenReturnTrue() {
        Coordinate[] coordinates = createOnDiagonalCoordinates(Color.WHITE, 1);
        assertTrue(this.createPiece(Color.WHITE).isAdvanced(coordinates[0], coordinates[1]));
    }

    @Test
    public void testGivenAPieceWhenCheckIsAdvancedAndPieceIsBlackAndAdvanceThenReturnTrue() {
        Coordinate[] coordinates = createOnDiagonalCoordinates(Color.BLACK, 1);
        assertTrue(this.createPiece(Color.BLACK).isAdvanced(coordinates[0], coordinates[1]));
    }

    protected static Coordinate[] createSameRowCoordinates() {
        return new Coordinate[]{
                new CoordinateBuilder().build(),
                new CoordinateBuilder().build()
        };
    }

    protected static Coordinate[] createOnDiagonalCoordinates(Color color, int distance) {
        List<Coordinate> coordinates = Arrays.asList(
                new CoordinateBuilder().build(),
                new CoordinateBuilder().row(distance).column(MAX_ROW - distance).build()
        );
        if (color == Color.WHITE) {
            Collections.reverse(coordinates);
        }
        return coordinates.toArray(new Coordinate[2]);
    }

}