package lua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class LuaEvent extends LuaLibrary {
	
	public final List<LuaValue> executions = Collections.synchronizedList(new ArrayList<LuaValue>());
	
	public void invokeEvents() {
		for (int i=0; i<executions.size(); i++)
			executions.get(i).call();
	}
	
	public LuaEvent(ScriptEngine engine) {
		super(engine, "LuaEvent");
		onframe.set("connect", connect);
		onframe.set("disconnect", disconnect);
	}
	
	public final LuaTable onframe = new LuaTable();
	
	public final LuaValue disconnect = new LuaValue() {
		
		public LuaValue call(LuaValue a) {
			executions.remove(a.checkint());
			return LuaValue.NIL;
		}
		
		@Override
		public int type() {
			return 0;
		}
		
		@Override
		public String typename() {
			return "function";
		}
		
	};
	
	public final LuaValue connect = new LuaValue() {
		
		public LuaValue call(LuaValue a) {
			executions.add(a.checkfunction());
			return LuaValue.valueOf(executions.size()-1);
		}
		
		@Override
		public int type() {
			return 0;
		}

		@Override
		public String typename() {
			return "function";
		}
		
	};

	@Override
	public LuaTable getLibrary() {
		return onframe;
	}
	
}
