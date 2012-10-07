package com.wisesource.product.eddm.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wisesource.product.eddm.dao.TaskDao;
import com.wisesource.product.eddm.domain.ModelComponent;
import com.wisesource.product.eddm.domain.TDbconnection;
import com.wisesource.product.eddm.domain.TProcess;
import com.wisesource.product.eddm.domain.TProcessDbconn;
import com.wisesource.product.eddm.domain.TProcessDbconnId;
import com.wisesource.product.eddm.domain.TProcessDo;
import com.wisesource.product.eddm.domain.TProcessDoBoa;
import com.wisesource.product.eddm.domain.TWork;
import com.wisesource.product.eddm.services.TaskService;
import com.wisesource.product.eddm.util.Param;
import com.wisetop.npf.util.Common;
import com.wisetop.npf.util.PaginationSupport;
import com.wisetop.npf.util.db.AbstractCommonDAO;

public class TaskServiceImpl implements TaskService {
	private static Logger log = Logger.getLogger(TaskServiceImpl.class);
	private TaskDao taskDao;
	private AbstractCommonDAO abstractCommonDAO;
	
	public PaginationSupport fetchTask(Map param) throws Exception {
		return taskDao.fetchTask(param);
	}

	public void addTask(Map param) throws Exception {
		//this.taskDao.test();
		String	ftaskid = Param.oneVal(param,"ftaskid");
		String	fjsondata = Param.oneVal(param,"fjsondata");
		log.debug("fjsondata:" + fjsondata );
		ModelComponent mc = null;
		if(Common.isNull(fjsondata)) throw new Exception("fjsondata is null");		

		/** del his begin **/
		String	sql;
		Map sqlparam = new HashMap();
		sqlparam.put("ftaskid", ftaskid + "%");
		sql = "delete from t_dbconnection c where exists(select * from t_process_dbconn d where d.fconnid = c.fconnid and d.fprocessid like :ftaskid ) ";
		abstractCommonDAO.executeSqlQuery(sql, sqlparam);
		sql = "delete from t_dbconnection c where exists(select * from t_work w where w.datasource = c.fconnid and w.fworkid  like :ftaskid ) ";
		abstractCommonDAO.executeSqlQuery(sql, sqlparam);		
		sql = "delete from t_process_dbconn c where c.fprocessid  like :ftaskid  ";
		abstractCommonDAO.executeSqlQuery(sql, sqlparam);		
		sql = "delete from t_process_do c where c.fprocessid  like :ftaskid  ";
		abstractCommonDAO.executeSqlQuery(sql, sqlparam);	
		sql = "delete from t_process_do_boa c where c.fdoid  like :ftaskid  ";
		abstractCommonDAO.executeSqlQuery(sql, sqlparam);		
		sql = "delete from t_process c where c.fprocessid  like :ftaskid ";
		abstractCommonDAO.executeSqlQuery(sql, sqlparam);	
		sql = "delete from t_work c where c.fworkid  like :ftaskid ";
		abstractCommonDAO.executeSqlQuery(sql, sqlparam);			
		/** del his end **/
		
		mc = parseJson(ftaskid,fjsondata);
		
		abstractCommonDAO.updateEntities(mc.getWorks());
		abstractCommonDAO.updateEntities(mc.getProcesses());
		abstractCommonDAO.updateEntities(mc.getProcessDoes() ); 
		abstractCommonDAO.updateEntities(mc.getDoBoaes() );
		abstractCommonDAO.updateEntities( mc.getProcessDbConn() );
		abstractCommonDAO.updateEntities(new ArrayList( mc.getDbConn().values() ) );		
		taskDao.addTask(param);			
	}

