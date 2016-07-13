package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Debug {

	public static final String ERROR_PREFIX = "OpenGL ran into an error";

	public static String getOGLErrorString(int error) {
		switch (error) {
		case GL11.GL_NO_ERROR:
			return "No error";
		case GL11.GL_INVALID_ENUM:
			return "Invalid enum";
		case GL11.GL_INVALID_VALUE:
			return "Invalid value";
		case GL11.GL_INVALID_OPERATION:
			return "Invalid operation";
		case GL11.GL_STACK_OVERFLOW:
			return "Stack overflow";
		case GL11.GL_STACK_UNDERFLOW:
			return "Stack underflow";
		case GL11.GL_OUT_OF_MEMORY:
			return "Out of memory";
		case GL30.GL_INVALID_FRAMEBUFFER_OPERATION:
			return "Invalid framebuffer operation";
		default:
			return "Unknown error";
		}
	}
	
	public static boolean checkOpenGLError() throws LWJGLException {
		final int error = GL11.glGetError();
		if (error != 0) throw new LWJGLException(ERROR_PREFIX + " | " + error + " | " + getOGLErrorString(error));
		return true;
	}
	
}
