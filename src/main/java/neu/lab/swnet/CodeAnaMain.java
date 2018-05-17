package neu.lab.swnet;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel;

import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.core.SysInfoLoader;
import neu.lab.swnet.debug.RltTester;
import neu.lab.swnet.debug.SqlWriter;
import neu.lab.swnet.debug.SysPrinter;
import neu.lab.swnet.risk.RiskWriter;
import neu.lab.swnet.view.Screen;
import soot.G;

public class CodeAnaMain {
	private static Logger logger = Logger.getRootLogger();
	public static String proPath = "D:\\cWsFile\\projectLib\\fop";

	public static void main(String[] args) {
		long startT = System.currentTimeMillis();
		// wrtSys();

		// rltFlt();
		// ui();
		// wrtRisk();
		// wrtDb();
		// G.reset();
		wrtLib();
		long runtime = (System.currentTimeMillis() - startT) / 1000;
		logger.info("all runtime:" + runtime);
	}

	private static void wrtSys() {
		SysInfo sysInfo = SysInfoLoader.loadSysInfo(CodeAnaMain.proPath);
		new SysPrinter(sysInfo).printAll();
	}

	private static void wrtLib() {
		SysInfo sysInfo = SysInfoLoader.loadSysInfo(CodeAnaMain.proPath);
		for (String jar : sysInfo.getAllJar()) {
			System.out.println(jar);
		}
	}

	private static void rltFlt() {
		SysInfo sysInfo = SysInfoLoader.loadSysInfo(CodeAnaMain.proPath);
		new RltTester().wrtAllRlt(sysInfo, "d:/j2j/sparkRlt.txt");
		// new RltTester().writeJ2j(sysInfo);

	}

	private static void wrtDb() {
		SysInfo sysInfo = SysInfoLoader.loadSysInfo(CodeAnaMain.proPath);
		new SqlWriter(sysInfo).wrtSysInfo();
	}

	private static void wrtRisk() {
		SysInfo sysInfo = SysInfoLoader.loadSysInfo(CodeAnaMain.proPath);
		new RiskWriter().write(sysInfo);
	}

	private static void ui() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// …Ë÷√Õ‚π€
					UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
					Screen.i().show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
