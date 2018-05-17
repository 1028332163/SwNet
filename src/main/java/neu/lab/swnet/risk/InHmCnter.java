package neu.lab.swnet.risk;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import neu.lab.swnet.SysConf;
import neu.lab.swnet.SysCons;
import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.core.SysUtil;
import neu.lab.swnet.dog.book.Book;
import neu.lab.swnet.vo.MethodVO;

/**
 * In Host Method Writer:根据lib Method被多少个Host Method调用
 * 
 * @author asus
 *
 */
public class InHmCnter extends RiskCounter {
	public InHmCnter(SysInfo sysInfo) {
		super(sysInfo);
		// TODO Auto-generated constructor stub
	}

	private static Logger logger = Logger.getRootLogger(); 
	@Override
	protected void initType() {
		type = "in_hm";
	}

	@Override
	protected void calRisk() throws IOException {
		for (String mthd : sysInfo.getAllMthd()) {
			MethodVO cLibM = sysInfo.getMthd(mthd);
			if (!SysCons.MY_JAR_NAME.equals(cLibM.getJar())) {// 外部方法
				Set<String> inMthds = cLibM.getInMthds();
				if (null == inMthds) {
					nums.add("" + 0);
				} else {
					Set<String> hostCalls = new HashSet<String>();
					for (String callMthd : inMthds) {
						if (sysInfo.isHostMthd(callMthd))
							hostCalls.add(callMthd);
					}
					nums.add("" + hostCalls.size());
					if (hostCalls.size() > SysConf.IN_HM_T) {
						String libMthdSig = cLibM.getMethodSig();
						riskMthds.add(libMthdSig);
						wrtEachM(libMthdSig, hostCalls);
					}
				}
			}
		}

	}

	private void wrtEachM(String libMthdSig, Set<String> hostCalls) throws IOException {
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(getWrtFile(dirPath, libMthdSig))));
		printer.println("called Method:" + libMthdSig);
		printer.println(hostCalls.size() + " host dir call:");
		for (String hostCall : hostCalls) {
			printer.println(hostCall);
		}
		printer.println();
		Book book = sysInfo.getBook(libMthdSig);

		if (null != book) {
			Set<String> acsMthds = (Set<String>) book.getRecords();
			StringBuilder sb = new StringBuilder("libMethod can reach:");
			int cnt = 0;
			String libJar = sysInfo.getMthd(libMthdSig).getJar();
			for (String acsM : acsMthds) {
				String acsJar = sysInfo.getMthd(acsM).getJar();
				if (libJar.equals(acsJar)) {
					cnt++;
					sb.append(acsM + "\n");
				}
			}
			sb.insert(0, cnt + " ");
			printer.println(sb.toString());
		} else {
			logger.warn("there no acs info:" + libMthdSig);
		}

		printer.close();
	}
}
