package neu.lab.dog;

import java.util.List;
import java.util.Set;

import neu.lab.dog.book.MthdBook;
import neu.lab.core.SysInfo;
import neu.lab.core.SysManager;
import neu.lab.dog.book.Book;

public class MthdDog extends Dog {

	public MthdDog(Set<String> startMthds) {
		super(startMthds);
	}

	@Override
	protected Book buyNodeBook(String nodeName) {
		return new MthdBook(nodeName);
	}

	@Override
	protected void printResult() {
		for(String mthd:this.startMthds) {
			MthdBook book = (MthdBook)this.books.get(mthd);
			book.print();
		}

	}

	@Override
	protected void dealLoopNd(String donePos) {
		MthdBook doneBook = (MthdBook)this.books.get(donePos);
		List<String> circle = this.circleMap.get(donePos);
		for (String node : circle) {//
			Book doingBook = buyNodeBook(node);
			doingBook.copy(doneBook);
			this.books.put(node, doingBook);
		}
	}
}
