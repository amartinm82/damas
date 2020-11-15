package amartinm.draughts.controllers;

import amartinm.draughts.models.Color;
import amartinm.draughts.models.Coordinate;
import amartinm.draughts.models.Game;
import amartinm.draughts.models.State;

class Controller {

    protected Game game;
    protected State state;

    protected Controller(Game game, State state) {
        assert game != null;
        assert state != null;
        this.game = game;
        this.state = state;
    }

    public Color getColor(Coordinate coordinate) {
        assert coordinate != null;
        return this.game.getColor(coordinate);
    }

    public int getDimension() {
        return this.game.getDimension();
    }

}
