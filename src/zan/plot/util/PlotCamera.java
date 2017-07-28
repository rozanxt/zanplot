package zan.plot.util;

import static zan.lib.input.InputManager.*;

import zan.lib.gfx.camera.StandCamera;
import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Vec3D;

public class PlotCamera extends StandCamera {

	protected double zoomScale = 0.0;

	public PlotCamera(double screenWidth, double screenHeight) {super(screenWidth, screenHeight);}

	public void handle() {
		Vec3D front = getFrontVector();
		Vec3D up = getUpVector();
		Vec3D side = getSideVector();

		if (isKeyReleased(IM_KEY_C)) {pos = LinAlgUtil.zeroVec3D;}
		if (isKeyReleased(IM_KEY_X)) {azimuth = 0.0; altitude = 0.0;}
		if (isKeyReleased(IM_KEY_Z)) {zoomScale = 0.0;}

		if (isKeyDown(IM_KEY_E)) {
			if (isKeyDown(IM_KEY_LEFT_SHIFT)) zoomScale -= 0.04;
			else pos = pos.add(front.scalar(0.1));
		}
		if (isKeyDown(IM_KEY_Q)) {
			if (isKeyDown(IM_KEY_LEFT_SHIFT)) zoomScale += 0.04;
			else pos = pos.sub(front.scalar(0.1));
		}
		if (isKeyDown(IM_KEY_W)) {
			if (isKeyDown(IM_KEY_LEFT_SHIFT)) altitude += 1.0;
			else pos = pos.add(up.scalar(0.1));
		}
		if (isKeyDown(IM_KEY_S)) {
			if (isKeyDown(IM_KEY_LEFT_SHIFT)) altitude -= 1.0;
			else pos = pos.sub(up.scalar(0.1));
		}
		if (isKeyDown(IM_KEY_D)) {
			if (isKeyDown(IM_KEY_LEFT_SHIFT)) azimuth -= 1.0;
			else pos = pos.add(side.scalar(0.1));
		}
		if (isKeyDown(IM_KEY_A)) {
			if (isKeyDown(IM_KEY_LEFT_SHIFT)) azimuth += 1.0;
			else pos = pos.sub(side.scalar(0.1));
		}

		if (isMouseDown(IM_MOUSE_BUTTON_1)) {
			azimuth += 0.1 * getMouseDX();
			altitude += 0.1 * getMouseDY();
		}
		if (isMouseDown(IM_MOUSE_BUTTON_2)) {
			pos = pos.add(side.scalar(-0.01*getMouseDX())).add(up.scalar(0.01*getMouseDY()));
		}

		zoomScale -= 0.1 * getMouseScroll();
		if (azimuth < 0.0) azimuth += 360.0;
		if (azimuth > 360.0) azimuth -= 360.0;
		if (altitude < -90.0) altitude = -90.0;
		if (altitude > 90.0) altitude = 90.0;
		if (zoomScale < -5.0) zoomScale = -5.0;
		if (zoomScale > 5.0) zoomScale = 5.0;
		offset = nearClip + Math.exp(zoomScale);
	}

}
