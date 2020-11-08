package usantatecla.draughts.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PawnTest {

    // TODO: refactor removing parameterized and finish all tests

    private Pawn pawn;
    @Parameterized.Parameter(0)
    public int amountBetweenDiagonalPieces;
    @Parameterized.Parameter(1)
    public int pair;
    @Parameterized.Parameter(2)
    public Coordinate[] coordinates;
    @Parameterized.Parameter(3)
    public Error expectedError;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 1, 0, new Coordinate[]{new Coordinate(6,1), new Coordinate(6,2)}, Error.NOT_ADVANCED},
                { 1, 0, new Coordinate[]{new Coordinate(6,1), new Coordinate(3,4)}, Error.TOO_MUCH_ADVANCED},
                { 0, 0, new Coordinate[]{new Coordinate(6,1), new Coordinate(4,3)}, Error.WITHOUT_EATING},
                { 1, 0, new Coordinate[]{new Coordinate(6,1), new Coordinate(4,3)}, null},
                { 1, 0, new Coordinate[]{new Coordinate(6,1), new Coordinate(5,2)}, null}
        });
    }

    @Before
    public void before() {
        this.pawn = new Pawn(Color.WHITE);
    }

    @Test
    public void testIsCorrectDiagonalMovement() {
        assertEquals(expectedError, this.pawn.isCorrectDiagonalMovement(amountBetweenDiagonalPieces, pair, coordinates));
    }

}
