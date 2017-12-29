package neu.lab.view.tab.sta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

public class StaTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	// 添加表头，设置类型，设置toolTip，设置返回数据
	public static String[] tHead = { "jarSig", "mthdCnt", "clsCnt", "dirJarM", "rDJM", "acsJarM", "rAJM", "dirJarC",
			"rDJC", "acsJarC", "rAJC", "dirHostM", "rDHM", "acsHostM", "rAHM", "dirHostC", "rDHC", "acsHostC", "rAHC",
			"passedM", "rPDM", "passM", "rPM" };
	public static Map<String, String> head2tip;// 表头的注释
	public static Set<String> iCols;// Integer类型的列
	public static Set<String> fCols;// float类型的列
	static {
		fCols = new HashSet<String>();
		fCols.add("rDJM");
		fCols.add("rAJM");
		fCols.add("rDJC");
		fCols.add("rAJC");

		fCols.add("rDHM");
		fCols.add("rAHM");
		fCols.add("rDHC");
		fCols.add("rAHC");
		fCols.add("rPDM");
		fCols.add("rPM");

		iCols = new HashSet<String>();
		iCols.add("mthdCnt");
		iCols.add("clsCnt");

		iCols.add("dirJarM");
		iCols.add("acsJarM");
		iCols.add("dirJarC");
		iCols.add("acsJarC");

		iCols.add("dirHostM");
		iCols.add("acsHostM");
		iCols.add("dirHostC");
		iCols.add("acsHostC");
		iCols.add("passedM");
		iCols.add("passM");
		// public static String[] tHead = { "jarSig", "mthdCnt", "clsCnt", "dirJarM",
		// "rDJM", "acsJarM", "rAJM", "dirJarC",
		// "rDJC", "acsJarC", "rAJC", "dirHostM", "rDHM", "acsHostM", "rAHM",
		// "dirHostC", "rDHC", "acsHostC", "rAHC",
		// "passedM", "rPDM", "passM", "rPM" };
		head2tip = new HashMap<String, String>();
		head2tip.put("jarSig", "lib.name");
		head2tip.put("mthdCnt", "lib.methodNumber(所有)");
		head2tip.put("clsCnt", "lib.classNumber(所有)");
		
		head2tip.put("dirJarM", "lib.methodNumber(host直接调用)");
		head2tip.put("rDJM", "比率: lib.methodNumber(host直接调用)/lib.methodNumber(所有)");
		head2tip.put("acsJarM", "lib.methodNumber(host直接或间接调用)");
		head2tip.put("rAJM", "比率: lib.methodNumber(host直接或间接调用)/lib.methodNumber(所有)");

		head2tip.put("dirJarC", "lib.classNumber(host直接调用)");
		head2tip.put("rDJC", "比率: lib.classNumber(host直接调用)/lib.classNumber(所有)");
		head2tip.put("acsJarC", "lib.classNumber(host直接或间接调用)");
		head2tip.put("rAJC", "比率: lib.classNumber(host直接或间接调用)/lib.classNumber(所有)");
		
		head2tip.put("dirHostM", "host.methodNumber(直接调用lib)");
		head2tip.put("rDHM", "比率: host.methodNumber(直接调用lib)/host.methodNumber(所有)");
		head2tip.put("acsHostM", "host.methodNumber(直接或间接调用lib)");
		head2tip.put("rAHM", "比率: host.methodNumber(直接或间接调用lib)/host.methodNumber(所有)");
		
		head2tip.put("dirHostC", "host.classNumber(直接调用lib)");
		head2tip.put("rDHC", "比率: host.classNumber(直接调用lib)/host.classNumber(所有)");
		head2tip.put("acsHostC", "host.classNumber(直接或间接调用lib)");
		head2tip.put("rAHC", "比率: host.classNumber(直接或间接调用lib)/host.classNumber(所有)");
		
		head2tip.put("passedM", "lib.methodNumber(直接或间接调用otherLib)");
		head2tip.put("rPDM", "比率: lib.methodNumber(可以直接或间接访问otherLib)/lib.methodNumber(所有)");
		head2tip.put("passM", "host.methodNumber(通过lib调用otherLib)");
		head2tip.put("rPM", "比率: host.methodNumber(通过lib调用otherLib)/host.methodNumber(所有)");

	}

	public StaTableModel(Object[][] rows) {
		super(rows, tHead);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (iCols.contains(this.getColumnName(columnIndex)))
			return Integer.class;
		if (fCols.contains(this.getColumnName(columnIndex)))
			return Float.class;
		return String.class;
	}
}
