package neu.lab.core;

import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import neu.lab.SysCons;
import neu.lab.vo.ClassVO;
import neu.lab.vo.FieldVO;
import neu.lab.vo.JarVO;
import neu.lab.vo.MethodVO;
import neu.lab.vo.PackageVO;
import neu.lab.vo.Relation;
import soot.Scene;
import soot.SootClass;

public class SysPrinter {
	private static Logger logger = Logger.getRootLogger(); 
	/**
	 * 打印系统的信息
	 */
	public static void printSys() {
		
		logger.info("pck size:" + SysInfo.pckTb.size() + ";cls Size:" + SysInfo.clsTb.size() + ";mth size"
				+ SysInfo.mthdTb.size() + ";rlt size:" + SysInfo.m2ms.size() + ";clsRltSize:" + SysInfo.c2cs.size());

//		printAll();
//		printInner();
	}

	public static void printInner() {

		JarVO jarVO = SysInfo.getJar(SysCons.MY_JAR_NAME);
		logger.debug("==================================jar:" + jarVO.getJarSig());
		Set<String> pcks = jarVO.getPcks();
		for (String pckSig : pcks) {
			printPck(pckSig);
		}

	}

	public static void printAll() {
		for (Entry<String, JarVO> entry : SysInfo.jarTb.entrySet()) {
			JarVO jarVO = entry.getValue();
			logger.debug("==================================jar:" + jarVO.getJarSig());
			Set<String> pcks = jarVO.getPcks();
			for (String pckSig : pcks) {
				printPck(pckSig);
			}
		}
	}

	public static void printPck(String pckSig) {
		logger.debug("=============================pck:" + pckSig);
		PackageVO pck = SysInfo.pckTb.get(pckSig);
		if (null != pck) {
			Set<String> clses = pck.getClses();
			for (String clsSig : clses) {
				printCls(clsSig);
			}
		} else {
			logger.warn("not found package:" + pckSig);
		}

	}

	public static void printCls(String clsSig) {
		logger.debug("++++++++++++++++class:" + clsSig);
		ClassVO clsVO = SysInfo.clsTb.get(clsSig);
		if (null != clsVO) {
			Set<String> flds = clsVO.getFields();
			for (String fldSig : flds) {
				printFld(fldSig);
			}
			Set<String> mthds = clsVO.getMethods();
			for (String mthdSig : mthds) {
				printMthd(mthdSig);
			}
		} else {
			logger.warn("not found class:" + clsSig);
		}

	}

	public static void printFld(String fldSig) {
		FieldVO fldVO = SysInfo.getFld(fldSig);
		if (null != fldVO) {
			logger.debug("---------f:" + fldVO.getFldSig() + "(" + fldVO.getType() + ")");
		} else {
			logger.warn("not found fld:" + fldSig);
		}
	}

	public static void printMthd(String mthdSig) {

		MethodVO mthdVO = SysInfo.getMthd(mthdSig);
		if (null != mthdVO) {
			logger.debug("---------m:" + mthdSig);
			Set<String> locTypes = mthdVO.getLocTypes();
			if (locTypes != null) {
				for (String loc : locTypes) {
					// logger.debug("------------------locType:" + loc);
				}
			}
			Set<String> outMthds = mthdVO.getOutMthds();
			if (outMthds != null) {
				for (String out : outMthds) {
					logger.debug("------------------outMthd:" + out);
				}
			}
			Set<String> inMthds = mthdVO.getInMthds();
			if (inMthds != null) {
				for (String in : inMthds) {
					logger.debug("------------------inMthd:" + in);
				}
			}
		} else {
			logger.warn("not found mthd:" + mthdSig);
		}

	}
}
