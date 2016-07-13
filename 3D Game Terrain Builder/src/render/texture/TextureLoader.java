package render.texture;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import hydroque.image.ImageLoader;
import hydroque.image.data.Image;
import hydroque.image.data.Mipmap;
import render.texture.data.ImageData;
import render.texture.data.MipmapData;

public class TextureLoader {

	public static ArrayList<ImageData> texture = new ArrayList<>();
	
	public static ByteBuffer generateBuffer(Image image) {
		final ByteBuffer bb = BufferUtils.createByteBuffer(image.height * image.height * 4);
		bb.put(image.body);
		bb.flip();
		return bb;
	}
	
	public static ImageData loadTextureXMI(String location) throws IOException {
		final Image image = ImageLoader.loadImageXMI(location);
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
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.width, image.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, generateBuffer(image));
		glBindTexture(GL_TEXTURE_2D, 0);
		return tid;
	}
	
	public static MipmapData loadMipmapXMM(String location) throws IOException {
		final Mipmap mipmap = ImageLoader.loadImageXMM(location);
		final int mip = generateMipmap(GL_REPEAT, mipmap, true, true, -0.5f);
		final MipmapData data = new MipmapData(mip, location, mipmap.getLevels(), mipmap.getImages()[0]);
		texture.add(data);
		return data;
	}
	
	public static int generateMipmap(int wrap, Mipmap data, boolean linear, boolean trilinear, float lod) {
		final int tid = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tid);
		glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
		glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, data.getLevels()-1);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, linear ? GL_LINEAR : GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, linear ? (trilinear ? GL_LINEAR_MIPMAP_LINEAR : GL_LINEAR_MIPMAP_NEAREST) : (trilinear ? GL_NEAREST_MIPMAP_LINEAR : GL_NEAREST_MIPMAP_NEAREST));
		glTexParameterf(GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
		for (int i=0; i<data.getImages().length; i++)
			glTexImage2D(GL_TEXTURE_2D, i, GL_RGBA, data.getImages()[i].width, data.getImages()[i].height, 0, GL_RGBA, GL_UNSIGNED_BYTE, generateBuffer(data.getImages()[i]));
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
