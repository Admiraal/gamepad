package nl.hsleiden.gamepad.events;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Event;
import nl.hsleiden.gamepad.exceptions.GamepadNotFoundException;
import nl.hsleiden.gamepad.input.GamepadInput;
import nl.hsleiden.gamepad.input.GamepadInputListener;

public class Gamepad implements GamepadInputListener{

	private List<GamepadListener> listeners = new ArrayList<GamepadListener>();
	private States gamepadState;
	private Buttons gamepadButton;
	
	
	public Gamepad() {
		
		GamepadInput gpi;
		try {
			gpi = new GamepadInput();
			gpi.addListener(this);
			
			// gamepadinput thread starten
			Thread gpInputThread = new Thread(gpi);
			gpInputThread.start();
			
		} catch (GamepadNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void addGamePadListener(GamepadListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeGamePadListener(GamepadListener listener) {
		this.listeners.remove(listener);
	}
	
	
	public void handleEvent() {
		for(GamepadListener listener : this.listeners) {
			listener.handleGamePadEvent(new GamepadEvent(this.gamepadButton, this.gamepadState));
		}
	}


	public void handleGamepadInputEvent(Event event) {
		
		if(this.gamepadState == States.PRESSED && this.gamepadButton == Buttons.RIGHT) {
			if(event.getValue() != 1 || event.getValue() !=  -1 ) {
				if(event.getComponent().getName() == "x") {
					this.gamepadButton = Buttons.RIGHT;
					this.gamepadState = States.RELEASED;
				}
			}
		} else if(this.gamepadState == States.PRESSED && this.gamepadButton == Buttons.DOWN) {
			if(event.getValue() != 1 || event.getValue() !=  -1 ) {
				if(event.getComponent().getName() == "y") {
					this.gamepadButton = Buttons.DOWN;
					this.gamepadState = States.RELEASED;
				}
			}			
		} else {
			this.gamepadState = determineState(event);
			this.gamepadButton = determineButton(event);			
		}
				
		handleEvent();
	}

	
	private Buttons determineButton(Event event) {
		
		// arrows
		if(event.getComponent().getName().equals("x")) {
			
			if(event.getValue() < 0) {
				return Buttons.LEFT;
			}
			
			if(event.getValue() > 0) {
				return Buttons.RIGHT;
			}	
		}
		
		if(event.getComponent().getName().equals("y")) {
			
			if(event.getValue() < 0) {
				return Buttons.UP;
			}
			
			if(event.getValue() > 0) {
				return Buttons.DOWN;
			}
		}
		
		// buttons
		switch (Integer.valueOf(event.getComponent().getName())) {
		case 0:	
			return Buttons.X;
		case 1:
			return Buttons.A;
		case 2:
			return Buttons.B;
		case 3:	
			return Buttons.Y;
		case 4:
			return Buttons.L;
		case 5:
			return Buttons.R;
		case 8:
			return Buttons.SELECT;
		case 9:
			return Buttons.START;
		}
		
		return null;
	}
	
	
	private States determineState(Event event) {
		
		float value = event.getValue();
		
		// arrows
		if(event.getComponent().getName().equals("x")  ) {
			
			if(value == -1 || value == 1) {
				return States.PRESSED;
			}
			return States.RELEASED;
		}
		
		
		if(event.getComponent().getName().equals("y")){
			
			if(value == -1 || value == 1) {
				return States.PRESSED;
			}
			return States.RELEASED;
		}
		
		
		// buttons
		if(value == 1.0) {
			return States.PRESSED;
		}
		
		return States.RELEASED;
	}
	
}
