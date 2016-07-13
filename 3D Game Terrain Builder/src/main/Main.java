package main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import main.shader.TerrainShader;
import plugin.Plugin;
import plugin.PluginLoader;
import plugin.Resources;
import render.Renderer;
import render.Time;
import render.entity.Terrain;
import render.entity.core.Spacial3D;
import render.model.Model;
import render.model.ModelLoader;
import render.model.terrain.Chunk;
import render.model.terrain.TerrainData;
import render.model.terrain.TerrainGenerator;
import render.model.terrain.TerrainGenerator.Triangle;
import render.model.terrain.TerrainGenerator.TrianglePack;
import render.shader.ShaderLoader;
import render.texture.Material;
import render.texture.TextureLoader;
import render.view.Window;
import render.view.matrix.Camera;
import userinput.MouseManager;
import userinput.MouseManager.Ray;
import userinput.keyboard.Key;
import userinput.keyboard.KeyListener;
import userinput.keyboard.KeyboardManager;
import util.Vec3;

public class Main {

	public static Editor editor = null;
	
	public static volatile boolean closeRequested = false;
	
	public static boolean linemode = false;
	public static int debug_context = 0;
	
	public static final String NATIVE_DIST;
	static {
		NATIVE_DIST = System.getProperty("os.name").split(" ")[0];
		System.setProperty("org.lwjgl.librarypath", new File("native/" + NATIVE_DIST).getAbsolutePath());
	}
	
	public static void main(String[] args) {
		if(args.length == 0) {
			begin();
			return;
		}
		switch(args[0].toLowerCase()) {
		case "editor":
			begin();
			break;
		case "?":
		case "/?":
		case "-?":
		case "help":
		case "-help":
		case "/help":
		case "commands":
		case "/commands":
		case "-commands":
		default:

			break;
		}
	}
	
