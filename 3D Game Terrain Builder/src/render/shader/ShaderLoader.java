package render.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ShaderLoader {

	private static final String LINK_ERROR = "Failed to link shader\n",
			VALIDATION_ERROR = "Failed to vaildate shader\n",
			COMPILATION_ERROR = "Failed to compile shader\n";
	
	private static final ArrayList<Shader> shaders = new ArrayList<Shader>();
	
	public static int createShader(String location, String name, int type) throws IOException {
		final File f = new File(location + name);
		final ByteBuffer bb = BufferUtils.createByteBuffer((int)f.length());
		
		final byte[] buffer = new byte[4096];
		final FileInputStream fis = new FileInputStream(f);
		
		int count;
		while((count = fis.read(buffer)) != -1)
			bb.put(buffer, 0, count);
		fis.close();
		
		final int shader = glCreateShader(type);
		glShaderSource(shader, bb);
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
