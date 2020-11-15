package amartinm.draughts.controllers;

import amartinm.draughts.models.State;
import amartinm.draughts.models.Game;

class CancelController extends Controller {

    protected CancelController(Game game, State state) {
        super(game, state);
    }

    public void cancel() {
		this.game.cancel();
		this.state.next();
	}

}