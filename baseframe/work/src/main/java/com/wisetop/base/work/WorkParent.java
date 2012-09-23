package com.wisetop.base.work;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wisetop.base.work.impl.DbTransportWorkImpl;
import com.wisetop.base.work.set.ISet;
import com.wisetop.npf.util.Common;

public abstract class WorkParent implements IWork {
	protected static Logger log = Logger.getLogger(DbTransportWorkImpl.class.getName());
	protected IWork parent = null;
	public  List<IWork> childWorks = new ArrayList();
	protected IWorkProcess process = null;	
	protected String	name = "";
	protected String	thisname = "";
	

	/**
	 * @param process the process to set
	 */
	public void setProcess(IWorkProcess process)  throws Exception{
		process.setWork(this);
		this.process = process;
		for(Object o:process.getWorkSet()){
			ISet workset = (ISet) o;
			workset.set(this,process);
		}
	}
	
	public String getFullname() {
		if(Common.isNotNull(thisname)){
			return thisname;
		}else{
			if(this.getParent() != null){
				thisname = this.getParent().getName() + "." + name;
			}else{
				thisname = name;
			}	
			return thisname;
		}			
	}
	
	public String	getName(){
		return this.name;
	}
	
	public IWork getWorkByName(String workname) {
		IWork wk = this;
		while(wk != null){
			if(workname.equals(wk.getFullname())){
				return wk;
			}
			wk = wk.getParent();
		}
		return null;
	}	

	public void addChildWork(IWork childWork){
		childWork.setParent(this);
		childWorks.add(childWork);
		
		log.debug("childWorks.size:" + childWorks.size());
	}
	
	public void setChildWorks(List<IWork> childWorks) {
		for(IWork o:childWorks){
			addChildWork(o);
		}
		
	}
	/**
	 * @return the childWorks
	 */
	public List<IWork> getChildWorks() {
		if(childWorks == null) childWorks = new ArrayList();
		return childWorks;
	}
	
	public void resetChildWorks() {
		 childWorks = new ArrayList();
	}
	
	protected void log(String loginfo){
		//System.out.println(this.getName() + ":" + loginfo);
		log.debug(this.getName() + ":" + loginfo);
	}
	
	public IWorkProcess getProcess() {
		return process;		
	}
	/**
	 * @return the parent
	 */
	public IWork getParent() {
		return parent;
	}

	public void setParent(IWork parent) {
		this.parent = parent;		
	}

	public void setName(String name) {
		this.name = name;
	}	
	
}
