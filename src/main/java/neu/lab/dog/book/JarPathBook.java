package neu.lab.dog.book;

import java.util.HashSet;
import java.util.Set;

public class JarPathBook extends Book {

	private Set<JarPath> records;

	public JarPathBook(String nodeName) {
		super(nodeName);
		records = new HashSet<JarPath>();
	}

	@Override
	public void addChild(Book childBook) {
		Set<JarPath> childRecords = (Set<JarPath>) childBook.getRecords();
		for (JarPath path : childRecords) {
			if (!this.records.contains(path)) {
				JarPath pathCopy = new JarPath();
				pathCopy.addAll(path);
				this.records.add(pathCopy);
			}
		}
	}

	@Override
	public Object getRecords() {
		return this.records;
	}

	@Override
	public void addSelf() {
		if (records.isEmpty()) {// �ýڵ�û���κεĵ���
			JarPath path = new JarPath();
			path.addHead(nodeName);
			records.add(path);
		} else {// �ýڵ��е��ã����Լ���jar����ӵ�ÿһ������·������ǰ��
			addNdToAll(nodeName);
		}

	}

	@Override
	public void addNdToAll(String node) {
		for (JarPath path : records) {
			path.addHead(node);
		}
	}

	@Override
	public void copy(Book book) {
		Set<JarPath> templet = (Set<JarPath>) book.getRecords();
		for (JarPath path : templet) {
			JarPath pathCopy = new JarPath();
			pathCopy.addAll(path);
			this.records.add(pathCopy);
		}
	}
}
