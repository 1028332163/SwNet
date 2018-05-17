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
			if (mthd2core.get(hostM) > SysConf.K_CORE_T) {// ���������Ĳ㼶��
				Set<String> riskOuts = new HashSet<String>();
				Set<String> outMthds = sysInfo.getMthd(hostM).getOutMthds();
				if (null != outMthds) {
					for (String outMthd : outMthds) {
						if (mthd2core.get(outMthd) > SysConf.K_CORE_T) {// �����ú����Ĳ㼶��
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
	 * ����ÿ��������kcoreֵ
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
	 * ���������core�ķ���
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
				int validDegree = 0;// ��¼������Ч�ĳ��ȣ�һ���ֺ������ڼ���Ĺ�����ɾ������
				Set<String> outMthds = getEdge(leftM);
				if (null != outMthds) {
					for (String outMthd : outMthds) {
						if (!delMthds.contains(outMthd)) {
							validDegree++;
						}
							
					}
				}
				if (validDegree < core) {// �����Ķ�̫С��ִ��ɾ��
					ite.remove();
					delMthds.add(leftM);

					//�ڼ���k-core��ͼʱɾ�����Ľڵ�ĺ���Ϊk-1
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
