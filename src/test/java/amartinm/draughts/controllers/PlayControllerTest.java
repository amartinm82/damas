package amartinm.draughts.controllers;

import org.junit.Before;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PlayControllerTest extends InteractorControllerTest {

    private PlayController playController;

    @Override
    protected InteractorController createController() {
        return new PlayController(this.game, this.state);
    }

    @Override
    protected void verifyControllerVisitorVisitMethodIsCalled() {
        verify(this.controllersVisitor, times(1)).visit(this.playController);
    }

    @Override
    @Before
    public void before() {
        super.before();
        this.playController = (PlayController) this.interactorController;
    }
}
