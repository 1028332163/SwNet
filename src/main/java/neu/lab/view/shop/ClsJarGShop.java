package neu.lab.view.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import neu.lab.core.SysInfo;
import neu.lab.vo.ClassVO;
import neu.lab.vo.JarVO;
import neu.lab.vo.MethodVO;
import neu.lab.vo.Relation;
import prefuse.data.Graph;
import prefuse.data.Table;

public class ClsJarGShop extends GraphShop {
	@Override
	protected void addNdRow(String sig) {
		ClassVO clsVO = SysInfo.getCls(sig);
		if (null != clsVO) {//类节点
			nodes.addRow();
			nodes.set(ndIdCnt, "id", ndIdCnt);
			nodes.set(ndIdCnt, "sig", clsVO.getClsSig());
			String jarSig = clsVO.getJar();
			nodes.set(ndIdCnt, "jarSig", jarSig);

			this.addNdMap(sig, Integer.valueOf(ndIdCnt), jarSig);
			ndIdCnt++;
		} else {
			JarVO jarVO = SysInfo.getJar(sig);
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
