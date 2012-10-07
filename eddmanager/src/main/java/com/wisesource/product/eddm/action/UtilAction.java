package com.wisesource.product.eddm.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wisetop.npf.util.config.ConfigManager;
import com.wisetop.npf.util.error.ExceptionUtil;

public class UtilAction {
	static Logger log = Logger.getLogger(UtilAction.class.getName());
	private Map param = new HashMap();
	
	private String	errmsg;
	private String	opResult;
	private Map	rtnData;
	public String	connDb(){
		opResult = "succ";
		try{
			String	driver = oneVal("driver") ;
			String	url = oneVal("url") ;
			String	username = oneVal("username") ;
			String	password = oneVal("password") ;		
			ConfigManager.getConn(driver,url,username,password);			
		}catch(Exception e){
			//e.printStackTrace();
			this.setErrmsg(ExceptionUtil.getExceptionMsg(e).replace("\r", "").replace("\n", ""));
			log.debug(ExceptionUtil.getExceptionMsg(e));
			opResult = "fail";
		}
		
		return "connDb";
	}
	
	public String oneVal(String attrNm){
    	if(param == null) return "";
    	String[] s = null;
    	String	sRtn = "";
    	if(param.get(attrNm) != null){
    		s = (String[]) param.get(attrNm);
    		if(s.length > 0 ){
    			sRtn = s[0];
    		}
    	}	   	
    	return sRtn;
    }
	
	public Map getParam() {
		return param;
	}
	public void setParam(Map param) {
		this.param = param;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = this.errmsg  +  errmsg + ";";
	}

	public String getOpResult() {
		return opResult;
	}

	public Map getRtnData() {
		return rtnData;
	}	
	
}