	private void clearAllHis(String taskid) throws Exception{
			
		
	}
	private ModelComponent parseJson(String ftaskid,String jsonData) throws Exception{
		Map param = new HashMap();
		Map index = new HashMap();
		Map data = new HashMap();
		String	startWork = null ;
		ModelComponent mc = new ModelComponent();

		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONArray nodes =  (JSONArray) jsonObject.get("nodes");
		JSONArray lines =  (JSONArray) jsonObject.get("lines");
		for(Object lineObj:lines){
			JSONObject line = (JSONObject) lineObj;
			ArrayList al = (ArrayList) (Common.nvl(  index.get( line.get("from")  )   ,ArrayList.class));
			al.add( line.get("to") );
			index.put(line.get("from") , al );					
		}
		for(Object nodeObj:nodes){
			JSONObject node = (JSONObject) nodeObj;
			data.put(node.get("id"), node);
			if(node.get("type").equals("start")) startWork = (String) node.get("id");
		}
		mc.setIndex(index);
		mc.setStartWork(startWork);
		parseTask(ftaskid,index,data,mc);
		return mc;
	}
	private void parseTask(String taskid,Map index,Map data,ModelComponent mc) throws Exception{
		List<String> nodes = (List) index.get(taskid);
		if(nodes == null) return;
		for(String workid:nodes){
			parseWork(taskid,null,workid,index,data,mc);
		}
	}
	
	private void parseWork(String taskid,String fpworkid,String workid,Map index,Map<String,JSONObject> data,ModelComponent mc) throws Exception{
						
			JSONObject node = (JSONObject) data.get(workid);
			String	typeex = node.getString("typeex");
			if( ! ("task".equals(typeex)) ) throw new Exception("typeex not task");
			List works = mc.getWorks();
			TWork work = new TWork();
			JSONArray property = node.getJSONArray("property");
			work.setFpworkid(fpworkid);
			String	connId = findPropertie(property,"n_p_TaskDbConnId");
			String	pConnid = taskid + "_" + connId;
			addDbConn(pConnid,connId,data,mc);
			work.setDatasource( pConnid );
			work.setFprocessid(""); //
			work.setFsql( findPropertie(property,"n_p_Sql") );
			work.setFtaskid(taskid);
			work.setFworkid(taskid + "_" + workid );
			work.setFworkname(  findPropertie(property,"n_p_taskName") );
			work.setKeys( findPropertie(property,"n_p_keys")  );
			work.setForeignkey(  findPropertie(property,"n_p_foreignkey")  );			
			works.add(work);
			parseProcess(taskid,workid,work,index,data,mc);
	}
	
	private void addDbConn(String pConnId,String connId,Map<String,JSONObject> data,ModelComponent mc) throws Exception{
		log.debug("addDbConn connId:" + connId);
		if(mc.getDbConn().get(pConnId) == null){
			JSONObject conn = data.get(connId);
			TDbconnection dbConn = new TDbconnection();
			dbConn.setFconnid( pConnId );
			JSONObject connNode = (JSONObject) data.get( connId );
			if(connNode == null) throw new Exception("connId is not exists");
			JSONArray connProperty = connNode.getJSONArray("property");
			dbConn.setFdriver( findPropertie(connProperty,"n_p_driver") );				
			dbConn.setFname(connNode.getString("name") );				
			dbConn.setFurl( findPropertie(connProperty,"n_p_url") );
			dbConn.setFusername( findPropertie(connProperty,"n_p_username") );
			dbConn.setFpassword( findPropertie(connProperty,"n_p_password") );
			mc.getDbConn().put(pConnId, dbConn);
		}
		
		
	}
	
	private void parseProcess(String taskid, String workid,TWork work,Map index,Map data,ModelComponent mc) throws Exception{
		List<String> nodes = (List) index.get( workid );
		String		myprocess = null ;
		if(nodes == null || nodes.size() < 1) return;
		int cx = 0;
		for(String key:nodes){
			JSONObject node = (JSONObject) data.get(key);
			if("process".equals( node.getString("typeex") ) ) {
				myprocess = key;
				cx ++;
			}
		}
		
		if(cx > 1) throw new Exception("one work one process:" + workid + "/" + nodes);
		if(cx < 0 ) throw new Exception("havn't process:" + workid );

		for(String processNodeId:nodes){
			JSONObject node = (JSONObject) data.get(processNodeId);
			if("process".equals( node.getString("typeex") ) ) {
				String	processId = taskid + "_" + processNodeId;
				List processes = mc.getProcesses();
				TProcess process = new TProcess();
				work.setFprocessid( processId );
				process.setFprocessid( processId );
				process.setFworkid(work.getFworkid());
				processes.add(process);
				parseDo(taskid,processNodeId,process,index,data,mc);
			}else if( "task".equals( node.getString("typeex") ) ){
				parseWork(taskid,work.getFworkid(),processNodeId,index,data,mc);
			}else{
				throw new Exception("typeex is not defiend here!");
			}
		}		

	}
	
