package neu.lab.risk;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import neu.lab.SysConf;
import neu.lab.SysCons;
import neu.lab.core.SysInfo;
import neu.lab.core.SysUtil;
import neu.lab.dog.book.Book;
import neu.lab.vo.MethodVO;

/**
 * 缩写解释: hm:host method;lm:lib method;lot:lots of;mc:method call;pl:per
 * lib;wrt:write;dir:direct
 * 
 * @author asus
 *
 */
public abstract class RiskWriter {

	String type;
	String dirPath;
	String numFile;
	String riskMthdFile;
	
	List<String> nums = new ArrayList<String>();
	Set<String> riskMthds = new HashSet<String>();

//	String lotLibDir = SysConf.riskOutPath + "lotLib/";
//	String lotMcPlDir = SysConf.riskOutPath + "lotMcPl/";
//	String lotInHmDir = SysConf.riskOutPath + "lotInHm/";
//	String lotDirLmDir = SysConf.riskOutPath + "lotDirLm/";
//
//	List<String> libNums = new ArrayList<String>();// host method直接引用的jar包个数
//	List<String> mcPlNums = new ArrayList<String>();// host Method对被引用的lib引用的方法的个数
//	List<String> inHmNums = new ArrayList<String>();// lib中的方法被多少个hostMthd调用
//	List<String> dirLmNums = new ArrayList<String>();
//
//	Set<String> lotLibMs = new HashSet<String>();// lots of lib method
//	Set<String> lotMcPlMs = new HashSet<String>();// lots of method Call per-Lib method
//	Set<String> lotInHmMs = new HashSet<String>();// lots of in-HostMethod method
//	Set<String> lotDirLmMs = new HashSet<String>();
	
	public void run() throws IOException {
		createDir();
		calRisk();
		wrtSta();
	}
	protected abstract void initType();
	protected abstract void calRisk() throws IOException;

	public void createDir() {
		new File(dirPath).mkdirs();
	}

	public RiskWriter() {
		initPath();
	}

	public void initPath() {
		initType();
		dirPath = SysConf.riskOutPath + type + "/";
		numFile = SysConf.staDir+"nums_"+type+".txt";
		riskMthdFile = SysConf.staDir +"risk_m_"+type+".txt";
	}



	public void wrtSta() throws IOException {
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(numFile)));
		for (String num : nums) {
			printer.println(num);
		}
		printer.close();
		printer = new PrintWriter(new BufferedWriter(new FileWriter(riskMthdFile)));
		for (String riskMthd : riskMthds) {
			printer.println(riskMthd);
		}
		printer.close();
	}



	public void wrtHostCall(PrintWriter printer, String hostMthd) {
		Set<String> inMthds = SysInfo.getMthd(hostMthd).getInMthds();
		StringBuilder sb = new StringBuilder("call from host:");
		int cnt = 0;// 计数
		if (null != inMthds) {
			for (String inMthd : inMthds) {
				if (SysCons.MY_JAR_NAME.equals(SysInfo.getMthd(inMthd).getJar())) {
					sb.append(inMthd + "  ");
					cnt++;
				}
			}
			sb.insert(0, cnt + " ");
			printer.println(sb.toString());
		}

	}

	protected File getWrtFile(String dir, String mthdSig) {
		File outFile = new File(dir + SysUtil.mthdSig2FileName(mthdSig));
		while (outFile.exists()) {// 防止方法名字的重复
			outFile = new File(dir + SysUtil.mthdSig2FileName(mthdSig));
		}
		return outFile;
	}


}
