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

	ClsTab clsPanel;// ��Ϊ�ڵ��tabҳ
	MthdPanel mthdPanel;// ����Ϊ�ڵ��tabҳ
	JarStaTab jarStaPanel;// jar����ͳ����Ϣ
	ChartTab scatterPanel;

	private JFastLabel jLabel;// �·�������ʾLabel
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
		Screen.i().updateLabel("����������!");
	}

	public void initJFrame() {
		frame = new JFrame("Code Analysis");
		ImageIcon icon = new ImageIcon("src\\main\\resources\\icon.png");
		frame.setIconImage(icon.getImage());

		initMenu();
		frame.setJMenuBar(menuBar);

		frame.setPreferredSize(new Dimension(ViewCons.FRAME_W, ViewCons.FRAME_H));
		frame.setLocation(ViewCons.FRAME_X, ViewCons.FRAME_Y);// �趨���ڳ���λ��
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initJPanel();
		frame.setContentPane(panel);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //���
	}

	public void initMenu() {
		JMenu menu = new JMenu("File"); // ����JMenu�˵�����
		JMenuItem t1 = new JMenuItem("ѡ���ļ���"); // �˵���
		t1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();// �ļ�ѡ����
				jfc.setFileSelectionMode(1);// �趨ֻ��ѡ���ļ���
				jfc.setCurrentDirectory(new File("D:\\cWsFile\\projectLib\\"));
				int returnVal = jfc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = jfc.getSelectedFile();// fΪѡ�񵽵�Ŀ¼
					logger.debug("selected dir:" + f.getAbsolutePath());
					// ProBarDialog dialog = new ProBarDialog(frame);
					updateLabel("���������...");
				}
			}
		});
		menu.add(t1); // ���˵���Ŀ��ӵ��˵�
		menuBar = new JMenuBar(); // �����˵�������
		menuBar.add(menu); // ���˵����ӵ��˵�������
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
		jLabel = new JFastLabel("��ѡ�񱻼��صĳ���!");
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
