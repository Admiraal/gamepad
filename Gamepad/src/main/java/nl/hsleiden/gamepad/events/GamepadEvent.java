package nl.hsleiden.gamepad.events;

public class GamepadEvent {

	private Buttons button;
	private States state;
	
	
	public GamepadEvent(Buttons button, States state) {
		this.button = button;
		this.state = state;
	}


	public Buttons getButton() {
		return button;
	}


	public States getState() {
		return state;
	}
	
	
}
