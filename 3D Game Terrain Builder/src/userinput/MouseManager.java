package userinput;

import org.lwjgl.input.Mouse;

import render.model.terrain.TerrainGenerator.Triangle;
import util.Mat4;
import util.Vec2;
import util.Vec3;
import util.Vec4;

public class MouseManager {

	public static class Ray {
	
		private Vec2 origin;
		private Vec3 direction;
		
		public Ray(Vec2 origin, Vec3 direction) {
			this.origin = origin;
			this.direction = direction;
		}
		
		public Vec2 getOrigin() {
			return origin;
		}
		
		public Vec3 getDirection() {
			return direction;
		}
		
	}
	
	public static Ray generateRay(int x, int y, int dx, int dy, Mat4 iproject, Mat4 iview) {
		Vec4 coords = new Vec4(
				(2f*x) / dx - 1,
				(2f*y) / dy - 1
				, -1f, 1f).mulMatrix(iproject);
		coords.w = 0;
		coords = coords.mulMatrix(iview);
		return new Ray(new Vec2(Mouse.getX(), Mouse.getY()), new Vec3(coords.x, coords.y, coords.z).normalize());
	}
	
	public static final float EPSILON = 1e-6f;
	
    public static float intersectRayTriangle(
    		Vec3 origin, Vec3 dir,
    		Triangle tri) {
    	final float
			edge1X = tri.b.x - tri.a.x,
			edge1Y = tri.b.y - tri.a.y,
			edge1Z = tri.b.z - tri.a.z,
			edge2X = tri.c.x - tri.a.x,
			edge2Y = tri.c.y - tri.a.y,
			edge2Z = tri.c.z - tri.a.z;
    	final float
			pvecX = dir.y * edge2Z - dir.z * edge2Y,
			pvecY = dir.z * edge2X - dir.x * edge2Z,
			pvecZ = dir.x * edge2Y - dir.y * edge2X,
			det = edge1X * pvecX + edge1Y * pvecY + edge1Z * pvecZ;
		if (det > -EPSILON && det < EPSILON) {
			return -1.0f;
		}
		final float
			tvecX = origin.x - tri.a.x,
			tvecY = origin.y - tri.a.y,
			tvecZ = origin.z - tri.a.z,
			invDet = 1.0f / det;
		final float
			u = (tvecX * pvecX + tvecY * pvecY + tvecZ * pvecZ) * invDet;
		if (u < 0.0f || u > 1.0f) {
			return -1.0f;
		}
		final float
			qvecX = tvecY * edge1Z - tvecZ * edge1Y,
			qvecY = tvecZ * edge1X - tvecX * edge1Z,
			qvecZ = tvecX * edge1Y - tvecY * edge1X;
		final float
			v = (dir.x * qvecX + dir.y * qvecY + dir.z * qvecZ) * invDet;
		if (v < 0.0f || u + v > 1.0f) {
			return -1.0f;
		}
		return (edge2X * qvecX + edge2Y * qvecY + edge2Z * qvecZ) * invDet;
	}
	
}
