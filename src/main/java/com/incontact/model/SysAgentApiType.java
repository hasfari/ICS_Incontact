package com.incontact.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * SysAgentApiType.java
 */

@Entity
@Table(name = "SYS_AGENT_API_TYPE")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="AGENT_API_TYPE")
public class SysAgentApiType implements java.io.Serializable {

	private int sysApiTypeId;
	private String sysApiTypeCd;
	private String sysApiTypeDscr;

	@Id
	@Column(name = "SYS_API_TYPE_ID", unique = true, nullable = false, precision = 3, scale = 0)
	public int getApiTypeId() {
		return this.sysApiTypeId;
	}

	public void setApiTypeId(int sysApiTypeId) {
		this.sysApiTypeId = sysApiTypeId;
	}

	@Column(name = "SYS_API_TYPE_CD", nullable = false, length = 100)
	public String getApiTypeCd() {
		return this.sysApiTypeCd;
	}

	public void setApiTypeCd(String sysApiTypeCd) {
		this.sysApiTypeCd = sysApiTypeCd;
	}

	@Column(name = "SYS_API_TYPE_DSCR", nullable = false, length = 300)
	public String getApiTypeDscr() {
		return this.sysApiTypeDscr;
	}

	public void setApiTypeDscr(String sysApiTypeDscr) {
		this.sysApiTypeDscr = sysApiTypeDscr;
	}

}
