package com.wisetop.exchangedata.process;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.wisetop.npf.util.db.DbDataUtil;

public class GetDbValues implements IValue {
	private String 	connId ;
	private String 	sql;
	Map tempData;
	public void getValues(Map data, Map conns)  throws Exception{
		// 这里只取一行
		List rows = DbDataUtil.getRowsDataToMap((Connection)conns.get(connId), sql, data, -1, -1);
		if(rows.size() > 0){
			data.putAll((Map)rows.get(0));		
		}
	}
	public String getConnId() {
		return connId;
	}
	public void setConnId(String connId) {
		this.connId = connId;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Map getTempData() {
		return tempData;
	}
	public void setTempData(Map tempData) {
		this.tempData = tempData;
	}

}
