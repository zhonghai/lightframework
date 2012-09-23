package com.wisetop.base.work.set;

import com.wisetop.base.work.IWork;
import com.wisetop.base.work.IWorkProcess;
import com.wisetop.npf.util.classop.ClassUtil;

public class SetProcessByName implements ISet {
	private IWork work = null;
	private IWorkProcess workprocess = null;
	private String workname = "";
	private String	field = "";
	
	public SetProcessByName(IWork work,IWorkProcess workprocess,String field,String workname){
		this.work = work;
		this.workprocess = workprocess;
		this.field = field;
		this.workname = workname;
	}
	
	public void set(Object dest,Object src) throws Exception{
		workprocess = (IWorkProcess) dest;
		work = (IWork) src;
		if(!ClassUtil.setValue(workprocess, field, work.getWorkByName(workname).getProcess() )){
			String errmsg = "";
			errmsg = "SetWorkByName failed:" + work.getName() + "/" + workprocess.getWork().getName() + "/" +
					workprocess.getClass().getName() + "/" + field + "/" + workname;
			throw new Exception(errmsg);
		}
	}
	
}
