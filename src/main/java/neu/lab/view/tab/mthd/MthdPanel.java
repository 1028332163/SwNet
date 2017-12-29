package neu.lab.view.tab.mthd;

import javax.swing.JPanel;

import neu.lab.view.Screen;

public class MthdPanel extends JPanel {
	Screen screen;
	private MthdDisplay mthdDisplay;

	public MthdPanel(Screen screen) {
		this.screen = screen;
		mthdDisplay = new MthdDisplay(screen);
	}

	public void showMthdDisplay(String clsSig) {
		mthdDisplay.updateView(clsSig);
	}
}
