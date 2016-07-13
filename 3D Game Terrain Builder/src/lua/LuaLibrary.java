package lua;

import javax.script.ScriptEngine;

import org.luaj.vm2.LuaTable;

public abstract class LuaLibrary {

	public ScriptEngine engine;
	
	public String name;
	
	public LuaLibrary(ScriptEngine engine, String name) {
		this.engine = engine;
		this.name = name;
	}
	
	public abstract LuaTable getLibrary();
	
}
