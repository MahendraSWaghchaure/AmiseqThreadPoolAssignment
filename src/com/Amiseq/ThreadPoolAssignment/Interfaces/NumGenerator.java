package com.Amiseq.ThreadPoolAssignment.Interfaces;

public interface NumGenerator extends Runnable {
	void stop();

	String getGeneratorId();

	boolean isActive();
}
