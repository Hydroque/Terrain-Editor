package render.model.terrain;

import render.model.terrain.TerrainGenerator.TrianglePack;
import util.Mat4;
import util.Vec3;

public class Chunk {

	private TrianglePack triangles;
	
	private final Vec3 position, scale;
	
	public int x, y;
	
	public Chunk(int x, int y, TrianglePack pack) {
		this.x = x;
		this.y = y;
		this.position = new Vec3();
		this.scale = new Vec3(1,1,1);
		this.triangles = pack;
	}
	
	public Chunk(int x, int y, Vec3 position, Vec3 scale, TrianglePack pack) {
		this.x = x;
		this.y = y;
		this.position = position;
		this.scale = scale;
		this.triangles = pack;
	}
	
	public Mat4 getMatrix() {
		final Mat4 mat = new Mat4();
		mat.setIdentity();
		mat.itranslate(position);
		mat.scale(scale);
		return mat;
	}
	
	public TrianglePack getTriangles() {
		return triangles;
	}
	
	public void setTriangles(TrianglePack pack) {
		this.triangles = pack;
	}
	
	public Vec3 getPosition() {
		return position;
	}
	
	public Vec3 getScale() {
		return scale;
	}
	
}
