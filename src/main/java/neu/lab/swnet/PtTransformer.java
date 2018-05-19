package neu.lab.swnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import neu.lab.swnet.core.SysInfo;
import neu.lab.swnet.vo.MethodVO;
import soot.Local;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.util.Chain;

public class PtTransformer extends SceneTransformer {
	private static Logger logger = Logger.getRootLogger();
	private SysInfo sysInfo;

	public PtTransformer(SysInfo sysInfo) {
		super();
		this.sysInfo = sysInfo;
	}

	@Override
	protected void internalTransform(String phaseName, Map option) {
		logger.debug("call from PtTransformer");
		Chain<SootClass> appClasses = Scene.v().getApplicationClasses();

		List<SootMethod> methods = new ArrayList<SootMethod>();
		List<SootField> fields = new ArrayList<SootField>();

		// 生成系统
		for (SootClass appClass : appClasses) {
			if (!neu.lab.swnet.core.SysUtil.isJdkCls(appClass.getName())) {// 不是jdk中的类
				// SysInfo.addPck(appClass.getPackageName());
				sysInfo.addClass(appClass);
				// RltBinder.cls2pck(appClass.getName(), appClass.getPackageName());
				for (SootMethod sootMethod : appClass.getMethods()) {
					methods.add(sootMethod);
					sysInfo.addMethod(sootMethod);
					sysInfo.mthd2cls(sootMethod.getSignature(), appClass.getName());
				}
				List<SootField> allFields = new ArrayList<SootField>();
				allFields.addAll(appClass.getFields());
				for (SootField fld : allFields) {
					fields.add(fld);
					sysInfo.addField(fld);
					sysInfo.fld2cls(fld.getSignature(), appClass.getName());
				}
			}
		}
		if (!SysConf.CG_TYPE.equals("off")) {
			CallGraph cg = Scene.v().getCallGraph();
			// 将call graph存储到methodVo中
			for (SootMethod sootMethod : methods) {
				// 设置outMthds、inMthds
				Set<String> outMthds = new HashSet<String>();
				Iterator<Edge> outIte = cg.edgesOutOf(sootMethod);
				while (outIte.hasNext()) {
					Edge edge = outIte.next();
					if (!neu.lab.swnet.core.SysUtil.isJdkCls(edge.tgt().getDeclaringClass().getName())) {
						String src = sootMethod.getSignature();
						String tgt = edge.tgt().getSignature();
						outMthds.add(tgt);
						// 添加类之间的关系
						MethodVO srcMthdVO = sysInfo.getMthd(src);
						MethodVO tgtMthdVO = sysInfo.getMthd(tgt);
						if (null != srcMthdVO && null != tgtMthdVO) {
							srcMthdVO.addOutMthds(tgt);
						}
					}
				}
			}
		}

		// 生成field的关系
		for (SootField sootField : fields) {
			String typeString = neu.lab.swnet.core.SysUtil.type2string(sootField.getType());
			sysInfo.addFldRlt(sootField.getSignature(), typeString);
		}
		// 生成local的关系
		for (SootMethod sootMethod : methods) {
			if (sootMethod.isConcrete()) {
				Chain<Local> locals = sootMethod.retrieveActiveBody().getLocals();
				Set<String> locTypes = new HashSet<String>();
				for (Local local : locals) {
					String typeString = neu.lab.swnet.core.SysUtil.type2string(local.getType());
					locTypes.add(typeString);
				}
				sysInfo.addLocRlt(sootMethod.getSignature(), locTypes);
			}
		}

	}
}
// 生成方法之间的关系
// Map<String, String> cgMap = new HashMap<String, String>();
// cgMap.put("enabled", "true");
// cgMap.put("apponly", "true");
// Scene.v().setEntryPoints(methods);
// SparkTransformer.v().transform("wjtp", cgMap);