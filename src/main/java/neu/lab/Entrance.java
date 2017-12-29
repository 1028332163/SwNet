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
					// �������
					UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
					Screen.i().show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		// // ��¼debug�������Ϣ
		// logger.debug("This is debug message.");
		// // ��¼info�������Ϣ
		// logger.info("This is info message.");
		// // ��¼error�������Ϣ
		// logger.error("This is error message.");
	}
}
