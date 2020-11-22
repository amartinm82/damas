package amartinm.draughts.views;

import amartinm.draughts.controllers.PlayController;
import amartinm.draughts.models.Color;
import amartinm.draughts.models.Coordinate;
import amartinm.draughts.models.CoordinateBuilder;
import amartinm.draughts.models.Error;
import amartinm.draughts.utils.Console;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlayViewTest {

    private static final String CANCEL_FORMAT = "-1";
    private static final String[] COLOR_VALUES = {"blancas", "negras"};
    private static final String PROMPT = "Mueven las %s: ";
    private static final String BAD_FORMAT_ERROR_MESSAGE = "Error!!! No te entiendo: <d><d>{,<d><d>}[0-2]";
    private static final String NOT_EMPTY_TARGET_ERROR_MESSAGE = "Error!!! No está vacío el destino";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";
    private static final String RIGHT_FORMAT = "18.27";
    private static final Coordinate[] COORDINATES = new Coordinate[]{
            new CoordinateBuilder().build(),
            new CoordinateBuilder().row(1).column(6).build(),
    };

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @InjectMocks
    private PlayView playView;

    @Mock
    private Console console;

    @Mock
    private PlayController playController;

    @Mock
    private ErrorView errorView;

    @Before
    public void before() {
        initMocks(this);
        System.setOut(new PrintStream(this.outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(this.standardOut);
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
        assertTrue(outputStreamCaptor.toString().trim().contains(BAD_FORMAT_ERROR_MESSAGE));
    }

    @Test
    public void testGivenPlayViewWhenInteractWithPlayControllerAndPlayControllerMoveMethodReturnErrorThenWriteErrorMessage() {
        when(playController.getColor()).thenReturn(Color.BLACK);
        when(playController.move(any())).thenReturn(Error.NOT_EMPTY_TARGET);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.BLACK.ordinal()]))).thenReturn(RIGHT_FORMAT, CANCEL_FORMAT);
        this.playView.interact(this.playController);
        assertTrue(outputStreamCaptor.toString().trim().contains(NOT_EMPTY_TARGET_ERROR_MESSAGE));
    }

    @Test
    public void testGivenPlayViewWhenInteractWithPlayControllerAndReadRightCoordinateThenPlayControllerMoveMethodIsCalled() {
        when(playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(RIGHT_FORMAT);
        this.playView.interact(this.playController);
        verify(this.playController, times(1)).move(COORDINATES);
        this.verifyGameViewWrite();
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
        this.verifyGameViewWrite();
        verify(this.console, times(1)).writeln(LOST_MESSAGE);
    }

    private void verifyGameViewWrite() {
        verify(this.playController, times(1)).getDimension();
    }

}
