package render.texture.data;

import hydroque.image.data.Image;

public class ImageData {

	private final int tid;
	private final String location;
	private final int width, height;
	private final int color_model;
	
	public ImageData(int tid, String location, Image image) {
		this.tid = tid;
		this.location = location;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.color_model = image.getColorModel();
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
	
	public int getColorModel() {
		return color_model;
	}
	
}
