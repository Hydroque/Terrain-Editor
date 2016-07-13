package plugin.data;

import plugin.Resources;

public class Tool {

	public static enum ToolType {
		BUTTON;
	}
	
	private final ToolType type;
	private final String text;
	
	private final Action action;
	
	public Tool(String text, ToolType type, Action action) {
		this.type = type;
		this.text = text;
		this.action = action;
		Resources.addTool(this);
	}
	
	public ToolType getToolType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	public Action getAction() {
		return action;
	}
	
}
