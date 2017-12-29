package neu.lab.sta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import neu.lab.dog.book.Book;
import neu.lab.SysCons;
import neu.lab.core.SysInfo;

/**
 * 需要统计的数据一共由访问方式、访问级别、
 * 
 * @author asus
 *
 */
public class HostSta extends Sta{
	private static Logger logger = Logger.getRootLogger(); 

	// private Map<String, Integer> hostPass;//
	// <libName,count>:host的方法个数:(通过thisLib到达thirdLib)
	// private Map<String, Integer> hostPassed;//
	// <libName,count>:thirdLib的方法个数:(被host通过thisLib到达)

	public HostSta() {

		dirHostMes = new HashSet<String>();
		acsHostMes = new HashSet<String>();

		dirHostCes = new HashSet<String>();
		acsHostCes = new HashSet<String>();

		dirJarMes = new HashSet<String>();
		acsJarMes = new HashSet<String>();

		dirJarCes = new HashSet<String>();
		acsJarCes = new HashSet<String>();

	}

	public void incMthdCnt() {
		mthdCnt++;
	}

	public void incClsCnt() {
		clsCnt++;
	}

	public void addDirInfo(String srcSig, String tgtSig) {
		dirHostMes.add(srcSig);
		dirJarMes.add(tgtSig);

		String srcCls = SysInfo.getMthd(srcSig).getCls();
		String tgtCls = SysInfo.getMthd(tgtSig).getCls();
		dirHostCes.add(srcCls);
		dirJarCes.add(tgtCls);
	}

	public void addAcsInfo(String srcSig, String tgtSig) {
		this.acsHostMes.add(srcSig);
		this.acsJarMes.add(tgtSig);

		String srcCls = SysInfo.getMthd(srcSig).getCls();
		String tgtCls = SysInfo.getMthd(tgtSig).getCls();
		this.acsHostCes.add(srcCls);
		this.acsJarCes.add(tgtCls);

	}

	public void print() {
		logger.debug("================" + SysCons.MY_JAR_NAME);

		logger.debug("mthdCnt:" + mthdCnt);
		logger.debug("clsCnt:" + clsCnt);

		logger.debug("dirHostMes:" + this.dirHostMes.size());
		logger.debug("acsHostMes:" + this.acsHostMes.size());

		logger.debug("dirHostCes:" + this.dirHostCes.size());
		logger.debug("acsHostCes:" + this.acsHostCes.size());

		logger.debug("dirJarMes:" + this.dirJarMes.size());
		logger.debug("acsJarMes:" + this.acsJarMes.size());

		logger.debug("dirJarCes:" + this.dirJarCes.size());
		logger.debug("acsJarCes:" + this.acsJarCes.size());
	}

	public int getClsCnt() {
		return clsCnt;
	}

	public int getMthdCnt() {
		return mthdCnt;
	}

	@Override
	protected Integer getPassM() {
		return 0;
	}

	@Override
	protected Integer getPassedM() {
		return 0;
	}

	@Override
	protected String getSig() {
		return SysCons.MY_JAR_NAME;
	}
}
