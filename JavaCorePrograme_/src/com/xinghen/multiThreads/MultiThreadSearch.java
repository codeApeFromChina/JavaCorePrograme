package com.xinghen.multiThreads;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

public class MultiThreadSearch {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("enter base directory:");
//		String directory = in.nextLine();
		String directory = "G:/";
		
		System.out.println("enter the keyword:");
//		String keyWord= in.nextLine();
		String keyWord= "tttt";
		final int FILE_QUEUE_SIZE = 10;
		final int SEARCH_THREADS = 100;
		ArrayBlockingQueue<File>queue =  new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);
		
		FileEnumerationTask fet = new FileEnumerationTask(queue, new File(directory));
		new Thread(fet).start();
		for (int i = 0 ; i < SEARCH_THREADS; i++) {
			new Thread(new SearchTask(queue, keyWord)).start();
		}
	}

}

/**
 * @ClassName: FileEnumerationTask
 * @Description: 目录下面的文件递归的都加入到队列中，最后加入一个空文件路径标识结束
 * @author 赵志强
 * @date 2017年8月14日 下午2:13:17
 */
class FileEnumerationTask implements Runnable {

	public static File DUMMY = new File("");
	private ArrayBlockingQueue<File> queue;
	private File startingDirectory;

	public FileEnumerationTask(ArrayBlockingQueue<File> queue, File startingDirectory) {
		super();
		this.queue = queue;
		this.startingDirectory = startingDirectory;
	}

	public void enumerate(File directory) throws InterruptedException {
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				enumerate(file);
			else
				queue.put(file);
		}
	}

	@Override
	public void run() {
		try {
			enumerate(startingDirectory);
			queue.put(DUMMY);
		} catch (Exception e) {
		}
	}
}

class SearchTask implements Runnable {
	private ArrayBlockingQueue<File> queue;
	private String keyWord;

	public SearchTask(ArrayBlockingQueue<File> queue, String keyWord) {
		super();
		this.queue = queue;
		this.keyWord = keyWord;
	}

	public void search(File file) {
		Scanner in = null;
		try {
			in = new Scanner(file);
			int lineNumber = 0;
			while (in.hasNextLine()) {
				lineNumber++;
				String line = in.nextLine();
				if (line.contains(keyWord)) {
					System.out.printf("%s:%d, %s %n", file.getName(), lineNumber, line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			in.close();

		}
	}

	@Override
	public void run() {
		Boolean done = false;
		try {

			while (!done) {
				File file = queue.take();
				if(file == FileEnumerationTask.DUMMY) {
					queue.put(file);
					done = true;
				}else search(file);
			}
		} catch (Exception e) {
		}
	}
}
