package util;

public class Vec2 {

	public float x = 0, y = 0;
	
	public Vec2() {
		
	}
	
	public Vec2(float x) {
		this.x = x;
	}
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float distance() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public float dot(Vec2 a, Vec2 b) {
		return dot(a.x, a.y, b.x, b.y);
	}
	
	public float dot(float x1, float y1, float x2, float y2) {
		return x1 * x2 + y1 * y2;
	}
	
	public Vec3 normalize() {
		final float h = (float) Math.sqrt(x*x + y*y);
		return new Vec3(x/h, y/h);
	}
	
	public Vec3 negate() {
		return new Vec3(-x, -y);
	}
	
	@Override
	public String toString() {
		return "Vec2 [" + x + ", " + y + "]";
	}
	
}
