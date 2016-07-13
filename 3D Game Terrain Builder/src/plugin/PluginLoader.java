package plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;

public class PluginLoader {

	public static ArrayList<Plugin> plugins = new ArrayList<>();
	
	public static Plugin loadPlugin(String location, String config) throws IOException {
		try {
			final File plugin = new File(location);
			final JarFile jf = new JarFile(plugin);
			final HashMap<String, String> settings = new HashMap<>();
			
			final BufferedReader br = new BufferedReader(new InputStreamReader(jf.getInputStream(jf.getJarEntry(config))));
			String in;
			while((in = br.readLine()) != null) {
				if(in.isEmpty() || in.startsWith("#"))
					continue;
				final String[] split = in.split(": ", 2);
				settings.put(split[0], split[1]);
			}
			br.close();
			jf.close();
			
			final Class<?> clazz = Class.forName(settings.get("Main"), true, new URLClassLoader(new URL[]{plugin.toURI().toURL()}));
			final Plugin instance = (Plugin) clazz.newInstance();
			instance.setConfig(settings);
			plugins.add(instance);
			return instance;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void releasePlugin(Plugin plugin) {
		plugins.remove(plugin);
	}
	
	public static void releasePlugins() {
		plugins.clear();
	}
	
	public static ArrayList<Plugin> getPlugins() {
		return plugins;
	}
	
}
