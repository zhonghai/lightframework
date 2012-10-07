package com.wisesource.product.eddm.action;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.impl.SessionFactoryImpl;

import com.wisesource.product.eddm.domain.TTask;
import com.wisesource.product.eddm.services.TaskService;
import com.wisesource.struts2action.ActionTestCase;
import com.wisetop.npf.util.PaginationSupport;

public class TaskActionTest extends ActionTestCase {
	private String srslt;

	public void testAddTask() {
		String opResult;
		try {
			String	json = "  {\"id\":null,\"name\":null,\"count\":32,\"nodes\":[    {\"id\":\"node_1\",\"name\":\"node_1\",\"type\":\"node\",\"shape\":\"imgdb\",\"typeex\":\"dbconn\",\"number\":1,\"left\":14,\"top\":41,\"width\":75,\"height\":70,\"property\":    [        {\"id\":\"n_p_dbConnId\",\"text\":\"input\",\"value\":\"v9-src\"        },        {\"id\":\"n_p_driver\",\"text\":\"select\",\"value\":\"com.mysql.jdbc.Driver\"        },        {\"id\":\"n_p_url\",\"text\":\"select\",\"value\":\"jdbc:mysql:\\/\\/127.0.0.1\\/zcms?useUnicode=true&characterEncoding=utf-8\"        },        {\"id\":\"n_p_username\",\"text\":\"input\",\"value\":\"root\"        },        {\"id\":\"n_p_password\",\"text\":\"input\",\"value\":\"zcms\"        },        {\"id\":\"n_p_test\",\"text\":\"input\",\"value\":\"\"        }    ]    },    {\"id\":\"test-id1\",\"name\":\"node_2\",\"type\":\"start\",\"shape\":\"oval\",\"typeex\":\"newTask\",\"number\":2,\"left\":80,\"top\":280,\"width\":20,\"height\":20,\"property\":null    },    {\"id\":\"node_3\",\"name\":\"node_3\",\"type\":\"node\",\"shape\":\"img\",\"typeex\":\"task\",\"number\":3,\"left\":241,\"top\":195,\"width\":75,\"height\":70,\"property\":    [        {\"id\":\"n_p_TaskDbConnId\",\"text\":\"input\",\"value\":\"v9-src\"        },        {\"id\":\"n_p_taskName\",\"text\":\"input\",\"value\":\"v9-src\"        },        {\"id\":\"n_p_Sql\",\"text\":\"textarea\",\"value\":\"select * from aa\"        },        {\"id\":\"n_p_keys\",\"text\":\"input\",\"value\":\"\"        },        {\"id\":\"n_p_foreignkey\",\"text\":\"input\",\"value\":\"\"        }    ]    },    {\"id\":\"node_5\",\"name\":\"node_5\",\"type\":\"node\",\"shape\":\"img\",\"typeex\":\"task\",\"number\":5,\"left\":241,\"top\":360,\"width\":75,\"height\":70,\"property\":    [        {\"id\":\"n_p_TaskDbConnId\",\"text\":\"input\",\"value\":\"v9-src\"        },        {\"id\":\"n_p_taskName\",\"text\":\"input\",\"value\":\"v9-src\"        },        {\"id\":\"n_p_Sql\",\"text\":\"textarea\",\"value\":\"v9-src\"        },        {\"id\":\"n_p_keys\",\"text\":\"input\",\"value\":\"\"        },        {\"id\":\"n_p_foreignkey\",\"text\":\"input\",\"value\":\"\"        }    ]    },    {\"id\":\"node_7\",\"name\":\"node_7\",\"type\":\"node\",\"shape\":\"rect\",\"typeex\":\"process\",\"number\":7,\"left\":420,\"top\":176,\"width\":100,\"height\":40,\"property\":    [        {\"id\":\"n_p_in\",\"text\":\"input\",\"value\":\"1\"        },        {\"id\":\"n_p_out\",\"text\":\"input\",\"value\":\"1\"        }    ]    },    {\"id\":\"node_8\",\"name\":\"node_8\",\"type\":\"end\",\"shape\":\"oval\",\"typeex\":\"doinsert\",\"number\":8,\"left\":658,\"top\":116,\"width\":20,\"height\":20,\"property\":    [        {\"id\":\"n_p_destTbl\",\"text\":\"input\",\"value\":\"asdf\"        },        {\"id\":\"n_p_destKeyFields\",\"text\":\"input\",\"value\":\"1\"        },        {\"id\":\"n_p_fields\",\"text\":\"input\",\"value\":\"aa\"        },        {\"id\":\"n_p_uptFields\",\"text\":\"input\",\"value\":\"aa\"        },        {\"id\":\"n_p_allowUpdate\",\"text\":\"input\",\"value\":\"1\"        },        {\"id\":\"n_p_doFlag\",\"text\":\"select\",\"value\":\"beforedo\"        }    ]    },    {\"id\":\"node_10\",\"name\":\"node_10\",\"type\":\"node\",\"shape\":\"rect\",\"typeex\":\"process\",\"number\":10,\"left\":432,\"top\":359,\"width\":100,\"height\":40,\"property\":    [        {\"id\":\"n_p_in\",\"text\":\"input\",\"value\":\"2\"        },        {\"id\":\"n_p_out\",\"text\":\"input\",\"value\":\"2\"        }    ]    },    {\"id\":\"node_11\",\"name\":\"node_11\",\"type\":\"end\",\"shape\":\"oval\",\"typeex\":\"doinsert\",\"number\":11,\"left\":664,\"top\":352,\"width\":20,\"height\":20,\"property\":    [        {\"id\":\"n_p_destTbl\",\"text\":\"input\",\"value\":\"aa\"        },        {\"id\":\"n_p_destKeyFields\",\"text\":\"input\",\"value\":\"aa\"        },        {\"id\":\"n_p_fields\",\"text\":\"input\",\"value\":\"aaa\"        },        {\"id\":\"n_p_uptFields\",\"text\":\"input\",\"value\":\"aaa\"        },        {\"id\":\"n_p_allowUpdate\",\"text\":\"input\",\"value\":\"1\"        },        {\"id\":\"n_p_doFlag\",\"text\":\"select\",\"value\":\"lastdo\"        }    ]    },    {\"id\":\"node_27\",\"name\":\"node_27\",\"type\":\"node\",\"shape\":\"imgdb\",\"typeex\":\"dbconn\",\"number\":27,\"left\":745,\"top\":520,\"width\":75,\"height\":70,\"property\":    [        {\"id\":\"n_p_dbConnId\",\"text\":\"input\",\"value\":\"dest\"        },        {\"id\":\"n_p_driver\",\"text\":\"select\",\"value\":\"com.mysql.jdbc.Driver\"        },        {\"id\":\"n_p_url\",\"text\":\"select\",\"value\":\"jdbc:mysql:\\/\\/127.0.0.1\\/zcms?useUnicode=true&characterEncoding=utf-8\"        },        {\"id\":\"n_p_username\",\"text\":\"input\",\"value\":\"\"        },        {\"id\":\"n_p_password\",\"text\":\"input\",\"value\":\"\"        },        {\"id\":\"n_p_test\",\"text\":\"input\",\"value\":\"\"        }    ]    }],\"lines\":[    {\"id\":\"line_19\",\"name\":\"line_19\",\"type\":\"line\",\"shape\":\"line\",\"typeex\":\"line\",\"number\":19,\"from\":\"node_2\",\"to\":\"node_3\",\"fromx\":100,\"fromy\":290,\"tox\":261,\"toy\":212.5,\"polydot\":    [    ],\"property\":null    },    {\"id\":\"line_20\",\"name\":\"line_20\",\"type\":\"line\",\"shape\":\"line\",\"typeex\":\"line\",\"number\":20,\"from\":\"node_3\",\"to\":\"node_7\",\"fromx\":296,\"fromy\":212.5,\"tox\":420,\"toy\":216,\"polydot\":    [    ],\"property\":null    },    {\"id\":\"line_21\",\"name\":\"line_21\",\"type\":\"line\",\"shape\":\"line\",\"typeex\":\"line\",\"number\":21,\"from\":\"node_7\",\"to\":\"node_8\",\"fromx\":520,\"fromy\":176,\"tox\":658,\"toy\":126,\"polydot\":    [    ],\"property\":null    },    {\"id\":\"line_23\",\"name\":\"line_23\",\"type\":\"line\",\"shape\":\"line\",\"typeex\":\"line\",\"number\":23,\"from\":\"node_10\",\"to\":\"node_11\",\"fromx\":532,\"fromy\":359,\"tox\":664,\"toy\":362,\"polydot\":    [    ],\"property\":null    },    {\"id\":\"line_24\",\"name\":\"line_24\",\"type\":\"line\",\"shape\":\"line\",\"typeex\":\"line\",\"number\":24,\"from\":\"node_5\",\"to\":\"node_10\",\"fromx\":296,\"fromy\":377.5,\"tox\":432,\"toy\":379,\"polydot\":    [    ],\"property\":null    },    {\"id\":\"line_26\",\"name\":\"line_26\",\"type\":\"line\",\"shape\":\"line\",\"typeex\":\"line\",\"number\":26,\"from\":\"node_2\",\"to\":\"node_5\",\"fromx\":100,\"fromy\":290,\"tox\":261,\"toy\":377.5,\"polydot\":    [    ],\"property\":null    },    {\"id\":\"line_29\",\"name\":\"line_29\",\"type\":\"line\",\"shape\":\"polyline\",\"typeex\":\"line\",\"number\":29,\"from\":\"node_8\",\"to\":\"node_27\",\"fromx\":678,\"fromy\":126,\"tox\":782.5,\"toy\":520,\"polydot\":    [        {\"x\":782.5,\"y\":126        }    ],\"property\":null    },    {\"id\":\"line_31\",\"name\":\"line_31\",\"type\":\"line\",\"shape\":\"polyline\",\"typeex\":\"line\",\"number\":31,\"from\":\"node_11\",\"to\":\"node_27\",\"fromx\":684,\"fromy\":362,\"tox\":782.5,\"toy\":520,\"polydot\":    [        {\"x\":782.5,\"y\":362        }    ],\"property\":null    }]} ";
			request.addParameter("param.ftaskid", "test-id1");
			request.addParameter("param.ftaskname", "test-task");
			request.addParameter("param.ftaskdesc", "test-task");
			request.addParameter("param.fjsondata", json);
			srslt = executeAction("/task_addTask.action");
			opResult = (String) findValueAfterExecute("opResult");			
			if(opResult.equals(Constant.FAIL)){
				System.out.println(findValueAfterExecute("errmsg"));
			}
			assertEquals(Constant.SUCCESS,opResult);
			

		} catch (Exception e) {
			fail();
		}
	}

