package neu.lab.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import neu.lab.SysConf;
import neu.lab.vo.MethodVO;

/**
 * method-relation-maintain 系统中有四个地方会涉及到方法之间的关系 1.MethodVO的outMthds
 * 2.MethodVO的inMthds 3.SysInfo中的m2ms 4.系统中的c2cs
 * 在执行完soot的程序后call-graph会被记录到outMthds中，需要根据outMthds中的信息将剩余三种的信息填补完成
 * 
 * @author asus
 *
 */
public class MthdRltMt {
	public static Set<String> filMs = new HashSet<String>();

	public void mtMthdRlt() {
		if (SysConf.FLT_M_Rlt) {
			filt();
		}
		Set<String> allMthd = SysInfo.getAllMthds();
		for (String srcSig : allMthd) {
			MethodVO mthdVO = SysInfo.getMthd(srcSig);
			Set<String> outMthds = mthdVO.getOutMthds();
			if (null != outMthds) {
				for (String tgtSig : outMthds) {
					MethodVO tgtVO = SysInfo.getMthd(tgtSig);
					tgtVO.addInMthds(srcSig);// inMthds
					SysInfo.addM2m(srcSig, tgtSig);// m2ms
					SysInfo.addC2c(mthdVO.getCls(), tgtVO.getCls());// c2cs
				}
			}
		}
	}

	private void filt() {
		Set<String> allMthd = SysInfo.getAllMthds();
		for (String srcSig : allMthd) {
			MethodVO mthdVO = SysInfo.getMthd(srcSig);
			mthdVO.filOutM();
		}
	}
}
