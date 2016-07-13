package render.view;

import java.nio.ByteBuffer;
import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import util.Vec2;

public class Window {

	private float width, height, aspect, fov;
	private float zNear3d, zFar3d;
	
	private boolean fullscreen;
	
	public Window(float width, float height, float fov, float zNear3d,
			float zFar3d, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.fov = fov;
		this.zNear3d = zNear3d;
		this.zFar3d = zFar3d;
		this.fullscreen = fullscreen;
		aspect = width/height;
	}
	
	public DisplayMode getDisplayMode() throws LWJGLException {
		final DisplayMode[] modes = Display.getAvailableDisplayModes();
		DisplayMode chosen = null;
		if(!fullscreen)
			return new DisplayMode((int) width, (int)height);
		for (final DisplayMode dm : modes)
			if(dm.getWidth() == width && dm.getHeight() == height && dm.isFullscreenCapable()) {
				chosen = dm;
				break;
			}
		if(chosen == null)
			System.err.println("Warning: Could not find applicable display mode!");
		return chosen == null ? new DisplayMode((int)width, (int)height) : chosen;
	}
	
	public void createDisplay(PixelFormat format, ContextAttribs attribs, DisplayMode mode) throws LWJGLException {
		Display.setFullscreen(true);
		Display.setVSyncEnabled(true);
		Display.setDisplayMode(mode);
		Display.create(format, attribs);
	}
	
	public void createDisplay(PixelFormat format, ContextAttribs attribs, DisplayMode mode, Canvas canvas) throws LWJGLException {
		Display.setFullscreen(true);
		Display.setVSyncEnabled(true);
		Display.setDisplayMode(mode);
		Display.setParent(canvas);
		Display.create(format, attribs);
	}
	
	public void setIcon(ByteBuffer[] texture) {
		Display.setIcon(texture);
	}
	
	public void setWidth(float width) {
		this.width = width;
		this.aspect = width/height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setHeight(float height) {
		this.height = height;
		this.aspect = width/height;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
		this.aspect = width/height;
	}
	
	public Vec2 getSize() {
		return new Vec2(width, height);
	}
	
	public void setFOV(float fov) {
		this.fov = fov;
	}
	
	public float getFOV() {
		return fov;
	}
	
	public void setZNear3D(float zNear3d) {
		this.zNear3d = zNear3d;
	}
	
	public float getZNear3D() {
		return zNear3d;
	}
	
	public void setZFar3D(float zFar3d) {
		this.zFar3d = zFar3d;
	}
	
	public float getZFar3D() {
		return zFar3d;
	}
	
	public float getAspect() {
		return aspect;
	}
	
}
