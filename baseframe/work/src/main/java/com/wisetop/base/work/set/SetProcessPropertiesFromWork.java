/**
 * create on : 2012-6-18
 * note:
 */
package com.wisetop.base.work.set;

import com.wisetop.base.work.IWork;
import com.wisetop.base.work.IWorkProcess;
import com.wisetop.npf.util.classop.ClassUtil;

/**
 * @author zh
 *
 */
public class SetProcessPropertiesFromWork implements ISet {
	private IWork work = null;
	private IWorkProcess workprocess = null;
	private String workname = "";
	private String	workfield = "";
	private String	field = "";
	
	public void set(Object dest,Object src) throws Exception {
		workprocess = (IWorkProcess) dest;
		work = (IWork) src;
		if(!ClassUtil.setValue(workprocess, field, ClassUtil.getBeanObject(work.getWorkByName(workname),workfield)  )){
			String errmsg = "";
			errmsg = "SetWorkByName failed:" + work.getName() + "/" + workprocess.getWork().getName() + "/" +
					workprocess.getClass().getName() + "/" + field + "/" + workname;
			throw new Exception(errmsg);
		}
	}

	public void setWork(IWork work) {
		this.work = work;
	}

	public void setWorkprocess(IWorkProcess workprocess) {
		this.workprocess = workprocess;
	}

	public void setWorkname(String workname) {
		this.workname = workname;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setWorkfield(String workfield) {
		this.workfield = workfield;
	}

}
