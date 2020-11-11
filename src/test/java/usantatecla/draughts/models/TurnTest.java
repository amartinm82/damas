package usantatecla.draughts.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TurnTest {

    private Turn turn;

    @Before
    public void before() {
        this.turn = new Turn();
    }

    @Test
    public void testGivenNewTurnWhenChangeTurnThenIsOtherTurn() {
        turn.change();
        assertEquals(turn.getColor(), Color.BLACK);
    }

    @Test
    public void testGivenNewTurnWhenChangeTurnTwoTimesThenIsTheSameTurn() {
        turn.change();
        assertEquals(turn.getColor(), Color.BLACK);
        turn.change();
        assertEquals(turn.getColor(), Color.WHITE);
    }
}
