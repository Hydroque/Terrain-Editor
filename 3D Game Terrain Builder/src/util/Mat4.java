package util;

import java.nio.FloatBuffer;

public class Mat4 {
	
	public float
		m00 = 0, m01 = 0, m02 = 0, m03 = 0,
		m10 = 0, m11 = 0, m12 = 0, m13 = 0,
		m20 = 0, m21 = 0, m22 = 0, m23 = 0,
		m30 = 0, m31 = 0, m32 = 0, m33 = 0;
	
	public void zero() {
		m00 = 0; m01 = 0; m02 = 0; m03 = 0;
		m10 = 0; m11 = 0; m12 = 0; m13 = 0;
		m20 = 0; m21 = 0; m22 = 0; m23 = 0;
		m30 = 0; m31 = 0; m32 = 0; m33 = 0;
	}
	
	public void setIdentity() {
		m00 = 1;
		m11 = 1;
		m22 = 1;
		m33 = 1;
	}
	
	public void translate(Vec2 pos) {
		translate(pos.x, pos.y, 0);
	}
	
	public void translate(Vec3 pos) {
		translate(pos.x, pos.y, pos.z);
	}
	
	public void itranslate(Vec2 pos) {
		translate(-pos.x, -pos.y, 0);
	}
	
	public void itranslate(Vec3 pos) {
		translate(-pos.x, -pos.y, -pos.z);
	}
	
	public void translate(float x, float y, float z) {
		m30 += m00 * x + m10 * y + m20 * z;
		m31 += m01 * x + m11 * y + m21 * z;
		m32 += m02 * x + m12 * y + m22 * z;
	}
	
	public void scale(Vec2 scale) {
		scale(scale.x, scale.y, 1);
	}
	
	public void scale(Vec3 scale) {
		scale(scale.x, scale.y, scale.z);
	}
	
	public void scale(float x, float y, float z) {
		m00 *= x;
		m01 *= x;
		m02 *= x;
		m03 *= x;
		m10 *= y;
		m11 *= y;
		m12 *= y;
		m13 *= y;
		m20 *= z;
		m21 *= z;
		m22 *= z;
		m23 *= z;
	}
	
	public void rotate(float a, float x, float y, float z) {
		rotate(a, x, y, z);
	}
	
	public void rotate(float a, Vec3 rot) {
		final float
			c = (float) Math.cos(a),
			s = (float) Math.sin(a),
			omc = 1.0f - c,
			xy = rot.x * rot.y,
			yz = rot.y * rot.z,
			xz = rot.x * rot.z,
			xs = rot.x * s,
			ys = rot.y * s,
			zs = rot.z * s,
			f00 = rot.x * rot.x * omc + c,
			f01 = xy * omc + zs,
			f02 = xz * omc - ys,
			f10 = xy * omc - zs,
			f11 = rot.y * rot.y * omc + c,
			f12 = yz * omc + xs,
			f20 = xz * omc + ys,
			f21 = yz * omc - xs,
			f22 = rot.z * rot.z * omc + c,
			t00 = m00 * f00 + m10 * f01 + m20 * f02,
			t01 = m01 * f00 + m11 * f01 + m21 * f02,
			t02 = m02 * f00 + m12 * f01 + m22 * f02,
			t03 = m03 * f00 + m13 * f01 + m23 * f02,
			t10 = m00 * f10 + m10 * f11 + m20 * f12,
			t11 = m01 * f10 + m11 * f11 + m21 * f12,
			t12 = m02 * f10 + m12 * f11 + m22 * f12,
			t13 = m03 * f10 + m13 * f11 + m23 * f12;
		
		m20 = m00 * f20 + m10 * f21 + m20 * f22;
		m21 = m01 * f20 + m11 * f21 + m21 * f22;
		m22 = m02 * f20 + m12 * f21 + m22 * f22;
		m23 = m03 * f20 + m13 * f21 + m23 * f22;
		m00 = t00;
		m01 = t01;
		m02 = t02;
		m03 = t03;
		m10 = t10;
		m11 = t11;
		m12 = t12;
		m13 = t13;
	}
	
