package com.wisesource.product.eddm.action;

import java.util.HashMap;
import java.util.Map;

import com.wisesource.product.eddm.services.TaskService;
import com.wisetop.npf.util.error.ExceptionUtil;

public class TaskAction {
	private Map param = new HashMap();
	private String	errmsg;
	private String	opResult;
	private Map	rtnData = new HashMap();;	
	private TaskService	taskService;
	
	public String fetchTask(){
		opResult = Constant.SUCCESS;
		try{
			rtnData.put("task",  taskService.fetchTask(param) );
		}catch(Exception e){
			opResult = Constant.FAIL;
			errmsg = ExceptionUtil.getExceptionMsg(e);
		}		
		return "fetchTask";
	}
	
	public String	addTask(){
		opResult = Constant.SUCCESS;
		try{
			taskService.addTask(param);
		}catch(Exception e){
			opResult = Constant.FAIL;
			errmsg = ExceptionUtil.getExceptionMsg(e).replace("\r", "").replace("\n", "").replace("\"", "'");
		}				
		return "addTask";
	}
	public String	delTask(){
		opResult = Constant.SUCCESS;
		try{
			taskService.deleteTask(param);
		}catch(Exception e){
			opResult = Constant.FAIL;
			errmsg = ExceptionUtil.getExceptionMsg(e);
		}		
		return "delTask";
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
		this.errmsg = errmsg;
	}
	public String getOpResult() {
		return opResult;
	}
	public void setOpResult(String opResult) {
		this.opResult = opResult;
	}
	public Map getRtnData() {
		return rtnData;
	}
	
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
}
