package zan.plot.core;

import static zan.lib.input.InputManager.*;

import java.text.DecimalFormat;
import java.util.ArrayList;

import zan.lib.core.FramePanel;
import zan.lib.gfx.Gfx;
import zan.lib.gfx.camera.ScreenCamera;
import zan.lib.gfx.object.VertexObject;
import zan.lib.gfx.scene.Scene2D;
import zan.lib.gfx.scene.Scene3D;
import zan.lib.gfx.text.TextManager;
import zan.lib.gfx.texture.TextureManager;
import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Vec3D;
import zan.plot.object.PlotObject;
import zan.plot.util.PlotCamera;
import zan.plot.struct.*;
import zan.plot.sample.*;

public class MainPanel extends FramePanel {

	public static final double infinitesimal = 1E-4;

	private static DecimalFormat df = new DecimalFormat("#.##");

	private MainCore core;

	private Scene2D scene2d;
	private ScreenCamera screen;

	private Scene3D scene3d;
	private PlotCamera camera;

	private VertexObject crux;

	private ArrayList<VertexObject> meshes;
	private ArrayList<PlotObject> objects;

	private boolean enableCrossHair = true;

	public MainPanel(MainCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		Gfx.setClearColor(0.0, 0.1, 0.1, 1.0);

		TextureManager.create();
		TextManager.create();
		TextManager.loadFontFile("res/fnt/fonts.res");

		scene2d = new Scene2D();
		scene2d.enableBlend(true);
		screen = new ScreenCamera(core.getScreenWidth(), core.getScreenHeight());

		scene3d = new Scene3D();
		camera = new PlotCamera(core.getScreenWidth(), core.getScreenHeight());
		camera.setVirtualDepth(1000.0);

		final float[] ver = {-1f,  0f,  0f,
							  1f,  0f,  0f,
							  0f, -1f,  0f,
							  0f,  1f,  0f,
							  0f,  0f, -1f,
							  0f,  0f,  1f};
		final int[] ind = {0, 1, 2, 3, 4, 5};
		crux = new VertexObject(ver, ind, 3, 0, 0, 0, VertexObject.GL_LINES);

		ArrayList<Vec3D> lemniscate = new ArrayList<Vec3D>();
		lemniscate.add(new Vec3D(-5.0, 0.0, 0.0));
		lemniscate.add(new Vec3D(-5.0, 10.0, 10.0));
		lemniscate.add(new Vec3D(5.0, 10.0, -10.0));
		lemniscate.add(new Vec3D(5.0, 0.0, 0.0));
		lemniscate.add(new Vec3D(5.0, -10.0, 10.0));
		lemniscate.add(new Vec3D(-5.0, -10.0, -10.0));
		lemniscate.add(new Vec3D(-5.0, 0.0, 0.0));

		ArrayList<Vec3D> bezier = new ArrayList<Vec3D>();
		bezier.add(new Vec3D(0.0, 0.0, 0.0));
		bezier.add(new Vec3D(0.0, 5.0, 0.0));
		bezier.add(new Vec3D(10.0, 0.0, 0.0));
		bezier.add(new Vec3D(0.0, 2.0, -5.0));
		bezier.add(new Vec3D(-10.0, 4.0, -10.0));
		bezier.add(new Vec3D(-10.0, 0.0, 0.0));
		bezier.add(new Vec3D(-5.0, -2.0, 0.0));
		bezier.add(new Vec3D(0.0, -4.0, 0.0));
		bezier.add(new Vec3D(0.0, 2.0, 0.0));
		bezier.add(new Vec3D(0.0, 0.0, 2.0));

		ArrayList<Vec3D> test = new ArrayList<Vec3D>();
		test.add(new Vec3D(1.0, 1.0, -5.0));
		test.add(new Vec3D(1.0, -1.0, -2.0));
		test.add(new Vec3D(1.0, 1.0, 2.0));
		test.add(new Vec3D(1.0, -1.0, 5.0));

		meshes = new ArrayList<VertexObject>();

		meshes.add(new Sphere(0.05).create(-Math.PI, -0.5*Math.PI, Math.PI, 0.5*Math.PI, 100, 100));
		meshes.add(new Torus(0.15, 0.075).create(0.0, 0.0, 2.0*Math.PI, 2.0*Math.PI, 100, 100));
		meshes.add(new MobiusStrip(0.5).create(-0.2, 0.0, 0.2, 2*Math.PI, 100, 100));
		meshes.add(new PrismCurve(new TorusKnot(2.0, 3.0), 0.1).create(0.0, 0.0, 2.0*Math.PI, 2.0*Math.PI, 500, 10));
		meshes.add(new BezierPrismCurve(lemniscate, 0.1).create(0.0, 0.0, 2.0, 2.0*Math.PI, 100, 10));

		//meshes.add(new BezierPrismCurve(bezier, 0.1).create(0.0, 0.0, 3.0, 2.0*Math.PI, 100, 10));
		//meshes.add(new BezierPrismCurve(test, 0.1).create(0.0, 0.0, 1.0, 2.0*Math.PI, 100, 10));

		//meshes.add(new PrismCurve(new Circle(1.0), 0.1).create(0.0, 0.0, 2.0*Math.PI, 2.0*Math.PI, 100, 10));
		//meshes.add(new PrismCurve(new Helix(1.0, 0.5), 0.1).create(-10.0, 0.0, 10.0, 2.0*Math.PI, 200, 10));

		//meshes.add(new Disk(1.0).create(0.0, 0.0, 1.0, 2.0*Math.PI, 100, 100));
		//meshes.add(new Cylinder(1.0).create(-2.0, 0.0, 2.0, 2.0*Math.PI, 100, 100));
		//meshes.add(new KleinBottle(1.0, 3.0, 6.0).create(0.0, 0.0, 2.0*Math.PI, 2.0*Math.PI, 100, 100));

		//meshes.add(new Lissajous(2.0, 3.0, 7.0, 6.0).create(0.0, 2.0*Math.PI, 100));

		/*meshes.add(new PrismCurve(new CurveGraph() {
			@Override
			public double func(double t) {
				return t*t*t;
			}
		}, 0.1).create(-1.0, 0.0, 1.0, 2.0*Math.PI, 100, 10));*/

		/*meshes.add(new SurfaceGraph() {
			@Override
			public double func(double u, double v) {
				double sincu = (u == 0.0) ? 1 : Math.sin(u)/u;
				double sincv = (v == 0.0) ? 1 : Math.sin(v)/v;
				return sincu * sincv;
			}
		}.create(-10.0, -10.0, 10.0, 10.0, 100, 100));*/

		/*meshes.add(new Surface() {
			@Override
			public Vec3D param(double u, double v) {
				return new Vec3D(2+v-2*u-2*v*u, v-u-v*u, 1+2*v);
			}
		}.create(0.0, 0.0, 1.0, 1.0, 100, 100));*/

		objects = new ArrayList<PlotObject>();
		for (int i=0;i<meshes.size();i++) {
			PlotObject obj = new PlotObject(meshes.get(i));
			//obj.setColor(Utility.getRandom().nextDouble(), Utility.getRandom().nextDouble(), Utility.getRandom().nextDouble(), 1.0);
			obj.setColor(0.0, 0.5, 0.8, 1.0);
			objects.add(obj);
		}
	}

