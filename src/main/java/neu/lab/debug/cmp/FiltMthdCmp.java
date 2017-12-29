package neu.lab.debug.cmp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class FiltMthdCmp {
	Set<String> dualMs;
	Set<String> setMs;

	public FiltMthdCmp() throws Exception {
		dualMs = readM("cmp/dual.txt");
		setMs = readM("cmp/set.txt");
	}

	public void cmp() throws IOException {
		printMin(dualMs,setMs,"cmp/dual-set.txt");
		printMin(setMs,dualMs,"cmp/set-dual.txt");
		
	}

	private void printMin(Set<String> total, Set<String> some, String fileName) throws IOException {
		PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		for (String m : total) {
			if (!some.contains(m))
				p.println(m);
		}
		p.close();
	}

	private Set<String> readM(String path) throws Exception {
		Set<String> result = new HashSet<String>();
		BufferedReader r = new BufferedReader(new FileReader(path));
		String mthd = r.readLine();
		while (null != mthd) {
			result.add(mthd);
			mthd = r.readLine();
			
		}
		r.close();
		return result;
	}
	public static void main(String[] args) throws Exception {
		new FiltMthdCmp().cmp();
	}
	
}
