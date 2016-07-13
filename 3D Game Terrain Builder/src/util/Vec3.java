package util;

public class Vec3 {

	public float x = 0, y = 0, z = 0;
	
	public Vec3() {
		
	}
	
	public Vec3(float x) {
		this.x = x;
	}
	
	public Vec3(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3 cross(Vec3 a, Vec3 b) {
		return cross(a.x, a.y, a.z, b.x, b.y, b.z);
	}
	
	public Vec3 cross(float x1, float y1, float z1, float x2, float y2, float z2) {
		return new Vec3(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
	}
	
	public float distance() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public float dot(Vec3 a, Vec3 b) {
		return dot(a.x, a.y, a.z, b.x, b.y, b.z);
	}
	
	public float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
		return x1 * x2 + y1 * y2 + z1 * z2;
	}
	
	public Vec3 normalize() {
		final float h = (float) Math.sqrt(x*x + y*y + z*z);
		return new Vec3(x/h, y/h, z/h);
	}
	
	public Vec3 negate() {
		return new Vec3(-x, -y, -z);
	}
	
	@Override
	public String toString() {
		return "Vec3 [" + x + ", " + y + ", " + z + "]";
	}
	
}
