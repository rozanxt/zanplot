package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Surface;

public class Sphere extends Surface {

	public final double radius;

	public Sphere(double radius) {
		this.radius = radius;
	}

	@Override
	public Vec3D param(double u, double v) {
		return new Vec3D(radius*Math.cos(u)*Math.cos(v), radius*Math.sin(u)*Math.cos(v), radius*Math.sin(v));
	}

}
