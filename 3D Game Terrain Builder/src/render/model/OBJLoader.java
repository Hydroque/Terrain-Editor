package render.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import util.Vec2;
import util.Vec3;

public class OBJLoader {

	private static final class Face {
		public final String vert, tex, norm;
		public Face(String vert, String tex, String norm) {
			this.vert = vert;
			this.tex = tex;
			this.norm = norm;
		}
	}
	
	public static Model loadModelOBJ3D(String file) throws IOException {
		final BufferedReader br = new BufferedReader(new FileReader(file));
		
		final ArrayList<Vec3> vertices = new ArrayList<>(), 
				normals = new ArrayList<>();
		final ArrayList<Vec2> texcoords = new ArrayList<>();
		final ArrayList<Integer> indices = new ArrayList<>();
		final ArrayList<Face> faces = new ArrayList<>();
		
		String in;
		while((in = br.readLine()) != null) {
			if(in.isEmpty() || in.startsWith("#"))
				continue;
			final String[] split = in.split(" ");
			switch(split[0]) {
			case "v":
				final int offset = split.length > 4 ? 1 : 0;
				vertices.add(new Vec3(Float.parseFloat(split[1+offset]), Float.parseFloat(split[2+offset]), Float.parseFloat(split[3+offset])));
				break;
			case "vt":
				texcoords.add(new Vec2(Float.parseFloat(split[1]), Float.parseFloat(split[2])));
				break;
			case "vn":
				normals.add(new Vec3(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3])));
				break;
			case "f":
				faces.add(new Face(split[1], split[2], split[3]));
				break;
			}
		}
		
		br.close();
		
		final float[] vertices_array = new float[3 * vertices.size()],
				normals_array = new float[3 * vertices.size()],
				texcoord_array = new float[2 * vertices.size()];
		
		for (final Face f : faces) {
			final String[][] splits = {
					f.vert.split("/"),
					f.tex.split("/"),
					f.norm.split("/")
			};
			
			for (int i=0; i<splits.length; i++) {
				final int vert = Integer.valueOf(splits[i][0]) - 1;
				final Vec2 text = texcoords.get(Integer.valueOf(splits[i][1])-1);
				final Vec3 norm = normals.get(Integer.valueOf(splits[i][2])-1);
				texcoord_array[vert*2] = text.x;
				texcoord_array[vert*2+1] = 1 - text.y;
				
				indices.add(vert);
				normals_array[vert*3] = norm.x;
				normals_array[vert*3+1] = norm.y;
				normals_array[vert*3+2] = norm.z;
			}
		}
		
		int index = 0;
		for (final Vec3 vert : vertices) {
			vertices_array[index++] = vert.x;
			vertices_array[index++] = vert.y;
			vertices_array[index++] = vert.z;
		}
		
		final int[] indices_array = new int[indices.size()];
		for (int i=0; i<indices.size(); i++)
			indices_array[i] = indices.get(i);
		
		return ModelLoader.load3DModel(vertices_array, texcoord_array, normals_array, indices_array);
	}
	
	public static Model loadModelOBJ2D(String file) throws IOException {
		final BufferedReader br = new BufferedReader(new FileReader(file));
		
		final ArrayList<Vec2> vertices = new ArrayList<>(),
				texcoords = new ArrayList<>();
		final ArrayList<Integer> indices = new ArrayList<>();
		final ArrayList<Face> faces = new ArrayList<>();
		
		String in;
		while((in = br.readLine()) != null) {
			if(in.isEmpty() || in.startsWith("#"))
				continue;
			final String[] split = in.split(" ");
			switch(split[0]) {
			case "v":
				final int offset = split.length > 4 ? 1 : 0;
				vertices.add(new Vec2(Float.parseFloat(split[1+offset]), Float.parseFloat(split[2+offset])));
				break;
			case "vt":
				texcoords.add(new Vec2(Float.parseFloat(split[1]), Float.parseFloat(split[2])));
				break;
			case "f":
				faces.add(new Face(split[1], split[2], split[3]));
				break;
			}
		}
		
		br.close();
		
		final float[] vertices_array = new float[2 * vertices.size()],
				texcoord_array = new float[2 * vertices.size()];
		
		for (final Face f : faces) {
			final String[][] splits = {
					f.vert.split("/"),
					f.tex.split("/"),
					f.norm.split("/")
			};
			
			for (int i=0; i<splits.length; i++) {
				final int vert = Integer.valueOf(splits[i][0]) - 1;
				final Vec2 text = texcoords.get(Integer.valueOf(splits[i][1])-1);
				texcoord_array[vert*2] = text.x;
				texcoord_array[vert*2+1] = 1 - text.y;
				
				indices.add(vert);
			}
		}
		
		int index = 0;
		for (final Vec2 vert : vertices) {
			vertices_array[index++] = vert.x;
			vertices_array[index++] = vert.y;
		}
		
		final int[] indices_array = new int[indices.size()];
		for (int i=0; i<indices.size(); i++)
			indices_array[i] = indices.get(i);
		
		return ModelLoader.load2DModel(vertices_array, texcoord_array, indices_array);
	}
	
}
