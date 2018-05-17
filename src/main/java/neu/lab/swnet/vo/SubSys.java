package neu.lab.swnet.vo;

import java.util.HashSet;
import java.util.Set;

import neu.lab.swnet.SysCons;
import neu.lab.swnet.core.SysInfo;

/**
 * @author asus
 *
 */
public class SubSys {

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

	
	/**
	 * 返回的subSys的node=innerclass+outJar;以及class与jar包之间的关系。
	 * 
	 */
	public static SubSys getInnerCls2Lib(SysInfo sysInfo) {
		SubSys subSys = new SubSys();
		for (Relation rlt : sysInfo.getAllClsRlt()) {
			ClassVO srcCls = sysInfo.getCls(rlt.getSrc());
			ClassVO tgtCls = sysInfo.getCls(rlt.getTgt());
			if (null != srcCls && null != tgtCls) {
				String srcJar = srcCls.getJar();
				if (SysCons.MY_JAR_NAME.equals(srcJar)) {// 和内部节点相关
					subSys.addNode(rlt.getSrc());
					String tgtJar = tgtCls.getJar();
					if (SysCons.MY_JAR_NAME.equals(tgtJar)) {// 内部方法关系
						subSys.addNode(rlt.getTgt());
						subSys.addEdge(rlt);
					} else {// 外部方法关系
						if (null != tgtJar) {
							subSys.addNode(tgtJar);
							subSys.addEdge(srcCls.getClsSig(), tgtJar);
						}
					}
				}
			}
		}
		return subSys;
	}

	/**
	 * 获得某个jar包的子系统，子系统中的节点和节点关系存在于jar包中
	 * 
	 * @return
	 */
	public static SubSys getJarSys(SysInfo sysInfo,String jarName) {
		SubSys subSys = new SubSys();
		for (String mthdSig : sysInfo.getAllMthd()) {
			MethodVO mthd = sysInfo.getMthd(mthdSig);
			if (mthd.getJar().equals(jarName)) {
				subSys.addNode(mthdSig);
			}
		}
		for (Relation rlt : sysInfo.getAllMthdRlt()) {
			String srcJar = sysInfo.getMthd(rlt.getSrc()).getJar();
			String tgtJar = sysInfo.getMthd(rlt.getTgt()).getJar();
			if (srcJar.equals(jarName) && tgtJar.equals(jarName)) {
				subSys.addEdge(rlt.getSrc(), rlt.getTgt());
			}
		}
		return subSys;
	}
}
