package zan.plot.struct;

import zan.lib.math.linalg.Vec3D;

public abstract class CurveGraph extends Curve {

	public abstract double func(double t);

	@Override
	public Vec3D param(double t) {
		return new Vec3D(t, func(t), 0.0);
	}

}
