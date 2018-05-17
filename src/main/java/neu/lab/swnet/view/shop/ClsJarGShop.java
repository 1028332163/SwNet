package neu.lab.swnet.view.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.vo.ClassVO;
import neu.lab.swnet.vo.JarVO;
import neu.lab.swnet.vo.MethodVO;
import neu.lab.swnet.vo.Relation;
import prefuse.data.Graph;
import prefuse.data.Table;

public class ClsJarGShop extends GraphShop {
	public ClsJarGShop(SysInfo sysInfo) {
		super(sysInfo);
	}

	@Override
	protected void addNdRow(String sig) {
		ClassVO clsVO = sysInfo.getCls(sig);
		if (null != clsVO) {//类节点
			nodes.addRow();
			nodes.set(ndIdCnt, "id", ndIdCnt);
			nodes.set(ndIdCnt, "sig", clsVO.getClsSig());
			String jarSig = clsVO.getJar();
			nodes.set(ndIdCnt, "jarSig", jarSig);

			this.addNdMap(sig, Integer.valueOf(ndIdCnt), jarSig);
			ndIdCnt++;
		} else {
			JarVO jarVO = sysInfo.getJar(sig);
			if (null != jarVO) {//jar节点
				nodes.addRow();
				nodes.set(ndIdCnt, "id", ndIdCnt);
				nodes.set(ndIdCnt, "sig", jarVO.getJarSig());
				String jarSig = jarVO.getJarSig();
				nodes.set(ndIdCnt, "jarSig", jarSig);

				this.addNdMap(sig, Integer.valueOf(ndIdCnt),jarSig);
				ndIdCnt++;
			}
		}
	}
}
