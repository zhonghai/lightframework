package com.wisetop.exchangedata.process;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.wisetop.base.work.IDo;
import com.wisetop.base.work.WorkProcessParent;
import com.wisetop.base.work.impl.DbDoInsertOrUpdate;
import com.wisetop.base.work.impl.DbTransportWorkImpl;
import com.wisetop.base.work.set.ISet;
import com.wisetop.npf.util.db.DbDataUtil;

public class DbProcessImpl extends WorkProcessParent {
	private Map<String,DataSource>				connDSMap = new HashMap();
	private static Map<String,Connection>				connMap = new HashMap();
	private boolean		ifCommit = false;
	private Map				data = null;
	private DbTransportWorkImpl mywork = null;
	private List<IDo>		beforeDo		= null;
	private List<IDo>		afterDo			= null;
	private List<IValue>	genValues		= null;
	private String			processId		= null;
	@Override
	public void doBusiProcessBefore() throws Exception {
		mywork = (DbTransportWorkImpl) this.getWork();
		connMap.put("myworkconn", mywork.getConn());
		// 将work里的数据存在map里
		data = new HashMap();
		
		data = DbDataUtil.getRowDataToMap(mywork.getMetaData(),mywork.getRs());
		// ???????  待实现
		if(genValues != null){
			for(IValue v:genValues){
				v.getValues(data, connMap);
			}			
		}

		String	columnnm ;
//		for(int i=1;i<=mywork.getMetaData().getColumnCount();i++){
//			columnnm = mywork.getMetaData().getColumnName(i);			
//			log.debug("************" + columnnm + "/" + data.get(columnnm));			
//		}
//		for(Object key:data.keySet()){
//			log.debug("======" + key + "/" + data.get(key)  );
//		}

		if(beforeDo != null){
			for(IDo d:beforeDo){		
				DbDoInsertOrUpdate dbdo = (DbDoInsertOrUpdate) d;
				dbdo.setConn(connMap.get(dbdo.getConnNm()));
				List<ISet> sets = d.getSets();
				if(sets != null){
					for(ISet oset:sets){
						oset.set(d, this);
					}					
				}
				d.work();
			}
		}
		
		
	}
	
	@Override
	public void doBusiProcessAfter() throws Exception {
		if(afterDo != null){
			for(IDo d:afterDo){
				d.work();
			}
		}
	}

	@Override
	public void processFailed(Exception arg0) throws Exception {
		log.debug(this.getWork().getName() + "/processFailed:" + ifCommit);
		if(ifCommit) {
			for(Object key:connMap.keySet()){
				connMap.get(key).rollback();
			}
		}
	}

	@Override
	public void processSucc() throws Exception {
		log.debug(this.getWork().getName() + "/processSucc:" + ifCommit);
		if(ifCommit)  {
			for(Object key:connMap.keySet()){
				connMap.get(key).commit();
			}		
		}

	}

	@Override
	public void processFinished() throws Exception {
	}
	
	public boolean isIfCommit() {
		return ifCommit;
	}

	public void setIfCommit(boolean ifCommit) {
		this.ifCommit = ifCommit;
	}

	public List<IDo> getBeforeDo() {
		if(beforeDo == null) beforeDo = new ArrayList();
		return beforeDo;
	}

	public void setBeforeDo(List<IDo> beforeDo) throws Exception{
		this.beforeDo = beforeDo;
		if(beforeDo != null){
			for(int i=0;i<beforeDo.size();i++){
				beforeDo.get(i).setProcess(this);
			}			
		}
	}

	public List<IDo> getAfterDo() {
		if(afterDo == null) afterDo = new ArrayList();
		return afterDo;
	}

	public void setAfterDo(List<IDo> afterDo) throws Exception {
		this.afterDo = afterDo;
		if(afterDo != null){
			for(int i=0;i<afterDo.size();i++){
				afterDo.get(i).setProcess(this);
			}			
		}
	}



	public Map<String, DataSource> getConnDSMap() {
		return connDSMap;
	}

	public void setConnDSMap(Map<String, DataSource> connDSMap) throws Exception {
		this.connDSMap = connDSMap;
		this.connMap.clear();
		for(Object key:connDSMap.keySet()){
			this.connMap.put("" + key, connDSMap.get(key).getConnection());
		}
	}

	public Map<String, Connection> getConnMap() {
		if(connMap == null) connMap = new HashMap();
		return connMap;
	}

	public void setConnMap(Map<String, Connection> connMap) {
		this.connMap = connMap;
	}

	public Map getData() {
		return data;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	
}

//String[] encoding = {"iso8859-1","GB2312","GBK",
//"unicode","UTF-8","UTF-16","UTF-16BE"
//,"US-ASCII","UTF-16LE"};
//for(int i=0;i<encoding.length;i++){
//for(int j=0;j<encoding.length;j++){
//log.debug( encoding[i] + "/" + encoding[j]  + "/" + new String(a_cognome.getBytes(encoding[i]),encoding[j] )  );
//}
//}
