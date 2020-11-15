package amartinm.draughts.controllers;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ResumeControllerTest extends InteractorControllerTest {

    private ResumeController resumeController;

    @Override
    protected InteractorController createController() {
        return new ResumeController(this.game, this.state);
    }

    @Override
    protected void verifyControllerVisitorVisitMethodIsCalled() {
        verify(this.controllersVisitor, times(1)).visit(this.resumeController);
    }

    @Override
    @Before
    public void before() {
        super.before();
        this.resumeController = (ResumeController) this.interactorController;
    }

    @Test
    public void testGivenResumeControllerWhenNextThenStateNextMethodIsCalled() {
        this.resumeController.next();
        verify(this.state, times(1)).next();
    }

    @Test
    public void testGivenResumeControllerWhenResetThenGameAndStateResetMethodsAreCalled() {
        this.resumeController.reset();
        verify(this.state, times(1)).reset();
        verify(this.game, times(1)).reset();
    }

}
