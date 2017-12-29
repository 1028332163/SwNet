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
 * ��ӡ��outMthd����������mthd���Ա��ڲ��Գ�һ�����õĹ��˷�ʽʹ�üȿ���ɾ�����ʹ�ϵ���ֿ��Ա��������Ķ�̬��Ϣ
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
