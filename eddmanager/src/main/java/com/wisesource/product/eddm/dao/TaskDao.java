package com.wisesource.product.eddm.dao;

import java.util.Map;

import com.wisetop.npf.util.PaginationSupport;

public interface	TaskDao {
	public PaginationSupport fetchTask(Map param) throws Exception;
	public void addTask(Map param) throws Exception;
	public void deleteTask(Map param) throws Exception;
	public void deleteInvalidData() throws Exception;
	public void test() throws Exception;
}
