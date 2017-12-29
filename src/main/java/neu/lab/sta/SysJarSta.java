package neu.lab.sta;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import neu.lab.SysCons;
import neu.lab.core.SysInfo;
import neu.lab.core.SysUtil;
import neu.lab.vo.ClassVO;
import neu.lab.vo.MethodVO;
import neu.lab.vo.Relation;

public class SysJarSta {
	public static SysJarSta instance = new SysJarSta();

	public HostSta hostSta;
	public Map<String, LibSta> jarStas;

	/**
	 * @return host class Number
	 */
	public int hClsNum() {
		return hostSta.getClsCnt();
	}

	/**
	 * @return host method Number
	 */
	public int hMthdNum() {
		return hostSta.getMthdCnt();
	}

	public static SysJarSta i() {
		return instance;
	}

	private SysJarSta() {
		jarStas = new HashMap<String, LibSta>();
		hostSta = new HostSta();
		Set<String> allJar = SysInfo.getAllJar();
		for (String jarSig : allJar) {
			if (!SysCons.MY_JAR_NAME.equals(jarSig))
				jarStas.put(jarSig, new LibSta(jarSig));
		}
		count();
	}

	public HostSta getHostSta() {
		return this.hostSta;
	}

	public void count() {
		countMthd();
		countCls();

		countDir();
		countAcs();

		countPassInfo();

		// print();
	}

	private void countPassInfo() {
		for (Entry<String, LibSta> entry : this.jarStas.entrySet()) {
			if (!SysCons.MY_JAR_NAME.equals(entry.getKey()))
				entry.getValue().calPassInfo();
		}
	}

	private void countMthd() {// 计算每个lib中的方法的个数
		Set<String> allMthds = SysInfo.getAllMthds();
		for (String mthd : allMthds) {
			MethodVO mthdVO = SysInfo.getMthd(mthd);
			String jarSig = mthdVO.getJar();
			if (SysCons.MY_JAR_NAME.equals(jarSig)) {
				hostSta.incMthdCnt();
			} else {
				jarStas.get(jarSig).incMthdCnt();
			}

		}
	}

	private void countCls() {// 计算每个lib中的方法的个数
		Set<String> allClses = SysInfo.getAllClses();
		for (String clsSig : allClses) {
			ClassVO clsVO = SysInfo.getCls(clsSig);
			String jarSig = clsVO.getJar();
			if (SysCons.MY_JAR_NAME.equals(jarSig)) {
				hostSta.incClsCnt();
			} else {
				jarStas.get(clsVO.getJar()).incClsCnt();
			}
		}
	}

	private void countDir() {// 直接访问信息
		Set<Relation> allRlt = SysInfo.getAllMthdRlts();
		for (Relation rlt : allRlt) {
			String srcSig = rlt.getSrc();
			String tgtSig = rlt.getTgt();
			if (SysUtil.isHostMthd(srcSig) && !SysUtil.isHostMthd(tgtSig)) {// 一条由host指向第三方的边
				LibSta jarSta = this.jarStas.get(SysInfo.getMthd(tgtSig).getJar());
				jarSta.addDirInfo(srcSig, tgtSig);
				hostSta.addDirInfo(srcSig, tgtSig);
			}
		}

	}

	private void countAcs() {
		for (String cHostM : SysInfo.books.keySet()) {
			if (SysUtil.isHostMthd(cHostM)) {
				Set<String> records = (Set<String>) SysInfo.books.get(cHostM).getRecords();
				for (String cJarM : records) {
					if (!SysUtil.isHostMthd(cJarM)) {
						LibSta jarSta = this.jarStas.get(SysInfo.getMthd(cJarM).getJar());
						jarSta.addAcsInfo(cHostM, cJarM);
						hostSta.addAcsInfo(cHostM, cJarM);
					}
				}
			}
		}
	}

	private void print() {
		for (String jar : this.jarStas.keySet()) {
			this.jarStas.get(jar).print();
		}
	}

}
