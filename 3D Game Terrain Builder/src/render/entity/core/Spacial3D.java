package render.entity.core;

import util.Mat4;
import util.Vec;
import util.Vec3;

public class Spacial3D {

	protected final Vec3 position, scale, rotation;
	
	protected final Mat4 matrix = null;
	
	public Spacial3D() {
		this.position = new Vec3(0,0,0);
		this.scale = new Vec3(1,1,1);
		this.rotation = new Vec3(0,0,0);
	}
	
	public Spacial3D(Vec3 position) {
		this.position = position;
		this.scale = new Vec3(1,1,1);
		this.rotation = new Vec3(0,0,0);
	}
	
	public Spacial3D(Vec3 position, Vec3 rotation) {
		this.position = position;
		this.scale = new Vec3(1,1,1);
		this.rotation = rotation;
	}
	
	public Spacial3D(Vec3 position, Vec3 scale, Vec3 rotation) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}
	
	public Vec3 getPosition() {
		return position;
	}
	
	public Vec3 getScale() {
		return scale;
	}
	
	public Vec3 getRotation() {
		return rotation;
	}
	
	public Mat4 getMatrix() {
		final Mat4 matrix = new Mat4();
		matrix.setIdentity();
		matrix.translate(position.x, position.y, position.z);
		matrix.rotate(rotation.x, Vec.RIGHT3);
		matrix.rotate(rotation.y, Vec.UP3);
		matrix.rotate(rotation.z, Vec.FORWARD3);
		matrix.scale(scale);
		return matrix;
	}
	
}
