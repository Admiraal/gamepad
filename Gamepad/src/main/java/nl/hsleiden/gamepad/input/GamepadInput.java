package nl.hsleiden.gamepad.input;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import nl.hsleiden.gamepad.exceptions.GamepadNotFoundException;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

//TODO: change name of this class
public class GamepadInput implements Runnable{

	private List<GamepadInputListener> listeners = new ArrayList<GamepadInputListener>();
	private boolean running;
	private Type gamepadType = Type.STICK;
	private Controller controller;
	
	
	public GamepadInput() throws GamepadNotFoundException {
		configure();
		this.running = true;
	}
	
	public void configure() throws GamepadNotFoundException {
		selectGamePadController();
	}

	
	/**
	 * TODO make this Type.STICK configurable
	 * @throws GamepadNotFoundException 
	 */
	public void selectGamePadController() throws GamepadNotFoundException {
		Controller[] availableControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for (Controller availableController : availableControllers)
		{
			
			if (availableController.getType() == this.gamepadType)
			{
				this.controller = availableController;
				return;
			}
		}
		
//		TODO make exceptions
		throw new GamepadNotFoundException();
	}
	
	
	public void run() {
		
		//todo hotplug detection
		
		if(this.controller == null) {
			return;
			// throw new GamepadNotConfiguredException();
		}
		
		
		while (running) {

			
			/* Remember to poll each one */
			this.controller.poll();
			
			if(!this.controller.poll()) {
				return;			//TODO Make it stop the thread or make an event out of it..
			}
			

			/* Get the controllers event queue */
			EventQueue queue = controller.getEventQueue();

			/* Create an event object for the underlying plugin to populate */
			Event event = new Event();
			
			/* For each object in the queue */
			while (queue.getNextEvent(event)) {
				
				this.notifyListeners(event);
				
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}		
	}
	
	
	
	public void addListener(GamepadInputListener listener) {
		this.listeners.add(listener);
	}
	
	private void notifyListeners(Event event) {
		for(GamepadInputListener listener : this.listeners) {
			listener.handleGamepadInputEvent(event);
		}
	}
}
