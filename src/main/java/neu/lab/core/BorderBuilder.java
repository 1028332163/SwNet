package neu.lab.core;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Set;

import neu.lab.SysCons;
import neu.lab.util.Detective;
import neu.lab.util.NameManager;
import neu.lab.vo.JarVO;
import neu.lab.vo.PackageVO;

/**
 * ͨ��jar������ϵͳ�ı߽�
 * 
 * @author asus
 *
 */
public class BorderBuilder {
	private static Logger logger = Logger.getRootLogger(); 
	public static void genePck(String srcPath, String binPath) {
		// ����Դ������Ŀ
		JarVO myJar = new JarVO(SysCons.MY_JAR_NAME, "");
		SysInfo.addJar(myJar.getJarSig(), myJar);
		Set<String> srcClses = Detective.findDirCls(new File(srcPath), ".java");
		for (String clsPath : srcClses) {

			String pckName = NameManager.path2pck(clsPath);
			String pckSig = PackageVO.getSig(SysCons.MY_JAR_NAME, pckName);
			String clsSig = pckName + "." + NameManager.path2cls(clsPath);

			SysInfo.addPck(pckSig);
			RltBinder.cls2pck(clsSig, pckSig);
			RltBinder.pck2jar(pckSig, myJar);
		}
		// ����jar����Ŀ
		Set<JarVO> jars = Detective.findJarPath(new File(binPath));// �õ����е�jar��
		for (JarVO jarVO : jars) {
			SysInfo.addJar(jarVO.getJarSig(), jarVO);
			Set<String> pckClses = Detective.findJarCls(jarVO.getJarPath());
			for (String clsPath : pckClses) {

				String pckName = NameManager.entry2pck(clsPath);
				String pckSig = PackageVO.getSig(jarVO.getJarSig(), pckName);
				String clsSig = pckName + "." + NameManager.entry2cls(clsPath);

				SysInfo.addPck(pckSig);

				RltBinder.cls2pck(clsSig, pckSig);
				RltBinder.pck2jar(pckSig, jarVO);
			}
		}
		// ����src�����jar���ص��İ�
		BorderBuilder.meltPck();
	}

	/**
	 * src�е�package��jar�ļ��е�package����ָ�����ͬһ������Ӧ�ý��кϲ� �ж�����packageΪͬһ����˼·��
	 * 1.packageName��ͬ 2.���ص���Class
	 */
	private static void meltPck() {
		for (String jarSig : SysInfo.getAllJar()) {
			if (!SysCons.MY_JAR_NAME.equals(jarSig)) {
				JarVO jarVO = SysInfo.getJar(jarSig);
				Iterator<String> ite = jarVO.getPcks().iterator();
				while (ite.hasNext()) {
					String libPckSig = ite.next();
					PackageVO libPckVO = SysInfo.getPck(libPckSig);
					String inPckSig = PackageVO.getSig(SysCons.MY_JAR_NAME, libPckVO.getPckName());
					PackageVO inPck = SysInfo.getPck(inPckSig);
					if (inPck == null) {// srcԴ����û����ͬ����
						continue;
					} else {
						Set<String> inClses = inPck.getClses();
						Set<String> libClses = libPckVO.getClses();
						boolean isSame = false;
						for (String cls : inClses) {
							if (libClses.contains(cls)) {// �����ĵ������в������src�е��࣬����ֻҪ��һ���ص��Ϳ�����Ϊһ��
								isSame = true;
								break;
							}
						}
						if (isSame) {
							// ��libPck����Ϣ���Ƶ�inPck
							for (String clsSig : libClses) {
								RltBinder.cls2pck(clsSig, inPckSig);
							}
							// ��jarVO�Ƴ�pck
							ite.remove();
							SysInfo.delPck(libPckSig);
						}
					}
				}
			}
		}
		delEmptyJar();
		// checkJarPck();
	}

	/**
	 * ɾ��melt֮�󲻰����κ�pck��jar����ϵͳ��ɾ��
	 */
	private static void delEmptyJar() {
		Iterator<Entry<String, JarVO>> jarIte = SysInfo.jatIte();
		while (jarIte.hasNext()) {
			Entry<String, JarVO> entry = jarIte.next();
			JarVO jarVO = entry.getValue();
			if (jarVO.getPcks().isEmpty() || jarVO.getClses().isEmpty()) {
				jarIte.remove();
			}
		}
	}

	/**
	 * ����һ��jar�ļ��е�����package�������������
	 * 1.����package��������Դ������-����jar�ļ���ȫ��Դ�������ɣ�Ӧ�ý���SysInfo.jarTb���Ƴ���jar��
	 * 2.����package��δ������Դ������-����jar�ļ���һ����׼�ĵ�����,����Ҫ�������⴦��
	 * 3.jar�ļ��е�package������һ���ֳ�����Դ������-��������
	 */
	private static void checkJarPck() {
		for (String jarSig : SysInfo.getAllJar()) {
			JarVO jarVO = SysInfo.getJar(jarSig);
			Set<String> srcPck = new HashSet<String>();
			Set<String> libPck = new HashSet<String>();
			for (String pckSig : jarVO.getPcks()) {
				PackageVO pckVO = SysInfo.getPck(pckSig);
				if (pckVO == null) {
					logger.warn(jarSig + ":no my pck " + pckSig);
				} else {
					if (SysCons.MY_JAR_NAME.equals(pckVO.getJarSig()))
						srcPck.add(pckSig);
					else
						libPck.add(pckSig);
				}
			}
			logger.debug("============================" + jarSig);
			if (srcPck.isEmpty() && libPck.isEmpty()) {// ��jar
				logger.debug(" is emptyJar\n");
			} else if (!srcPck.isEmpty() && !libPck.isEmpty()) {// ���jar
				logger.debug(" is mixJar\n");
				logger.debug("++++++srcPck");
				for (String src : srcPck) {
					logger.debug(src + "+++++++");
				}
				logger.debug("\n");
				logger.debug("++++++libPck");
				for (String lib : libPck) {
					logger.debug(lib + "+++++++");
				}
				logger.debug("\n");
			} else {
				if (srcPck.isEmpty()) {
					logger.debug(" is libJar\n");
				}
				if (libPck.isEmpty()) {
					logger.debug(" is srcJar\n");
				}
			}

		}
	}

}
