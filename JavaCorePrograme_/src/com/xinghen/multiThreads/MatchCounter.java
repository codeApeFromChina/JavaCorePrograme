package com.xinghen.multiThreads;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
* @ClassName: MatchCounter
* @Description: callbale and future ,   callbale 和runnable类似，执行完成之后返回结果。
* 				future 为，线程执行任务，然后忘记任务，直到任务执行完成，get（）阻塞，直到执行完成
* 				futureTask 为future和callable的包装器
* @author 赵志强
* @date 2017年8月14日 下午5:08:11
*/ 
public class MatchCounter implements Callable<Integer> {
	private File directory;
	private String keyWord;
	private int count;

	public MatchCounter(File directory, String keyWord) {
		super();
		this.directory = directory;
		this.keyWord = keyWord;
	}

	@Override
	public Integer call() throws Exception {
		count = 0;
		try {
			File[] files = directory.listFiles();
			List<Future<Integer>> results = new ArrayList<Future<Integer>>();
			for (File file : files) {
				if (file.isDirectory()) {
					MatchCounter counter = new MatchCounter(file, keyWord);
					FutureTask<Integer> task = new FutureTask<Integer>(counter);
					results.add(task);
					Thread t = new Thread(task);
					t.start();
				} else {
					if (search(file))
						count++;
				}
			}

			for (Future<Integer> future : results) {
				count += future.get();
			}

		} catch (Exception e) {
		}
		return count;
	}

	public Boolean search(File directory) {
		Boolean found = false;
		Scanner in = null;
		try {
			in = new Scanner(directory);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (line.contains(keyWord)) {
					found = true;
					break;
				}
			}
		} catch (Exception e) {
		} finally {
			in.close();
			found = false;
		}
		return false;
	}
}