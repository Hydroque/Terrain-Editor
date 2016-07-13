package render.view.matrix;

import util.Mat4;

public class Projection {

	public static Mat4 createPerspective(float near, float far, float fov, float aspect) {
		final Mat4 matrix = new Mat4();
		
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspect);
		float x_scale = y_scale / aspect;
		float frustum = far - near;
		
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((far + near) / frustum);
		matrix.m23 = -1;
		matrix.m32 = -((2 * near * far) / frustum);
		matrix.m33 = 0;
		
		return matrix;
	}
	
}
