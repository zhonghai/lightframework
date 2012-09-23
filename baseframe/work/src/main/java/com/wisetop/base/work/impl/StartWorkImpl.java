/**
 * 用于启动其它工作的类
 * @author zh
 * create on 2012-01-14
 */
package com.wisetop.base.work.impl;

import org.apache.log4j.Logger;

import com.wisetop.base.work.WorkParent;

public class StartWorkImpl extends WorkParent {
	protected static Logger log = Logger.getLogger(StartWorkImpl.class.getName());

	
	public StartWorkImpl(String	name){
		this.name = name;
	}
	
	public void work() throws Exception {
		try{
			log(" this.getName:" + this.getName());
			process.process();
		}catch(Exception e){
			e.printStackTrace();
			log.error(this.getName() + " work failed!");		
			throw e;
		}
	}
}
