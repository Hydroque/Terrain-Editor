package render.model.terrain;

import render.model.Model;
import render.model.ModelLoader;
import util.Vec3;

public class TerrainGenerator {

	public static class Triangle {
		public int ia, ib, ic;
		public final Vec3 a, b, c;
		public Triangle(int ia, int ib, int ic, Vec3 a, Vec3 b, Vec3 c) {
			this.ia = ia;
			this.ib = ib;
			this.ic = ic;
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	
	public static class TrianglePack {
		public final Triangle[] triangles;
		public final Model model;
		public TrianglePack(Triangle[] triangles, Model model) {
			this.triangles = triangles;
			this.model = model;
		}
	}
	
	public static TrianglePack generateFlatTerrainModel(int vertex, int size, int x, int y){
		final int count = vertex * vertex;
		final float[] vertices = new float[count * 3];
		final float[] normals = new float[count * 3];
		final float[] textureCoords = new float[count * 2];
		final int[] indices = new int[6 * (vertex-1) * (vertex-1)];
		int vp = 0;
		for(int i=0;i<vertex;i++){
			for(int j=0;j<vertex;j++){
				final float vert = (float) vertex - 1;
				vertices[vp*3] = (float)j/vert * size;
				vertices[vp*3+1] = 0;
				vertices[vp*3+2] = (float)i/vert * size;
				normals[vp*3] = 0;
				normals[vp*3+1] = 1;
				normals[vp*3+2] = 0;
				textureCoords[vp*2] = (float)j/vert;
				textureCoords[vp*2+1] = (float)i/vert;
				vp++;
			}
		}
		final Triangle[] triangles = new Triangle[indices.length/3];
		int tripointer = 0;
		int pointer = 0;
		for(int gz=0;gz<vertex-1;gz++){
			final int gzv = gz * vertex, gzb = (gz+1) * vertex;
			for(int gx=0;gx<vertex-1;gx++){
				final int topLeft = gzv+gx;
				final int topRight = topLeft + 1;
				final int bottomLeft = gzb+gx;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				final float sx = size*x, sy = size*y;
				final int tl3 = topLeft*3, bl3 = bottomLeft*3, tr3 = topRight*3;
				triangles[tripointer++] = new Triangle(
						tl3, bl3, tr3,
						new Vec3(vertices[tl3]+sx, vertices[tl3+1], vertices[tl3+2]+sy),
						new Vec3(vertices[bl3]+sx, vertices[bl3+1], vertices[bl3+2]+sy),
						new Vec3(vertices[tr3]+sx, vertices[tr3+1], vertices[tr3+2]+sy));
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomLeft + 1;
				triangles[tripointer++] = new Triangle(
						tr3, bl3, bl3+3,
						new Vec3(vertices[tr3]+sx, vertices[tr3+1], vertices[tr3+2]+sy),
						new Vec3(vertices[bl3]+sx, vertices[bl3+1], vertices[bl3+2]+sy),
						new Vec3(vertices[bl3+3]+sx, vertices[bl3+4], vertices[bl3+5]+sy));
			}
		}
		
		return new TrianglePack(triangles, ModelLoader.load3DModel(vertices, textureCoords, normals, indices));
	}
	
}
