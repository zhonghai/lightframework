package com.wisesource.product.eddm.domain;

// Generated 2012-9-11 22:49:16 by Hibernate Tools 3.4.0.CR1

/**
 * TProcessDbconnId generated by hbm2java
 */
public class TProcessDbconnId implements java.io.Serializable {

	private String fprocessid;
	private String fconnid;

	public TProcessDbconnId() {
	}

	public TProcessDbconnId(String fprocessid, String fconnid) {
		this.fprocessid = fprocessid;
		this.fconnid = fconnid;
	}

	public String getFprocessid() {
		return this.fprocessid;
	}

	public void setFprocessid(String fprocessid) {
		this.fprocessid = fprocessid;
	}

	public String getFconnid() {
		return this.fconnid;
	}

	public void setFconnid(String fconnid) {
		this.fconnid = fconnid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TProcessDbconnId))
			return false;
		TProcessDbconnId castOther = (TProcessDbconnId) other;

		return ((this.getFprocessid() == castOther.getFprocessid()) || (this
				.getFprocessid() != null && castOther.getFprocessid() != null && this
				.getFprocessid().equals(castOther.getFprocessid())))
				&& ((this.getFconnid() == castOther.getFconnid()) || (this
						.getFconnid() != null && castOther.getFconnid() != null && this
						.getFconnid().equals(castOther.getFconnid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getFprocessid() == null ? 0 : this.getFprocessid()
						.hashCode());
		result = 37 * result
				+ (getFconnid() == null ? 0 : this.getFconnid().hashCode());
		return result;
	}

}