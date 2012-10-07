package com.wisesource.product.eddm.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 *   
 * @author zh
 * create on	2012-8-12 下午8:58:17
 */
public class ModelComponent {
	private Map<String,TDbconnection> dbConn;
	private List<TProcess> processes;
	private List<TProcessDbconn> processDbConn;
	private List<TProcessDo> processDoes;
	private List<TProcessDoBoa> doBoaes;
	private List<TWork> works;
	private String	startWork;
	private Map index;
	public Map<String, TDbconnection> getDbConn() {
		if(dbConn == null ) dbConn = new HashMap();
		return dbConn;
	}
	public List<TProcess> getProcesses() {
		if(processes == null ) processes = new ArrayList();
		return processes;
	}
	public List<TProcessDbconn> getProcessDbConn() {
		if(processDbConn == null ) processDbConn = new ArrayList();
		return processDbConn;
	}
	public List<TProcessDo> getProcessDoes() {
		if(processDoes == null ) processDoes = new ArrayList();
		return processDoes;
	}
	public List<TProcessDoBoa> getDoBoaes() {
		if(doBoaes == null ) doBoaes = new ArrayList();
		return doBoaes;
	}
	public List<TWork> getWorks() {
		if(works == null ) works = new ArrayList();
		return works;
	}
	
	
	public String getStartWork() {
		return startWork;
	}
	public Map getIndex() {
		return index;
	}
	public void setDbConn(Map<String, TDbconnection> dbConn) {
		this.dbConn = dbConn;
	}
	public void setProcesses(List<TProcess> processes) {
		this.processes = processes;
	}
	public void setProcessDbConn(List<TProcessDbconn> processDbConn) {
		this.processDbConn = processDbConn;
	}
	public void setProcessDo(List<TProcessDo> processDoes) {
		this.processDoes = processDoes;
	}
	public void setDoBoaes(List<TProcessDoBoa> doBoaes) {
		this.doBoaes = doBoaes;
	}
	public void setWorks(List<TWork> works) {
		this.works = works;
	}
	public void setStartWork(String startWork) {
		this.startWork = startWork;
	}
	public void setIndex(Map index) {
		this.index = index;
	}

	
	
}
