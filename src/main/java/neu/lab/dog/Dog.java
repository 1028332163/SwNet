package neu.lab.dog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import neu.lab.dog.book.Book;
import neu.lab.core.SysInfo;
import neu.lab.core.SysManager;
import neu.lab.vo.MethodVO;

/**
 * ���Ͻ���������ȱ�������ȡ��Ҫ��õ���Ϣ
 * 
 * @author asus
 *
 */
public abstract class Dog {
	private static Logger logger = Logger.getRootLogger(); 
	protected Set<String> startMthds;
	protected String pos;// ��¼dog�ĵ�ǰλ��
	protected List<String> route;// ��¼dog���ﵱǰλ�õ�·�ߣ��Ա���л���

	protected Map<String, Cross> graphMap = new HashMap<String, Cross>();

	protected Map<String, List<String>> circleMap = new HashMap<String, List<String>>();// ��¼��·��a->b->c->a��map�еļ�¼Ϊ
																						// <a,[b,c]>
	protected Map<String, Book> books = new HashMap<String, Book>();// node to
	// reachJar����¼ÿ�������ڵ���Ե����jar��

	protected Map<String, Book> tempBooks = new HashMap<String, Book>();

	public Dog(Set<String> startMthds) {
		this.startMthds = startMthds;
	}

	protected abstract Book buyNodeBook(String nodeName);

	protected abstract void printResult();

	public Map<String, Book> findRlt() {
		for (String mthd : startMthds) {
				 
			route = new ArrayList<String>();
			if (books.containsKey(mthd))
				continue;
			else {
				forward(mthd);
				while (pos != null) {
					if (needChildBook()) {// ��ǰλ�û��з�֧
						String frontNode = graphMap.get(pos).getBranch();
						getChildBook(frontNode);
					} else {
						back();
					}
				}
			}
		}
		return this.books;
//		printResult();
	}

	public boolean needChildBook() {
		return graphMap.get(pos).hasBranch() && route.size() < 10;
//		return graphMap.get(pos).hasBranch();
	}

	private void getChildBook(String frontNode) {
		if (books.containsKey(frontNode)) {// child����ɵ��ֲ�
			addChildBook(frontNode, pos);
		} else {
			forward(frontNode);
		}

	}

	/**
	 * frontNode��һ���ֲ�û����ɵĽڵ㣬��ҪΪ����ڵ㽨���ֲ�
	 * 
	 * @param frontNode
	 */
	private void forward(String frontNode) {
		MethodVO methodVO = SysInfo.getMthd(frontNode);
		if (methodVO != null) {
			if (!route.contains(frontNode)) {// �����ɻ�·
				pos = frontNode;// λ��ǰ��
				route.add(pos);// ·������½ڵ�
				Book nodeRch = buyNodeBook(frontNode);// �õ��ýڵ�ļ�¼�ֲ�
				this.tempBooks.put(frontNode, nodeRch);// <�����ڵ㣬�ɴ�jar��>����ʱmap�з���ýڵ�
				graphMap.put(pos, new Cross(methodVO));// ���µ�ͼ
			} else {// �����˻�·����Ҫ����·��¼�������ڻ�·�Ŀ�ʼ�ڵ��Ϊdone��ʱ��done�ڵ�Ŀɴ�ڵ�Ϊ��·�����нڵ�Ŀɴ�ڵ�
				List<String> circle = new ArrayList<String>();
				int index = route.indexOf(frontNode) + 1;
				while (index < route.size()) {
					circle.add(route.get(index));
					index++;
				}
				this.circleMap.put(frontNode, circle);
			}
		}
	}

	private void back() {
		String donePos = route.get(route.size() - 1);
		graphMap.remove(donePos);// ������Ҫ���method�ĵ�ͼ
	
		// ��jar���ĵ���·���Ͻ��Լ�����·������ǰ��
		Book book = this.tempBooks.get(donePos);
		book.addSelf();
		// ��ֵ�Դ���ʱmap�з�������map
		this.tempBooks.remove(donePos);
		this.books.put(donePos, book);

		// �а����ýڵ�Ļ�·����·�ϵĺ���ڵ�Ӧ�ø���
		if (circleMap.containsKey(donePos)) {

			dealLoopNd(donePos);
			circleMap.remove(donePos);
		}

		route.remove(route.size() - 1);// ·�߻���
		// λ�ø���
		if (route.size() == 0) {
			pos = null;
		} else {
			pos = route.get(route.size() - 1);
			addChildBook(donePos, pos);// ��������ķ�֧mthd�ڵĵ�����ӵ�pos��
		}
	}

	private void addChildBook(String donePos, String pos) {
		Book doneBook = this.books.get(donePos);
		Book doingBook = this.tempBooks.get(pos);
		doingBook.addChild(doneBook);
	}


	protected abstract void dealLoopNd(String donePos);

	public static void testDog() {
		long startTime = System.currentTimeMillis();
		// Set<String> mthds = new HashSet<String>();
		// mthds.add("<org.apache.maven.cli.MavenCli: void
		// toolchains(org.apache.maven.cli.CliRequest)>");
		// MethodVO mthdVO = SysInfo.getMthd("<org.apache.commons.cli.Options: boolean
		// hasOption(java.lang.String)>");
		// for (String out : mthdVO.getOutMthds()) {
		// logger.debug(out);
		// }
		// Iterator<String> ite = SysInfo.getAllMthds().iterator();
		// int count = 0;
		// while (ite.hasNext()) {
		// mthds.add(ite.next());
		// count++;
		// }
		Set<String> mthds = SysManager.getInnerMthds();
//		new JarPathDog(mthds).findRlt();
		new MthdPathDog(mthds).findRlt();
		Long runTime = (System.currentTimeMillis() - startTime) / 1000;
		logger.info("JarPathDog runTime:" + runTime);
	}
}
