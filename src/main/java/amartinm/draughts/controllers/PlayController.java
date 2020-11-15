package amartinm.draughts.controllers;

import amartinm.draughts.models.Color;
import amartinm.draughts.models.Coordinate;
import amartinm.draughts.models.State;
import amartinm.draughts.models.Error;
import amartinm.draughts.models.Game;

public class PlayController extends InteractorController {

	private CancelController cancelController;
	private MoveController moveController;

	public PlayController(Game game, State state) {
		super(game, state);
		this.cancelController = new CancelController(game, state);
		this.moveController = new MoveController(game, state);
	}

	public Error move(Coordinate... coordinates) {
		return this.moveController.move(coordinates);
	}

	public void cancel() {
		this.cancelController.cancel();
	}

	public Color getColor() {
		return this.game.getTurnColor();
	}

	public boolean isBlocked() {
		return this.game.isBlocked();
	}

	@Override
	public void accept(InteractorControllersVisitor controllersVisitor) {
		assert controllersVisitor != null;
		controllersVisitor.visit(this);
	}

}