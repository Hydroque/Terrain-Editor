package main.shader;

import render.shader.Shader;
import render.shader.ShaderModel;
import render.texture.Material;
import util.Mat4;
import util.Vec3;

public class DefaultShader extends ShaderModel {

	public DefaultShader(Shader shader) {
		super(shader);
	}
	
	private int matrix_projection, matrix_transform, matrix_camera, matrix_icamera;
	
	@Override
	public void init() {
		shader.bindAttribute(0, "position");
		
		matrix_projection = shader.getUniform("matrix_projection");
		matrix_transform = shader.getUniform("matrix_transform");
		matrix_camera = shader.getUniform("matrix_camera");
		matrix_icamera = shader.getUniform("matrix_icamera");
	}
	
	@Override
	public void updateMatrix(Mat4... matrices) {
		shader.bindUniformm(matrix_projection, matrices[0]);
		shader.bindUniformm(matrix_transform, matrices[1]);
		shader.bindUniformm(matrix_camera, matrices[2]);
		shader.bindUniformm(matrix_icamera, matrices[3]);
	}
	
	@Override
	public void updateVector3(Vec3... vecs) {
		
	}
	
	@Override
	public void updateInt(int... ints) {
		
	}
	
	@Override
	public void updateFloat(float... floats) {
		
	}

	@Override
	public void bindMaterials(Material[] materials) {
		
	}
	
}