	public static void begin() {
		System.out.println("Asserting '" + NATIVE_DIST + "' native distribution: org.lwjgl.librarypath=" + System.getProperty("org.lwjgl.librarypath"));
		
		// Plugins
		try {
			for (final File f : new File("plugin").listFiles())
				PluginLoader.loadPlugin(f.getAbsolutePath(), "config.cfg").initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// EOF Plugins
		
		// Context
		final Window window = new Window(800, 600, 60, 1, 5000, false);
		final EditorWindow ewin = new EditorWindow();
		ewin.setTitle("Polite Kiwi MXER Suite");
		ewin.addTools(Resources.getTools());
		ewin.setVisible(true);
		try {
			ewin.setIconImage(ImageIO.read(new File("res/icon/chick.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			editor = new Editor(window, ewin.getCanvas());
			editor.setupContext();
			editor.updateMatrices();
		} catch (Exception e) {
			crash(e);
		}
		// EOF Context
		
		// Materials
		TerrainShader terrain_shader = null;
		Material[] a_materials = null;
		try {
			terrain_shader = new TerrainShader(ShaderLoader.makeShader("res/shader/120/", "terrain.vp", "terrain.fp"));
			terrain_shader.init();
			a_materials = new Material[] {new Material(TextureLoader.loadMipmapXMM("res/texture/heightmap.xmm"))};
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		final TerrainData terrain = new TerrainData(100);
		for (int i=0; i<1; i++) {
			for (int j=0; j<1; j++) {
				terrain.addChunk(new Chunk(i, j, TerrainGenerator.generateFlatTerrainModel(20, 100, i, j)));
			}
		}
		final Spacial3D terrain_space = new Spacial3D();
		final Terrain ter = new Terrain(terrain, terrain_space, terrain_shader, a_materials);
		
		final TerrainData terrain2 = new TerrainData(100);
		for (int i=0; i<1; i++) {
			for (int j=0; j<1; j++) {
				terrain2.addChunk(new Chunk(i, j, TerrainGenerator.generateFlatTerrainModel(20, 100, i, j)));
			}
		}
		final Spacial3D terrain_space2 = new Spacial3D();
		final Terrain ter2 = new Terrain(terrain2, terrain_space2, terrain_shader, a_materials);
		// EOF Materials
		
		// Controls and Camera
		final Camera camera = new Camera(new Vec3(0, 0, 0), new Vec3()) {
			@Override
			public void update() {
				final float dx = Mouse.getDX() * 0.3f, dy = Mouse.getDY() * 0.3f;
				if(Mouse.isButtonDown(2)) {
					Mouse.setGrabbed(true);
					this.getRotation().x -= dy;
					this.getRotation().y += dx;
				}
				if(!Mouse.isButtonDown(2))
					Mouse.setGrabbed(false);
				this.getPosition().x += 0.01f;
			}
		};
		camera.getPosition().y = -10f;
		camera.getRotation().y = 180f;
		editor.setCamera(camera);
		
		final float distance = 1;
		@SuppressWarnings("unused")
		final KeyListener
				Q = KeyboardManager.addKeyPressListener(new KeyListener(() -> {
					if(!linemode) {
						GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
						linemode = true;
						return;
					}
					GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
					linemode = false;
				}, Key.Q, true)),
				W = KeyboardManager.addKeyHoldListener(new KeyListener(() -> {
					camera.getPosition().z += -distance * -Math.cos(Math.toRadians(camera.getRotation().y));
					camera.getPosition().x += -distance * Math.sin(Math.toRadians(camera.getRotation().y));
				}, Key.W)),
				S = KeyboardManager.addKeyHoldListener(new KeyListener(() -> {
					camera.getPosition().z += distance * -Math.cos(Math.toRadians(camera.getRotation().y));
					camera.getPosition().x += distance * Math.sin(Math.toRadians(camera.getRotation().y));
				}, Key.S)),
				A = KeyboardManager.addKeyHoldListener(new KeyListener(() -> {
					camera.getPosition().z += distance * -Math.cos(Math.toRadians(camera.getRotation().y+90));
					camera.getPosition().x += distance * Math.sin(Math.toRadians(camera.getRotation().y+90));
				}, Key.A)),
				D = KeyboardManager.addKeyHoldListener(new KeyListener(() -> {
					camera.getPosition().z += -distance * -Math.cos(Math.toRadians(camera.getRotation().y+90));
					camera.getPosition().x += -distance * Math.sin(Math.toRadians(camera.getRotation().y+90));
				}, Key.D)),
				SPACE = KeyboardManager.addKeyHoldListener(new KeyListener(() -> {
					camera.getPosition().y -= 1f;
				}, Key.SPACE)),
				LSHIFT = KeyboardManager.addKeyHoldListener(new KeyListener(() -> {
					camera.getPosition().y += 1f;
				}, Key.LSHIFT));
		// EOF Controls and Camera
		
		try {
			Debug.checkOpenGLError();
		} catch (LWJGLException e) {
			crash(e);
		}
		
		Time.init();
		
		while(!Display.isCloseRequested() && !closeRequested) {
			Time.updateFPS(); Time.updateDelta();
			KeyboardManager.update();
			camera.update();
			
			final Ray mouseray = MouseManager.generateRay(
					Mouse.getX(), Mouse.getY(),
					(int)window.getWidth(), (int)window.getHeight(),
					editor.getIPerspective(),
					camera.getMatrix().inverse());
			
			ewin.getFPS().setText("FPS: " + Time.getFPS());
			
			if(Mouse.isButtonDown(0)) {
				chunks : for (final Chunk chunk : terrain.getChunks()) {
					final TrianglePack pack = chunk.getTriangles();
					final Model model = pack.model;
					final Triangle[] tris = pack.triangles;
					final int[] vbos = model.getVbos();
					for (int i=0; i<tris.length; i++){
						final Triangle tri = tris[i];
						final float intersection = MouseManager.intersectRayTriangle(camera.getPosition(), mouseray.getDirection(), tri);
						if(intersection != -1) {
							final float[] taba = new float[]{tri.a.y + 0.01f};
							final float[] tabb = new float[]{tri.b.y + 0.01f};
							final float[] tabc = new float[]{tri.c.y + 0.01f};
							tri.a.y = taba[0];
							tri.b.y = tabb[0];
							tri.c.y = tabc[0];
							
							ModelLoader.loadModel(model);
							GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbos[0]);
							ModelLoader.modBuffer(vbos[0], tri.ia*4+4, taba);
							ModelLoader.modBuffer(vbos[0], tri.ib*4+4, tabb);
							ModelLoader.modBuffer(vbos[0], tri.ic*4+4, tabc);
							GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
							ModelLoader.finalizeModel();
							break chunks;
						}
					}
				}
			}
			
			Renderer.prepare();
			Renderer.renderTerrain(ter, editor.getPerspective(), camera.getMatrix());
			Renderer.renderTerrain(ter2, editor.getPerspective(), camera.getMatrix());
			Renderer.finish(60);
			
			try {
				Debug.checkOpenGLError();
			} catch (LWJGLException e) {
				crash(e);
			}
		}
		cleanup();
	}
	
	public static void crash(Exception e) {
		e.printStackTrace();
		cleanup();
		System.err.println("Exited with error.");
		System.exit(1);
	}
	
	public static void cleanup() {
		System.out.println("Cleaning up...");
		for (final Plugin p : PluginLoader.getPlugins())
			p.close();
		PluginLoader.releasePlugins();
		TextureLoader.releaseTextures();
		ShaderLoader.releaseShaders();
		ModelLoader.releaseModels();
		editor.destroy();
	}
	
}
