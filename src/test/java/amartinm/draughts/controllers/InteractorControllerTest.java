package amartinm.draughts.controllers;

import amartinm.draughts.models.Game;
import amartinm.draughts.models.State;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.MockitoAnnotations.initMocks;

public abstract class InteractorControllerTest {

    protected InteractorController interactorController;

    @Mock
    protected InteractorControllersVisitor controllersVisitor;

    @Spy
    protected Game game;

    @Spy
    protected State state;

    public void before() {
        initMocks(this);
        this.interactorController = this.createController();
    }

    protected abstract InteractorController createController();

    @Test(expected = AssertionError.class)
    public void testGivenInteractorControllerWhenAcceptWithNullInteractorControllersVisitorThenThrowsAssertionError() {
        this.interactorController.accept(null);
    }

    @Test
    public void testGivenInteractorControllerWhenAcceptWithInteractorControllersVisitorThenCallControllerVisitorVisitMethod() {
        this.interactorController.accept(this.controllersVisitor);
        this.verifyControllerVisitorVisitMethodIsCalled();
    }

    protected abstract void verifyControllerVisitorVisitMethodIsCalled();

}
