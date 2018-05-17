package neu.lab.swnet.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import neu.lab.swnet.CodeAnaMain;
import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.core.SysInfoLoader;
import neu.lab.swnet.view.tab.chart.ChartTab;
import neu.lab.swnet.view.tab.cls.ClsTab;
import neu.lab.swnet.view.tab.mthd.MthdPanel;
import neu.lab.swnet.view.tab.sta.JarStaTab;
import prefuse.util.ui.JFastLabel;

public class Screen {
	private static Logger logger = Logger.getRootLogger();
	private static Screen instance = new Screen();
	public SysInfo sysInfo;

	public static Screen i() {
		return instance;
	}

	private JFrame frame;
	private JPanel panel;
	private JMenuBar menuBar;

	JTabbedPane tabPane;

	ClsTab clsPanel;// 类为节点的tab页
	MthdPanel mthdPanel;// 方法为节点的tab页
	JarStaTab jarStaPanel;// jar包的统计信息
	ChartTab scatterPanel;

	private JFastLabel jLabel;// 下方用于显示Label
	private Box box;

	private Screen() {
		initJFrame();
	}

	public void show() {
		refreshTab();
		frame.pack();
		frame.setVisible(true); // show the window
	}

	private void refreshTab() {
		Screen.i().sysInfo = SysInfoLoader.loadSysInfo(CodeAnaMain.proPath);
		Screen.i().setTabPanel();
		Screen.i().updateLabel("程序加载完成!");
	}

	public void initJFrame() {
		frame = new JFrame("Code Analysis");
		ImageIcon icon = new ImageIcon("src\\main\\resources\\icon.png");
		frame.setIconImage(icon.getImage());

		initMenu();
		frame.setJMenuBar(menuBar);

		frame.setPreferredSize(new Dimension(ViewCons.FRAME_W, ViewCons.FRAME_H));
		frame.setLocation(ViewCons.FRAME_X, ViewCons.FRAME_Y);// 设定窗口出现位置
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initJPanel();
		frame.setContentPane(panel);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //最大化
	}

	public void initMenu() {
		JMenu menu = new JMenu("File"); // 创建JMenu菜单对象
		JMenuItem t1 = new JMenuItem("选择文件夹"); // 菜单项
		t1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();// 文件选择器
				jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
				jfc.setCurrentDirectory(new File("D:\\cWsFile\\projectLib\\"));
				int returnVal = jfc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = jfc.getSelectedFile();// f为选择到的目录
					logger.debug("selected dir:" + f.getAbsolutePath());
					// ProBarDialog dialog = new ProBarDialog(frame);
					updateLabel("程序加载中...");
				}
			}
		});
		menu.add(t1); // 将菜单项目添加到菜单
		menuBar = new JMenuBar(); // 创建菜单工具栏
		menuBar.add(menu); // 将菜单增加到菜单工具栏
	}

	public void initJPanel() {
		panel = new JPanel(new BorderLayout());

		initBox();
		panel.add(box, BorderLayout.SOUTH);
	}

	public void setTabPanel() {
		this.tabPane = new JTabbedPane();

		clsPanel = new ClsTab();
		tabPane.addTab("cls", null, clsPanel, null);

		// mthdPanel = new MthdPanel(this);
		// tabPane.addTab("mthd", mthdPanel);

		jarStaPanel = new JarStaTab();
		tabPane.addTab("jarSta", jarStaPanel);

		scatterPanel = new ChartTab();
		tabPane.addTab("scatter", scatterPanel);

		panel.add(tabPane, BorderLayout.CENTER);
		frame.pack();
	}

	public void initJLabel() {
		jLabel = new JFastLabel("请选择被加载的程序!");
		jLabel.setPreferredSize(new Dimension(ViewCons.JLABEL_W, ViewCons.JLABEL_H));
		jLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		jLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));

		jLabel.setFont(panel.getFont().deriveFont((float) 16.0));
	}

	public void initBox() {
		box = new Box(BoxLayout.X_AXIS);
		box.add(Box.createHorizontalStrut(10));
		initJLabel();
		box.add(jLabel);
		box.add(Box.createHorizontalGlue());
	}

	public void updateLabel(String label) {
		jLabel.setText(label);
	}

	public void updateMthdTab(String mthdName) {
		mthdPanel.showMthdDisplay(mthdName);
	}

}
