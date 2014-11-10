package com.incontact.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * SysTeam.java
 */

@Entity
@Table(name = "SYS_TEAM")
public class SysTeam implements java.io.Serializable {

	private BigDecimal sysTeamId;
	private String sysTeamName;
	private String sysTeamStatus;
	private Boolean sysTeamExcluded;
	private Timestamp wsStartTime;
	private Timestamp wsEndTime;

	@Id
	@Column(name = "SYS_TEAM_ID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SYS_TEAM_ID")
	@SequenceGenerator(name="SYS_TEAM_ID", sequenceName="AGENT_TEAM_ID_SEQ")
	public BigDecimal getSysTeamId() {
		return this.sysTeamId;
	}

	public void setSysTeamId(BigDecimal sysTeamId) {
		this.sysTeamId = sysTeamId;
	}

	@Column(name = "SYS_TEAM_NAME", length = 100)
	public String getSysTeamName() {
		return this.sysTeamName;
	}

	public void setSysTeamName(String sysTeamName) {
		this.sysTeamName = sysTeamName;
	}

	@Column(name = "SYS_TEAM_STATUS", length = 10)
	public String getSysTeamStatus() {
		return this.sysTeamStatus;
	}

	public void setSysTeamStatus(String sysTeamStatus) {
		this.sysTeamStatus = sysTeamStatus;
	}

	@Convert(converter=ConvertStringToBollean.class)
	@Column(name = "SYS_TEAM_EXCLUDED")
	public Boolean getSysTeamExcluded() {
		return this.sysTeamExcluded;
	}

	public void setSysTeamExcluded(Boolean sysTeamExcluded) {
		this.sysTeamExcluded = sysTeamExcluded;
	}

	@Column(name = "WS_START_TIME", nullable = false)
	public Timestamp getWsStartTime() {
		return wsStartTime;
	}

	public void setWsStartTime(Timestamp wsStartTime) {
		this.wsStartTime = wsStartTime;
	}

	@Column(name = "WS_END_TIME", nullable = false)
	public Timestamp getWsEndTime() {
		return wsEndTime;
	}

	public void setWsEndTime(Timestamp wsEndTime) {
		this.wsEndTime = wsEndTime;
	}
}
