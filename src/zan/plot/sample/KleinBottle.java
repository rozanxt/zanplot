package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Surface;

public class KleinBottle extends Surface {

	public final double radius;
	public final double width, height;

	public KleinBottle(double radius, double width, double height) {
		this.radius = radius;
		this.width = width;
		this.height = height;
	}

	@Override
	public Vec3D param(double u, double v) {
		return new Vec3D(width*(1-Math.sin(u))*Math.cos(u)+radius*Math.cos(v)*(2*Math.exp(-(u/2-Math.PI)*(u/2-Math.PI))-1), radius*Math.sin(v), height*Math.sin(u)+0.5*radius*Math.sin(u)*Math.cos(v)*Math.exp(-(u-1.5*Math.PI)*(u-1.5*Math.PI)));
	}

}