	public float determinant() {
		float f =
			m00 * ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32)
					- m13 * m22 * m31
					- m11 * m23 * m32
					- m12 * m21 * m33);
		f -=
			m01 * ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32)
				- m13 * m22 * m30
				- m10 * m23 * m32
				- m12 * m20 * m33);
		f +=
			m02 * ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31)
				- m13 * m21 * m30
				- m10 * m23 * m31
				- m11 * m20 * m33);
		f -=
			m03 * ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31)
				- m12 * m21 * m30
				- m10 * m22 * m31
				- m11 * m20 * m32);
		return f;
	}
	
	public static float determinant3x3(
			float t00, float t01, float t02,
			float t10, float t11, float t12,
			float t20, float t21, float t22) {
		return
			t00 * (t11 * t22 - t12 * t21)
	      + t01 * (t12 * t20 - t10 * t22)
	      + t02 * (t10 * t21 - t11 * t20);
	}
	
	public static float ideterminant3x3(
			float idet,
			float t00, float t01, float t02,
			float t10, float t11, float t12,
			float t20, float t21, float t22) {
		return
		   (t00 * (t11 * t22 - t12 * t21)
	      + t01 * (t12 * t20 - t10 * t22)
	      + t02 * (t10 * t21 - t11 * t20)) * idet;
	}
	
	public Mat4 inverse() {
		final float
				det = determinant(),
				idet = 1f/det;
		final Mat4 mat = new Mat4();
		mat.m00 =  ideterminant3x3(idet, m11, m12, m13, m21, m22, m23, m31, m32, m33);
		mat.m10 = -ideterminant3x3(idet, m10, m12, m13, m20, m22, m23, m30, m32, m33);
		mat.m20 =  ideterminant3x3(idet, m10, m11, m13, m20, m21, m23, m30, m31, m33);
		mat.m30 = -ideterminant3x3(idet, m10, m11, m12, m20, m21, m22, m30, m31, m32);
		mat.m01 = -ideterminant3x3(idet, m01, m02, m03, m21, m22, m23, m31, m32, m33);
		mat.m11 =  ideterminant3x3(idet, m00, m02, m03, m20, m22, m23, m30, m32, m33);
		mat.m21 = -ideterminant3x3(idet, m00, m01, m03, m20, m21, m23, m30, m31, m33);
		mat.m31 =  ideterminant3x3(idet, m00, m01, m02, m20, m21, m22, m30, m31, m32);
		mat.m02 =  ideterminant3x3(idet, m01, m02, m03, m11, m12, m13, m31, m32, m33);
		mat.m12 = -ideterminant3x3(idet, m00, m02, m03, m10, m12, m13, m30, m32, m33);
		mat.m22 =  ideterminant3x3(idet, m00, m01, m03, m10, m11, m13, m30, m31, m33);
		mat.m32 = -ideterminant3x3(idet, m00, m01, m02, m10, m11, m12, m30, m31, m32);
		mat.m03 = -ideterminant3x3(idet, m01, m02, m03, m11, m12, m13, m21, m22, m23);
		mat.m13 =  ideterminant3x3(idet, m00, m02, m03, m10, m12, m13, m20, m22, m23);
		mat.m23 = -ideterminant3x3(idet, m00, m01, m03, m10, m11, m13, m20, m21, m23);
		mat.m33 =  ideterminant3x3(idet, m00, m01, m02, m10, m11, m12, m20, m21, m22);
		return mat;
	}
	
	public Mat4 normalize() {
		final Mat4 mat = new Mat4();
		final float
			det = determinant(),
			s = 1.0f / det,
			nm00 = m11 * m22 - m21 * m12,
			nm01 = m20 * m12 - m10 * m22,
			nm02 = m10 * m21 - m20 * m11,
			nm10 = m21 * m02 - m01 * m22,
			nm11 = m00 * m22 - m20 * m02,
			nm12 = m20 * m01 - m00 * m21,
			nm20 = m01 * m12 - m11 * m02,
			nm21 = m10 * m02 - m00 * m12,
			nm22 = m00 * m11 - m10 * m01;
		mat.m00 = nm00 * s;
		mat.m01 = nm01 * s;
		mat.m02 = nm02 * s;
		mat.m03 = 0.0f;
		mat.m10 = nm10 * s;
		mat.m11 = nm11 * s;
		mat.m12 = nm12 * s;
		mat.m13 = 0.0f;
		mat.m20 = nm20 * s;
		mat.m21 = nm21 * s;
		mat.m22 = nm22 * s;
		mat.m23 = 0.0f;
		mat.m30 = 0.0f;
		mat.m31 = 0.0f;
		mat.m32 = 0.0f;
		mat.m33 = 1.0f;
		return mat;
	}
	
	public void setNormalized() {
	final float
			det = determinant(),
			s = 1.0f / det,
			nm00 = m11 * m22 - m21 * m12,
			nm01 = m20 * m12 - m10 * m22,
			nm02 = m10 * m21 - m20 * m11,
			nm10 = m21 * m02 - m01 * m22,
			nm11 = m00 * m22 - m20 * m02,
			nm12 = m20 * m01 - m00 * m21,
			nm20 = m01 * m12 - m11 * m02,
			nm21 = m10 * m02 - m00 * m12,
			nm22 = m00 * m11 - m10 * m01;
		m00 = nm00 * s;
		m01 = nm01 * s;
		m02 = nm02 * s;
		m03 = 0.0f;
		m10 = nm10 * s;
		m11 = nm11 * s;
		m12 = nm12 * s;
		m13 = 0.0f;
		m20 = nm20 * s;
		m21 = nm21 * s;
		m22 = nm22 * s;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}
	
	public void store(FloatBuffer buffer) {
		buffer.put(m00).put(m01).put(m02).put(m03)
			  .put(m10).put(m11).put(m12).put(m13)
			  .put(m20).put(m21).put(m22).put(m23)
			  .put(m30).put(m31).put(m32).put(m33);
	}
	
	@Override
	public String toString() {
		return "Mat4 [" + '\n' +
			m00 + ", " + m01 + ", " + m02 + ", " + m03 + '\n' +
			m10 + ", " + m11 + ", " + m12 + ", " + m13 + '\n' +
			m20 + ", " + m21 + ", " + m22 + ", " + m23 + '\n' +
			m30 + ", " + m31 + ", " + m32 + ", " + m33 + '\n' +
			"]";
	}
	
}
