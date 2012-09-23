package com.wisetop.base.work;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.wisetop.base.work.set.ISet;

public abstract class WorkProcessParent implements IWorkProcess {
	protected static Logger log = Logger.getLogger( WorkProcessParent.class );
	private List workSets = new ArrayList();
	protected IWork work = null;
	// 处理业务逻辑
	public abstract void doBusiProcessBefore() throws Exception;
	
	public abstract void doBusiProcessAfter() throws Exception;
	
	public abstract void processSucc() throws Exception;
	
	public abstract void processFailed(Exception e) throws Exception;
	
	public abstract void processFinished() throws Exception;
	
	public void process() throws Exception{
		try{

			doBusiProcessBefore();
			
			IWork child = null;
			
			 Iterator it = work.getChildWorks().iterator();
			for(;it.hasNext();){
				child = (IWork) it.next();
				child.work();				
			}
			
			doBusiProcessAfter();
			
			processSucc();
		}catch(Exception e){
			e.printStackTrace();
			log.debug("err-----" + e.getMessage() + e.getStackTrace().toString() );
			processFailed(e);
		}finally{
			processFinished();
		}
	}

	/**
	 * @return the work
	 */
	public IWork getWork() {
		return work;
	}

	/**
	 * @param work the work to set
	 */
	public void setWork(IWork work) {
		this.work = work;
	}

	protected void log(String loginfo){
		//System.out.println(this.getWork().getName() + "-process:" + loginfo);
		log.debug(this.getWork().getName() + "-process:" + loginfo);
	}
	public void setWorkSet(ISet workSet) {
		workSets.add(workSet);		
	}
	public List<ISet> getWorkSet(){
		return workSets;
	}
}
