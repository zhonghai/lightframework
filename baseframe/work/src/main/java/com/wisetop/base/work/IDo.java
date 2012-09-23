package com.wisetop.base.work;

import java.sql.Connection;
import java.util.List;

import com.wisetop.base.work.set.ISet;

public interface IDo {
	public boolean work()  throws Exception;
	public void 		setProcess(IWorkProcess myprocess) throws Exception;
	public List<ISet> 		getSets() throws Exception;
	public String getDoid();
	public void setDoid(String doid);
}
