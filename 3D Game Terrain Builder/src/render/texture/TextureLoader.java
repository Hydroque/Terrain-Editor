package render.texture;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import hydroque.image.XMLoader;
import hydroque.image.data.Image;
import render.texture.data.ImageData;
import render.texture.data.MipmapData;

public class TextureLoader {

	public static ArrayList<ImageData> texture = new ArrayList<>();
	
	public static ByteBuffer generateBuffer(Image image) {
		final ByteBuffer bb = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);
		bb.put(image.getBody());
		bb.flip();
		return bb;
	}
	
	public static ImageData loadTextureXMI(String location) throws IOException {
		final Image image = XMLoader.loadImageXMI(location);
		final ImageData t = new ImageData(generateTexture(GL_LINEAR, GL_REPEAT, image), location, image);
		texture.add(t);
		return t;
	}
	
	private static int generateTexture(int filter, int wrap, Image image) {
		final int tid = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tid);
		glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
		glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, generateBuffer(image));
		glBindTexture(GL_TEXTURE_2D, 0);
		return tid;
	}
	
	public static MipmapData loadMipmapXMM(String location) throws IOException {
		final Image[] mipmap = XMLoader.loadImageXMM(location);
		final int mip = generateMipmap(GL_REPEAT, mipmap, true, true, -0.5f);
		final MipmapData data = new MipmapData(mip, location, mipmap.length, mipmap[0]);
		texture.add(data);
		return data;
	}
	
	public static int generateMipmap(int wrap, Image[] data, boolean linear, boolean trilinear, float lod) {
		final int tid = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tid);
		glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
		glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, data.length-1);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, linear ? GL_LINEAR : GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, linear ? (trilinear ? GL_LINEAR_MIPMAP_LINEAR : GL_LINEAR_MIPMAP_NEAREST) : (trilinear ? GL_NEAREST_MIPMAP_LINEAR : GL_NEAREST_MIPMAP_NEAREST));
		glTexParameterf(GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
		for (int i=0; i<data.length; i++)
			glTexImage2D(GL_TEXTURE_2D, i, GL_RGBA, data[i].getWidth(), data[i].getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, generateBuffer(data[i]));
		glBindTexture(GL_TEXTURE_2D, 0);
		return tid;
	}
	
	public static void releaseTexture(ImageData t, boolean delete) {
		glDeleteTextures(t.getTID());
		if(delete) texture.remove(t);
	}
	
	public static void releaseTextures() {
		for (final ImageData t : texture)
			releaseTexture(t, false);
		texture.clear();
	}
	
}
