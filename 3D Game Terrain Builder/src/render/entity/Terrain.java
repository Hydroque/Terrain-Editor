package render.entity;

import render.entity.core.CoreEntity;
import render.entity.core.Spacial3D;
import render.model.terrain.TerrainData;
import render.shader.ShaderModel;
import render.texture.Material;

public class Terrain extends CoreEntity {

	private final TerrainData terrain;
	
	private final Spacial3D space;
	
	public Terrain(TerrainData terrain, Spacial3D space, ShaderModel shader, Material[] materials) {
		super(shader, materials);
		this.terrain = terrain;
		this.space = space;
	}
	
	public Spacial3D getSpacial3D() {
		return space;
	}
	
	public TerrainData getTerrain() {
		return terrain;
	}
	
}
