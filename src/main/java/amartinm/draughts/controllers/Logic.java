package amartinm.draughts.controllers;

import java.util.HashMap;
import java.util.Map;

import amartinm.draughts.models.Game;
import amartinm.draughts.models.State;
import amartinm.draughts.models.StateValue;

public class Logic {

	private Game game;
	private State state;
	private Map<StateValue, InteractorController> controllers;

	public Logic() {
		this.game = new Game();
		this.state = new State();
        this.controllers = new HashMap<StateValue, InteractorController>();
		this.controllers.put(StateValue.INITIAL, new StartController(this.game, this.state));
		this.controllers.put(StateValue.IN_GAME, new PlayController(this.game, this.state));
		this.controllers.put(StateValue.FINAL, new ResumeController(this.game, this.state));
		this.controllers.put(StateValue.EXIT, null);
	}

	public InteractorController getController() {
		return this.controllers.get(this.state.getValueState());
    }

}
