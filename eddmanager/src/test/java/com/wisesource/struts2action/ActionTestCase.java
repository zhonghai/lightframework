package com.wisesource.struts2action;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Struts2的Action类测试基础类。
 * 
 * @author xgj
 *
 */
public class ActionTestCase   extends StrutsSpringTestCase {
	protected static ApplicationContext applicationContext;
	
	@Override
	public String getContextLocations() {
		//StrutsSpringTestCase是StrutsTestCase的子类，这个单元测试默认读取配置文件applicationContext.xml的位置是类路径的根目录，
		//如果你把这个文件放在不同位置或者取了一个不同的名称可以通过覆盖父类中的getContextLocations()来指定你的配置文件。
		return "classpath*:applicationContext-*.xml";
	}

	
	@Override
	protected void setupBeforeInitDispatcher() {
		try {
		GenericXmlContextLoader xmlContextLoader = new GenericXmlContextLoader();
		applicationContext = xmlContextLoader.loadContext( getContextLocation() );
		servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				applicationContext);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getContextLocation() {
		/*
		URL classUrl = SpringBeanFactoryMock.class.getResource("");
		String path = classUrl.getPath();
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		path = path.substring(1, path.indexOf("WEB-INF")) + "WEB-INF/";
		File configPath = new File(path);
		String[] applicationContexts = configPath.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().startsWith("applicationcontext")) {
					return true;
				}
				return false;
			}
		});
		for (int i = 0; i < applicationContexts.length; i++) {
			applicationContexts[i] = "file:" + path + applicationContexts[i];
		}
		return applicationContexts;
		*/
		
		return new String[]{"classpath*:applicationContext-action-servlet.xml"
				,"classpath*:applicationContext-dao.xml"
				,"classpath*:applicationContext-hibernate.xml"
				,"classpath*:applicationContext-service.xml"
				};
	}
	

}
