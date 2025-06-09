package com.Amiseq.ThreadPoolAssignment.Interfaces;

public interface OutputProcess extends Runnable{
	void stop();

	boolean isActive();
}
