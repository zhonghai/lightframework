package com.wisetop.exchangedata.main;

import java.sql.Connection;

import com.wisetop.npf.util.config.ConfigManager;

public class TestDb {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
			Connection conn = ConfigManager.getConn("com.mysql.jdbc.Driver","jdbc:mysql://localhost:13306/todb?useUnicode=true","root","zcms");
	}

}
