package render.texture.data;

import hydroque.image.data.Image;

public class ImageData {

	private final int tid;
	private final String location;
	private final int width, height;
	
	private final boolean transparency;
	
	public ImageData(int tid, String location, Image image) {
		this.tid = tid;
		this.location = location;
		this.width = image.width;
		this.height = image.height;
		this.transparency = image.transparency;
	}
	
	public int getTID() {
		return tid;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isTransparent() {
		return transparency;
	}
	
}
