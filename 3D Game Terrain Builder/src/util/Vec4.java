package util;

public class Vec4 {

	public float x = 0, y = 0, z = 0, w = 0;
	
	public Vec4() {
		
	}
	
	public Vec4(float x) {
		this.x = x;
	}
	
	public Vec4(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec4(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public float dot(Vec4 a, Vec4 b) {
		return dot(a.x, a.y, a.z, a.w, b.x, b.y, b.z, b.w);
	}
	
	public float dot(float x1, float y1, float z1, float w1, float x2, float y2, float z2, float w2) {
		return x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;
	}
	
	public Vec4 normalize() {
		final float h = (float) Math.sqrt(x*x + y*y + z*z + w*w);
		return new Vec4(x/h, y/h, z/h, w/h);
	}
	
	public Vec4 negate() {
		return new Vec4(-x, -y, -z, -w);
	}
	
	public Vec4 mulMatrix(Mat4 in) {
		return new Vec4(
				in.m00 * this.x + in.m10 * this.y + in.m20 * this.z + in.m30 * this.w,
				in.m01 * this.x + in.m11 * this.y + in.m21 * this.z + in.m31 * this.w,
				in.m02 * this.x + in.m12 * this.y + in.m22 * this.z + in.m32 * this.w,
				in.m03 * this.x + in.m13 * this.y + in.m23 * this.z + in.m33 * this.w
				);
	}
	
	@Override
	public String toString() {
		return "Vec4 [" + x + ", " + y + ", " + z + ", " + w + "]";
	}
	
}
