package neu.lab.view.shop;

import neu.lab.core.SysInfo;
import neu.lab.vo.MethodVO;

public class MthdGShop extends GraphShop{
	@Override
	protected void addNdRow(String sig) {
		MethodVO mthdVO = SysInfo.getMthd(sig);
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