	@Override
	public void onScreenResize(int width, int height) {
		screen.setScreen(width, height);
		screen.setVirtualScreen(width, height);
		screen.setViewPort(0, 0, width, height);
		camera.setScreen(width, height);
		camera.setViewPort(0, 0, width, height);
	}

	@Override
	public void destroy() {
		for (int i=0;i<meshes.size();i++) meshes.get(i).destroy();
		meshes.clear();
		objects.clear();
		scene2d.destroy();
		scene3d.destroy();
		TextManager.destroy();
		TextureManager.destroy();
	}

	@Override
	public void update(double time) {
		if (isKeyReleased(IM_KEY_SPACE)) enableCrossHair = !enableCrossHair;
		camera.handle();

		objects.get(0).setTransformation(LinAlgUtil.scaleMat44D(1.0+0.1*Math.sin(0.01*core.getTicks()), 1.0+0.1*Math.sin(0.01*core.getTicks()), 1.0+0.1*Math.sin(0.01*core.getTicks())));
		objects.get(1).setTransformation(LinAlgUtil.rotationMat44D(core.getTicks(), -1.0, 0.0, -1.0));
		objects.get(2).setTransformation(LinAlgUtil.rotationMat44D(core.getTicks(), 0.0, 1.0, 1.0));
		objects.get(3).setTransformation(LinAlgUtil.rotationMat44D(0.1*core.getTicks(), 0.0, 0.0, -1.0));
		objects.get(4).setTransformation(LinAlgUtil.rotationMat44D(0.2*core.getTicks(), 0.0, 1.0, 0.0));
	}

	@Override
	public void render(double ip) {
		scene3d.begin();
		camera.apply(scene3d);

		scene3d.setColor(0.0, 1.0, 0.0, 1.0);
		scene3d.pushMatrix();
		scene3d.scale(1000.0, 1000.0, 1000.0);
		scene3d.applyModelMatrix();
		crux.render(scene3d);
		scene3d.popMatrix();

		if (enableCrossHair) {
			scene3d.setColor(1.0, 1.0, 0.0, 1.0);
			scene3d.pushMatrix();
			Vec3D crosshair = camera.getPos();
			scene3d.translate(crosshair.x, crosshair.y, crosshair.z);
			scene3d.scale(0.1, 0.1, 0.1);
			scene3d.applyModelMatrix();
			crux.render(scene3d);
			scene3d.popMatrix();
		}

		for (int i=0;i<objects.size();i++) objects.get(i).render(scene3d);

		scene3d.end();

		scene2d.begin();
		screen.apply(scene2d);

		scene2d.setColor(1.0, 1.0, 1.0, 1.0);
		scene2d.pushMatrix();
		scene2d.translate(4.0, 4.0, 0.0);
		scene2d.scale(16.0, 16.0, 1.0);
		scene3d.applyModelMatrix();
		Vec3D cameraPos = camera.getPos();
		TextManager.renderText(scene2d, "FPS: " + df.format(core.getFPS()) +
										" X: " + df.format(cameraPos.x) +
										" Y: " + df.format(cameraPos.y) +
										" Z: " + df.format(cameraPos.z) +
										" Azimuth: " + df.format(camera.getAzimuth()) +
										" Altitude: " + df.format(camera.getAltitude()), "defont");
		scene2d.popMatrix();

		scene2d.end();
	}

}
