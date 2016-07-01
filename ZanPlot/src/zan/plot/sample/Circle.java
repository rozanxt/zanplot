package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Curve;

public class Circle extends Curve {

	public final double radius;

	public Circle(double radius) {
		this.radius = radius;
	}

	@Override
	public Vec3D param(double t) {
		return new Vec3D(radius*Math.cos(t), radius*Math.sin(t), 0.0);
	}

}
