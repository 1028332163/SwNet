package neu.lab.swnet.risk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import neu.lab.swnet.SysConf;
import neu.lab.swnet.core.SysInfo;

public class KCoreCnter extends RiskCounter {
	public KCoreCnter(SysInfo sysInfo) {
		super(sysInfo);
	}
	Map<String, Integer> mthd2core;
	Set<String> leftMthds;
	Set<String> delMthds;

	@Override
	protected void initType() {
		type = "k_core";
	}

	@Override
	protected void calRisk() throws IOException {
		calCoreMap();

		for (String hostM : sysInfo.getHostMthds()) {
			if (mthd2core.get(hostM) > SysConf.K_CORE_T) {// 宿主函数的层级高
				Set<String> riskOuts = new HashSet<String>();
				Set<String> outMthds = sysInfo.getMthd(hostM).getOutMthds();
				if (null != outMthds) {
					for (String outMthd : outMthds) {
						if (mthd2core.get(outMthd) > SysConf.K_CORE_T) {// 被调用函数的层级高
							riskMthds.add(hostM);
							riskOuts.add(outMthd);
							wrtEachM(hostM, riskOuts);
						}
					}
				}
			}
		}

	}

	private void wrtEachM(String hostM, Set<String> riskOuts) throws IOException {
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(getWrtFile(dirPath, hostM))));
		printer.println(hostM);
		this.wrtHostCall(printer, hostM);
		printer.println(riskOuts.size() + " risk lib_method");
		for (String riskOut : riskOuts) {
			printer.println(riskOut);
		}
		printer.close();
	}

	/**
	 * 计算每个函数的kcore值
	 */
	private void calCoreMap() {
		mthd2core = new HashMap<String, Integer>();

		leftMthds = new HashSet<String>();
		leftMthds.addAll(sysInfo.getAllMthd());

		delMthds = new HashSet<String>();

		int core = 0;
		while (!leftMthds.isEmpty()) {
			calCoreMthds(core);
			core++;
		}
	}

	/**
	 * 计算核数是core的方法
	 * 
	 * @param core
	 * @param leftMthds
	 * @param delMthds
	 * @return
	 */
	private void calCoreMthds(int core) {

		int delNum;
		do {
			delNum = 0;
			Iterator<String> ite = leftMthds.iterator();
			while(ite.hasNext()) {
				String leftM = ite.next();
				int validDegree = 0;// 记录函数有效的出度（一部分函数会在计算的过程中删除掉）
				Set<String> outMthds = getEdge(leftM);
				if (null != outMthds) {
					for (String outMthd : outMthds) {
						if (!delMthds.contains(outMthd)) {
							validDegree++;
						}
							
					}
				}
				if (validDegree < core) {// 方法的度太小，执行删除
					ite.remove();
					delMthds.add(leftM);

					//在计算k-core子图时删除掉的节点的核数为k-1
					int mthdCore = core-1;
					nums.add(mthdCore+"");
					mthd2core.put(leftM, new Integer(mthdCore));

					delNum++;
				}
			}
		} while (delNum > 0);
	}
	private Set<String> getEdge(String mthd){
		Set<String> edges = new HashSet<String>();
		
		Set<String> outMthds = sysInfo.getMthd(mthd).getOutMthds();
		if(null!=outMthds) {
			edges.addAll(outMthds);
		}
		
//		Set<String> inMthds = SysInfo.getMthd(mthd).getInMthds();
//		if(null!=inMthds) {
//			edges.addAll(inMthds);
//		}
//		
		return edges;
	}
}
