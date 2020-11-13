package usantatecla.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import usantatecla.draughts.controllers.InteractorController;
import usantatecla.draughts.controllers.StartController;
import usantatecla.draughts.models.Color;
import usantatecla.draughts.models.Coordinate;
import usantatecla.draughts.models.Game;
import usantatecla.draughts.models.State;
import usantatecla.draughts.utils.Console;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameViewTest {

    @InjectMocks
    private GameView gameView;

    @Mock
    private Console console;

    private InteractorController interactorController;

    @Before
    public void before() {
        initMocks(this);
        this.interactorController = spy(new StartController(new Game(), new State()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenGameViewWhenWriteWithNullControllerThenThrowsAssertionError() {
        this.gameView.write(null);
    }

    @Test
    public void testGivenGameViewWhenWriteWithControllerThenWriteBoard() {
        this.gameView.write(this.interactorController);
        verify(this.interactorController, times(Coordinate.getDimension() * (Coordinate.getDimension() + 1) + 1)).getDimension();
        // empty coordinates in board
        verify(this.console, times(42)).write(" ");
        verify(this.console, times(12)).write(Color.WHITE.name());
        verify(this.console, times(12)).write(Color.BLACK.name());
        verify(this.console, times(2)).writeln();
        verify(this.console, times(8)).writeln(anyString());
    }
}
