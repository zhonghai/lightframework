package com.wisesource.product.eddm.domain;

// Generated 2012-9-11 22:49:16 by Hibernate Tools 3.4.0.CR1

/**
 * TTask generated by hbm2java
 */
public class TTask implements java.io.Serializable {

	private String ftaskid;
	private String ftaskname;
	private String ftaskdesc;
	private String fjsondata;

	public TTask() {
	}

	public TTask(String ftaskid) {
		this.ftaskid = ftaskid;
	}

	public TTask(String ftaskid, String ftaskname, String ftaskdesc,
			String fjsondata) {
		this.ftaskid = ftaskid;
		this.ftaskname = ftaskname;
		this.ftaskdesc = ftaskdesc;
		this.fjsondata = fjsondata;
	}

	public String getFtaskid() {
		return this.ftaskid;
	}

	public void setFtaskid(String ftaskid) {
		this.ftaskid = ftaskid;
	}

	public String getFtaskname() {
		return this.ftaskname;
	}

	public void setFtaskname(String ftaskname) {
		this.ftaskname = ftaskname;
	}

	public String getFtaskdesc() {
		return this.ftaskdesc;
	}

	public void setFtaskdesc(String ftaskdesc) {
		this.ftaskdesc = ftaskdesc;
	}

	public String getFjsondata() {
		return this.fjsondata;
	}

	public void setFjsondata(String fjsondata) {
		this.fjsondata = fjsondata;
	}

}
