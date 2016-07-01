package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Curve;

public class TorusKnot extends Curve {

	public final double p, q;

	public TorusKnot(double p, double q) {
		this.p = p;
		this.q = q;
	}

	@Override
	public Vec3D param(double t) {
		double r = Math.cos(q*t)+2;
		return new Vec3D(r*Math.cos(p*t), r*Math.sin(p*t), -Math.sin(q*t));
	}

}
