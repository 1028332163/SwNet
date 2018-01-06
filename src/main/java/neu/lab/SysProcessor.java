package neu.lab;

import java.util.List;

import org.apache.log4j.Logger;

import neu.lab.debug.test.DualPrinter;
import neu.lab.dog.MthdDog;
import neu.lab.risk.WriterChain;
import neu.lab.core.BorderBuilder;
import neu.lab.core.MthdRltMt;
import neu.lab.core.SysInfo;
import neu.lab.core.SysManager;
import neu.lab.core.SysPrinter;
import neu.lab.util.ArgUtil;
import neu.lab.view.Screen;
import soot.PackManager;
import soot.Transform;

public class SysProcessor implements Runnable {
	private static Logger logger = Logger.getRootLogger();
	String path;

	public SysProcessor(String path) {
		this.path = path;
	}

	@Override
	public void run() {
		processSys();
		Screen.i().setTabPanel();
		Screen.i().updateLabel("����������!");
	}

	/**
	 * ����ϵͳ���ṩ��ͼ��Ҫ������
	 * 
	 * @param path
	 */
	public void processSys() {
		Long startTime = System.currentTimeMillis();
		String srcPath = path + "\\src";
		String binPath = path + "\\bin";

		List<String> argsList = ArgUtil.getArgs(binPath);

		PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new PtTransformer()));

		String[] args = argsList.toArray(new String[0]);
		soot.Main.main(args);
		BorderBuilder.genePck(srcPath, binPath);// ����ϵͳ�ı߽�

		new MthdRltMt().mtMthdRlt();// �Է����ĵ��ù�ϵ����ά��
		new DualPrinter().printDual();

		SysManager.geneClsJarRlts();// �������jar�Ĺ�ϵ

		SysInfo.hostMthds = SysManager.getInnerMthds();

		SysInfo.books = new MthdDog(SysInfo.hostMthds).findRlt();// ����host�����Ŀɴ﷽��
		new WriterChain().invokeAllWrt();

		SysPrinter.printSys();
		logger.info("system runTime:" + ((System.currentTimeMillis() - startTime) / 1000));
	}


}
