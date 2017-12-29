package neu.lab.util.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import neu.lab.SysConf;
import neu.lab.core.SysUtil;
import neu.lab.vo.MethodVO;

public class FilterChain {
	/**
	 * method的outMthds中每个方法名字出现几次
	 */
	Map<String, Integer> nameCnts;
	private List<Filter> filters;

	public FilterChain(MethodVO mthd) {
		filters = new ArrayList<Filter>();
		nameCnts = mthd.calNameCnt();
	}

	public void addFilter(Filter f) {
		filters.add(f);
	}

	public boolean shdFltM(String mthdSig) {
		if (isDanger(mthdSig)) {
			for (Filter f : filters) {
				if (f.shdFltM(mthdSig))
					return true;
			}
		}
		return false;

	}

	public boolean isDanger(String mthdSig) {
		String mthdName = SysUtil.mthdSig2Name(mthdSig);
		Integer cnt = nameCnts.get(mthdName);
		if (cnt > SysConf.DANGER_NUM) {// 重名方法太多，进行过滤
			return true;
		} else {
			return false;
		}
	}
}
