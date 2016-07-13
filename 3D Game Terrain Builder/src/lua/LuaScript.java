package lua;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class LuaScript extends Thread {

	public static final ScriptEngineManager engineManager = new ScriptEngineManager();
	public static final ScriptEngine engine = engineManager.getEngineByExtension(".lua");
	
	private final String source;
	
	public LuaScript(String source) {
		this.source = source;
	}
	
	public void injectLibrary(LuaLibrary library) {
		engine.put(library.name, library.getLibrary());
	}
	
	@Override
	public void run() {
		try {
			engine.eval(source);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
}
