package usantatecla.draughts.views;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import usantatecla.draughts.controllers.PlayController;
import usantatecla.draughts.models.Color;
import usantatecla.draughts.models.Coordinate;
import usantatecla.draughts.models.CoordinateBuilder;
import usantatecla.draughts.utils.Console;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlayViewTest {

    private static final String CANCEL_FORMAT = "-1";
    private static final String[] COLOR_VALUES = {"blancas", "negras"};
    private static final String PROMPT = "Mueven las %s: ";
    private static final String ERROR_MESSAGE = "Error!!! Formato incorrecto";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";
    private static final String RIGHT_FORMAT = "18.27";
    private static final Coordinate[] COORDINATES = new Coordinate[]{
            new CoordinateBuilder().build(),
            new CoordinateBuilder().row(1).column(6).build(),
    };

    @InjectMocks
    private PlayView playView;

    @Mock
    private Console console;

    @Mock
    private PlayController playController;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test(expected = AssertionError.class)
    public void testGivenPlayViewWhenInteractWithNullPlayControllerThenThrowsAssertionError() {
        this.playView.interact(null);
    }

    @Test
    public void testGivenPlayViewWhenInteractWithPlayControllerAndReadCancelFormatThenPlayControllerCancelMethodIsCalled() {
        when(playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(CANCEL_FORMAT);
        this.playView.interact(this.playController);
        verify(this.playController, times(1)).cancel();
    }

    @Test
    public void testGivenPlayViewWhenInteractWithPlayControllerAndReadBadFormatThenPlayControllerCancelMethodIsCalled() {
        when(playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(CANCEL_FORMAT);
        this.playView.interact(this.playController);
        verify(this.playController, times(1)).cancel();
    }

    @Test
    public void testGivenPlayViewWhenInteractWithPlayControllerAndReadBadFormatThenWriteErrorMessage() {
        when(playController.getColor()).thenReturn(Color.BLACK);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.BLACK.ordinal()]))).thenReturn("09.90", CANCEL_FORMAT);
        this.playView.interact(this.playController);
        verify(this.console, times(1)).writeln(ERROR_MESSAGE);
    }

    @Test
    public void testGivenPlayViewWhenInteractWithPlayControllerAndReadRightCoordinateThenPlayControllerMoveMethodIsCalled() {
        when(playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(RIGHT_FORMAT);
        this.playView.interact(this.playController);
        verify(this.playController, times(1)).move(COORDINATES);
        // to check GameView write method is called check if the call to getDimension method inside is done
        verify(this.playController, times(1)).getDimension();
        verify(this.playController, times(1)).isBlocked();
        verify(this.console, never()).writeln(LOST_MESSAGE);
    }

    @Test
    public void testGivenPlayViewWhenInteractWithPlayControllerAndReadRightCoordinateAndIsBlockedThenPlayControllerMoveMethodIsCalledAndWriteLostMessage() {
        when(playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(RIGHT_FORMAT);
        when(this.playController.isBlocked()).thenReturn(true);
        this.playView.interact(this.playController);
        verify(this.playController, times(1)).move(COORDINATES);
        // to check GameView write method is called check if the call to getDimension method inside is done
        verify(this.playController, times(1)).getDimension();
        verify(this.console, times(1)).writeln(LOST_MESSAGE);
    }

}
