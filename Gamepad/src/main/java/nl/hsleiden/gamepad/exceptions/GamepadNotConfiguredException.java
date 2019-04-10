package nl.hsleiden.gamepad.exceptions;

public class GamepadNotConfiguredException extends GamepadException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GamepadNotConfiguredException() {
		super("The gamepad is not configured yet.");
	}

}
