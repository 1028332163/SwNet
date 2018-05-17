package neu.lab.swnet.risk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import neu.lab.swnet.SysConf;
import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.core.SysUtil;

public class AcsLmCnter extends RiskCounter {
	public AcsLmCnter(SysInfo sysInfo) {
		super(sysInfo);
	}

	@Override
	public void initType() {
		type = "acs_lm";
	}

	@Override
	public void calRisk() throws IOException {
		for (String hostM : sysInfo.getHostMthds()) {
			Set<String> acsMthds = (Set<String>) sysInfo.getBook(hostM).getRecords();
			Set<String> acsLibMs = new HashSet<String>();
			for (String acsMthd : acsMthds) {
				if (sysInfo.isLibMthd(acsMthd))
					acsLibMs.add(acsMthd);
			}

			nums.add("" + acsLibMs.size());
			riskGraphData.addNum(hostM, acsLibMs.size());
			if (acsLibMs.size() > SysConf.ACS_LM_T) {
				riskMthds.add(hostM);
				wrtEachM(hostM, acsLibMs);
			}
		}
	}

	private void wrtEachM(String hostM, Set<String> acsLibMs) throws IOException {
//		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(getWrtFile(dirPath, hostM))));
//		printer.println(hostM);
//		this.wrtHostCall(printer, hostM);
//		printer.println(acsLibMs.size() + " access lib-method");
//		for (String libOut : acsLibMs) {
//			printer.println(libOut);
//		}
//		printer.close();
	}

}
