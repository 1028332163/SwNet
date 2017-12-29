package neu.lab.risk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import neu.lab.SysConf;
import neu.lab.SysCons;
import neu.lab.core.SysInfo;
import neu.lab.vo.MethodVO;

/**
 * method call per-lib writer：根据host method 对每一个第三方依赖的函数的个数
 * 
 * @author asus
 *
 */
public class McPlWriter extends RiskWriter {

	@Override
	protected void initType() {
		type = "mc_pl";
	}

	@Override
	protected void calRisk() throws IOException {
		for (String hostM : SysInfo.hostMthds) {
			Set<String> outMthds = SysInfo.getMthd(hostM).getOutMthds();
			Map<String, Set<String>> lib2Mthds = new HashMap<String, Set<String>>();
			if (null != outMthds) {// 存在外部引用
				for (String outMthd : outMthds) {// 遍历hostMthd的调用方法
					MethodVO methodVO = SysInfo.getMthd(outMthd);
					String jarName = methodVO.getJar();
					if (!SysCons.MY_JAR_NAME.equals(jarName)) {
						// 引用了外部方法
						Set<String> libMthds = lib2Mthds.get(jarName);
						if (null == libMthds) {
							libMthds = new HashSet<String>();
						}
						libMthds.add(outMthd);
						lib2Mthds.put(jarName, libMthds);
					}
				}
			}
			wrtEachM(hostM, lib2Mthds);
		}

	}

	public void wrtEachM(String hostM, Map<String, Set<String>> lib2Mthds) throws IOException {
		PrintWriter printer = null;
		// 对每个lib调用的方法的个数
		for (String lib : lib2Mthds.keySet()) {
			Set<String> libMthds = lib2Mthds.get(lib);
			int cgSize = libMthds.size();
			nums.add("" + cgSize);
			if (cgSize > SysConf.MC_PL_T) {// 超过阈值
				if (printer == null)
					printer = new PrintWriter(new BufferedWriter(new FileWriter(getWrtFile(dirPath, hostM))));
				riskMthds.add(hostM);
				printer.println(hostM);
				this.wrtHostCall(printer, hostM);
				printer.println(cgSize + " " + lib + ":");
				for (String libMthd : libMthds) {
					printer.println(libMthd);
				}
			}
		}
		if (null != printer)
			printer.close();
	}

}
