package amartinm.draughts.views;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import amartinm.draughts.controllers.InteractorController;
import amartinm.draughts.controllers.StartController;
import amartinm.draughts.models.Coordinate;
import amartinm.draughts.models.Game;
import amartinm.draughts.models.State;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameViewTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private GameView gameView;
    private InteractorController interactorController;

    @Before
    public void before() {
        initMocks(this);
        this.gameView = new GameView();
        this.interactorController = spy(new StartController(new Game(), new State()));
        System.setOut(new PrintStream(this.outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(this.standardOut);
    }


    @Test(expected = AssertionError.class)
    public void testGivenGameViewWhenWriteWithNullControllerThenThrowsAssertionError() {
        this.gameView.write(null);
    }

    @Test
    public void testGivenGameViewWhenWriteWithControllerThenWriteBoard() {
        this.gameView.write(this.interactorController);
        verify(this.interactorController, times(Coordinate.getDimension() * (Coordinate.getDimension() + 1) + 1)).getDimension();
        assertEquals("12345678\n" +
                "1 n n n n1\n" +
                "2n n n n 2\n" +
                "3 n n n n3\n" +
                "4        4\n" +
                "5        5\n" +
                "6b b b b 6\n" +
                "7 b b b b7\n" +
                "8b b b b 8\n" +
                " 12345678", outputStreamCaptor.toString().trim());
    }
}
