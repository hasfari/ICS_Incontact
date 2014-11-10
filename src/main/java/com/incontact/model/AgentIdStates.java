package com.incontact.model;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * @author HAsfari added on Nov 10, 2014
 *
 * @param <T>
 *
 * AgentIdStates.java
 */

@Entity
@Table(name = "AGENT_ID_STATES")
public class AgentIdStates<T> implements java.io.Serializable {

	private static final long serialVersionUID = -1523523593031286643L;
	private int tblId;
	private BigDecimal agentId;
	private BigDecimal stateId;
	private String stateName;
	private BigDecimal businessUnitId;
	private BigDecimal contactId;
	private String isAcw;
	private String firstName;
	private String fromAddress;
	private String lastName;
	private Timestamp lastPolltime;
	private Timestamp lastUpdateTime;
	private String mediaName;
	private String mediaType;
	private String outStateDescription;
	private BigDecimal outStateId;
	private BigDecimal skillId;
	private String skillName;
	private Date startDate;
	private BigDecimal stationId;
	private String stationPhoneNumber;
	private BigDecimal teamId;
	private String teamName;
	private String toAddress;
	private Timestamp wsStartTime;
	private Timestamp wsEndTime;
	private Timestamp sysUpdateTime;
	private SysAgentMainLog sysAgentIdStatesLog;

	public AgentIdStates() {
	}

	public AgentIdStates(BigDecimal agentId, Timestamp wsStartTime, Timestamp wsEndTime) {
		this.agentId = agentId;
		this.wsStartTime = wsStartTime;
		this.wsEndTime = wsEndTime;
	}

	@Id
	@Column(name = "TBL_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AGENT_ID_STATES_ID")
	@SequenceGenerator(name = "AGENT_ID_STATES_ID", sequenceName = "AGENT_ID_STATES_SEQ")
	public int getTblId() {
		return this.tblId;
	}

	public void setTblId(int tblId) {
		this.tblId = tblId;
	}

	@Column(name = "AGENT_ID", nullable = false, precision = 10, scale = 0)
	public BigDecimal getAgentId() {
		return this.agentId;
	}

	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	@Column(name = "STATE_ID", nullable = false, precision = 10, scale = 0)
	public BigDecimal getStateId() {
		return this.stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	@Column(name = "STATE_NAME", length = 150)
	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Column(name = "BUSINESS_UNIT_ID", precision = 10, scale = 0)
	public BigDecimal getBusinessUnitId() {
		return this.businessUnitId;
	}

	public void setBusinessUnitId(BigDecimal businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	@Column(name = "CONTACT_ID", precision = 10, scale = 0)
	public BigDecimal getContactId() {
		return this.contactId;
	}

	public void setContactId(BigDecimal contactId) {
		this.contactId = contactId;
	}

	@Column(name = "IS_ACW", length = 1)
	public String getIsAcw() {
		return this.isAcw;
	}

	public void setIsAcw(String isAcw) {
		this.isAcw = isAcw;
	}

	@Column(name = "FIRST_NAME", length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "FROM_ADDRESS", length = 250)
	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	@Column(name = "LAST_NAME", length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "LAST_POLLTIME")
	public Timestamp getLastPolltime() {
		return this.lastPolltime;
	}

	public void setLastPolltime(Timestamp lastPolltime) {
		this.lastPolltime = lastPolltime;
	}

	@Column(name = "LAST_UPDATE_TIME")
	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "MEDIA_NAME", length = 50)
	public String getMediaName() {
		return this.mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	@Column(name = "MEDIA_TYPE", length = 50)
	public String getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	@Column(name = "OUT_STATE_DESCRIPTION", length = 250)
	public String getOutStateDescription() {
		return this.outStateDescription;
	}

	public void setOutStateDescription(String outStateDescription) {
		this.outStateDescription = outStateDescription;
	}

	@Column(name = "OUT_STATE_ID", precision = 10, scale = 0)
	public BigDecimal getOutStateId() {
		return this.outStateId;
	}

	public void setOutStateId(BigDecimal outStateId) {
		this.outStateId = outStateId;
	}

	@Column(name = "SKILL_ID", precision = 10, scale = 0)
	public BigDecimal getSkillId() {
		return this.skillId;
	}

	public void setSkillId(BigDecimal skillId) {
		this.skillId = skillId;
	}

	@Column(name = "SKILL_NAME", length = 50)
	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", nullable = true, length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "STATION_ID", precision = 10, scale = 0)
	public BigDecimal getStationId() {
		return this.stationId;
	}

	public void setStationId(BigDecimal stationId) {
		this.stationId = stationId;
	}

	@Column(name = "STATION_PHONE_NUMBER", length = 50)
	public String getStationPhoneNumber() {
		return this.stationPhoneNumber;
	}

	public void setStationPhoneNumber(String stationPhoneNumber) {
		this.stationPhoneNumber = stationPhoneNumber;
	}

	@Column(name = "TEAM_ID", precision = 10, scale = 0)
	public BigDecimal getTeamId() {
		return this.teamId;
	}

	public void setTeamId(BigDecimal teamId) {
		this.teamId = teamId;
	}

	@Column(name = "TEAM_NAME", length = 250)
	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Column(name = "TO_ADDRESS", length = 250)
	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
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

	@Column(name = "SYS_UPDATE_TIME", nullable = false)
	public Timestamp getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Timestamp sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYS_LOG_ID", nullable = true)
	public SysAgentMainLog getSysAgentPerformanceLog() {
		return this.sysAgentIdStatesLog;
	}

	public void setSysAgentPerformanceLog(SysAgentMainLog sysAgentIdStatesLog) {
		this.sysAgentIdStatesLog = sysAgentIdStatesLog;
	}

}
