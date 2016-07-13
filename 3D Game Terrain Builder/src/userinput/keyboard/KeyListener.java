package userinput.keyboard;

public class KeyListener {

	@FunctionalInterface
	public interface Executable {
		public void run();
	}
	
	private final boolean trigger_boo;
	private final int trigger_int;
	
	private final Executable run;
	
	public KeyListener(Executable run, int trigger_int) {
		this.trigger_int = trigger_int;
		this.trigger_boo = false;
		this.run = run;
	}
	
	public KeyListener(Executable run, int trigger_int, boolean trigger_boo) {
		this.trigger_int = trigger_int;
		this.trigger_boo = trigger_boo;
		this.run = run;
	}
	
	public void update(int key, boolean state) {
		if (trigger_int == key && trigger_boo == state)
			run();
	}
	
	public void run() {
		run.run();
	}
	
	public int getTriggerKey() {
		return trigger_int;
	}
	
	public boolean getTriggerState() {
		return trigger_boo;
	}
	
}
