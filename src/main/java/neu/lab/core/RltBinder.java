package neu.lab.core;

import java.util.Set;

import neu.lab.vo.ClassVO;
import neu.lab.vo.FieldVO;
import neu.lab.vo.JarVO;
import neu.lab.vo.MethodVO;
import neu.lab.vo.PackageVO;

/**
 * Relation Binder
 * 负责关系的绑定
 * @author asus
 *
 */
public class RltBinder {

	public static void cls2pck(String clsSig, String pckSig) {
		ClassVO cls = SysInfo.getCls(clsSig);
		PackageVO pck = SysInfo.getPck(pckSig);
		if (null != pck && null != cls) {
			pck.addClass(clsSig);
			cls.setPck(pckSig);
		}
	}

	public static void mthd2cls(String mthdSig, String clsSig) {
		MethodVO mthd = SysInfo.getMthd(mthdSig);
		ClassVO cls = SysInfo.getCls(clsSig);
		if (null != cls&&mthd != null) {
			cls.addMethod(mthdSig);
			mthd.setCls(clsSig);
		}

	}

	public static void fld2cls(String fldSig, String clsSig) {
		FieldVO fieldVO = SysInfo.getFld(fldSig);
		ClassVO cls = SysInfo.getCls(clsSig);
		if (null != cls&&null != fieldVO) {
			cls.addField(fldSig);
			fieldVO.setCls(clsSig);
		}
	}

	public static void pck2jar(String pckSig, JarVO jarVO) {
		PackageVO pckVO = SysInfo.getPck(pckSig);
		if (null != pckVO && null != jarVO) {
			jarVO.addPck(pckSig);
			pckVO.setJarSig(jarVO.getJarSig());
		}
	}

	public static void pcks2jar(Set<String> pcks, JarVO jarVO) {
		jarVO.setPcks(pcks);
		for (String pckSig : pcks) {
			PackageVO pckVO = SysInfo.getPck(pckSig);
			if (null != pckVO) {
				pckVO.setJarSig(jarVO.getJarSig());
			}
		}
	}
	// public static void pck2Jar(String pckSig, String jarSig) {
	// PackageVO pckVO = pckTb.get(pckSig);
	// JarVO jarVO = jarTb.get(jarSig);
	// if (null != jarVO) {
	// jarVO.addPck(pckSig);
	// }
	// }

}
