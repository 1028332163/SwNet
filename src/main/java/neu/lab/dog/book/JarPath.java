package neu.lab.dog.book;

import java.util.LinkedList;
import java.util.UUID;

import org.apache.log4j.Logger;

import neu.lab.core.SysInfo;

public class JarPath extends LinkedList<String> {// �����ݽṹ��֤���ڵ�������Ԫ���ǲ�һ����
	private static Logger logger = Logger.getRootLogger();
	private static final long serialVersionUID = 1L;
	// public LinkedList<String> mthds = new LinkedList<String>();
	String uuid;

	public void addHead(String mthd) {
		// mthds.addFirst(mthd);

		String jar = getJar(mthd);
		String first = this.peekFirst();
		if (first != null) {
			if (first.equals(jar))// ���������ӵ�Ԫ�غ����һ��Ԫ����ͬ�����
				return;
		}
		super.addFirst(jar);
	}

	public void addTail(String mthd) {
		// mthds.addLast(mthd);

		String jar = getJar(mthd);
		String last = this.peekLast();
		if (last != null) {
			if (last.equals(jar))// ���������ӵ�Ԫ�غ����һ��Ԫ����ͬ�����
				return;
		}
		super.addLast(jar);
	}

	public JarPath() {
		super();
		uuid = UUID.randomUUID().toString();
	}

	private String getJar(String mthd) {
		return SysInfo.getMthd(mthd).getJar();
	}

	public void print() {
		for (String jar : this) {
			logger.debug(jar + "->");
		}
		logger.debug("\n");
	}


	public static void main(String[] args) {
		// LinkedList q = new LinkedList();
		// q.add("a");
		// LinkedList q1 = new LinkedList();
		// q1.add("a");
	}

}
