package neu.lab.swnet.view.shop;

import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.vo.MethodVO;

public class MthdGShop extends GraphShop{
	public MthdGShop(SysInfo sysInfo) {
		super(sysInfo);
	}

	@Override
	protected void addNdRow(String sig) {
		MethodVO mthdVO = sysInfo.getMthd(sig);
		if(null!=mthdVO) {
			nodes.addRow();
			nodes.set(ndIdCnt, "id", ndIdCnt);
			nodes.set(ndIdCnt, "sig", mthdVO.getMethodSig());
			String jarSig =  mthdVO.getJar();
			nodes.set(ndIdCnt, "jarSig", jarSig);
			
			this.addNdMap(sig, Integer.valueOf(ndIdCnt),jarSig);
			ndIdCnt++;
		}
	}

}
