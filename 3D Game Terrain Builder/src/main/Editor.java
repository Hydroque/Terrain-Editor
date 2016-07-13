package main;

import java.awt.Canvas;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import render.view.Window;
import render.view.matrix.Camera;
import render.view.matrix.Projection;
import util.Mat4;

public class Editor {

	public static final int REQUIRED_MAJOR_VERSION = 2, REQUIRED_MINOR_VERSION = 1;
	
	private final Window window;
	private Camera camera;
	
	private Mat4 perspective, iperspective;
	
	public Editor(Window window) throws LWJGLException, IOException {
		this.window = window;
		final PixelFormat format = new PixelFormat();
		final ContextAttribs attribs = new ContextAttribs(REQUIRED_MAJOR_VERSION, REQUIRED_MINOR_VERSION);
		window.createDisplay(format, attribs, window.getDisplayMode());
		AL.create();
	}
	
	public Editor(Window window, Canvas canvas) throws LWJGLException, IOException {
		this.window = window;
		final PixelFormat format = new PixelFormat();
		final ContextAttribs attribs = new ContextAttribs(REQUIRED_MAJOR_VERSION, REQUIRED_MINOR_VERSION);
		window.createDisplay(format, attribs, window.getDisplayMode(), canvas);
		AL.create();
	}
	
	public void setupContext() {
		GL11.glViewport(0, 0, (int) window.getWidth(), (int) window.getHeight());
		GL11.glClearColor(0, (float) 139/255, (float) 139/255, 1f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public void updateMatrices() {
		perspective = Projection.createPerspective(window.getZNear3D(), window.getZFar3D(), window.getFOV(), window.getAspect());
		iperspective = perspective.inverse();
	}
	
	public void destroy() {
		AL.destroy();
		Display.destroy();
	}
	
	public Window getWindow() {
		return window;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public Mat4 getPerspective() {
		return perspective;
	}
	
	public Mat4 getIPerspective() {
		return iperspective;
	}
	
}
