package com.incontact.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * SysAgentSchdl.java
 */

@Entity
@Table(name = "SYS_AGENT_SCHDL")
public class SysAgentSchdl implements java.io.Serializable {

	private short sysSchdlId;
	private SysAgentApiType sysAgentApiType;
	private Long sysSchdlIntrvl;
	private String sysSchdlDscr;

	@Id
	@Column(name = "SYS_SCHDL_ID", unique = true, nullable = false, precision = 3, scale = 0)
	public short getSysSchdlId() {
		return this.sysSchdlId;
	}

	public void setSysSchdlId(short sysSchdlId) {
		this.sysSchdlId = sysSchdlId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYS_API_TYPE_ID", nullable = false)
	public SysAgentApiType getSysAgentApiType() {
		return this.sysAgentApiType;
	}

	public void setSysAgentApiType(SysAgentApiType sysAgentApiType) {
		this.sysAgentApiType = sysAgentApiType;
	}

	@Column(name = "SYS_SCHDL_INTRVL", precision = 10, scale = 0)
	public Long getSysSchdlIntrvl() {
		return this.sysSchdlIntrvl;
	}

	public void setSysSchdlIntrvl(Long sysSchdlIntrvl) {
		this.sysSchdlIntrvl = sysSchdlIntrvl;
	}

	@Column(name = "SYS_SCHDL_DSCR", nullable = false, length = 300)
	public String getSysSchdlDscr() {
		return this.sysSchdlDscr;
	}

	public void setSysSchdlDscr(String sysSchdlDscr) {
		this.sysSchdlDscr = sysSchdlDscr;
	}

}
