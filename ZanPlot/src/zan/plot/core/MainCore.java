package zan.plot.core;

import static zan.lib.input.InputManager.*;

import zan.lib.core.FrameEngine;

public class MainCore extends FrameEngine {

	@Override
	protected void onKey(int key, int state, int mods, int scancode) {
		if (key == IM_KEY_ESCAPE && state == IM_RELEASE) close();
		else if (key == IM_KEY_F11 && state == IM_RELEASE) setFullScreen(!isFullScreen());
		super.onKey(key, state, mods, scancode);
	}

	public static void main(String[] args) {
		MainCore core = new MainCore();
		core.setTitle("ZanPlot");
		core.setIcon("res/ico/zanplot_icon.png");
		core.setWindowSize(1024, 768);
		core.setPanel(new MainPanel(core));
		core.run();
	}

}
