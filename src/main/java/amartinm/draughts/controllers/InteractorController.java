package amartinm.draughts.controllers;

import amartinm.draughts.models.Coordinate;
import amartinm.draughts.models.Game;
import amartinm.draughts.models.Piece;
import amartinm.draughts.models.State;

public abstract class InteractorController extends Controller {

	protected InteractorController(Game game, State state) {
		super(game, state);
	}

	public Piece getPiece(Coordinate coordinate) {
		return this.game.getPiece(coordinate);
	}

	abstract public void accept(InteractorControllersVisitor controllersVisitor);

}
