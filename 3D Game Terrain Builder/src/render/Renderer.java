package render;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import render.entity.Terrain;
import render.model.Model;
import render.model.terrain.Chunk;
import render.model.terrain.TerrainData;
import render.shader.ShaderModel;
import util.Mat4;

public class Renderer {

	public static void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void renderTerrain(Terrain element, Mat4 projection, Mat4 camera) {
		final TerrainData terrain = element.getTerrain();
		final ShaderModel shader_model = element.getShaderModel();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		for (final Chunk chunk : terrain.getChunks()) {
			chunk.getPosition().x = chunk.x * terrain.getChunkSize();
			chunk.getPosition().z = chunk.y * terrain.getChunkSize();
			final Model model = chunk.getTriangles().model;
			GL30.glBindVertexArray(model.getVao());
				GL20.glUseProgram(shader_model.getShader().getProgram());
					shader_model.bindMaterials(element.getMaterials());
					shader_model.updateMatrix(projection, chunk.getMatrix(), camera);
					shader_model.updateInt(20);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				GL20.glUseProgram(0);
			GL30.glBindVertexArray(0);
		}
	}
	
	public static void finish(int fps) {
		Display.update();
		Display.sync(fps);
	}
	
}
