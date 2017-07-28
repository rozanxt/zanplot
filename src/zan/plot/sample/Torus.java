package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Surface;

public class Torus extends Surface {

	public final double outerRadius, innerRadius;

	public Torus(double outerRadius, double innerRadius) {
		this.outerRadius = outerRadius;
		this.innerRadius = innerRadius;
	}

	@Override
	public Vec3D param(double u, double v) {
		return new Vec3D(Math.cos(u)*(outerRadius+innerRadius*Math.cos(v)), Math.sin(u)*(outerRadius+innerRadius*Math.cos(v)), innerRadius*Math.sin(v));
	}

}
