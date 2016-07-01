package zan.plot.struct;

import zan.lib.math.linalg.Vec3D;

public class PrismCurve extends Surface {

	public final Curve curve;
	public final double radius;

	public PrismCurve(Curve curve, double radius) {
		this.curve = curve;
		this.radius = radius;
	}

	@Override
	public Vec3D param(double s, double t) {
		Vec3D trace = curve.param(s);
		Vec3D[] tnb = curve.getTNB(s);
		return new Vec3D(trace.x, trace.y, trace.z).add(tnb[1].scalar(radius*Math.sin(t))).add(tnb[2].scalar(radius*Math.cos(t)));
	}

}
