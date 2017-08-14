package com.xinghen.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;

public class FileController {
	private static HashSet<String> picExt = new HashSet<String>(){{
		add("jpg");
	}};

	public static void main(String[] args) throws IOException {
		String path = "E:/1ANeed/NO.1701-1800/";
		String distPath = "E:/pic_html/";
		File distFile = new File(distPath);
		if(!distFile.exists()){
			distFile.mkdir();
		}
		File lf2Item = new File(path);
//		System.out.println();
//		PrintStream p = new PrintStream(new FileOutputStream(lf2Item));
		listFile(path,distPath);
	}
	
	public static void listFile(String path, String distPath) throws IOException{
		File file = new File(path);
		File[] listFiles = file.listFiles();
		for (File item : listFiles) {
			if (item.isDirectory()) {
//				System.out.println(item.getPath());
				File[] lf2 = item.listFiles();
				int count = 0;
				for (File lf2Item : lf2) {
					lf2Item.getName().substring(lf2Item.getName().lastIndexOf("."));
					if(lf2Item.isFile() && //判断当先项为文件类型
					//判断当前文件后缀名为图片类型
					FileController.picExt.contains(lf2Item.getName().substring(lf2Item.getName().lastIndexOf(".")+1).toLowerCase().trim())){
						count ++;
					}
					//如果是目录类型，则继续递归
					if(lf2Item.isDirectory()){
						listFile(lf2Item.getPath(),distPath);

					}
					
				}
				if(count > 2){
//					FileOutputStream fs = new FileOutputStream(new File(distPath + item.getName() + ".html"));
//					PrintStream p = new PrintStream(fs);
					listFile(item.getPath(),distPath);
//					p.close();
				}

			}else{
//				p2.println("<img src = '" + item.getPath() + "' />");
				File distF = new File(distPath + item.getParentFile().getName().replace(".", "_") + ".html");
				distF.createNewFile();
				BufferedWriter bfw = new BufferedWriter(new FileWriter(distF, true));
				bfw.write("<img src = '" + item.getPath() + "' />");
				bfw.close();
				System.out.println(item.getPath());
			}
		}
	}
}
