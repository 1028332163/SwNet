package neu.lab.view.shop;

import neu.lab.core.SysInfo;
import neu.lab.vo.ClassVO;

public class ClsGShop extends GraphShop{

	@Override
	protected void addNdRow(String sig) {
		ClassVO clsVO = SysInfo.getCls(sig);
		if(null!=clsVO) {
			nodes.addRow();
			nodes.set(ndIdCnt, "id", ndIdCnt);
			nodes.set(ndIdCnt, "sig", clsVO.getClsSig());
			String jarSig = clsVO.getJar();
			nodes.set(ndIdCnt, "jarSig", jarSig);
			
			this.addNdMap(sig, Integer.valueOf(ndIdCnt), jarSig);
			ndIdCnt++;
		}
		
	}
	
}
