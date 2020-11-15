package amartinm.draughts.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PawnTest extends PieceTest {

    @Before
    public void before() {
        this.piece = new Pawn(Color.WHITE);
    }

    protected Piece createPiece(Color color) {
        return new Pawn(color);
    }

    @Test
    public void testGivenAPawnWhenCheckIsCorrectDiagonalMovementAndDoesNotAdvanceThenReturnNotAdvancedError() {
        assertEquals(Error.NOT_ADVANCED,
                this.piece.isCorrectDiagonalMovement(1, 0, createSameRowCoordinates()));
    }

    @Test
    public void testGivenAPawnWhenCheckIsCorrectDiagonalMovementAndDoesAdvanceTooMuchThenReturnTooMuchError() {
        assertEquals(Error.TOO_MUCH_ADVANCED,
                this.piece.isCorrectDiagonalMovement(0, 0,
                        createOnDiagonalCoordinates(this.piece.getColor(), 3)));
    }

    @Test
    public void testGivenAPawnWhenCheckIsCorrectDiagonalMovementAndDoesAdvanceMaxDistanceWithoutEatingThenReturnWithoutEatingError() {
        assertEquals(Error.WITHOUT_EATING,
                this.piece.isCorrectDiagonalMovement(2, 0,
                        createOnDiagonalCoordinates(this.piece.getColor(), 2)));
    }

    @Test
    public void testGivenAPawnWhenCheckIsCorrectDiagonalMovementAndDoesAdvanceThenReturnNullError() {
        assertNull(this.piece.isCorrectDiagonalMovement(1, 0,
                createOnDiagonalCoordinates(this.piece.getColor(), 2)));
    }

    @Test
    public void testGivenAPawnWhenCheckIsCorrectDiagonalMovementAndDoesAdvanceMaxDistanceEatingThenReturnNullError() {
        assertNull(this.piece.isCorrectDiagonalMovement(1, 0,
                createOnDiagonalCoordinates(this.piece.getColor(), 1)));
    }

}
