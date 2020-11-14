package usantatecla.draughts.controllers;

import org.junit.Before;
import org.junit.Test;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;

public class ControllerTest {

    private Controller controller;

    @Before
    public void before() {
        this.controller = new Controller(new Game(), new State());
    }

    @Test(expected = AssertionError.class)
    public void testGivenControllerWhenGetColorWithNullCoordinateThenThrowsAssertionError() {
        this.controller.getColor(null);
    }

}
