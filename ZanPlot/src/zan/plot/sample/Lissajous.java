package zan.plot.sample;

import zan.lib.math.linalg.Vec3D;
import zan.plot.struct.Curve;

public class Lissajous extends Curve {

	public final double a, b, c, d;

	public Lissajous(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override
	public Vec3D param(double t) {
		return new Vec3D(Math.sin(a*t+c), Math.sin(b*t+d), 0.0);
	}

}
