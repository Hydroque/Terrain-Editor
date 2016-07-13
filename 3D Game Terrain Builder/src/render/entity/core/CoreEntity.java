package render.entity.core;

import render.shader.Shader;
import render.shader.ShaderModel;
import render.texture.Material;

public class CoreEntity {

	private final ShaderModel shader;
	private final Material[] materials;
	
	private boolean transparency = false;
	
	public CoreEntity(ShaderModel shader, Material[] materials) {
		this.shader = shader;
		this.materials = materials;
		for (final Material m : materials)
			if(transparency = m.getTextureData().getColorModel() == 4)
				break;
	}
	
	public ShaderModel getShaderModel() {
		return shader;
	}
	
	public Shader getShader() {
		return shader.getShader();
	}
	
	public Material[] getMaterials() {
		return materials;
	}
	
	public boolean hasTransparency() {
		return transparency;
	}
	
}
