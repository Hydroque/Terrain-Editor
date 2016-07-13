package render.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import util.Mat4;

public class Shader {

	private static final FloatBuffer temp_buffer = BufferUtils.createFloatBuffer(16);

	private final int program, vert, frag;
	
	public Shader(int program, int vert, int frag) {
		this.program = program;
		this.vert = vert;
		this.frag = frag;
	}
	
	public void bindAttribute(int location, String name) {
		GL20.glBindAttribLocation(program, location, name);
	}
	
	public int getUniform(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	public void bindUniformfb(int location, int len, FloatBuffer buffer) {
		switch(len) {
		case 1:
			GL20.glUniform1(location, buffer);
			break;
		case 2:
			GL20.glUniform2(location, buffer);
			break;
		case 3:
			GL20.glUniform3(location, buffer);
			break;
		case 4:
			GL20.glUniform4(location, buffer);
		}
	}
	
	public void bindUniformib(int location, int len, IntBuffer buffer) {
		switch(len) {
		case 1:
			GL20.glUniform1(location, buffer);
			break;
		case 2:
			GL20.glUniform2(location, buffer);
			break;
		case 3:
			GL20.glUniform3(location, buffer);
			break;
		case 4:
			GL20.glUniform4(location, buffer);
		}
	}
	
	public void bindUniformf(int location, float... uni) {
		switch(uni.length) {
		case 1:
			GL20.glUniform1f(location, uni[0]);
			break;
		case 2:
			GL20.glUniform2f(location, uni[0], uni[1]);
			break;
		case 3:
			GL20.glUniform3f(location, uni[0], uni[1], uni[2]);
			break;
		case 4:
			GL20.glUniform4f(location, uni[0], uni[1], uni[2], uni[3]);
			break;
		}
	}
	
	public void bindUniformi(int location, int... uni) {
		switch(uni.length) {
		case 1:
			GL20.glUniform1i(location, uni[0]);
			break;
		case 2:
			GL20.glUniform2i(location, uni[0], uni[1]);
			break;
		case 3:
			GL20.glUniform3i(location, uni[0], uni[1], uni[2]);
			break;
		case 4:
			GL20.glUniform4i(location, uni[0], uni[1], uni[2], uni[3]);
			break;
		}
	}
	
	public void bindUniformm(int location, Mat4 mat) {
		mat.store(temp_buffer);
		temp_buffer.flip();
		GL20.glUniformMatrix4(location, false, temp_buffer);
	}
	
	public void bindTexture(int target, int location, int unit, int tid) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(target, tid);
		bindUniformi(location, unit);
	}
	
	public int getProgram() {
		return program;
	}
	
	public int getVertex() {
		return vert;
	}
	
	public int getFragment() {
		return frag;
	}
	
}
