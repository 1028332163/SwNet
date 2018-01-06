package neu.lab.risk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import neu.lab.SysConf;
import neu.lab.SysCons;
import neu.lab.core.SysInfo;
import neu.lab.core.SysUtil;
import neu.lab.vo.MethodVO;

/**
 * direct call lib_method：根据host_method直接调用的lib方法个数
 * 
 * @author asus
 *
 */
public class DirLmWriter extends RiskWriter {

	@Override
	protected void initType() {
		type = "dir_lm";
	}

	@Override
	protected void calRisk() throws IOException {
		for (String hostM : SysInfo.hostMthds) {
			Set<String> outMthds = SysInfo.getMthd(hostM).getOutMthds();
			Set<String> libOuts = new HashSet<String>();
			// 统计调用外部方法的个数
			if (null != outMthds) {
				for (String outMthd : outMthds) {
					if (SysUtil.isLibMthd(outMthd)) {
						libOuts.add(outMthd);
					}
				}
			}

			nums.add("" + libOuts.size());
			graphData.addNum(hostM, libOuts.size());
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
