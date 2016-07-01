package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Surface;

public class Disk extends Surface {

	public final double radius;

	public Disk(double radius) {
		this.radius = radius;
	}

	@Override
	public Vec3D param(double u, double v) {
		return new Vec3D(radius*u*Math.cos(v), radius*u*Math.sin(v), 0.0);
	}

}
