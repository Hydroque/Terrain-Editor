package userinput.keyboard;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class KeyboardManager {

	private static final ArrayList<KeyListener> keylisteners = new ArrayList<KeyListener>(),
			keypresslisteners = new ArrayList<KeyListener>();
	
	private static final ArrayList<KeyState> keys = new ArrayList<KeyState>();
	
	public static KeyListener addKeyPressListener(KeyListener listener) {
		keypresslisteners.add(listener);
		return listener;
	}
	
	public static void removeKeyPressListener(KeyListener listener) {
		keypresslisteners.remove(listener);
	}
	
	public static KeyListener addKeyHoldListener(KeyListener listener) {
		keylisteners.add(listener);
		return listener;
	}
	
	public static void removeKeyHoldListener(KeyListener listener) {
		keylisteners.remove(listener);
	}
	
	public static void update() {
		for (int i=0; i<keys.size(); i++)
			if(keys.get(i).isMarked()) {
				keys.remove(i);
				continue;
			}
		for (int i=0; i<keys.size(); i++)
			if(!keys.get(i).isMarked())
				keys.get(i).mark();
		
		while (Keyboard.next()) {
			keys.add(new KeyState(Keyboard.getEventKey()));
			for (int i=0; i<keypresslisteners.size(); i++)
				keypresslisteners.get(i).update(Keyboard.getEventKey(), Keyboard.getEventKeyState());
		}
		for (int i=0; i<keylisteners.size(); i++) {
			final KeyListener kl = keylisteners.get(i);
			if(Keyboard.isKeyDown(kl.getTriggerKey()))
				kl.run();
		}
	}
	
	public ArrayList<KeyState> getPressedKeys() {
		return keys;
	}
	
}
