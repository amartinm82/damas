package amartinm.draughts.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DraughtTest extends PieceTest {

    @Before
    public void before() {
        this.piece = new Draught(Color.BLACK);
    }

    protected Piece createPiece(Color color) {
        return new Draught(color);
    }

    @Test
    public void testGivenADraughtWhenCheckIsCorrectDiagonalMovementAndDoesEatTooMuchPiecesThenReturnTooMuchEatingError() {
        assertEquals(Error.TOO_MUCH_EATINGS,
                this.piece.isCorrectDiagonalMovement(2, 0,
                        createOnDiagonalCoordinates(this.piece.getColor(), 2)));
    }

    @Test
    public void testGivenADraughtWhenCheckIsCorrectDiagonalMovementAndItIsThenReturnNullError() {
        assertNull(this.piece.isCorrectDiagonalMovement(1, 0,
                createOnDiagonalCoordinates(this.piece.getColor(), 7)));
    }

}
