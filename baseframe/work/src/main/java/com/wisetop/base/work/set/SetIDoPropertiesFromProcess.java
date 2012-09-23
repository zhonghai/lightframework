package com.wisetop.base.work.set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wisetop.base.work.IDo;
import com.wisetop.base.work.IWorkProcess;
import com.wisetop.base.work.WorkProcessParent;
import com.wisetop.npf.util.classop.ClassUtil;

public class SetIDoPropertiesFromProcess implements ISet {
	private static Logger log = Logger.getLogger( SetIDoPropertiesFromProcess.class );
	private IWorkProcess 	myProcess = null;
	private IDo					mydo = null;
	private	String				fields = "";			// aa->bb,cc->dd
	public void set(Object dest,Object src) throws Exception {
		myProcess = (IWorkProcess) src;
		mydo = (IDo) dest;
		log.debug("fields:" + fields);
		if(StringUtils.isNotEmpty(fields)){
			String[] field = fields.split(",");
			for(int i=0;i<field.length;i++){
				String[]	str = field[i].split("->");
				if(!ClassUtil.setValue(mydo, str[1], ClassUtil.getBeanObject(myProcess,str[0]))){
					String errmsg = "";					
					errmsg = "SetIDoPropertiesFromProcess failed:" ;
					if(myProcess.getWork()!=null) errmsg += myProcess.getWork().getName() ;
					errmsg += "/" + myProcess.getClass().getName() + "/" + field[i] ;
					throw new Exception(errmsg);
				}						
			}	
		}		
	}
	public IWorkProcess getMyProcess() {
		return myProcess;
	}
	public void setMyProcess(IWorkProcess myProcess) {
		this.myProcess = myProcess;
	}
	public IDo getMydo() {
		return mydo;
	}
	public void setMydo(IDo mydo) {
		this.mydo = mydo;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	
}
