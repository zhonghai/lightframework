package com.wisetop.exchangedata.main;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wisetop.base.work.IWork;
import com.wisetop.npf.util.config.ConfigManager;
import com.wisetop.npf.util.error.ExceptionUtil;
/**   
 *   
 * @author zh
 * create on	2012-8-16 下午2:46:42
 */
public class MainWork implements Runnable {
	public static final String configfile =  System.getProperty("user.dir") + File.separator + "config"+ File.separator  + "config.properties";
	public static final String beansxml =  System.getProperty("user.dir") + File.separator + "config"+ File.separator  + "beansxml.xml";
	public static  final String log4jfile = System.getProperty("user.dir") + File.separator + "config"+ File.separator + "log4j.properties";
	private static Logger log = null;
	private List<IWork> workList = null;
	
	public static void main(String[] args){
		MainWork mw = new MainWork();
		mw.run();
	}
	
	public MainWork(){		
		if(!(new File(log4jfile).exists())){
			System.out.println("log4j.properties file is not exists,system is will exit!");			
			JOptionPane.showConfirmDialog(null,
					"log4j.properties 文件不存在,程序不能运行!", "系统提示", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			System.exit(0) ;
		}		

		// 设置log4j.properties文件位置　
		PropertyConfigurator.configure(log4jfile);		
		ConfigManager.setBundle(configfile);
		log = Logger.getLogger(MainWork.class);
		log.debug("log4jfile:" + log4jfile);
		log.debug("beanxml:" + beansxml);
		log.debug("log4jfile:" + log4jfile);
	}
	public void run() {
		String	intervalTime = ConfigManager.getString("intervalTime");
		if(StringUtils.isEmpty(intervalTime)) intervalTime = "6000";
		
		log.debug("程序启动正在...");
		try{
			Thread.sleep(6000);
			while(true){
				ApplicationContext context = 
						new ClassPathXmlApplicationContext(
								new String[] { "file:///" + beansxml });
				IWork mainWork = (IWork) context.getBean("mainwork");
				mainWork.work();		
					
				mainWork  = null;		
				log.debug("程序休息:" + intervalTime);
				Thread.sleep(Long.valueOf(intervalTime));				
			}
		}catch(Exception e){
			log.debug(ExceptionUtil.getExceptionMsg(e));
		}			
	}	

}
