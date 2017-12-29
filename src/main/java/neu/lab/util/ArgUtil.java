package neu.lab.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import neu.lab.vo.JarVO;

public class ArgUtil {
	public static List<String> getArgs(String path) {
		List<String> argsList = ArgUtil.getGenArg();
		// argsList.add("-process-dir");
		// argsList.add("D:\\bin");
		// ��Ѱ�ҵ�jar����ӵ�������
		Set<JarVO> jarVOs = Detective.findJarPath(new File(path));

		for (JarVO jarVO : jarVOs) {
			String jarPath = jarVO.getJarPath();
			argsList.add("-process-dir");
			argsList.add(jarPath);
		}
		return argsList;
	}

	/**
	 * �õ�ͨ�õĲ�������
	 * 
	 * @return
	 */
	private static List<String> getGenArg() {
		List<String> argsList = new ArrayList<String>();
		argsList.add("-pp");// ��soot��classPath�е������ڽ���
		argsList.add("-ire");// ����classPath�е���Чʵ��
		argsList.add("-app");// ���е��඼����ΪappClass
		argsList.add("-allow-phantom-refs");// ������Ч�����ͽ���
		argsList.add("-w");// ������Ŀ����

		argsList.addAll(Arrays.asList(new String[] { "-p", "cg", "all-reachable:true", }));// �����е�appclass���е��÷���

//		argsList.addAll(Arrays.asList(new String[] { "-p", "cg.spark", "on", }));
//		argsList.addAll(Arrays.asList(new String[] { "-p", "cg.spark", "apponly:true", }));

		 argsList.addAll(Arrays.asList(new String[] { "-p", "cg.cha", "apponly:true",
		 }));
		// �ر��Դ���callGraph���ɻ���
		// argsList.addAll(Arrays.asList(new String[] { "-p", "cg", "off", }));//
		// argsList.addAll(Arrays.asList(new String[] { "-p", "wjop", "off", }));
		// argsList.addAll(Arrays.asList(new String[] { "-p", "wjap", "off", }));
		// argsList.addAll(Arrays.asList(new String[] { "-p", "jtp", "off", }));
		// argsList.addAll(Arrays.asList(new String[] { "-p", "jop", "off", }));
		// argsList.addAll(Arrays.asList(new String[] { "-p", "jap", "off", }));
		// argsList.addAll(Arrays.asList(new String[] { "-p", "bb", "off", }));
		// argsList.addAll(Arrays.asList(new String[] { "-p", "tag", "off", }));
		argsList.addAll(Arrays.asList(new String[] { "-f", "n", }));// �ر��ļ������
		// argsList.add("-outjar");//������Ŀ���һ��.jar

		return argsList;
	}
}
// //����classPath
// argsList.addAll(Arrays.asList(new String[] {
// "-cp", classPath
// }));
// argsList.addAll(Arrays.asList(new String[] {
// "-p", "bb", "off",
// }));
// argsList.addAll(Arrays.asList(new String[] {
// "-p", "jop", "off",
// }));
// argsList.addAll(Arrays.asList(new String[] {
// "-p", "db", "off",
// }));

// argsList.addAll(Arrays.asList(new String[] {
// "-p", "cg", "all-reachable:true",
// }));

// ������Classloader����
// argsList.addAll(Arrays.asList(new String[] {
// "-p", "cg.cha", "apponly:true"
// }));

// argsList.addAll(Arrays.asList(new String[] { "-p", "jb.tr",
// "use-older-type-assigner:true", }));
