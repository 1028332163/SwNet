package neu.lab;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel;

import neu.lab.view.Screen;

public class Entrance {
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// 设置外观
					UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
					Screen.i().show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		// // 记录debug级别的信息
		// logger.debug("This is debug message.");
		// // 记录info级别的信息
		// logger.info("This is info message.");
		// // 记录error级别的信息
		// logger.error("This is error message.");
	}
}
