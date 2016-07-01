package zan.plot.struct;

import zan.lib.math.linalg.Vec3D;

public class BezierSegment extends Curve {

	public final Vec3D p0, p1, p2, p3;

	public BezierSegment(Vec3D p0, Vec3D p1, Vec3D p2, Vec3D p3) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	@Override
	public Vec3D param(double t) {
		return p0.scalar((1-t)*(1-t)*(1-t)).add(p1.scalar(3*(1-t)*(1-t)*t)).add(p2.scalar(3*(1-t)*t*t).add(p3.scalar(t*t*t)));
	}

	public Vec3D derivative(double t) {
		return p0.scalar(-3*(1-t)*(1-t)).add(p1.scalar(3*(1-3*t)*(1-t))).add(p2.scalar(3*(2-3*t)*t)).add(p3.scalar(3*t*t));
	}

	public Vec3D derivative2(double t) {
		return p0.scalar(6*(1-t)).add(p1.scalar(6*(3*t-2))).add(p2.scalar(6*(1-3*t))).add(p3.scalar(6*t));
	}

}
