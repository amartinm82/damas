package amartinm.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import amartinm.draughts.controllers.ResumeController;
import amartinm.draughts.utils.YesNoDialog;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ResumeViewTest {

    private static final String MESSAGE = "¿Queréis jugar otra";

    @InjectMocks
    private ResumeView resumeView;

    @Mock
    private YesNoDialog yesNoDialog;

    @Mock
    private ResumeController resumeController;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test(expected = AssertionError.class)
    public void testGivenResumeViewWhenInteractWithNullResumeControllerThenThrowsAssertionError() {
        this.resumeView.interact(null);
    }

    @Test
    public void testGivenResumeViewWhenInteractWithResumeControllerAndYesNoDialogReadIsTrueThenCallResumeControllerResetMethod() {
        when(yesNoDialog.read(MESSAGE)).thenReturn(true);
        this.resumeView.interact(this.resumeController);
        verify(this.resumeController, times(1)).reset();
    }

    @Test
    public void testGivenResumeViewWhenInteractWithResumeControllerAndYesNoDialogReadIsFalseThenCallResumeControllerNextMethod() {
        this.resumeView.interact(this.resumeController);
        verify(this.resumeController, times(1)).next();
    }

}
