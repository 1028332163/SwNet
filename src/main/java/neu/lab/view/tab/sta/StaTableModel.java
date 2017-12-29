package neu.lab.view.tab.sta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

public class StaTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	// ��ӱ�ͷ���������ͣ�����toolTip�����÷�������
	public static String[] tHead = { "jarSig", "mthdCnt", "clsCnt", "dirJarM", "rDJM", "acsJarM", "rAJM", "dirJarC",
			"rDJC", "acsJarC", "rAJC", "dirHostM", "rDHM", "acsHostM", "rAHM", "dirHostC", "rDHC", "acsHostC", "rAHC",
			"passedM", "rPDM", "passM", "rPM" };
	public static Map<String, String> head2tip;// ��ͷ��ע��
	public static Set<String> iCols;// Integer���͵���
	public static Set<String> fCols;// float���͵���
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
		head2tip.put("mthdCnt", "lib.methodNumber(����)");
		head2tip.put("clsCnt", "lib.classNumber(����)");
		
		head2tip.put("dirJarM", "lib.methodNumber(hostֱ�ӵ���)");
		head2tip.put("rDJM", "����: lib.methodNumber(hostֱ�ӵ���)/lib.methodNumber(����)");
		head2tip.put("acsJarM", "lib.methodNumber(hostֱ�ӻ��ӵ���)");
		head2tip.put("rAJM", "����: lib.methodNumber(hostֱ�ӻ��ӵ���)/lib.methodNumber(����)");

		head2tip.put("dirJarC", "lib.classNumber(hostֱ�ӵ���)");
		head2tip.put("rDJC", "����: lib.classNumber(hostֱ�ӵ���)/lib.classNumber(����)");
		head2tip.put("acsJarC", "lib.classNumber(hostֱ�ӻ��ӵ���)");
		head2tip.put("rAJC", "����: lib.classNumber(hostֱ�ӻ��ӵ���)/lib.classNumber(����)");
		
		head2tip.put("dirHostM", "host.methodNumber(ֱ�ӵ���lib)");
		head2tip.put("rDHM", "����: host.methodNumber(ֱ�ӵ���lib)/host.methodNumber(����)");
		head2tip.put("acsHostM", "host.methodNumber(ֱ�ӻ��ӵ���lib)");
		head2tip.put("rAHM", "����: host.methodNumber(ֱ�ӻ��ӵ���lib)/host.methodNumber(����)");
		
		head2tip.put("dirHostC", "host.classNumber(ֱ�ӵ���lib)");
		head2tip.put("rDHC", "����: host.classNumber(ֱ�ӵ���lib)/host.classNumber(����)");
		head2tip.put("acsHostC", "host.classNumber(ֱ�ӻ��ӵ���lib)");
		head2tip.put("rAHC", "����: host.classNumber(ֱ�ӻ��ӵ���lib)/host.classNumber(����)");
		
		head2tip.put("passedM", "lib.methodNumber(ֱ�ӻ��ӵ���otherLib)");
		head2tip.put("rPDM", "����: lib.methodNumber(����ֱ�ӻ��ӷ���otherLib)/lib.methodNumber(����)");
		head2tip.put("passM", "host.methodNumber(ͨ��lib����otherLib)");
		head2tip.put("rPM", "����: host.methodNumber(ͨ��lib����otherLib)/host.methodNumber(����)");

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
