package com.incontact.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * AgentPerformance.java
 */

@Entity
@Table(name = "AGENT_PERFORMANCE")
public class AgentPerformance implements java.io.Serializable {

	private int tblId;
	private BigDecimal agentId;
	private BigDecimal teamId;
	private SysAgentMainLog sysAgentPerformanceLog;
	private String agentOffered;
	private String inboundHandled;
	private BigDecimal inboundTime;
	private BigDecimal inboundTalkTime;
	private BigDecimal inboundAvgtalkTime;
	private String outboundHandled;
	private BigDecimal outboundTime;
	private BigDecimal outboundTalkTime;
	private BigDecimal outboundAvgTalkTime;
	private BigDecimal totalHandled;
	private BigDecimal totalTalkTime;
	private BigDecimal totalAvgTalkTime;
	private BigDecimal totalAvgHandleTime;
	private BigDecimal consultTime;
	private BigDecimal availableTime;
	private BigDecimal unavailableTime;
	private BigDecimal acwTime;
	private BigDecimal refused;
	private BigDecimal percentRefused;
	private BigDecimal loginTime;
	private BigDecimal workingRate;
	private BigDecimal occupancy;
	private Timestamp wsStartTime;
	private Timestamp wsEndTime;
	private Timestamp sysUpdateTime;

	public AgentPerformance() {
	}


	@Id
	@Column(name = "TBL_ID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PERFORMANCE_ID")
	@SequenceGenerator(name="PERFORMANCE_ID", sequenceName="AGENT_PERFORMANCE_SEQ")
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

	@Column(name = "TEAM_ID", nullable = false, precision = 10, scale = 0)
	public BigDecimal getTeamId() {
		return this.teamId;
	}

