package render;

import org.lwjgl.Sys;

public class Time {

	private static int fps, currentfps;
	private static long lastfps;
	private static boolean fpsdone;
	
	private static long lastframe;
	private static int delta;
	
	public static void init() {
		lastfps = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		lastframe = lastfps;
	}
	
	public static void updateFPS() {
		final long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		if (time - lastfps > 1000) {
			if(!fpsdone)
				fpsdone = true;
			currentfps = fps;
			fps = 0;
			lastfps += 1000;
		}
		fps++;
	}
	
	public static void updateDelta() {
		final long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		delta = (int) (time - lastframe);
		lastframe = time;
	}
	
	public static int getFPS() {
		return currentfps;
	}
	
	public static int getDelta() {
		return delta;
	}
	
}
