package com.wisetop.exchangedata.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wisetop.base.work.IWork;
import com.wisetop.npf.util.config.ConfigManager;
import com.wisetop.npf.util.db.DbDataUtil;

/**   
 *   
 * @author zh
 * create on	2012-9-11 下午10:12:45
 */
public class MainWorkDbTest {
	String	beansxml = System.getProperty("user.dir") + File.separator + "config"+ File.separator  + "beansxml.xml";;
	@Test
	public void testToDb(){
		try{
			ApplicationContext context = 
					new ClassPathXmlApplicationContext(
							new String[] { "file:///" + beansxml });
			IWork mainWork = (IWork) context.getBean("mainwork");
			mainWork.work();	
			Connection conn = ConfigManager.getConn("com.mysql.jdbc.Driver","jdbc:mysql://localhost:13306/todb","root","zcms");
			org.junit.Assert.assertTrue("zccatalog is failed",Long.parseLong( DbDataUtil.getOneResult(conn, "select count(*) from zccatalog") + "") > 0 );
			org.junit.Assert.assertTrue("zcarticle is failed",Long.parseLong( DbDataUtil.getOneResult(conn, "select count(*) from  zcarticle ") + "") > 0 );

		}catch(Exception e){
			e.printStackTrace();
			fail();
			
		}

	}
}
