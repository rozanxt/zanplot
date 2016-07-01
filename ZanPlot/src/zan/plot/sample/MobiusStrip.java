package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Surface;

public class MobiusStrip extends Surface {

	public final double radius;

	public MobiusStrip(double radius) {
		this.radius = radius;
	}

	@Override
	public Vec3D param(double u, double v) {
		return new Vec3D((radius+u*Math.cos(0.5*v))*Math.cos(v), (radius+u*Math.cos(0.5*v))*Math.sin(v), u*Math.sin(0.5*v));
	}

}
