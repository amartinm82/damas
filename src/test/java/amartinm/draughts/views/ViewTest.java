package amartinm.draughts.views;

import amartinm.draughts.controllers.InteractorController;
import amartinm.draughts.controllers.PlayController;
import amartinm.draughts.controllers.ResumeController;
import amartinm.draughts.controllers.StartController;
import amartinm.draughts.models.Error;
import amartinm.draughts.models.*;
import amartinm.draughts.utils.Console;
import amartinm.draughts.utils.YesNoDialog;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ViewTest {

    private static final String TITTLE = "Draughts";
    private static final String CANCEL_FORMAT = "-1";
    private static final String[] COLOR_VALUES = {"blancas", "negras"};
    private static final String PROMPT = "Mueven las %s: ";
    private static final String BAD_FORMAT_ERROR_MESSAGE = "Error!!! No te entiendo: <d><d>{,<d><d>}[0-2]";
    private static final String NOT_EMPTY_TARGET_ERROR_MESSAGE = "Error!!! No está vacío el destino";
    private static final String LOST_MESSAGE = "Derrota!!! No puedes mover tus fichas!!!";
    private static final String RIGHT_FORMAT = "61.52";
    private static final Coordinate[] COORDINATES = new Coordinate[]{
            new CoordinateBuilder().row(5).column(0).build(),
            new CoordinateBuilder().row(4).column(1).build(),
    };
    private static final String MESSAGE = "¿Queréis jugar otra";

    @InjectMocks
    private View view;
    @Mock
    private InteractorController interactorController;
    @Mock
    private Console console;
    @Mock
    private YesNoDialog yesNoDialog;
    private StartController startController;
    private ResumeController resumeController;
    private PlayController playController;
    @Captor
    ArgumentCaptor<String> argument;

    @Before
    public void init() {
        initMocks(this);
        Game game = new Game();
        State state = new State();
        this.startController = spy(new StartController(game, state));
        this.playController = spy(new PlayController(game, state));
        this.resumeController = spy(new ResumeController(game, state));
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
    public void testGivenViewWhenVisitWithStartControllerThenInteract() {
        this.view.visit(this.startController);
        verify(this.console, times(1)).writeln(TITTLE);
        this.verifyWriteGame(this.startController, boardRowsInInitialState());
        verify(this.startController, times(1)).start();
    }

    @Test(expected = AssertionError.class)
    public void testGivenViewWhenVisitWithNullPlayControllerThenThrowsAssertionError() {
        this.view.visit((PlayController) null);
    }

    @Test
    public void testGivenViewWhenVisitWithPlayControllerAndReadCancelFormatThenPlayControllerCancelMethodIsCalled() {
        when(this.playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(CANCEL_FORMAT);
        this.view.visit(this.playController);
        verify(this.playController, times(1)).cancel();
    }

    @Test
    public void testGivenViewWhenVisitWithPlayControllerAndReadBadFormatThenPlayControllerCancelMethodIsCalled() {
        when(this.playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(CANCEL_FORMAT);
        this.view.visit(this.playController);
        verify(this.playController, times(1)).cancel();
    }

    @Test
    public void testGivenViewWhenVisitWithPlayControllerAndReadBadFormatThenWriteErrorMessage() {
        when(this.playController.getColor()).thenReturn(Color.BLACK);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.BLACK.ordinal()]))).thenReturn("09.90", CANCEL_FORMAT);
        this.view.visit(this.playController);
        verify(this.console, times(1)).writeln(BAD_FORMAT_ERROR_MESSAGE);
    }

    @Test
    public void testGivenViewWhenVisitWithPlayControllerAndPlayControllerMoveMethodReturnErrorThenWriteErrorMessage() {
        when(this.playController.getColor()).thenReturn(Color.BLACK);
        when(this.playController.move(COORDINATES)).thenReturn(Error.NOT_EMPTY_TARGET);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.BLACK.ordinal()]))).thenReturn(RIGHT_FORMAT, CANCEL_FORMAT);
        this.view.visit(this.playController);
        verify(this.console, times(1)).writeln(NOT_EMPTY_TARGET_ERROR_MESSAGE);
    }

    @Test
    public void testGivenViewWhenVisitWithPlayControllerAndReadRightCoordinateThenPlayControllerMoveMethodIsCalled() {
        when(this.playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(RIGHT_FORMAT);
        this.view.visit(this.playController);
        verify(this.playController, times(1)).move(COORDINATES);
        this.verifyWriteGame(this.playController, boardRowsWithPieceMoved());
        verify(this.playController, times(1)).isBlocked();
        verify(this.console, never()).writeln(LOST_MESSAGE);
    }

    @Test
    public void testGivenViewWhenVisitWithPlayControllerAndReadRightCoordinateAndIsBlockedThenPlayControllerMoveMethodIsCalledAndWriteLostMessage() {
        when(this.playController.getColor()).thenReturn(Color.WHITE);
        when(this.console.readString(String.format(PROMPT, COLOR_VALUES[Color.WHITE.ordinal()]))).thenReturn(RIGHT_FORMAT);
        when(this.playController.isBlocked()).thenReturn(true);
        this.view.visit(this.playController);
        verify(this.playController, times(1)).move(COORDINATES);
        this.verifyWriteGame(this.playController, boardRowsWithPieceMoved());
        verify(this.console, times(1)).writeln(LOST_MESSAGE);
    }

    @Test(expected = AssertionError.class)
    public void testGivenViewWhenVisitWithNullResumeControllerThenThrowsAssertionError() {
        this.view.visit((ResumeController) null);
    }

    @Test
    public void testGivenViewWhenVisitWithResumeControllerAndYesNoDialogReadIsTrueThenCallResumeControllerResetMethod() {
        when(this.yesNoDialog.read(MESSAGE)).thenReturn(true);
        this.view.visit(this.resumeController);
        verify(this.resumeController, times(1)).reset();
    }

    @Test
    public void testGivenViewWhenVisitWithResumeControllerAndYesNoDialogReadIsFalseThenCallResumeControllerNextMethod() {
        this.view.visit(this.resumeController);
        verify(this.resumeController, times(1)).next();
    }

    private void verifyWriteGame(InteractorController controller, List<String> rows) {
        verify(controller, times(Coordinate.getDimension() * (Coordinate.getDimension() + 1) + 1)).getDimension();
        verify(console, times(90)).write(argument.capture());
        assertEquals(marshall(rows), marshall(argument.getAllValues()));
    }

    private static List<String> boardRowsInInitialState() {
        return Arrays.asList(
                " 12345678",
                "1 n n n n",
                "2n n n n ",
                "3 n n n n",
                "4        ",
                "5        ",
                "6b b b b ",
                "7 b b b b",
                "8b b b b ",
                " 12345678");
    }

    private static List<String> boardRowsWithPieceMoved() {
        return Arrays.asList(
                " 12345678",
                "1 n n n n",
                "2n n n n ",
                "3 n n n n",
                "4        ",
                "5 b      ",
                "6  b b b ",
                "7 b b b b",
                "8b b b b ",
                " 12345678");
    }

    private static String marshall(List<String> strings) {
        String string = "";
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            string += iterator.next();
        }
        return string;
    }

}
