package com.wisetop.base.work.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wisetop.base.work.IDo;
import com.wisetop.base.work.IWorkProcess;
import com.wisetop.base.work.set.ISet;
import com.wisetop.base.work.set.SetIDoPropertiesFromProcess;
import com.wisetop.npf.util.Common;
import com.wisetop.npf.util.db.DbDataUtil;

public class DbDoInsertOrUpdate implements IDo {
	protected static Logger log = Logger.getLogger(DbDoInsertOrUpdate.class.getName());
	private IWorkProcess	myprocess = null;		// 当前处理的过程
	private Connection 		conn = null;				// 目标数据库
	private String				connNm = "";				// 目标数据库代号,通过代号给conn赋值
	private String 				tbl = "";						// 写到目标数据库的表
	private String 				keyFields = "";			// 表的主键
	private String 				fields = "";					// 新增到表的字段
	private String 				uptFields = "";			// 更新到表的字段
	private Map 					data = null;				// 源数据
	private boolean			allowUpdate = true;	// 当数据存在时是否更新
	private boolean			onlyUpdate = false;	// 只进行更新操作
	private	List<ISet>		setProperties = null;
	private String			doid;
	public DbDoInsertOrUpdate(){
		
	}
	
	public DbDoInsertOrUpdate(IWorkProcess myprocess,Connection conn,String tbl,String keyFields,String fields,String uptFields,Map data){
		this.myprocess = myprocess;
		this.conn = conn;
		this.tbl = tbl;
		this.keyFields = keyFields;
		this.fields = fields;
		this.uptFields = uptFields;
		this.data = data;
	}
	public boolean work() throws Exception{
		if(conn == null) {
			log.debug(" DbDoInsertOrUpdate conn is null");
			return true;
		}

		if(Common.isNull(tbl)) {
			log.debug(" DbDoInsertOrUpdate tbl is null");
			return true;
		}

		if(Common.isNull(keyFields)) {
			log.debug(" DbDoInsertOrUpdate keyFields is null");
			return true;
		}		
		
		for(Object key:data.keySet()){
			log.debug("/////////////////" + key + "/" + data.get(key) );
		}
		
		if(onlyUpdate){
			log.debug("do insert or update :" + tbl + "/" + keyFields + "/" +uptFields );
			DbDataUtil.updateTableEx(conn, tbl, keyFields, uptFields, data);
		}else{
			String sql = "";
			log.debug("DbDoInsertOrUpdate begin..." );
			sql = "select count(*) from " + tbl + " where " + getSelectWhereSql(keyFields);
			log.debug("DbDoInsertOrUpdate sql:" + sql + "/" + keyFields );
			Long	count = (Long) DbDataUtil.getOneResult(conn,tbl, "count(*)", keyFields, keyFields,data);
			log.debug("DbDoInsertOrUpdate count:" + count);
			if(count > 0){
				// 修改
				if(allowUpdate){
					DbDataUtil.updateTableEx(conn, tbl, keyFields, uptFields, data);
				}
			}else{
				// 新增
				DbDataUtil.insertToTable(conn, tbl,keyFields, fields, data);
			}
		}
		return true;
	}
	
	private String getSelectWhereSql(String fields){
		String srtn = "";
		String[] field = fields.split(",");
		for(int i=0;i<field.length - 1;i++){
			srtn += field[i] + "= ? and ";
		}
		srtn += field[ field.length - 1] + "= ? ";
		return srtn;
	}
	
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn)  throws Exception{
		this.conn = conn;
	}
	public String getTbl() {
		return tbl;
	}
	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	public String getKeyFields() {
		return keyFields;
	}
	public void setKeyFields(String keyFields) {
		this.keyFields = keyFields.toLowerCase();
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		
		this.fields = fields.replace(" ", "").toLowerCase().replace("\r", "").replace("\n", "").replace("\t", "");
	}
	public String getUptFields() {
		return uptFields;
	}
	public void setUptFields(String uptFields) {
		this.uptFields = uptFields.replace(" ", "").toLowerCase().replace("\r", "").replace("\n", "").replace("\t", "");
	}
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}

	public boolean isAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public boolean isOnlyUpdate() {
		return onlyUpdate;
	}

	public void setOnlyUpdate(boolean onlyUpdate) {
		this.onlyUpdate = onlyUpdate;
	}

	public IWorkProcess getMyprocess() {
		return myprocess;
	}
	public void setMyprocess(IWorkProcess myprocess) {
		this.myprocess = myprocess;
	}
	public void setProcess(IWorkProcess myprocess) throws Exception {
		this.myprocess = myprocess;
	}
	public List<ISet> getSetProperties() {
		return setProperties;
	}
	public void setSetProperties(List<ISet> setProperties) {
		this.setProperties = setProperties;
	}

	public List<ISet> getSets() throws Exception {		
		return setProperties;
	}

	public String getConnNm() {
		return connNm;
	}

	public void setConnNm(String connNm) {
		this.connNm = connNm;
	}

	public String getDoid() {
		return doid;
	}

	public void setDoid(String doid) {
		this.doid = doid;
	}
	
}
