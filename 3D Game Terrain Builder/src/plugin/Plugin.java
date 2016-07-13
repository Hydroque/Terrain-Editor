package plugin;

import java.util.HashMap;

public abstract class Plugin {

	private HashMap<String, String> config;
	
	public void setConfig(HashMap<String, String> config) {
		this.config = config;
	}
	
	public abstract void initialize();
	
	public abstract void run();
	
	public abstract void close();
	
	public String getSetting(String setting) {
		return config.get(setting);
	}
	
	public void setSetting(String setting, String value) {
		this.config.put(setting, value);
	}
	
}
