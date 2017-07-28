package zan.plot.object;

import zan.lib.gfx.object.VertexObject;
import zan.lib.gfx.scene.Scene3D;
import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Mat44D;
import zan.lib.math.linalg.Vec4D;

public class PlotObject {

	protected VertexObject mesh;

	protected Mat44D transformation = LinAlgUtil.idMat44D;
	protected Vec4D color = LinAlgUtil.oneVec4D;

	public PlotObject(VertexObject mesh) {
		this.mesh = mesh;
	}

	public void setTransformation(Mat44D transformation) {
		this.transformation = transformation;
	}

	public void setColor(double r, double g, double b, double a) {
		color = new Vec4D(r, g, b, a);
	}

	public void draw(Scene3D sc) {
		mesh.render(sc);
	}

	public void render(Scene3D sc) {
		sc.setColor(color.x, color.y, color.z, color.w);
		sc.setModelMatrix(transformation);
		draw(sc);
	}

}
