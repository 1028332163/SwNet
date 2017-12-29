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
 * method-relation-maintain ϵͳ�����ĸ��ط����漰������֮��Ĺ�ϵ 1.MethodVO��outMthds
 * 2.MethodVO��inMthds 3.SysInfo�е�m2ms 4.ϵͳ�е�c2cs
 * ��ִ����soot�ĳ����call-graph�ᱻ��¼��outMthds�У���Ҫ����outMthds�е���Ϣ��ʣ�����ֵ���Ϣ����
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
