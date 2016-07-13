package render.texture.data;

import hydroque.image.data.Image;

public class MipmapData extends ImageData {

	private final int levels;
	
	public MipmapData(int tid, String location, int levels, Image base) {
		super(tid, location, base);
		this.levels = levels;
	}
	
	public int getLevels() {
		return levels;
	}
	
}
