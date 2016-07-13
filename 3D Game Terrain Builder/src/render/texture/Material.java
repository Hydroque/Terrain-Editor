package render.texture;

import render.texture.data.ImageData;

public class Material {

	private final ImageData data;
	
	public Material(ImageData data) {
		this.data = data;
	}
	
	public ImageData getTextureData() {
		return data;
	}
	
}
