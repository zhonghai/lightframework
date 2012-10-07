package com.wisesource.product.eddm.domain;

// Generated 2012-9-11 22:49:16 by Hibernate Tools 3.4.0.CR1

/**
 * TProcessDoBoa generated by hbm2java
 */
public class TProcessDoBoa implements java.io.Serializable {

	private String fdoboaid;
	private String fdoid;
	private String connnm;
	private String tbl;
	private String keyfields;
	private String fields;
	private String uptfields;
	private String onlyupdate;
	private String allowupdate;

	public TProcessDoBoa() {
	}

	public TProcessDoBoa(String fdoboaid, String fdoid, String connnm,
			String tbl, String keyfields, String fields, String uptfields) {
		this.fdoboaid = fdoboaid;
		this.fdoid = fdoid;
		this.connnm = connnm;
		this.tbl = tbl;
		this.keyfields = keyfields;
		this.fields = fields;
		this.uptfields = uptfields;
	}

	public TProcessDoBoa(String fdoboaid, String fdoid, String connnm,
			String tbl, String keyfields, String fields, String uptfields,
			String onlyupdate, String allowupdate) {
		this.fdoboaid = fdoboaid;
		this.fdoid = fdoid;
		this.connnm = connnm;
		this.tbl = tbl;
		this.keyfields = keyfields;
		this.fields = fields;
		this.uptfields = uptfields;
		this.onlyupdate = onlyupdate;
		this.allowupdate = allowupdate;
	}

	public String getFdoboaid() {
		return this.fdoboaid;
	}

	public void setFdoboaid(String fdoboaid) {
		this.fdoboaid = fdoboaid;
	}

	public String getFdoid() {
		return this.fdoid;
	}

	public void setFdoid(String fdoid) {
		this.fdoid = fdoid;
	}

	public String getConnnm() {
		return this.connnm;
	}

	public void setConnnm(String connnm) {
		this.connnm = connnm;
	}

	public String getTbl() {
		return this.tbl;
	}

	public void setTbl(String tbl) {
		this.tbl = tbl;
	}

	public String getKeyfields() {
		return this.keyfields;
	}

	public void setKeyfields(String keyfields) {
		this.keyfields = keyfields;
	}

	public String getFields() {
		return this.fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getUptfields() {
		return this.uptfields;
	}

	public void setUptfields(String uptfields) {
		this.uptfields = uptfields;
	}

	public String getOnlyupdate() {
		return this.onlyupdate;
	}

	public void setOnlyupdate(String onlyupdate) {
		this.onlyupdate = onlyupdate;
	}

	public String getAllowupdate() {
		return this.allowupdate;
	}

	public void setAllowupdate(String allowupdate) {
		this.allowupdate = allowupdate;
	}

}
