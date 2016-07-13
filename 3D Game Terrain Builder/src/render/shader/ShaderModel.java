package render.shader;

import render.texture.Material;
import util.Mat4;
import util.Vec3;

public abstract class ShaderModel {

	protected final Shader shader;
	
	public ShaderModel(Shader shader) {
		this.shader = shader;
	}
	
	public abstract void init();
	public abstract void bindMaterials(Material[] materials);
	public abstract void updateMatrix(Mat4... matrices);
	public abstract void updateInt(int... ints);
	public abstract void updateFloat(float... floats);
	public abstract void updateVector3(Vec3... vecs);
	
	public Shader getShader() {
		return shader;
	}
	
}
