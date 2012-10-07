package com.wisesource.product.eddm.services;

import java.util.Map;

import com.wisetop.npf.util.PaginationSupport;

public interface TaskService {
	public PaginationSupport fetchTask(Map param) throws Exception;
	public void addTask(Map param) throws Exception;
	public void deleteTask(Map param) throws Exception;
	public void deleteInvalidData() throws Exception;
}
