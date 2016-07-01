package zan.plot.struct;

import zan.lib.gfx.object.VertexObject;
import zan.lib.math.linalg.Vec3D;

public abstract class Surface {

	public abstract Vec3D param(double u, double v);

	public VertexObject create(double startU, double startV, double endU, double endV, int resolutionU, int resolutionV) {
		float[] ver = new float[6 * 4 * (resolutionU) * (resolutionV)];
		for (int j=0;j<resolutionV;j++) {
			for (int i=0;i<resolutionU;i++) {
				Vec3D bottomleft = param(startU+(endU-startU)*((double)i/(double)resolutionU), startV+(endV-startV)*((double)j/(double)resolutionV));
				Vec3D bottomright = param(startU+(endU-startU)*((double)(i+1)/(double)resolutionU), startV+(endV-startV)*((double)j/(double)resolutionV));
				Vec3D topleft = param(startU+(endU-startU)*((double)i/(double)resolutionU), startV+(endV-startV)*((double)(j+1)/(double)resolutionV));
				Vec3D topright = param(startU+(endU-startU)*((double)(i+1)/(double)resolutionU), startV+(endV-startV)*((double)(j+1)/(double)resolutionV));
				Vec3D normal = bottomright.sub(bottomleft).cross(topleft.sub(bottomleft)).normalize();
				ver[resolutionU*6*4*j+6*4*i+0] = (float)bottomleft.x;
				ver[resolutionU*6*4*j+6*4*i+1] = (float)bottomleft.y;
				ver[resolutionU*6*4*j+6*4*i+2] = (float)bottomleft.z;
				ver[resolutionU*6*4*j+6*4*i+3] = (float)normal.x;
				ver[resolutionU*6*4*j+6*4*i+4] = (float)normal.y;
				ver[resolutionU*6*4*j+6*4*i+5] = (float)normal.z;
				ver[resolutionU*6*4*j+6*4*i+6] = (float)bottomright.x;
				ver[resolutionU*6*4*j+6*4*i+7] = (float)bottomright.y;
				ver[resolutionU*6*4*j+6*4*i+8] = (float)bottomright.z;
				ver[resolutionU*6*4*j+6*4*i+9] = (float)normal.x;
				ver[resolutionU*6*4*j+6*4*i+10] = (float)normal.y;
				ver[resolutionU*6*4*j+6*4*i+11] = (float)normal.z;
				ver[resolutionU*6*4*j+6*4*i+12] = (float)topleft.x;
				ver[resolutionU*6*4*j+6*4*i+13] = (float)topleft.y;
				ver[resolutionU*6*4*j+6*4*i+14] = (float)topleft.z;
				ver[resolutionU*6*4*j+6*4*i+15] = (float)normal.x;
				ver[resolutionU*6*4*j+6*4*i+16] = (float)normal.y;
				ver[resolutionU*6*4*j+6*4*i+17] = (float)normal.z;
				ver[resolutionU*6*4*j+6*4*i+18] = (float)topright.x;
				ver[resolutionU*6*4*j+6*4*i+19] = (float)topright.y;
				ver[resolutionU*6*4*j+6*4*i+20] = (float)topright.z;
				ver[resolutionU*6*4*j+6*4*i+21] = (float)normal.x;
				ver[resolutionU*6*4*j+6*4*i+22] = (float)normal.y;
				ver[resolutionU*6*4*j+6*4*i+23] = (float)normal.z;
			}
		}
		int[] ind = new int[6 * (resolutionU) * (resolutionV)];
		for (int j=0;j<resolutionV;j++) {
			for (int i=0;i<resolutionU;i++) {
				ind[resolutionU*6*j+6*i+0] = resolutionU*4*j+4*i+0;
				ind[resolutionU*6*j+6*i+1] = resolutionU*4*j+4*i+1;
				ind[resolutionU*6*j+6*i+2] = resolutionU*4*j+4*i+2;
				ind[resolutionU*6*j+6*i+3] = resolutionU*4*j+4*i+2;
				ind[resolutionU*6*j+6*i+4] = resolutionU*4*j+4*i+1;
				ind[resolutionU*6*j+6*i+5] = resolutionU*4*j+4*i+3;
			}
		}
		return new VertexObject(ver, ind, 3, 3, 0, 0, VertexObject.GL_TRIANGLES);
	}

}
