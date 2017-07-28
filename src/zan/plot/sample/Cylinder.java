package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Surface;

public class Cylinder extends Surface {

	public final double radius;

	public Cylinder(double radius) {
		this.radius = radius;
	}

	@Override
	public Vec3D param(double u, double v) {
		return new Vec3D(radius*Math.sin(v), radius*Math.cos(v), u);
	}

}
