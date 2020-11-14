package usantatecla.draughts.controllers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LogicTest {

    private Logic logic;

    @Before
    public void before() {
        this.logic = new Logic();
    }

    @Test
    public void testGivenLogicWhenGetControllerAtInitialStateThenReturnStartController() {
        assertTrue(this.logic.getController() instanceof StartController);
    }

    @Test
    public void testGivenLogicWhenGetControllerAtFinalStateThenReturnNull() {
        ((StartController) this.logic.getController()).start();
        ((PlayController) this.logic.getController()).cancel();
        ((ResumeController) this.logic.getController()).next();
        assertNull(this.logic.getController());
    }

}
