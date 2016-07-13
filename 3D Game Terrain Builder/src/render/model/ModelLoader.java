package render.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelLoader {
	
	private static ArrayList<Model> Models = new ArrayList<Model>();
	
	private static int model = -1, vbo_index = 0, offshore_vbo_index = 0;
	private static int[] vbos = null, offshore_vbos = null;
	
	public static void newModel(int vbo, int offshorevbos) {
		model = GL30.glGenVertexArrays();
		vbo_index = 0;
		offshore_vbo_index = 0;
		vbos = new int[vbo];
		offshore_vbos = new int[offshorevbos];
		GL30.glBindVertexArray(model);
	}
	
	public static void loadModel(Model m) {
		model = m.getVao();
		vbo_index = m.getVbos().length;
		offshore_vbo_index = m.getOffshoreVbos().length;
		vbos = m.getVbos();
		offshore_vbos = m.getOffshoreVbos();
		GL30.glBindVertexArray(model);
	}
	
	public static void pushAttributeBuffer(int location, int stride, float[] buffer) {
		final int vbo = GL15.glGenBuffers();
		final FloatBuffer fb = BufferUtils.createFloatBuffer(buffer.length);
		fb.put(buffer);
		fb.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(location, stride, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		vbos[vbo_index++] = vbo;
	}
	
	public static void modBuffer(int vbo, int offset, float[] buffer) {
		final FloatBuffer fb = BufferUtils.createFloatBuffer(buffer.length);
		fb.put(buffer);
		fb.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, offset, fb);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public static void pushIndexBuffer(int[] indices) {
		final int buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER,
				(IntBuffer) BufferUtils.createIntBuffer(indices.length).put(indices).flip(),
				GL15.GL_STATIC_DRAW);
		offshore_vbos[offshore_vbo_index++] = buffer;
	}
	
	public static void finalizeModel() {
		for (int i=0; i<vbos.length; i++)
			GL20.glEnableVertexAttribArray(i);
		GL30.glBindVertexArray(0);
	}
	
	// I don't think we need any static "all should be like this" functions like this here.
	// I wonder how I can split this section of the class up, or create a class specifically for generation
	// Maybe put this in another class and rename this ModelGenerator??
	
	public static Model load2DModel(float[] positions, float[] texture_coords, int[] indices) {
		newModel(2, 1);
		pushAttributeBuffer(0, 2, positions);
		pushAttributeBuffer(1, 2, texture_coords);
		pushIndexBuffer(indices);
		finalizeModel();
		return storeModel(new Model(model, vbos, offshore_vbos, indices.length));
	}
	
	public static Model load3DModel(float[] positions, float[] texture_coords, float[] normals, int[] indices) {
		newModel(3, 1);
		pushAttributeBuffer(0, 3, positions);
		pushAttributeBuffer(1, 2, texture_coords);
		pushAttributeBuffer(2, 3, normals);
		pushIndexBuffer(indices);
		finalizeModel();
		return storeModel(new Model(model, vbos, offshore_vbos, indices.length));
	}
	
	public static Model storeModel(Model m) {
		Models.add(m);
		return m;
	}
	
	public static void releaseModel(Model m, boolean delete) {
		GL30.glDeleteVertexArrays(m.getVao());
		for (final int vbo : m.getVbos())
			GL15.glDeleteBuffers(vbo);
		for (final int vbo : m.getOffshoreVbos())
			GL15.glDeleteBuffers(vbo);
		if(delete) Models.remove(m);
	}
	
	public static void releaseModels() {
		for (final Model m : Models) 
			releaseModel(m, false);
		Models.clear();
	}
	
}
