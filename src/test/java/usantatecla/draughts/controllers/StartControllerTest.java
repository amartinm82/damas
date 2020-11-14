package usantatecla.draughts.controllers;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class StartControllerTest extends InteractorControllerTest {

    private StartController startController;

    @Override
    protected InteractorController createController() {
        return new StartController(this.game, this.state);
    }

    @Override
    protected void verifyControllerVisitorVisitMethodIsCalled() {
        verify(this.controllersVisitor, times(1)).visit(this.startController);
    }

    @Override
    @Before
    public void before() {
        super.before();
        this.startController = (StartController) this.interactorController;
    }

    @Test
    public void testGivenStartControllerWhenStartThenStateNextMethodIsCalled() {
        this.startController.start();
        verify(this.state, times(1)).next();
    }

}
