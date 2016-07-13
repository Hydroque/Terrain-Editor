package main.shader;

import org.lwjgl.opengl.GL11;

import render.shader.Shader;
import render.shader.ShaderModel;
import render.texture.Material;
import util.Mat4;
import util.Vec3;

public class TerrainShader extends ShaderModel {

	public TerrainShader(Shader shader) {
		super(shader);
	}
	
	private final int MAX_TEXTURES = 1;
	
	private int[] u_texture;
	private int matrix_projection, matrix_transform, matrix_camera;
	
	private int repeat;
	
	@Override
	public void init() {
		u_texture = new int[MAX_TEXTURES];
		shader.bindAttribute(0, "position");
		
		for (int i=0; i<MAX_TEXTURES; i++)
			u_texture[i] = shader.getUniform("u_texture" + i);
		
		matrix_projection = shader.getUniform("matrix_projection");
		matrix_transform = shader.getUniform("matrix_transform");
		matrix_camera = shader.getUniform("matrix_camera");
		
		repeat = shader.getUniform("repeat");
	}
	
	@Override
	public void updateMatrix(Mat4... matrices) {
		shader.bindUniformm(matrix_projection, matrices[0]);
		shader.bindUniformm(matrix_transform, matrices[1]);
		shader.bindUniformm(matrix_camera, matrices[2]);
	}
	
	@Override
	public void updateVector3(Vec3... vecs) {
		
	}
	
	@Override
	public void updateInt(int... ints) {
		shader.bindUniformi(repeat, ints[0]);
	}
	
	@Override
	public void updateFloat(float... floats) {
		
	}

	@Override
	public void bindMaterials(Material[] materials) {
		for (int i=0; i<MAX_TEXTURES; i++) {
			final Material material = materials[i];
			if(material == null) return;
			shader.bindTexture(GL11.GL_TEXTURE_2D, u_texture[i], i, material.getTextureData().getTID());
		}
	}
	
}
