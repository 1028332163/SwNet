package neu.lab.dog.book;

import java.util.LinkedList;
import java.util.UUID;

import org.apache.log4j.Logger;

import neu.lab.core.SysInfo;

public class JarPath extends LinkedList<String> {// 该数据结构保证相邻的两个的元素是不一样的
	private static Logger logger = Logger.getRootLogger();
	private static final long serialVersionUID = 1L;
	// public LinkedList<String> mthds = new LinkedList<String>();
	String uuid;

	public void addHead(String mthd) {
		// mthds.addFirst(mthd);

		String jar = getJar(mthd);
		String first = this.peekFirst();
		if (first != null) {
			if (first.equals(jar))// 如果即将添加的元素和最后一个元素相同则不添加
				return;
		}
		super.addFirst(jar);
	}

	public void addTail(String mthd) {
		// mthds.addLast(mthd);

		String jar = getJar(mthd);
		String last = this.peekLast();
		if (last != null) {
			if (last.equals(jar))// 如果即将添加的元素和最后一个元素相同则不添加
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
