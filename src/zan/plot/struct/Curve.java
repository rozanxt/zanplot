package zan.plot.struct;

import zan.lib.gfx.object.VertexObject;
import zan.lib.math.linalg.Vec3D;
import zan.plot.core.MainPanel;

public abstract class Curve {

	public abstract Vec3D param(double t);

	public Vec3D tangent(double t) {return param(t+MainPanel.infinitesimal).sub(param(t)).normalize();}
	public Vec3D normal(double t) {return tangent(t+MainPanel.infinitesimal).sub(tangent(t)).normalize();}
	public Vec3D binormal(double t) {return tangent(t).cross(normal(t));}

	public Vec3D[] getTNB(double t) {
		Vec3D tangent = tangent(t);
		Vec3D tangent2 = tangent(t+MainPanel.infinitesimal);
		Vec3D normal = tangent2.sub(tangent).normalize();
		Vec3D binormal = tangent.cross(normal);
		Vec3D[] tnb = new Vec3D[3];
		tnb[0] = tangent;
		tnb[1] = normal;
		tnb[2] = binormal;
		return tnb;
	}

	public VertexObject create(double a, double b, int resolution) {
		float[] ver = new float[3*(resolution+1)];
		int[] ind = new int[resolution+1];
		for (int i=0;i<resolution+1;i++) {
			Vec3D point = param(a+(b-a)*((double)i/(double)resolution));
			ver[3*i+0] = (float)point.x;
			ver[3*i+1] = (float)point.y;
			ver[3*i+2] = (float)point.z;
			ind[i] = i;
		}
		return new VertexObject(ver, ind, 3, 0, 0, 0, VertexObject.GL_LINE_STRIP);
	}

}
