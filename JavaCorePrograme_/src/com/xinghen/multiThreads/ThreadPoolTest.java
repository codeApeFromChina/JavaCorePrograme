package com.xinghen.multiThreads;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
* @ClassName: MatchCounter   使用线程池版本
* @Description: callbale and future ,   callbale 和runnable类似，执行完成之后返回结果。
* 				future 为，线程执行任务，然后忘记任务，直到任务执行完成，get（）阻塞，直到执行完成
* 				futureTask 为future和callable的包装器
* @author 赵志强
* @date 2017年8月14日 下午5:08:11
*/ 
public class ThreadPoolTest implements Callable<Integer> {
	private File directory;
	private String keyWord;
	private int count;
	private ExecutorService pool;
	
	public static void main(String[] args) {
		String directory = "G:/ttttt";
		String keyWord = "tttt";
		ExecutorService pool = Executors.newCachedThreadPool(); //线程池
		ThreadPoolTest mtcc = new ThreadPoolTest(new File(directory), keyWord, pool);
		Future<Integer> result = pool.submit(mtcc);
//		Thread t = new Thread(task);
//		t.start();
		try{
			System.out.println(result.get());
		}catch (Exception e){
			e.printStackTrace();
		}
		pool.shutdown();
	}

	public ThreadPoolTest(File directory, String keyWord, ExecutorService pool) {
		super();
		this.pool = pool;
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
					ThreadPoolTest counter = new ThreadPoolTest(file, keyWord, pool);
					FutureTask<Integer> task = new FutureTask<Integer>(counter);
					pool.submit(task);
					results.add(task);
//					Thread t = new Thread(task);
//					t.start();
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
			found = false;
		} finally {
			in.close();
		}
		return found;
	}
}