package amartinm.draughts.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateTest {

    private State state;

    @Before
    public void before() {
        this.state = new State();
    }

    @Test
    public void testGivenNewStateWhenGetValueStateThenIsInitial() {
        assertEquals(StateValue.INITIAL, this.state.getValueState());
    }

    @Test
    public void testGivenNewStateWhenDoNextAndGetValueStateThenIsInGame() {
        this.state.next();
        assertEquals(StateValue.IN_GAME, this.state.getValueState());
    }

    @Test
    public void testGivenNewStateWhenDoThreeNextAndGetValueStateThenIsExit() {
        for (int i = 0; i < StateValue.values().length - 1; i++) {
            this.state.next();
        }
        assertEquals(StateValue.EXIT, this.state.getValueState());
    }

    @Test(expected = AssertionError.class)
    public void testGivenNewStateWhenDoFourNextAndGetValueStateThenFails() {
        for (int i = 0; i < StateValue.values().length; i++) {
            this.state.next();
        }
    }

    @Test
    public void testGivenNewStateWhenDoThreeNextAndResetAndGetValueStateThenIsInitial() {
        for (int i = 0; i < StateValue.values().length - 1; i++) {
            this.state.next();
        }
        this.state.reset();
        assertEquals(StateValue.INITIAL, this.state.getValueState());
    }

}
