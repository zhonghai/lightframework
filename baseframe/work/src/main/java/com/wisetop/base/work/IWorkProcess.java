package com.wisetop.base.work;

import java.util.List;

import com.wisetop.base.work.set.ISet;

public interface IWorkProcess {
	public void process() throws Exception;
	public void setWork(IWork work);
	public IWork getWork();
	public void setWorkSet(ISet workSet);
	public List<ISet> getWorkSet();
}
