package neu.lab.risk;

import java.io.File;
import java.util.ArrayList;

import java.util.List;
import neu.lab.SysConf;

public class WriterChain {
	private List<RiskWriter> writers;
	public WriterChain() {
		writers = new ArrayList<RiskWriter>();
//		writers.add(new LibNumWriter());
//		writers.add(new McPlWriter());
//		writers.add(new InHmWriter());
		writers.add(new DirLmWriter());
		writers.add(new AcsLmWriter());
//		writers.add(new KCoreWriter());
//		writers.add(new HitsWriter());
	}
	public void invokeAllWrt() {
		try {
			delFile(new File(SysConf.riskOutPath));
			new File(SysConf.staDir).mkdirs();
			for(RiskWriter writer:writers) {
				writer.run();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void delFile(File path) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			delFile(files[i]);
		}
		path.delete();
	}
}
