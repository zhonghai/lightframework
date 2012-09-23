/**
 * 用于数据库数据驱动的工作
 * @author zh
 * create on 2012-01-14
 */
package com.wisetop.base.work.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.wisetop.base.work.IWork;
import com.wisetop.base.work.WorkParent;
import com.wisetop.npf.util.db.DbDataUtil;

public class DbTransportWorkImpl extends WorkParent {
	private String	sql = null;
	private String	keys= null;
	private String	foreignkey = null;
	private Connection conn = null;
	private ResultSetMetaData metaData = null;
	private ResultSet rs = null;	
	private DataSource dataSource;
	
	public DbTransportWorkImpl(){
		
	}
	public DbTransportWorkImpl(String	name,String sql,String keys,Connection conn,String foreignkey){
		this.name = name;
		this.sql = sql;
		this.conn = conn;
		this.keys = keys;
		this.foreignkey = foreignkey;
		
	}
	public void work() throws Exception{
		String	worksql = getSql();
		log("begin work......");
		log("work sql:" + worksql);		
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(worksql);		
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData  rd = pstmt.getMetaData();
		// ???????????  这里加一个验证，如果列名重复，则报错，并提示
		Map tempMap = new HashMap();
		String		columnnm;
		for(int i=1;i<=rd.getColumnCount();i++){
			columnnm = rd.getColumnName(i);
			if(tempMap.containsKey(columnnm)) throw new Exception("work sql:" + worksql + "/ column is duplicate:" + columnnm);
			tempMap.put(columnnm, columnnm);
		}
		tempMap = null;
		this.setRs(rs);
		this.setMetaData(rd);
		if(process != null){
			while(rs.next()){
				try{				
						process.process();
				}catch(Exception e){				
					e.printStackTrace();
					log.error("worksql:" + worksql + "," + e.getMessage());
					throw e;
				}			
			}
		}else{
			log("process 为空!");
		}
		rs.close();
		pstmt.close();
		pstmt = null;
		log.debug("work finished!......");
	}


	/**
	 * 取sql
	 * @return
	 */
	private String getSql() throws Exception{
		String	tmpSql = "";		
		String scondition = "";
		
		if(!(foreignkey == null || "".equals(foreignkey))){
			String[] fks = foreignkey.split(",");
			for(int i=0;i<fks.length;i++){
				String[] tmp = fks[i].split("=");
				String key = tmp[0];
				String fk = tmp[1];
				scondition += " and (" +  generateCondition(key,fk) + ")";
			}
		}
		
		if(!(scondition == null || "".equals(scondition) )){
			tmpSql = sql + scondition;
		}else{
			tmpSql = sql;
		}
		return tmpSql;
	}
	
	private String generateCondition(String key,String fk) throws Exception{
		String condition = "";
		IWork pwk = this.getParent();		
		String[]	fks = fk.split("\\.");
		if(fks.length > 0){
			for(int i=0;i<fks.length;i++){
				if(fks[i].toLowerCase().equals("#parent")){
					pwk = pwk.getParent();
				}
			}
			 
			log("pwk.getName():" + pwk.getName() + "/fks[fks.length - 1]:" + fks[fks.length - 1]);
			
			Object o =((DbTransportWorkImpl)pwk).getRs().getObject(fks[fks.length - 1]);
			condition = key + "=" + DbDataUtil.getRsObjVal(o);
		}
		
		return condition;
	}
	/**
	 * @return the metaData
	 */
	public ResultSetMetaData getMetaData() {
		return metaData;
	}
	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(ResultSetMetaData metaData) {
		this.metaData = metaData;
	}
	/**
	 * @return the rs
	 */
	public ResultSet getRs() {
		return rs;
	}
	/**
	 * @param rs the rs to set
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) throws Exception {
		this.dataSource = dataSource;
		this.conn = dataSource.getConnection();
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public void setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
	}
	
}
