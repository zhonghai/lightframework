/**
 * @author zh
 * 用于下载有权限控制的网页
 */
package com.wisetop.base.work.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.wisetop.base.work.IWork;
import com.wisetop.base.work.IWorkProcess;
import com.wisetop.base.work.WorkParent;
import com.wisetop.base.work.set.ISet;
import com.wisetop.npf.util.Common;

public class HtmlAuthenticationDownloadWorkImpl extends WorkParent {
	protected static Logger log = Logger
			.getLogger(HtmlAuthenticationDownloadWorkImpl.class.getName());
	
	private String host = "";
	private int port = 0;
	private String url = "";
	private String username = "";
	private String pw = "";
	private String	html = "";

	public HtmlAuthenticationDownloadWorkImpl(String name, String host,
			int port, String url, String username, String pw) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.url = url;
		this.username = username;
		this.pw = pw;
	}

	public void work() throws Exception {		
		DefaultHttpClient httpclient = new DefaultHttpClient();	
		String	sgetinfo = "";
		try {
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(host, port),
					new UsernamePasswordCredentials(username, pw));

			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			sgetinfo = "Response content length: "
					+ entity.getContentLength() + "/StatusLine():" + response.getStatusLine() 
					+ "executing request" + httpget.getRequestLine();					
			
			if (entity != null) {
				html = IOUtils.toString(entity.getContent());
			}
			EntityUtils.consume(entity);
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}

		if (Common.isNotNull(html)) {
			if(process != null){
				try {					
						process.process();		
				} catch (Exception e) {
					e.printStackTrace();
					log("work failed:" + e.getMessage());
					throw e;
				}
			}else{				
				log("process 为空!");
			}
		} else {
			log("getinfo:" + sgetinfo);
			log("取到的html为空!");
		}
		
	}
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getHtml() {
		return html;
	}
	
	
}
