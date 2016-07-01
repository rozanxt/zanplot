package zan.plot.struct;

import zan.lib.math.linalg.Vec3D;

public abstract class SurfaceGraph extends Surface {

	public abstract double func(double u, double v);

	@Override
	public Vec3D param(double u, double v) {
		return new Vec3D(v, func(u, v), u);
	}

}
