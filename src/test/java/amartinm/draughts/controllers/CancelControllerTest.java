package amartinm.draughts.controllers;

import amartinm.draughts.models.Game;
import amartinm.draughts.models.State;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CancelControllerTest {

    @InjectMocks
    private CancelController cancelController;

    @Spy
    protected Game game;

    @Spy
    protected State state;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void testGivenCancelControllerWhenCancelThenGameCancelMethodAndStateNextMethodAreCalled() {
        this.cancelController.cancel();
        verify(this.game, times(1)).cancel();
        verify(this.state, times(1)).next();
    }
}
