package render.view.matrix;

import util.Mat4;
import util.Vec;
import util.Vec3;

public abstract class Camera {

	private final Vec3 position, rotation;
	
	public Camera(Vec3 position, Vec3 rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public Mat4 getMatrix() {
		final Mat4 mat = new Mat4();
		mat.setIdentity();
		mat.rotate((float) Math.toRadians(rotation.x), Vec.RIGHT3);
		mat.rotate((float) Math.toRadians(rotation.y), Vec.UP3);
		mat.rotate((float) Math.toRadians(rotation.z), Vec.FORWARD3);
		mat.translate(position);
		return mat;
	}
	
	public abstract void update();
	
	public Vec3 getPosition() {
		return position;
	}
	
	public Vec3 getRotation() {
		return rotation;
	}
	
}