	public void testFetchAllTask() {
		String opResult;
		Map rtnData;
		try {
			srslt = executeAction("/task_fetchTask.action");
			opResult = (String) findValueAfterExecute("opResult");
			if(opResult.equals(Constant.FAIL)){
				System.out.println(findValueAfterExecute("errmsg"));
			}						
			rtnData = (Map) findValueAfterExecute("rtnData");
			PaginationSupport ps = (PaginationSupport) rtnData.get("task");
			assertTrue(ps.getItems().size() >= 1);
			assertEquals(Constant.SUCCESS,opResult);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	public void testDelTask() {
		String	opResult ;
		try {
			request.addParameter("param.taskid", "test-id1");
			srslt = executeAction("/task_delTask.action");
			opResult = (String) findValueAfterExecute("opResult");
			if(opResult.equals(Constant.FAIL)){
				System.out.println(findValueAfterExecute("errmsg"));
			}					
			assertEquals(Constant.SUCCESS,opResult);
		} catch (Exception e) {
			fail();
		}
	}
	public void testJson(){
		try{
			String	fjsondata = "";
			Map param = new HashMap();

			TaskService taskService = (TaskService) applicationContext.getBean("taskService");
			 PaginationSupport ps = taskService.fetchTask(param);
			 List<TTask> tasks = ps.getItems();
			 for(TTask task:tasks){
				 System.out.println(task.getFjsondata());
			 }
			//
		}catch(Exception e){			
			e.printStackTrace();
		}

	}
	public void testDb(){
		try{
			Connection conn = null;
			org.hibernate.impl.SessionFactoryImpl taskDao = (SessionFactoryImpl) applicationContext.getBean("sessionFactory");
			Session s = taskDao.openSession();
			conn = s.connection() ;
//			Properties p = taskDao.getCurrentSession()
//			for(Object o:p.keySet()){
//				System.out.println("key:" + o + "/" +  p.get(o) );
//			}

			System.out.println(conn.getAutoCommit());			
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
