package amartinm.draughts.controllers;

import amartinm.draughts.models.Error;
import amartinm.draughts.models.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MoveControllerTest {

    @InjectMocks
    private MoveController moveController;

    @Spy
    protected Game game;

    @Spy
    protected State state;

    private Coordinate origin;

    @Before
    public void before() {
        initMocks(this);
        this.origin = new CoordinateBuilder().row(5).column(0).build();
    }

    @Test(expected = AssertionError.class)
    public void testGivenMoveControllerWhenMoveWithNullCoordinatesThenThrowsAssertionError() {
        this.moveController.move(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenMoveControllerWhenMoveWithoutMinimumCoordinatesThenThrowsAssertionError() {
        this.moveController.move(this.origin);
    }

    @Test(expected = AssertionError.class)
    public void testGivenMoveControllerWhenMoveWithNullCoordinateInCoordinatesThenThrowsAssertionError() {
        this.moveController.move(this.origin, null);
    }

    @Test
    public void testGivenMoveControllerWhenMoveWithErroneousMovementThenReturnError() {
        Coordinate[] oppositeColorCoordinatesMovement = new Coordinate[]{
                new CoordinateBuilder().row(2).column(7).build(),
                new CoordinateBuilder().row(3).column(6).build(),
        };
        Error error = this.moveController.move(oppositeColorCoordinatesMovement[0], oppositeColorCoordinatesMovement[1]);
        assertNotNull(error);
        verify(this.state, never()).next();
    }

    @Test
    public void testGivenMoveControllerWhenMoveAndGameIsBlockedThenCallStateNextMethod() {
        when(this.game.isBlocked()).thenReturn(true);
        Error error = this.moveController.move(this.origin, diagonalCoordinateFromOrigin());
        assertNull(error);
        verify(this.state, times(1)).next();
    }

    private Coordinate diagonalCoordinateFromOrigin() {
        return new CoordinateBuilder().row(4).column(1).build();
    }
}
