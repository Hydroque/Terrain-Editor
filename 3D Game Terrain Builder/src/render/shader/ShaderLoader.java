package render.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL20.*;

public class ShaderLoader {

	private static final String LINK_ERROR = "Failed to link shader\n",
			VALIDATION_ERROR = "Failed to vaildate shader\n",
			COMPILATION_ERROR = "Failed to compile shader\n";
	
	private static final ArrayList<Shader> shaders = new ArrayList<Shader>();
	
	public static int createShader(String location, String name, int type) throws IOException {
		final BufferedReader br = new BufferedReader(new FileReader(location + name));
		final StringBuffer buffer = new StringBuffer();
		
		String in;
		while((in = br.readLine()) != null)
			buffer.append(in).append('\n');
		
		br.close();
		
		final int shader = glCreateShader(type);
		glShaderSource(shader, buffer);
		glCompileShader(shader);
		
		checkForShaderError(shader, GL_COMPILE_STATUS, COMPILATION_ERROR + name);
		
		return shader;
	}
	
	public static Shader makeShader(String location, String vertex, String fragment) throws IOException {
		final int vert = createShader(location, vertex, GL_VERTEX_SHADER),
				frag = createShader(location, fragment, GL_FRAGMENT_SHADER);
		
		final int program = glCreateProgram();
		
		glAttachShader(program, vert);
		glAttachShader(program, frag);
		
		glLinkProgram(program);
		glValidateProgram(program);
		
		checkForProgramError(program, GL_LINK_STATUS, LINK_ERROR);
		checkForProgramError(program, GL_VALIDATE_STATUS, VALIDATION_ERROR);
		
		final Shader s = new Shader(program, vert, frag);
		shaders.add(s);
		return s;
	}
	
	public static ArrayList<Shader> getShaders() {
		return shaders;
	}
	
	public static void releaseShader(Shader s, boolean delete) {
		final int program = s.getProgram(),
				vert = s.getVertex(),
				frag = s.getFragment();
		glDetachShader(program, vert);
		glDetachShader(program, frag);
		glDeleteShader(vert);
		glDeleteShader(frag);
		glDeleteProgram(program);
		if(delete) shaders.remove(s);
	}
	
	public static void releaseShaders() {
		for (final Shader s : shaders)
			releaseShader(s, false);
		shaders.clear();
	}
	
	public static void checkForShaderError(int shader, int area, String msg) {
		if (glGetShaderi(shader, area) == GL11.GL_FALSE)
			System.err.println(msg + glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH)));
	}
	
	public static void checkForProgramError(int program, int area, String msg) {
		if (glGetProgrami(program, area) == GL11.GL_FALSE)
			System.err.println(msg + glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH)));
	}
	
}
