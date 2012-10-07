package com.wisesource.product.eddm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.wisesource.product.eddm.dao.TaskDao;
import com.wisesource.product.eddm.domain.TTask;
import com.wisetop.npf.util.Common;
import com.wisetop.npf.util.PaginationSupport;
import com.wisetop.npf.util.db.DbDataUtil;

public class TaskDaoImpl extends com.wisetop.npf.util.db.GenericDaoImpl  implements TaskDao{
	
	public PaginationSupport fetchTask(Map param) throws Exception {
		String	ftaskid;
		DetachedCriteria query = DetachedCriteria.forClass(TTask.class);
		if(param.get("ftaskid") != null){
			ftaskid = oneVal(param,"ftaskid");
			if(Common.isNotNull(ftaskid)){
				query.add( Property.forName("ftaskid").eq( ftaskid )  );
			}
		}		
		return findPageByCriteria(query, null, 1000, 0);
	}

	public void addTask(Map param) throws Exception {
		TTask task = new TTask();
		task.setFjsondata(oneVal(param,"fjsondata"));
		task.setFtaskdesc(oneVal(param,"ftaskdesc"));
		task.setFtaskid(oneVal(param,"ftaskid"));
		task.setFtaskname(oneVal(param,"ftaskname"));
		this.updateEntity(task);
	}

	public void deleteTask(Map param) throws Exception {
		String	taskid = oneVal(param,"taskid");
		TTask task = (TTask) getEntity(TTask.class, taskid);
		if(task != null){
			this.delEntity( task );
		}
	}

	public void deleteInvalidData() throws Exception {
		Connection conn = this.currentSession().connection();
		DbDataUtil.executeUpdate(conn, "delete from T_TASK where ftaskid is null or ftaskid = ''");
		conn.close();
	}
	public void test() throws Exception{

//		Connection conn =  this.currentSession().connection();
//		String sql = "select aaa from T_XMLTYPE t";
//		PreparedStatement ps = conn.prepareStatement(sql);		
//		ResultSet set = ps.executeQuery();
//		set.next();
//		Statement stmt = set.getStatement();
//		Class oraclePreparedStatement = Class.forName("oracle.jdbc.OraclePreparedStatement");
//		java.sql.PreparedStatement st = (java.sql.PreparedStatement)(((DelegatingPreparedStatement)stmt).getInnermostDelegate());
//		if (!oraclePreparedStatement.isAssignableFrom(st.getClass()))
//		{
//		 
//		}			
//		//			now bind the string..
//		oracle.jdbc.OraclePreparedStatement ost = (oracle.jdbc.OraclePreparedStatement)st;
//	
//		oracle.jdbc.OracleResultSet orset=(oracle.jdbc.OracleResultSet)ost.getResultSet();
//							
////		oracle.jdbc.OracleResultSet orset = (oracle.jdbc.OracleResultSet)(set);		
//		oracle.sql.OPAQUE opt = orset.getOPAQUE("aaa");
//	
//		String xml = null;
//		if(opt!=null)	{
//		   XMLType poxml= XMLType.createXML(opt);
//		   if(poxml!=null)   {
//			  // get the XML as a string...
//			  xml = poxml.getStringVal();			
//			  if(xml!=null)  {
//				 xml = xml.trim();
//			  } // end of opt != null               
//		   } // end of poxml != null 
//		} // end of opt!=null
//		System.out.println(xml);
//		
//	

	}
	public Connection conn(){
		return this.currentSession().connection();
	}
}
