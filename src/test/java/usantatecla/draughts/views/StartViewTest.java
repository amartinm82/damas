package usantatecla.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import usantatecla.draughts.controllers.StartController;
import usantatecla.draughts.utils.Console;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class StartViewTest {

    private static final String TITTLE = "Draughts";

    @InjectMocks
    private StartView startView;

    @Mock
    private Console console;

    @Spy
    private GameView gameView;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test(expected = AssertionError.class)
    public void testGivenStartViewWhenInteractWithNullStartControllerThenThrowsAssertionError() {
        this.startView.interact(null);
    }

    @Test
    public void testGivenStartViewWhenInteractWithStartControllerThenInteract() {
        StartController startController = mock(StartController.class);
        this.startView.interact(startController);
        verify(this.console, times(1)).writeln(TITTLE);
        // to check GameView write method is called check if the call to getDimension method inside is done
        verify(startController, times(1)).getDimension();
        verify(startController, times(1)).start();
    }

}
