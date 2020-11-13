package usantatecla.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import usantatecla.draughts.controllers.InteractorController;
import usantatecla.draughts.controllers.PlayController;
import usantatecla.draughts.controllers.ResumeController;
import usantatecla.draughts.controllers.StartController;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ViewTest {

    @InjectMocks
    private View view;
    @Mock
    private StartView startView;
    @Mock
    private PlayView playView;
    @Mock
    private ResumeView resumeView;
    @Mock
    private InteractorController interactorController;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test(expected = AssertionError.class)
    public void testGivenViewWhenInteractWithNullThenThrowsAssertionError() {
        this.view.interact(null);
    }

    @Test
    public void testGivenViewWhenInteractWithInteractorControllerThenCallAcceptControllerMethod() {
        this.view.interact(this.interactorController);
        verify(this.interactorController, times(1)).accept(this.view);
    }

    @Test(expected = AssertionError.class)
    public void testGivenViewWhenVisitWithNullStartControllerThenThrowsAssertionError() {
        this.view.visit((StartController) null);
    }

    @Test
    public void testGivenViewWhenVisitWithStartControllerThenCallStartViewInteractMethod() {
        StartController startController = new StartController(new Game(), new State());
        this.view.visit(startController);
        verify(this.startView, times(1)).interact(startController);
    }

    @Test(expected = AssertionError.class)
    public void testGivenViewWhenVisitWithNullPlayControllerThenThrowsAssertionError() {
        this.view.visit((PlayController) null);
    }

    @Test
    public void testGivenViewWhenVisitWithPlayControllerThenCallPlayViewInteractMethod() {
        PlayController playController = new PlayController(new Game(), new State());
        this.view.visit(playController);
        verify(this.playView, times(1)).interact(playController);
    }

    @Test(expected = AssertionError.class)
    public void testGivenViewWhenVisitWithNullResumeControllerThenThrowsAssertionError() {
        this.view.visit((ResumeController) null);
    }

    @Test
    public void testGivenViewWhenVisitWithResumeControllerThenCallResumeViewInteractMethod() {
        ResumeController resumeController = new ResumeController(new Game(), new State());
        this.view.visit(resumeController);
        verify(this.resumeView, times(1)).interact(resumeController);
    }

}
