package neu.lab.debug.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import neu.lab.core.SysInfo;
import neu.lab.vo.MethodVO;

/**
 * 打印出outMthd中有重名的mthd，以便于测试出一种良好的过滤方式使得既可以删除杂质关系，又可以保留正常的多态信息
 * 
 * @author asus
 *
 */
public class DualPrinter {
	private static Logger logger = Logger.getLogger("dualCall");
	public void printDual() {
		for (String mthd : SysInfo.getAllMthds()) {
			StringBuilder sb = new StringBuilder("");
			MethodVO mthdVO = SysInfo.getMthd(mthd);
			Map<String, Integer> nameCnts = mthdVO.calNameCnt();
			for (String name : nameCnts.keySet()) {
				if (nameCnts.get(name) > 1) {
					for (String outMthd : mthdVO.getOutMthds()) {
						if (outMthd.contains(name)) {
							sb.append(outMthd + "\n");
						}
					}
					sb.append("\n");
				}
			}
			String result = sb.toString();
			if (!"".equals(result)) {
				logger.debug("=================" + mthd);
				logger.debug(result);
			}
		}

	}
}
