package render.model;

public class Model {

	private final int vaoID, vertexCount;
	private final int[] vbos, offshoreVbos;
	
	public Model(int vaoID, int[] vbolist, int[] vbolist2, int vertexCount) {
		this.vaoID = vaoID;
		this.vbos = vbolist;
		this.offshoreVbos = vbolist2;
		this.vertexCount = vertexCount;
	}
	
	public int getVao() {
		return vaoID;
	}
	
	public int[] getVbos() {
		return vbos;
	}
	
	public int[] getOffshoreVbos() {
		return offshoreVbos;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
}
