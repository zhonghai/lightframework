package com.wisetop.exchangedata.main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.wisetop.base.work.IWork;
import com.wisetop.base.work.WorkParent;
import com.wisetop.base.work.impl.DbDoInsertOrUpdate;
import com.wisetop.base.work.impl.DbTransportWorkImpl;
import com.wisetop.base.work.set.SetIDoPropertiesFromProcess;
import com.wisetop.exchangedata.process.DbProcessImpl;
import com.wisetop.npf.util.config.ConfigManager;
import com.wisetop.npf.util.db.DbDataUtil;

public class MainWorkDB extends WorkParent implements IWork {	
	protected static Logger log = Logger.getLogger(MainWorkDB.class.getName());
	private List<IWork> childWorks ;
	private DataSource dataSource;
	private Connection conn;
	private Map<String,Connection> connMap = new HashMap();		// 数据来源连接
	
	@Override
	public void work() throws Exception {
		conn = dataSource.getConnection();
		childWorks = getChildWorks();
		System.out.println("childWorks.size():" + childWorks.size());
		for(IWork work:childWorks){
			work.work();
		}
	}

	@Override
	public void addChildWork(IWork arg0) {
		
	}

	@Override
	public List getChildWorks() {
		if(childWorks == null ) childWorks = buildMyTask();
		return childWorks;
	}

