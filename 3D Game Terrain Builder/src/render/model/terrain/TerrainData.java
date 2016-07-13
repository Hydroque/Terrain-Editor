package render.model.terrain;

import java.util.ArrayList;

public class TerrainData {

	private final ArrayList<Chunk> chunks;
	
	private final int size;
	
	public TerrainData(int size) {
		this.chunks = new ArrayList<>();
		this.size = size;
	}
	
	public TerrainData(int size, ArrayList<Chunk> chunks) {
		this.chunks = new ArrayList<>();
		this.size = size;
	}
	
	public void addChunk(Chunk model) {
		chunks.add(model);
	}
	
	public void removeChunk(Chunk model) {
		chunks.remove(model);
	}
	
	public ArrayList<Chunk> getChunks() {
		return chunks;
	}
	
	public int getChunkSize() {
		return size;
	}
	
}
