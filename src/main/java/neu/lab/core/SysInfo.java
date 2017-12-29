package neu.lab.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import neu.lab.dog.book.Book;
import neu.lab.sta.SysJarSta;
import neu.lab.util.Detective;
import neu.lab.vo.ClassVO;
import neu.lab.vo.FieldVO;
import neu.lab.vo.JarVO;
import neu.lab.vo.MethodVO;
import neu.lab.vo.PackageVO;
import neu.lab.vo.Relation;
import neu.lab.vo.SubSys;
import neu.lab.vo.ClosureSys;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

/**
 * @author asus 类中保存着被分析的系统中的所有的信息
 */
public class SysInfo {

	public static int phantomRef = 0;
	public static Set<String> gbgMthds = new HashSet<String>();
	// static Set<String> innerPcksSet;// 存在.java文件的包名
	public static Map<String, JarVO> jarTb = new HashMap<String, JarVO>();// 存储二进制项目中的出现的jar包
	public static Map<String, PackageVO> pckTb = new HashMap<String, PackageVO>();
	public static Map<String, ClassVO> clsTb = new HashMap<String, ClassVO>();
	public static Map<String, MethodVO> mthdTb = new HashMap<String, MethodVO>();
	
	public static Map<String, FieldVO> fldTb = new HashMap<String, FieldVO>();
	public static Set<Relation> m2ms = new HashSet<Relation>();// methodRelations method与method的调用关系 method to method
	public static Set<Relation> c2cs = new HashSet<Relation>();// 如果两个类中的method有关联，则两个Class有关联
	public static SubSys c2jSys = new SubSys();
	public static Map<String, Book> books;
	public static Set<String> hostMthds;
	public static void delPck(String pckSig) {
		pckTb.remove(pckSig);
	}

	public static void delJar(String jarSig) {
		jarTb.remove(jarSig);
	}

	public static Iterator<Entry<String, JarVO>> jatIte() {
		return jarTb.entrySet().iterator();
	}

	public static Set<String> getAllJar() {
		return jarTb.keySet();
	}

	public static boolean addM2m(String src, String target) {
		return m2ms.add(new Relation(src, target));
	}

	public static boolean addC2jNode(String sig) {
		return c2jSys.addNode(sig);
	}

	public static boolean addC2jEdge(String src, String tgt) {
		return c2jSys.addEdge(new Relation(src, tgt));
	}

	public static boolean addC2jEdge(Relation rlt) {
		return c2jSys.addEdge(rlt);
	}

	public static boolean addC2c(String src, String tgt) {
		return c2cs.add(new Relation(src, tgt));
	}

	public static int getJarNum() {
		return jarTb.size();
	}

	public static Set<String> getAllClses() {
		return clsTb.keySet();
	}

	public static Set<Relation> getAllC2c() {
		return c2cs;
	}

	public static Set<String> getAllMthds() {
		return mthdTb.keySet();
	}

	public static Set<Relation> getAllMthdRlts() {
		return m2ms;
	}

	public static Set<String> getAllPcks() {
		return pckTb.keySet();
	}

	public static boolean addJar(String jarSig, JarVO jarVO) {
		JarVO oldJar = jarTb.get(jarSig);
		if (null == oldJar) {
			jarTb.put(jarSig, jarVO);
			return true;
		}
		return false;
	}

	public static boolean isSysMthd(String mthdSig) {
		if (mthdTb.get(mthdSig) != null) {
			return true;
		}
		return false;
	}

	public static boolean isSysCls(String clsSig) {
		if (clsTb.get(clsSig) != null) {
			return true;
		}
		return false;
	}

	public static JarVO getJar(String jarSig) {
		return jarTb.get(jarSig);
	}

	public static PackageVO getPck(String pckSig) {
		return pckTb.get(pckSig);
	}

	public static ClassVO getCls(String clsSig) {
		return clsTb.get(clsSig);
	}

	public static MethodVO getMthd(String mthdSig) {
		return mthdTb.get(mthdSig);
	}

	public static FieldVO getFld(String fldSig) {
		return fldTb.get(fldSig);
	}

	// public static void setSrcPckPaths(Set<String> pckPaths) {
	// innerPcksSet = pckPaths;
	// }
//	public static boolean setOutMthds(String mthd, Set<String> outMthds) {
//		MethodVO methodVO = mthdTb.get(mthd);
//		if (methodVO != null) {
//			methodVO.setOutMthds(outMthds);
//			return true;
//		}
//		return false;
//	}

//	public static boolean setInMthds(String mthd, Set<String> inMthds) {
//		MethodVO methodVO = mthdTb.get(mthd);
//		if (methodVO != null) {
//			methodVO.setInMthds(inMthds);
//			return true;
//		}
//		return false;
//	}

	public static boolean addFldRlt(String src, String target) {
		FieldVO fieldVO = fldTb.get(src);
		if (fieldVO != null) {
			fieldVO.setType(target);
			return true;
		}
		return false;
	}

	public static boolean addLocRlt(String src, Set<String> locTypeSet) {
		MethodVO methodVO = mthdTb.get(src);
		if (methodVO != null) {
			methodVO.setLocTypes(locTypeSet);
			return true;
		}
		return false;
	}

	/**
	 * @param pckSig
	 *            添加的包名
	 * @return true:packages中原来没有pck; false：packages中原来存在pck
	 */
	public static boolean addPck(String pckSig) {
		if (null == pckTb.get(pckSig)) {
			PackageVO packageVO = new PackageVO(pckSig);
			pckTb.put(pckSig, packageVO);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param sootClass
	 *            添加的类
	 * @return true:classes中原来没有sootClass; false：classes中原来存在这个sootClass
	 */
	public static boolean addClass(SootClass sootClass) {
		String className = sootClass.getName();
		if (clsTb.get(className) == null) {
			ClassVO classVO = new ClassVO(className);
			if (sootClass.hasSuperclass()) {
				classVO.setFathClsSig(sootClass.getSuperclass().getName());
			}
			clsTb.put(className, classVO);
			return true;
		} else {
			return false;
		}
	}

	public static boolean addMethod(SootMethod sootMethod) {
		String mthdSig = sootMethod.getSignature();
		if (mthdTb.get(mthdSig) == null) {
			MethodVO methodVO = new MethodVO(mthdSig);
			mthdTb.put(mthdSig, methodVO);
			return true;
		} else {
			return false;
		}

	}

	public static boolean addField(SootField sootField) {
		String fldSig = sootField.getSignature();
		if (fldTb.get(fldSig) == null) {
			FieldVO fieldVO = new FieldVO(fldSig);
			fldTb.put(fldSig, fieldVO);
			return true;
		} else {
			return false;
		}
	}
}
