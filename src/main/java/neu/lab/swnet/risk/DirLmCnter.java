package neu.lab.swnet.risk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import neu.lab.swnet.SysConf;
import neu.lab.swnet.SysCons;
import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.core.SysUtil;
import neu.lab.swnet.vo.MethodVO;

/**
 * direct call lib_method：根据host_method直接调用的lib方法个数
 * 
 * @author asus
 *
 */
public class DirLmCnter extends RiskCounter {

	public DirLmCnter(SysInfo sysInfo) {
		super(sysInfo);
	}

	@Override
	protected void initType() {
		type = "dir_lm";
	}

	@Override
	protected void calRisk() throws IOException {
		for (String hostM : sysInfo.getHostMthds()) {
			Set<String> outMthds = sysInfo.getMthd(hostM).getOutMthds();
			Set<String> libOuts = new HashSet<String>();
			// 统计调用外部方法的个数
			if (null != outMthds) {
				for (String outMthd : outMthds) {
					if (sysInfo.isLibMthd(outMthd)) {
						libOuts.add(outMthd);
					}
				}
			}

			nums.add("" + libOuts.size());
			riskGraphData.addNum(hostM, libOuts.size());
			if (libOuts.size() > SysConf.DIR_LM_T) {
				riskMthds.add(hostM);
				wrtEachM(hostM, libOuts);
			}
		}
	}

	private void wrtEachM(String hostM, Set<String> libOuts) throws IOException {
//		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(getWrtFile(dirPath, hostM))));
//		printer.println(hostM);
//		this.wrtHostCall(printer, hostM);
//		printer.println(libOuts.size() + " direct lib-method");
//		for (String libOut : libOuts) {
//			printer.println(libOut);
//		}
//		printer.close();
	}

}
