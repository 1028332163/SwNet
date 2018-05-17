package neu.lab.swnet.view.shop;

import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.vo.ClassVO;

public class ClsGShop extends GraphShop{

	public ClsGShop(SysInfo sysInfo) {
		super(sysInfo);
	}

	@Override
	protected void addNdRow(String sig) {
		ClassVO clsVO = sysInfo.getCls(sig);
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
