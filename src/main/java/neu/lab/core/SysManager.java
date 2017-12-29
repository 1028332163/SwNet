package neu.lab.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import neu.lab.SysCons;
import neu.lab.vo.ClassVO;
import neu.lab.vo.MethodVO;
import neu.lab.vo.Relation;

/**
 * @author asus ���ڹ���sysInfo�������ṩ����sysInfo�е���Ϣ������֯��
 */
public class SysManager {

	public static void geneClsJarRlts() {
		for (Relation rlt : SysInfo.c2cs) {
			ClassVO srcCls = SysInfo.getCls(rlt.getSrc());
			ClassVO tgtCls = SysInfo.getCls(rlt.getTgt());
			if (null != srcCls && null != tgtCls) {
				String srcJar = srcCls.getJar();
				if (SysCons.MY_JAR_NAME.equals(srcJar)) {// ���ڲ��ڵ����
					SysInfo.addC2jNode(rlt.getSrc());
					String tgtJar = tgtCls.getJar();
					if (SysCons.MY_JAR_NAME.equals(tgtJar)) {// �ڲ�������ϵ
						SysInfo.addC2jNode(rlt.getTgt());
						SysInfo.addC2jEdge(rlt);
					} else {// �ⲿ������ϵ
						if (null != tgtJar) {
							SysInfo.addC2jNode(tgtJar);
							SysInfo.addC2jEdge(srcCls.getClsSig(), tgtJar);
						}
					}
				}
			}
		}
	}

	public static Set<String> getInnerMthds() {// ϵͳ�ڲ��ķ���
		Set<String> result = new HashSet<String>();
		for (String mthdSig : SysInfo.getAllMthds()) {
			MethodVO mthdVO = SysInfo.getMthd(mthdSig);
			if (null != mthdVO) {
				String jarSig = mthdVO.getJar();
				if (SysCons.MY_JAR_NAME.equals(jarSig)) {
					result.add(mthdSig);
				}
			}
		}
		return result;
	}

	public static Set<String> getOutBndMthds(Set<Relation> rlts) {// ϵͳ�ڲ������ⲿjar���ķ���
		Set<String> result = new HashSet<String>();
		for (Relation rlt : rlts) {
			MethodVO src = SysInfo.getMthd(rlt.getSrc());
			MethodVO tgt = SysInfo.getMthd(rlt.getTgt());
			if (null != src && null != tgt) {
				if (SysCons.MY_JAR_NAME.equals(src.getJar()) && !SysCons.MY_JAR_NAME.equals(tgt.getJar())) {
					result.add(rlt.getSrc());
				}
			}
		}
		return result;
	}

	// private static Set<String> getChildren(String) {
	//
	// }
}
