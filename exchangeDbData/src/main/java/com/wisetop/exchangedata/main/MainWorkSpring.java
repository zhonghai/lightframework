package com.wisetop.exchangedata.main;

import java.util.List;

import com.wisetop.base.work.IWork;
import com.wisetop.base.work.IWorkProcess;
import com.wisetop.base.work.WorkParent;
import com.wisetop.npf.util.error.ExceptionUtil;

public class MainWorkSpring extends WorkParent implements IWork {
	private List<IWork> workList;
	
	public void work(){
		if(workList != null){
			for(IWork work:workList){
				try{
					work.work();
				}catch(Exception e){
					e.printStackTrace();
					ExceptionUtil.getExceptionMsg(e);
				}			
			}			
		}
	}
	

}
