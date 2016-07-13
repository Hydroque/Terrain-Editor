package userinput.keyboard;

public class KeyState {

	private final int key;
	private volatile boolean down = true, marked = false;
	
	public KeyState(int key) {
		this.key = key;
	}
	
	public KeyState(int key, boolean down) {
		this.key = key;
		this.down = down;
	}
	
	public void mark() {
		marked = true;
	}
	
	public void press() {
		down = !down;
	}
	
	public int getKey() {
		return key;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
}