	private void parseDo(String taskid,String processNodeId,TProcess process,Map index,Map data,ModelComponent mc) throws Exception{
		List<String> nodes = (List) index.get(processNodeId);

		if(nodes == null) return;
		for(String n:nodes){
			JSONObject node = (JSONObject) data.get(n);
			String	typeex = node.getString("typeex");
			if( (node.get("property") == null) || ("null".equals( node.get("property").toString() )) ) throw new Exception("property can not be null:" + n);
			JSONArray property =node.getJSONArray("property") ;
			if("doinsert".equals(typeex) ){
				List processDoes = mc.getProcessDoes();
				List doBoaes = mc.getDoBoaes();
				String	doid = taskid + "_" + n;
				TProcessDo pd = new TProcessDo();
				TProcessDoBoa pdb = new TProcessDoBoa();
				pd.setFdoid( doid );
				pd.setFlag( findPropertie(property,"n_p_doFlag") );
				pd.setFprocessid(process.getFprocessid());
				
				List dbConnLst = (List) index.get( n );
				if(dbConnLst == null ) throw new Exception("dbConn is null:" + n);
				if(dbConnLst.size() == 0 ) throw new Exception("dbConn.size() == 0");
				if(dbConnLst.size() > 1 ) throw new Exception(" dbConn.size() > 1 ");
				String	connId = dbConnLst.get(0).toString();
				String	pConnId = taskid + "_" + connId;
				TProcessDbconn pdc = new TProcessDbconn();
				TProcessDbconnId id = new TProcessDbconnId ();				
				id.setFconnid( pConnId );
				id.setFprocessid(process.getFprocessid());
				pdc.setId(id);
				
				addDbConn(pConnId,connId,data,mc);			
				
				pdb.setConnnm(  pConnId );
				
				pdb.setFdoboaid( doid + "_" + findPropertie(property,"n_p_doFlag") );
				pdb.setFdoid( doid );
				pdb.setKeyfields(findPropertie(property,"n_p_destKeyFields"));
				pdb.setAllowupdate(findPropertie(property,"n_p_allowUpdate"));
				pdb.setOnlyupdate("0");
				pdb.setTbl(findPropertie(property,"n_p_destTbl"));
				pdb.setUptfields( findPropertie(property,"n_p_uptFields"));
				pdb.setFields(  findPropertie(property,"n_p_fields") ) ;
				processDoes.add(pd);
				doBoaes.add(pdb);
				
				boolean	bPDExists = false;
				for(TProcessDbconn c:mc.getProcessDbConn()){
					if(c.getId().equals(pdc.getId())) {
						bPDExists = true;
						break;
					}
				}
				if(! bPDExists){
					mc.getProcessDbConn().add(pdc);
				}
			}else{
				throw new Exception("typeex not defined:" + typeex );
			}
		}		
	}
	
	private String findPropertie(JSONArray property,String id){
		for( Object o: property){
			JSONObject obj = (JSONObject) o;
			if( id.equals( obj.getString("id")) ) return obj.getString("value");
		}
		return null;
	}
	public void deleteTask(Map param) throws Exception {
		taskDao.deleteTask(param);
	}
	public void deleteInvalidData() throws Exception {
		taskDao.deleteInvalidData();
	}
	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setAbstractCommonDAO(AbstractCommonDAO abstractCommonDAO) {
		this.abstractCommonDAO = abstractCommonDAO;
	}
}
