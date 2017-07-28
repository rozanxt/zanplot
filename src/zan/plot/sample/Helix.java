package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Curve;

public class Helix extends Curve {

	public final double radius, dilation;

	public Helix(double radius, double dilation) {
		this.radius = radius;
		this.dilation = dilation;
	}

	@Override
	public Vec3D param(double t) {
		return new Vec3D(radius*Math.cos(t), radius*Math.sin(t), dilation*t);
	}

}