	public void setTeamId(BigDecimal teamId) {
		this.teamId = teamId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYS_LOG_ID", nullable = false)
	public SysAgentMainLog getSysAgentPerformanceLog() {
		return this.sysAgentPerformanceLog;
	}

	public void setSysAgentPerformanceLog(SysAgentMainLog sysAgentPerformanceLog) {
		this.sysAgentPerformanceLog = sysAgentPerformanceLog;
	}

	@Column(name = "AGENT_OFFERED", precision = 1, scale = 0)
	public String getAgentOffered() {
		return this.agentOffered;
	}

	public void setAgentOffered(String agentOffered) {
		this.agentOffered = agentOffered;
	}

	@Column(name = "INBOUND_HANDLED", precision = 1, scale = 0)
	public String getInboundHandled() {
		return this.inboundHandled;
	}

	public void setInboundHandled(String inboundHandled) {
		this.inboundHandled = inboundHandled;
	}

	@Column(name = "INBOUND_TIME" , precision = 12, scale = 5)
	public BigDecimal getInboundTime() {
		return this.inboundTime;
	}

	public void setInboundTime(BigDecimal inboundTime) {
		this.inboundTime = inboundTime;
	}

	@Column(name = "INBOUND_TALK_TIME" , precision = 12, scale = 5)
	public BigDecimal getInboundTalkTime() {
		return this.inboundTalkTime;
	}

	public void setInboundTalkTime(BigDecimal inboundTalkTime) {
		this.inboundTalkTime = inboundTalkTime;
	}

	@Column(name = "INBOUND_AVGTALK_TIME" , precision = 12, scale = 5)
	public BigDecimal getInboundAvgtalkTime() {
		return this.inboundAvgtalkTime;
	}

	public void setInboundAvgtalkTime(BigDecimal inboundAvgtalkTime) {
		this.inboundAvgtalkTime = inboundAvgtalkTime;
	}

	@Column(name = "OUTBOUND_HANDLED", precision = 1, scale = 0)
	public String getOutboundHandled() {
		return this.outboundHandled;
	}

	public void setOutboundHandled(String outboundHandled) {
		this.outboundHandled = outboundHandled;
	}

	@Column(name = "OUTBOUND_TIME" , precision = 12, scale = 5)
	public BigDecimal getOutboundTime() {
		return this.outboundTime;
	}

	public void setOutboundTime(BigDecimal outboundTime) {
		this.outboundTime = outboundTime;
	}

	@Column(name = "OUTBOUND_TALK_TIME" , precision = 12, scale = 5)
	public BigDecimal getOutboundTalkTime() {
		return this.outboundTalkTime;
	}

	public void setOutboundTalkTime(BigDecimal outboundTalkTime) {
		this.outboundTalkTime = outboundTalkTime;
	}

	@Column(name = "OUTBOUND_AVG_TALK_TIME" , precision = 12, scale = 5)
	public BigDecimal getOutboundAvgTalkTime() {
		return this.outboundAvgTalkTime;
	}

	public void setOutboundAvgTalkTime(BigDecimal outboundAvgTalkTime) {
		this.outboundAvgTalkTime = outboundAvgTalkTime;
	}

	@Column(name = "TOTAL_HANDLED", precision = 10, scale = 0)
	public BigDecimal getTotalHandled() {
		return this.totalHandled;
	}

	public void setTotalHandled(BigDecimal totalHandled) {
		this.totalHandled = totalHandled;
	}

	@Column(name = "TOTAL_TALK_TIME" , precision = 12, scale = 5)
	public BigDecimal getTotalTalkTime() {
		return this.totalTalkTime;
	}

	public void setTotalTalkTime(BigDecimal totalTalkTime) {
		this.totalTalkTime = totalTalkTime;
	}

	@Column(name = "TOTAL_AVG_TALK_TIME" , precision = 12, scale = 5)
	public BigDecimal getTotalAvgTalkTime() {
		return this.totalAvgTalkTime;
	}

	public void setTotalAvgTalkTime(BigDecimal totalAvgTalkTime) {
		this.totalAvgTalkTime = totalAvgTalkTime;
	}

	@Column(name = "TOTAL_AVG_HANDLE_TIME" , precision = 12, scale = 5)
	public BigDecimal getTotalAvgHandleTime() {
		return this.totalAvgHandleTime;
	}

	public void setTotalAvgHandleTime(BigDecimal totalAvgHandleTime) {
		this.totalAvgHandleTime = totalAvgHandleTime;
	}

	@Column(name = "CONSULT_TIME" , precision = 12, scale = 5)
	public BigDecimal getConsultTime() {
		return this.consultTime;
	}

	public void setConsultTime(BigDecimal consultTime) {
		this.consultTime = consultTime;
	}

	@Column(name = "AVAILABLE_TIME" , precision = 12, scale = 5)
	public BigDecimal getAvailableTime() {
		return this.availableTime;
	}

	public void setAvailableTime(BigDecimal availableTime) {
		this.availableTime = availableTime;
	}

	@Column(name = "UNAVAILABLE_TIME" , precision = 12, scale = 5)
	public BigDecimal getUnavailableTime() {
		return this.unavailableTime;
	}

	public void setUnavailableTime(BigDecimal unavailableTime) {
		this.unavailableTime = unavailableTime;
	}

	@Column(name = "ACW_TIME" , precision = 12, scale = 5)
	public BigDecimal getAcwTime() {
		return this.acwTime;
	}

	public void setAcwTime(BigDecimal acwTime) {
		this.acwTime = acwTime;
	}

	@Column(name = "REFUSED", precision = 10, scale = 0)
	public BigDecimal getRefused() {
		return this.refused;
	}

	public void setRefused(BigDecimal refused) {
		this.refused = refused;
	}

	@Column(name = "PERCENT_REFUSED", precision = 10)
	public BigDecimal getPercentRefused() {
		return this.percentRefused;
	}

	public void setPercentRefused(BigDecimal percentRefused) {
		this.percentRefused = percentRefused;
	}

	@Column(name = "LOGIN_TIME" , precision = 12, scale = 5)
	public BigDecimal getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(BigDecimal loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "WORKING_RATE", precision = 10)
	public BigDecimal getWorkingRate() {
		return this.workingRate;
	}

	public void setWorkingRate(BigDecimal workingRate) {
		this.workingRate = workingRate;
	}

	@Column(name = "OCCUPANCY", precision = 10)
	public BigDecimal getOccupancy() {
		return this.occupancy;
	}

	public void setOccupancy(BigDecimal occupancy) {
		this.occupancy = occupancy;
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

}
