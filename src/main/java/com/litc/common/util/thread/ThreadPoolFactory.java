package com.litc.common.util.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadPoolFactory {
	private static ThreadPoolExecutor executor = null;

	public static void init(int corePoolSize, int maxPoolSize,
			int queueCapacity, int keepAliveSeconds) {
		executor = new ThreadPoolExecutor(
				corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueCapacity),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	public static String executor(WorkerExecutor worker){
		String result = "";
		try {
			result = executor.submit(worker).get(300, TimeUnit.SECONDS).toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String executor(WorkerCallable worker){
		String result = "";
		try {
			result = executor.submit(worker).get(300, TimeUnit.SECONDS).toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return result;
	}

}
