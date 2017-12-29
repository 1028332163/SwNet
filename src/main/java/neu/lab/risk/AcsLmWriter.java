package neu.lab.risk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import neu.lab.SysConf;
import neu.lab.core.SysInfo;
import neu.lab.core.SysUtil;

public class AcsLmWriter extends RiskWriter {
	@Override
	public void initType() {
		type = "acs_lm";
	}

	@Override
	public void calRisk() throws IOException {
		for (String hostM : SysInfo.hostMthds) {
			Set<String> acsMthds = (Set<String>) SysInfo.books.get(hostM).getRecords();
			Set<String> acsLibMs = new HashSet<String>();
			for (String acsMthd : acsMthds) {
				if (SysUtil.isLibMthd(acsMthd))
					acsLibMs.add(acsMthd);
			}

			nums.add("" + acsLibMs.size());
			if (acsLibMs.size() > SysConf.ACS_LM_T) {
				riskMthds.add(hostM);
				wrtEachM(hostM, acsLibMs);
			}
		}
	}

	private void wrtEachM(String hostM, Set<String> acsLibMs) throws IOException {
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(getWrtFile(dirPath, hostM))));
		printer.println(hostM);
		this.wrtHostCall(printer, hostM);
		printer.println(acsLibMs.size() + " access lib-method");
		for (String libOut : acsLibMs) {
			printer.println(libOut);
		}
		printer.close();
	}

}
