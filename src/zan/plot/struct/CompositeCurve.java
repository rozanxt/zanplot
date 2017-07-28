package zan.plot.struct;

import java.util.ArrayList;

import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Vec3D;
import zan.plot.core.MainPanel;

public class CompositeCurve extends Curve {

	protected final ArrayList<Curve> segments;
	protected final double[] torsionShift;

	public CompositeCurve(ArrayList<Curve> segments) {
		this.segments = new ArrayList<Curve>(segments);
		torsionShift = new double[segments.size()];
		calcTorsionShift();
	}

	private void calcTorsionShift() {
		for (int j=0;j<segments.size();j++) {
			torsionShift[j] = 0.0;
			for (int i=0;i<j;i++) {
				Vec3D prenormal = segments.get(i).normal(1.0);
				Vec3D postnormal = segments.get(i+1).normal(0.0);
				double dot = prenormal.dot(postnormal);
				if (dot < -1.0) dot = -1.0;
				else if (dot > 1.0) dot = 1.0;
				double angle = Math.acos(dot);
				Vec3D cross = prenormal.cross(postnormal).normalize();
				double dot2 = cross.dot(segments.get(i+1).tangent(0.0));
				if (dot2 < -1.0) dot2 = -1.0;
				else if (dot2 > 1.0) dot2 = 1.0;
				if (dot2 > 0.0) torsionShift[j] += angle;
				else torsionShift[j] += -angle;
			}
		}
	}

	@Override
	public Vec3D param(double t) {
		int segment = (int)t;
		double offset = -segment;
		if (segment < 0) {
			segment = 0;
			offset = 0;
		} else if (segment >= segments.size()) {
			segment = segments.size()-1;
			offset = 1.0-segments.size();
		}
		return segments.get(segment).param(t+offset);
	}

	@Override
	public Vec3D tangent(double t) {
		int segment = (int)t;
		double offset = -segment;
		if (segment < 0) {
			segment = 0;
			offset = 0;
		} else if (segment >= segments.size()) {
			segment = segments.size()-1;
			offset = 1.0-segments.size();
		}
		return segments.get(segment).tangent(t+offset);
	}

	@Override
	public Vec3D normal(double t) {
		int segment = (int)t;
		double offset = -segment;
		if (segment < 0) {
			segment = 0;
			offset = 0;
		} else if (segment >= segments.size()) {
			segment = segments.size()-1;
			offset = 1.0-segments.size();
		}
		return LinAlgUtil.map(LinAlgUtil.rotationMat33D(torsionShift[segment], segments.get(segment).tangent(t+offset)), segments.get(segment).normal(t+offset));
	}

	@Override
	public Vec3D binormal(double t) {
		int segment = (int)t;
		double offset = -segment;
		if (segment < 0) {
			segment = 0;
			offset = 0;
		} else if (segment >= segments.size()) {
			segment = segments.size()-1;
			offset = 1.0-segments.size();
		}
		return LinAlgUtil.map(LinAlgUtil.rotationMat33D(torsionShift[segment], segments.get(segment).tangent(t+offset)), segments.get(segment).binormal(t+offset));
	}

	@Override
	public Vec3D[] getTNB(double t) {
		int segment = (int)t;
		double offset = -segment;
		if (segment < 0) {
			segment = 0;
			offset = 0;
		} else if (segment >= segments.size()) {
			segment = segments.size()-1;
			offset = 1.0-segments.size();
		}

		Vec3D tangent = segments.get(segment).tangent(t+offset);
		Vec3D tangent2 = segments.get(segment).tangent(t+offset+MainPanel.infinitesimal);
		Vec3D normal = tangent2.sub(tangent).normalize();
		Vec3D binormal = tangent.cross(normal);

		normal = LinAlgUtil.map(LinAlgUtil.rotationMat33D(torsionShift[segment], tangent), normal);
		binormal = LinAlgUtil.map(LinAlgUtil.rotationMat33D(torsionShift[segment], tangent), binormal);

		Vec3D[] tnb = new Vec3D[3];
		tnb[0] = tangent;
		tnb[1] = normal;
		tnb[2] = binormal;
		return tnb;
	}

}
