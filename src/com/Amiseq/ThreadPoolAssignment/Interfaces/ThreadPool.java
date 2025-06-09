package com.Amiseq.ThreadPoolAssignment.Interfaces;

public interface ThreadPool {
	boolean submitTask(Runnable task);

	void shutdown();

	boolean isShutdown();
}
