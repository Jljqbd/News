package com.org.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FileRW {
	// 一行行读，一般用户文件夹里的收藏，点赞文件都是按行存储文章id信息的
	public List<String> ReadlineFile(String FilePath) throws IOException {
		FileInputStream fis = new FileInputStream(FilePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		List<String> FileContent = new ArrayList<String>();

		String line = "";
		// String[] arrs=null;
		while ((line = br.readLine()) != null) {
			// arrs=line.split(",");
			FileContent.add(line);
		}
		br.close();
		isr.close();
		fis.close();
		return FileContent;
	}

	// 全部读出来，一般文章类的直接全部读取出来就可以
	public String ReadFileContent(String filePath) {
		File file = new File(filePath);
		// BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str = null;
		StringBuffer Buff = new StringBuffer();
		try {
			while ((str = br.readLine()) != null) {
				Buff.append(str + "\r\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("路径:"+filePath);
		System.out.println(Buff.toString());
		return Buff.toString();
	}

	// 按行写入，如写入用户喜欢或点过赞的文章id
	public boolean WriteFile(String FilePath, List<String> arrs) throws IOException {
		FileWriter writer = null;
		try {
			File file = new File(FilePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			writer = new FileWriter(file, true);
			for (int i = 0; i < arrs.size(); i++) {
				writer.append(arrs.get(i) + '\n');
			}
			writer.flush();

			// System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return true;
	}

	public boolean WriteFile(String FilePath, List<String> arrs, boolean type) throws IOException {
		FileWriter writer = null;
		try {
			File file = new File(FilePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			writer = new FileWriter(file, type);
			for (int i = 0; i < arrs.size(); i++) {
				writer.append(arrs.get(i) + '\n');
			}
			writer.flush();

			// System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return true;
	}

	// 全部写入，如文章类的,如果新写入一个新闻文件也可以直接调用这个文件，因为他会自动创建这个文件
	public boolean WriteFile(String FilePath, String content) {
		File dir = new File(FilePath);
		// 一、检查放置文件的文件夹路径是否存在，不存在则创建
		File checkFile = new File(FilePath);
		FileWriter writer = null;
		try {
			// 二、检查目标文件是否存在，不存在则创建
			if (!checkFile.exists()) {
				checkFile.createNewFile();// 创建目标文件
			}
			// 三、向目标文件中写入内容
			// FileWriter(File file, boolean
			// append)，append为true时为追加模式，false或缺省则为覆盖模式
			writer = new FileWriter(checkFile, false);
			writer.append(content);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return true;
	}

	// 删除文件or文件夹及其旗下文件
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	// 删除文件
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + filePath + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + filePath + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + filePath + "不存在！");
			return false;
		}
	}

	// 删除文件夹及其下的所有文件
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = FileRW.deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = FileRW.deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

	// 查看是否有这个文件,或文件夹
	public boolean IsExist(String FilePath) {
		File file = new File(FilePath);
		return file.exists();
	}

	// 文件移动,移动后原文件被删除,移动到新文件夹中的同时可以改名，也就是调用时oldpath的文件名和newpath的文件名可以不同
	public boolean FileMove(String OldPath, String NewPath) {
		File f1 = new File(OldPath);
		File f2 = new File(NewPath);
		return f1.renameTo(f2);
	}

	// 文件重命名
	public boolean ReName(String OldFileName, String NewFileName) {
		File f1 = new File(OldFileName);
		File f2 = new File(NewFileName);
		return f1.renameTo(f2);
	}

	// 删除按行存的文件中的一个条目
	public boolean DelStr(String FilePath, String delstr) {
		List<String> content = new ArrayList<String>();
		try {
			content = ReadlineFile(FilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("删除文字的文件路径:" + FilePath);
		System.out.println("要删除的文字:" + delstr);
		Iterator<String> iterator = content.iterator();
		while (iterator.hasNext()) {
			String con = iterator.next();
			//System.out.println("本行文字为:" + con);
			if (delstr.equals(con)) {
				System.out.println("找到" + con);
				iterator.remove();// 使用迭代器的删除方法删除
			}
		}
		boolean isok = false;
		try {
			isok = WriteFile(FilePath, content, false);// 覆盖式的写入
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isok;
	}

	// 替换按行存的文件中的一个条目,注意他是一行行读取的，所以比对是一行行比对的
	public boolean ReplaceStr(String FilePath, String oldstr, String newstr) {
		List<String> content = new ArrayList<String>();
		try {
			content = ReadlineFile(FilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<String> iterator = content.iterator();
		String delstr = null;
		int delnum = 0;
		while (iterator.hasNext()) {
			String con = iterator.next();
			if (oldstr.equals(con)) {
				delstr = con;
				iterator.remove();// 使用迭代器的删除方法删除
				++delnum;
			}
		}
		for (int i = 0; i < delnum; i++) {
			content.add(newstr);
		}
		boolean isok = false;
		try {
			isok = WriteFile(FilePath, content, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isok;
	}

	// 查询按行存的文件中的是否存在这个条目
	public boolean IsExist(String FilePath, String fstr) throws IOException {
		FileInputStream fis = new FileInputStream(FilePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.equals(fstr)) {
				br.close();
				isr.close();
				fis.close();
				return true;
			}
		}
		br.close();
		isr.close();
		fis.close();
		return false;
	}
	public static String StringToMd5(String psw) {
        {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(psw.getBytes("UTF-8"));
                byte[] encryption = md5.digest();
 
                StringBuffer strBuf = new StringBuffer();
                for (int i = 0; i < encryption.length; i++) {
                    if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                        strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                    } else {
                        strBuf.append(Integer.toHexString(0xff & encryption[i]));
                    }
                }
 
                return strBuf.toString();
            } catch (NoSuchAlgorithmException e) {
                return "";
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }

	public boolean CreateFile(String Name, String content) {
		String[] filename = Name.split("/");
		String dirPath = Name;
		int isfile = -1;
		if (filename[filename.length - 1].indexOf(".") != -1) {// 在文本文件名称中没有提取到后缀名的标识”.“
			dirPath = Name.replaceAll(filename[filename.length - 1], "");// 你要创建的文件的所属路径
			isfile = 1;
		}
		File dir = new File(dirPath);
		if (dir.exists() && isfile == -1) {
			// System.out.println("创建目录" + Name + "失败，目标目录已经存在");
			return false;
		}
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}
		// 创建目录
		if (isfile != -1 || dir.mkdirs()) {
			System.out.println("创建目录" + Name + "成功！");
			if (isfile != -1) {
				return WriteFile(Name, content);
			} else {
				// 他是一个目录，并且被mkdirs创建成功了
				System.out.println("创建成功");
				return true;
			}
		} else {
			// name是个目录但是他原本就存在了
			return false;
		}
	}

	// 有个非常大的bug，用户不能创建含有"."的文件夹，所以用户名字不能有特殊字符
	public boolean CreateFile(String Name, List<String> content) {
		String[] filename = Name.split("/");
		String dirPath = Name;
		int isfile = -1;
		if (filename[filename.length - 1].indexOf(".") != -1) {// 在文本文件名称中没有提取到后缀名的标识”.“
			dirPath = Name.replaceAll(filename[filename.length - 1], "");// 你要创建的文件的所属路径
			isfile = 1;
		}
		File dir = new File(dirPath);
		if (dir.exists() && isfile == -1) {
			// System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}
		// 创建目录
		if (isfile != -1 || dir.mkdirs()) {
			// System.out.println("创建目录" + Name + "成功！");
			if (isfile != -1) {
				try {
					return WriteFile(Name, content);// 在这个创建好的路径中创建文本文件
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return true;
			}
			return false;
		} else {
			// System.out.println("创建目录" + Name + "失败！");
			return false;
		}
	}

	public static void main(String[] agrs) {
		String csPath = "E:/VSC_project/VSC py/newraper/userfolder/0bede0f9fa46fcbea794bae8405ac0bf/";
		String fpath = "E:/VSC_project/VSC py/newraper/userfolder/0bede0f9fa46fcbea794bae8405ac0bf/theme.txt";
		// 在一个已经创建或还未创建的路径中新建一个文件并按文章类型写入数据
		FileRW fw = new FileRW();
		File dir = new File(csPath);
		dir.mkdirs();
		if(fw.CreateFile(fpath, "")){
			System.out.println("创建成功");
		}else{
			System.out.println("创建失败");
		}
		/*
		 * if(fw.CreateFile(csPath+"w/d/a.txt", "我槽np！！!")){
		 * System.out.println("成功"); }else{ System.out.println("失败"); }
		 */
		// 写入一行行的内容
		/*
		List<String> line_str = new ArrayList<String>();
		line_str.add("txxw123912i01");
		line_str.add("txxw123124131");
		line_str.add("txxw239210391");
		boolean isok = false;
		String path = "E:/VSC_project/Java project/Test/w/d/a.txt";
		try {
			isok = fw.WriteFile(path, line_str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isok) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
	*/	
		// 一行行读，一般用户文件夹里的收藏，点赞文件都是按行存储文章id信息的
		/*
		 * String csrPath = "E:/VSC_project/Java project/Test/w/d/a.txt";
		 * List<String> line_str = new ArrayList<String>(); try { line_str =
		 * fw.ReadlineFile(csrPath); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } for (int i = 0; i <
		 * line_str.size(); i++){ System.out.println(line_str.get(i)); }
		 */
		// 全部读出来，一般文章类的直接全部读取出来就可以
		/*
		 * String cscPath =
		 * "E:/VSC_project/VSC py/newraper/wy/健康/wyjkFDS891V20038804H.txt";
		 * String content = null; content = fw.ReadFileContent(cscPath);
		 * System.out.println(content);
		 */
		// 删除文件or文件夹及其旗下文件
		// 删除文件夹
		/*
		 * String delPath = "E:/VSC_project/Java project/Test/w/c";
		 * if(fw.delete(delPath)){ System.out.println("成功"); }else{
		 * System.out.println("失败"); }
		 */
		// 删除文件
		/*
		 * String delfile = "E:/VSC_project/Java project/Test/abc.txt";
		 * if(fw.delete(delfile)){ System.out.println("成功"); }else{
		 * System.out.println("失败"); }
		 */
		// 查看是否有这个文件,或文件夹
		// 是否有这个文件夹
		/*
		 * String filename = "E:/VSC_project/Java project/Test";
		 * if(fw.IsExist(filename)){ System.out.println("存在"); }else{
		 * System.out.println("不存在"); }
		 */
		// 是否有这个文件
		/*
		 * String filename = "E:/VSC_project/Java project/Test/w/d/a.txt";
		 * if(fw.IsExist(filename)){ System.out.println("存在"); }else{
		 * System.out.println("不存在"); }
		 */
		// 文件移动,移动后原文件被删除,移动到新文件夹中的同时可以改名，也就是调用时oldpath的文件名和newpath的文件名可以不同
		/*
		 * String oldpath = "E:/VSC_project/Java project/Test/w/d/a.txt"; String
		 * newpath = "E:/VSC_project/Java project/Test/w/b.txt";
		 * if(fw.FileMove(oldpath, newpath)){ System.out.println("成功"); }else{
		 * System.out.println("失败"); }
		 */
		// 文件重命名
		/*
		 * String oldpath = "E:/VSC_project/Java project/Test/w/d/b.txt"; String
		 * newpath = "E:/VSC_project/Java project/Test/w/d/a.txt";
		 * if(fw.ReName(oldpath, newpath)){ System.out.println("成功"); }else{
		 * System.out.println("失败"); }
		 */
		// 删除按行存的文件中的一个条目
		/*
		String filepath = "E:/VSC_project/Java project/Test/w/d/a.txt";
		String delstr = "txxw239210391";
		if (fw.DelStr(filepath, delstr)) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
	*/	
		// 替换按行存的文件中的一个条目，注意他是一行行读取的，所以比对是一行行比对的
		/*
		 * String filePath = "E:/VSC_project/Java project/Test/w/d/a.txt";
		 * String oldStr = "我槽np！！!"; String NewStr = "田汝浩真帅";
		 * if(fw.ReplaceStr(filePath, oldStr, NewStr)){
		 * System.out.println("成功"); }else{ System.out.println("失败"); }
		 */
		// 查询按行存的文件中的是否存在这个条目
		/*
		 * String filePath = "E:/VSC_project/Java project/Test/w/d/a.txt";
		 * String fstr = "田汝浩真帅"; boolean isok = false; try { isok =
		 * fw.IsExist(filePath, fstr); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } if(isok){
		 * System.out.println("存在"); }else{ System.out.println("不存在"); }
		 */
	}
}
