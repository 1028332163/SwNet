package neu.lab.vo;

import java.util.HashSet;
import java.util.Set;

import neu.lab.core.SysInfo;

public class SubSys {
	/**
	 * ���ĳ��jar������ϵͳ����ϵͳ�еĽڵ�ͽڵ��ϵ������jar����
	 * 
	 * @return
	 */
	public static SubSys getJarSys(String jarName) {
		SubSys subSys = new SubSys();
		for (String mthdSig : SysInfo.getAllMthds()) {
			MethodVO mthd = SysInfo.getMthd(mthdSig);
			if (mthd.getJar().equals(jarName)) {
				subSys.addNode(mthdSig);
			}
		}
		for (Relation rlt : SysInfo.getAllMthdRlts()) {
			String srcJar = SysInfo.getMthd(rlt.getSrc()).getJar();
			String tgtJar = SysInfo.getMthd(rlt.getTgt()).getJar();
			if (srcJar.equals(jarName) && tgtJar.equals(jarName)) {
				subSys.addEdge(rlt.getSrc(), rlt.getTgt());
			}
		}
		return subSys;
	}
	public Set<String> nodes = new HashSet<String>();
	public Set<Relation> edges = new HashSet<Relation>();

	public boolean addNode(String node) {
		return nodes.add(node);
	}

	public boolean addEdge(Relation rlt) {
		return edges.add(rlt);
	}

	public boolean addEdge(String src, String tgt) {
		return edges.add(new Relation(src, tgt));
	}
}
