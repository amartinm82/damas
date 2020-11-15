package amartinm.draughts.controllers;

import amartinm.draughts.models.Error;
import amartinm.draughts.models.State;
import amartinm.draughts.models.Coordinate;
import amartinm.draughts.models.Game;

class MoveController extends Controller {

    private static final int MINIMUM_COORDINATES = 2;

    protected MoveController(Game game, State state) {
        super(game, state);
    }

    public Error move(Coordinate... coordinates) {
        assert coordinates != null;
        assert coordinates.length >= MoveController.MINIMUM_COORDINATES;
        for (Coordinate coordinate : coordinates)
            assert coordinate != null;
        Error error = this.game.move(coordinates);
        if (this.game.isBlocked())
            this.state.next();
        return error;
    }

}