	private List buildMyTask() {
		
		List childWorks  = new ArrayList();
		try{
			String	 sql = "select w.* from t_mytask m,t_task t,t_work w where m.ftaskid = t.ftaskid and t.ftaskid = w.ftaskid and fpworkid is null";
			List<Map> rows = DbDataUtil.getRowsDataToMap(conn, sql, null, 20, 0);
			for(Map row:rows){
				String	workid = (String) row.get("fworkid");
				DbTransportWorkImpl work = new DbTransportWorkImpl();
				childWorks.add(work);
				setChildWorks(work,workid);	
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}

		return childWorks;
	}
	
	private void setChildWorks(DbTransportWorkImpl work,String workid) throws Exception{
		String	sql = "select * from t_work where fworkid = :fworkid";
		Map param = new HashMap();
		param.put("fworkid", workid);
		List<Map> rows = DbDataUtil.getRowsDataToMap(conn, sql, param, 20, 0);
		Map row = rows.get(0);
		
		
		work.setKeys(toStr(row.get("keys") ));
		work.setForeignkey(toStr( row.get("foreignkey") ));
		work.setName(toStr( row.get("fworkid") ));
		work.setSql(toStr( row.get("fsql") ));
		setWorkConn(work,toStr( row.get("datasource") ));
		
		sql = "select * from t_work where fpworkid=:fworkid";
		rows = DbDataUtil.getRowsDataToMap(conn, sql, param, 20, 0);
		for(Map r:rows){
			DbTransportWorkImpl wk = new DbTransportWorkImpl();
			work.getChildWorks().add(wk);
			wk.setParent(work);
			setChildWorks(wk,r.get("fworkid").toString());	
		}
		setProcess(work);
	}
	private void setWorkConn(DbTransportWorkImpl work,String connId) throws Exception{
		Connection workConn = null;
		String	sql = "select * from t_dbconnection where fconnid=:fconnid";
		String	fworkid = work.getName();
		Map param = new HashMap();
		param.put("fconnid", connId);	
		List<Map> rows = DbDataUtil.getRowsDataToMap(conn, sql, param, 20, 0);
		Map row = rows.get(0);
		workConn =  this.connMap.get(connId);
		if( workConn == null){
			String	fdriver = toStr( row.get("fdriver")) ;
			String furl =toStr(  row.get("furl")) ;
			String	fusername = toStr( row.get("fusername")) ;
			String	fpassword = toStr( row.get("fpassword")) ;
			log.debug("fdriver:" + fdriver + "/furl:" + furl + "/fusername:" + fusername +  "/fpassword:" + fpassword );
			workConn = ConfigManager.getConn(fdriver,furl,fusername,fpassword);
			workConn.setAutoCommit(false);
			this.connMap.put(connId, workConn);
		}
		work.setConn(workConn);
	}
	
	private String toStr(Object str){
		if(str == null){
			return "";
		}else{
			return str.toString();
		}
	}
	private void setProcess(DbTransportWorkImpl work) throws Exception{
		String	sql = "select * from t_process where fworkid=:fworkid";
		String	fworkid = work.getName();
		Map param = new HashMap();
		param.put("fworkid", fworkid);
		log.debug("sql:" + sql + "/workid:" + fworkid);
		List<Map> rows = DbDataUtil.getRowsDataToMap(conn, sql, param, 20, 0);
		Map row = rows.get(0);
		DbProcessImpl dp = new DbProcessImpl();
		work.setProcess(dp);

		dp.setProcessId(row.get("fprocessid").toString());
		setAfterDo(dp);
//		dp.setAfterDo(afterDo);
//		dp.setBeforeDo(beforeDo);
//		dp.setConnDSMap(connDSMap);
//		dp.setConnMap(connMap);
		if(work.getParent() == null){
			dp.setIfCommit(true);
		}
		
		dp.setWork(work);
	}
	
	private void setAfterDo(DbProcessImpl dp) throws  Exception{
		String	 sql = "select * from t_process_do where flag = 'beforedo' and fprocessid=:fprocessid";
		Map param = new HashMap();
		param.put("fprocessid", dp.getProcessId());
		List<Map> rows = DbDataUtil.getRowsDataToMap(conn, sql, param, 20, 0);
		for(Map row:rows){
			DbDoInsertOrUpdate dbdo  = new DbDoInsertOrUpdate();
			dbdo.setMyprocess(dp);
			dp.getBeforeDo().add(dbdo);
			dbdo.setDoid(row.get("fdoid").toString());
			setDo(dp,dbdo);
		}
	}
	private void setDo(DbProcessImpl dp,DbDoInsertOrUpdate d) throws Exception{
		String sql = "select * from t_process_do_boa where fdoid=:fdoid";
		Map param = new HashMap();
		param.put("fdoid", d.getDoid() );
		List<Map> rows = DbDataUtil.getRowsDataToMap(conn, sql, param, 20, 0);
		Map row = rows.get(0);
		
		d.setOnlyUpdate("1".equals(row.get("onlyupdate")));		
		d.setAllowUpdate("1".equals(row.get("allowupdate")));
		
		d.setConnNm(row.get("connnm").toString());
		setConn(dp,d,d.getConnNm());
		
		List setList = new ArrayList();
		SetIDoPropertiesFromProcess set = new SetIDoPropertiesFromProcess();
		set.setFields("data->data");
		setList.add(set);
		d.setSetProperties(setList);	
		d.setFields(row.get("fields").toString());
		d.setKeyFields(row.get("keyfields").toString());		
		d.setTbl(row.get("tbl").toString());
		d.setUptFields(row.get("uptfields").toString());
		
	}
	@Override
	public String getName() {		
		return "mainwork";
	}

	private void setConn(DbProcessImpl dp,DbDoInsertOrUpdate d,String connId) throws Exception{
		log.debug("setConn connId:" + connId);
		Connection pConn = dp.getConnMap().get(connId) ;
		if( pConn == null){
			String sql = "select * from t_dbconnection where fconnid=:fconnid";
			Map param = new HashMap();
			param.put("fconnid", connId );
			List<Map> rows = DbDataUtil.getRowsDataToMap(conn, sql, param, 20, 0);			
			Map row = rows.get(0);	
			String	fdriver = toStr(row.get("fdriver"));
			String furl = toStr(row.get("furl"));
			String	fusername = toStr(row.get("fusername"));
			String	fpassword = toStr(row.get("fpassword"));
			pConn = ConfigManager.getConn(fdriver,furl,fusername,fpassword);
			pConn.setAutoCommit(false);
			dp.getConnMap().put(connId, pConn);
		}
		d.setConn(pConn);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
}
