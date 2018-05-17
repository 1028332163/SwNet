package neu.lab.swnet.view.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import neu.lab.swnet.view.Screen;

public class MouseInOutListener extends MouseAdapter {
	String tip;

	public MouseInOutListener(String tip) {
		this.tip = tip;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Screen.i().updateLabel(tip);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Screen.i().updateLabel("");
	}

}
