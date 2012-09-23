package com.wisetop.base.work;

import java.util.List;

public interface IWork {	
	public void work() throws Exception;
	public void addChildWork(IWork childWork);
	public void setChildWorks(List<IWork> childWorks);
	public List getChildWorks();
	public void resetChildWorks();
	public void setParent(IWork parent);
	public IWork getParent( );
	public void setProcess(IWorkProcess process) throws Exception;
	public IWorkProcess getProcess();
	public String	getName();
	public String	getFullname();
	public IWork getWorkByName(String workname);

}
