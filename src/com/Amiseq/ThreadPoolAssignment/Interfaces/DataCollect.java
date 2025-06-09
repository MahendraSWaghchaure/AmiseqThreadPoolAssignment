package com.Amiseq.ThreadPoolAssignment.Interfaces;

import java.util.List;

public interface DataCollect<T> {
	void addData(T data);

	List<T> retrieveAllData();

	boolean hasData();
}
