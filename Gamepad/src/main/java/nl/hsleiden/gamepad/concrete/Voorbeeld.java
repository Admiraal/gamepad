package nl.hsleiden.gamepad.concrete;

import nl.hsleiden.gamepad.events.Gamepad;
import nl.hsleiden.gamepad.events.GamepadEvent;
import nl.hsleiden.gamepad.events.GamepadListener;

public class Voorbeeld implements GamepadListener{
	
	public Voorbeeld() {
		Gamepad gp = new Gamepad();
		gp.addGamePadListener(this);	
	}
	
	public static void main(String[] args) {
		new Voorbeeld(); //
	}
	
	
	public void handleGamePadEvent(GamepadEvent event) {
		System.out.println("knop: " + event.getButton());
		System.out.println("status: " + event.getState());
	}
}