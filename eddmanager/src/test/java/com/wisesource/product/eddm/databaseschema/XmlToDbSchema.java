package com.wisesource.product.eddm.databaseschema;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import org.junit.Test;

import com.wisetop.npf.util.classop.ClassUtil;
import com.wisetop.npf.util.db.DbDataUtil;

public class XmlToDbSchema {

	/**
	 * @param args
	 */

	public static void main(String[] args) throws Exception {
		test();
	}
	@Test
	public static void test() {
		try{
			System.out.println("begin");
		ResourceBundle rb = ResourceBundle.getBundle("jdbc");
		Class.forName( rb.getString("datasource.driverClassName") );
		Connection conn =  DriverManager.getConnection(	 rb.getString("datasource.url") );
		//System.out.println( Thread.currentThread().getClass().getResource("/DatabaseSchema.xml").getPath() );
		System.out.println(new File("./src/main/resources/DatabaseSchema.xml").getAbsolutePath());
		DbDataUtil.xmlToDbSchema(conn, new File("./src/main/resources/DatabaseSchema.xml").getAbsolutePath());
		conn.close();
		System.out.println("over");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
