package zan.plot.struct;

import java.util.ArrayList;

import zan.lib.math.linalg.Vec3D;

public class BezierPrismCurve extends Surface {

	public final ArrayList<BezierSegment> segments;
	public final double radius;

	private double[] phaseShift;

	public BezierPrismCurve(ArrayList<Vec3D> data, double radius) {
		segments = new ArrayList<BezierSegment>();
		for (int i=0;i<(data.size()-1)/3;i++) segments.add(new BezierSegment(data.get(3*i+0), data.get(3*i+1), data.get(3*i+2), data.get(3*i+3)));
		this.radius = radius;
		calcPhaseShift();
	}

	private void calcPhaseShift() {
		phaseShift = new double[segments.size()];
		for (int j=0;j<segments.size();j++) {
			phaseShift[j] = 0.0;
			for (int i=0;i<j;i++) {
				Vec3D prenormal = calcNormal(i, 1.0);
				Vec3D postnormal = calcNormal(i+1, 0.0);
				double dot = prenormal.dot(postnormal);
				if (dot < -1.0) dot = -1.0;
				else if (dot > 1.0) dot = 1.0;
				double angle = Math.acos(dot);
				Vec3D cross = prenormal.cross(postnormal).normalize();
				double dot2 = cross.dot(segments.get(i+1).derivative(0.0).normalize());
				if (dot2 < -1.0) dot2 = -1.0;
				else if (dot2 > 1.0) dot2 = 1.0;
				if (dot2 > 0.0) phaseShift[j] += angle;
				else phaseShift[j] += -angle;
			}
		}
	}

	private Vec3D calcNormal(int segment, double t) {
		Vec3D tangent = segments.get(segment).derivative(t).normalize();
		Vec3D tangent2 = segments.get(segment).derivative(t+1E-4).normalize();
		return tangent2.sub(tangent).normalize();
	}

	@Override
	public Vec3D param(double u, double v) {
		int segment = (int)u;
		double offsetU = -segment;
		if (segment < 0) {
			segment = 0;
			offsetU = 0;
		} else if (segment >= segments.size()) {
			segment = segments.size()-1;
			offsetU = 1.0-segments.size();
		}

		Vec3D trace = segments.get(segment).param(u+offsetU);
		Vec3D tangent = segments.get(segment).derivative(u+offsetU).normalize();
		Vec3D tangent2 = segments.get(segment).derivative(u+offsetU+1E-4).normalize();
		Vec3D normal = tangent2.sub(tangent).normalize();
		Vec3D binormal = tangent.cross(normal);

		return new Vec3D(trace.x, trace.y, trace.z).add(normal.scalar(radius*Math.sin(v+phaseShift[segment]))).add(binormal.scalar(radius*Math.cos(v+phaseShift[segment])));
	}

}
