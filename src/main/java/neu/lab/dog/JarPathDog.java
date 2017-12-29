package neu.lab.dog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import neu.lab.SysCons;
import neu.lab.dog.book.JarPath;
import neu.lab.dog.book.JarPathBook;
import neu.lab.dog.book.Book;
import neu.lab.core.SysInfo;
import neu.lab.core.SysManager;
import neu.lab.vo.MethodVO;

/**
 * 寻找所有的jar包调用顺序
 * 
 * @author asus
 *
 */
public class JarPathDog extends Dog {

	public JarPathDog(Set<String> startMthds) {
		super(startMthds);
	}

	@Override
	protected Book buyNodeBook(String nodeName) {
		return new JarPathBook(nodeName);
	}

	@Override
	protected void printResult() {
		Set<JarPath> result = new HashSet<JarPath>();
		for (String mthd : startMthds) {
			JarPathBook book = (JarPathBook) this.books.get(mthd);
			Set<JarPath> paths = (Set<JarPath>)book.getRecords();
			for (JarPath path : paths) {
				result.add(path);
			}
		}
		for (JarPath path : result) {
			path.print();
		}

	}

	@Override
	protected void dealLoopNd(String donePos) {
		JarPathBook doneBook = (JarPathBook)this.books.get(donePos);
		List<String> circle = this.circleMap.get(donePos);
		for (String node : circle) {//
			// A->B->C->A时，B的jar调用等于B.jar+C.jar+A的jar所有调用；C的jar调用等于C.jar+A的jar所有调用；
			List<String> preList = circle.subList(circle.indexOf(node), circle.size());
			Book doingBook = buyNodeBook(node);
			doingBook.copy(doneBook);
			for (int index = preList.size() - 1; index >= 0; index--) {// 将前缀加入
				String mthdNd = preList.get(index);
				doingBook.addNdToAll(mthdNd);
			}
			this.books.put(node, doingBook);
		}
	}
}
