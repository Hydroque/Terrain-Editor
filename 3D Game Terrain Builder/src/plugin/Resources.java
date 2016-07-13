package plugin;

import java.util.ArrayList;

import plugin.data.Tool;

public class Resources {

	private static ArrayList<Tool> tools = new ArrayList<Tool>();
	
	public static void addTool(Tool tool) {
		tools.add(tool);
	}
	
	public static ArrayList<Tool> getTools() {
		return tools;
	}
	
}
