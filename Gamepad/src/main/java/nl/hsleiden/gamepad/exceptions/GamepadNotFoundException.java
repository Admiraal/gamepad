package nl.hsleiden.gamepad.exceptions;

public class GamepadNotFoundException extends GamepadException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public GamepadNotFoundException() {
		super("The gamepad is not configurable because it is not a known gamepad.");
	}
	
}
