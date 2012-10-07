package com.wisesource.product.eddm.action;

import com.wisesource.product.eddm.dao.TaskDao;
import com.wisesource.struts2action.ActionTestCase;

public class XmlTypeTest extends ActionTestCase{

	/**
	 * @param args
	 */
	public void test()  throws Exception {
		TaskDao taskDao = (TaskDao) applicationContext.getBean("taskDao");
		taskDao.test();
//		oracle.i18n.text.converter.CharacterConverterOGS d = null;
//		d.getInstance(1);
	}

}
