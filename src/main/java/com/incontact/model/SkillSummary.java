package com.incontact.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
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
 * @param <T>
 *
 *            SkillSummary.java
 */

@Entity
@Table(name = "SKILL_SUMMARIES")
public class SkillSummary<T> implements java.io.Serializable {

	private static final long serialVersionUID = -2392426932871243178L;
	private int tblId;
	private BigDecimal businessUnitId;
	private BigDecimal abandonCount;
	private BigDecimal abandonRate;
	private Boolean agentsAcw;
	private BigDecimal agentsAvailable;
	private BigDecimal agentsIdle;
	private BigDecimal agentsLoggedIn;
	private BigDecimal agentsUnavailable;
	private BigDecimal agentsWorking;
	private BigDecimal averageHandleTime;
	private BigDecimal averageInqueueTime;
	private BigDecimal averageSpeedToAnswer;
	private BigDecimal averageTalkTime;
	private BigDecimal averageWrapTime;
	private BigDecimal campaignId;
	private String campaignName;
	private Boolean contactsActive;
	private Boolean contactsHandled;
	private Boolean contactsOffered;
	private Boolean contactsQueued;
	private Boolean contactsOutOfSLA;
	private Boolean contactsWithinSLA;
	private BigDecimal holdTime;
	private Boolean isOutbound;
	private BigDecimal longestQueueDuration;
	private String MediaTypeId;
	private String MediaTypeName;
	private BigDecimal QueueCount;
	private BigDecimal ServiceLevel;
	private String SkillName;
	private BigDecimal SkillId;
	private BigDecimal ServiceLevelGoal;
	private BigDecimal ServiceLevelThreshold;
	private Timestamp wsStartTime;
	private Timestamp wsEndTime;
	private Timestamp sysUpdateTime;
	private SysAgentMainLog sysSkillSummariesLog;

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

	@Column(name = "BUSINESS_UNIT_ID", precision = 10, scale = 0)
	public BigDecimal getBusinessUnitId() {
		return this.businessUnitId;
	}

	public void setBusinessUnitId(BigDecimal businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	@Column(name = "ABANDON_COUNT", precision = 10, scale = 0)
	public BigDecimal getAbandonCount() {
		return this.abandonCount;
	}

	public void setAbandonCount(BigDecimal abandonCount) {
		this.abandonCount = abandonCount;
	}

	@Column(name = "ABANDON_RATE", precision = 10, scale = 0)
	public BigDecimal getAbandonRate() {
		return this.abandonRate;
	}

	public void setAbandonRate(BigDecimal abandonRate) {
		this.abandonRate = abandonRate;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "AGENTS_ACW", length = 1)
	public Boolean getAgentsAcw() {
		return agentsAcw;
	}

	public void setAgentsAcw(Boolean agentsAcw) {
		this.agentsAcw = agentsAcw;
	}

	@Column(name = "AGENTS_AVALIABLE", precision = 10, scale = 0)
	public BigDecimal getAgentsAvailable() {
		return agentsAvailable;
	}

	public void setAgentsAvailable(BigDecimal agentsAvailable) {
		this.agentsAvailable = agentsAvailable;
	}

	@Column(name = "AGENTS_IDLE", precision = 10, scale = 0)
	public BigDecimal getAgentsIdle() {
		return agentsIdle;
	}

	public void setAgentsIdle(BigDecimal agentsIdle) {
		this.agentsIdle = agentsIdle;
	}

	@Column(name = "AGENTS_LOGGEDIN", precision = 10, scale = 0)
	public BigDecimal getAgentsLoggedIn() {
		return agentsLoggedIn;
	}

	public void setAgentsLoggedIn(BigDecimal agentsLoggedIn) {
		this.agentsLoggedIn = agentsLoggedIn;
	}

	@Column(name = "AGENTS_UNAVALIABLE", precision = 10, scale = 0)
	public BigDecimal getAgentsUnavailable() {
		return agentsUnavailable;
	}

	public void setAgentsUnavailable(BigDecimal agentsUnavailable) {
		this.agentsUnavailable = agentsUnavailable;
	}

	@Column(name = "AGENTS_WORKING", precision = 10, scale = 0)
	public BigDecimal getAgentsWorking() {
		return agentsWorking;
	}

	public void setAgentsWorking(BigDecimal agentsWorking) {
		this.agentsWorking = agentsWorking;
	}

	@Column(name = "AVERAGE_HANDLE_TIME", precision = 10, scale = 0)
	public BigDecimal getAverageHandleTime() {
		return averageHandleTime;
	}

	public void setAverageHandleTime(BigDecimal averageHandleTime) {
		this.averageHandleTime = averageHandleTime;
	}

	@Column(name = "AVERAGE_INQUEUE_TIME", precision = 10, scale = 0)
	public BigDecimal getAverageInqueueTime() {
		return averageInqueueTime;
	}

	public void setAverageInqueueTime(BigDecimal averageInqueueTime) {
		this.averageInqueueTime = averageInqueueTime;
	}

	@Column(name = "AVERAGE_SPEEDTO_ANSWER", precision = 10, scale = 0)
	public BigDecimal getAverageSpeedToAnswer() {
		return averageSpeedToAnswer;
	}

	public void setAverageSpeedToAnswer(BigDecimal averageSpeedToAnswer) {
		this.averageSpeedToAnswer = averageSpeedToAnswer;
	}

	@Column(name = "AVERAGE_TALK_TIME", precision = 10, scale = 0)
	public BigDecimal getAverageTalkTime() {
		return averageTalkTime;
	}

	public void setAverageTalkTime(BigDecimal averageTalkTime) {
		this.averageTalkTime = averageTalkTime;
	}

	@Column(name = "AVERAGE_WRAP_TIME", precision = 10, scale = 0)
	public BigDecimal getAverageWrapTime() {
		return averageWrapTime;
	}

	public void setAverageWrapTime(BigDecimal averageWrapTime) {
		this.averageWrapTime = averageWrapTime;
	}

	@Column(name = "CAMPAIGN_ID", precision = 10, scale = 0)
	public BigDecimal getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(BigDecimal campaignId) {
		this.campaignId = campaignId;
	}

	@Column(name = "CAMPAIGN_NAME", length = 150)
	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "CONTACTS_ACTIVE", length = 1)
	public Boolean getContactsActive() {
		return contactsActive;
	}

	public void setContactsActive(Boolean contactsActive) {
		this.contactsActive = contactsActive;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "CONTACTS_HANDLED", length = 1)
	public Boolean getContactsHandled() {
		return contactsHandled;
	}

	public void setContactsHandled(Boolean contactsHandled) {
		this.contactsHandled = contactsHandled;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "CONTACTS_OFFERED", length = 1)
	public Boolean getContactsOffered() {
		return contactsOffered;
	}

	public void setContactsOffered(Boolean contactsOffered) {
		this.contactsOffered = contactsOffered;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "CONTACTS_QUEUED", length = 1)
	public Boolean getContactsQueued() {
		return contactsQueued;
	}

	public void setContactsQueued(Boolean contactsQueued) {
		this.contactsQueued = contactsQueued;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "CONTACTS_OUTOF_SLA", length = 1)
	public Boolean getContactsOutOfSLA() {
		return contactsOutOfSLA;
	}

	public void setContactsOutOfSLA(Boolean contactsOutOfSLA) {
		this.contactsOutOfSLA = contactsOutOfSLA;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "CONTACTS_WITHIN_SLA", length = 1)
	public Boolean getContactsWithinSLA() {
		return contactsWithinSLA;
	}

	public void setContactsWithinSLA(Boolean contactsWithinSLA) {
		this.contactsWithinSLA = contactsWithinSLA;
	}

	@Column(name = "HOLD_TIME", precision = 10, scale = 0)
	public BigDecimal getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(BigDecimal holdTime) {
		this.holdTime = holdTime;
	}

	@Convert(converter = ConvertStringToBollean.class)
	@Column(name = "IS_OUTBOUND", length = 1)
	public Boolean getIsOutbound() {
		return isOutbound;
	}

	public void setIsOutbound(Boolean isOutbound) {
		this.isOutbound = isOutbound;
	}

	@Column(name = "LONGEST_QUEUE_DURATION", precision = 10, scale = 0)
	public BigDecimal getLongestQueueDuration() {
		return longestQueueDuration;
	}

	public void setLongestQueueDuration(BigDecimal longestQueueDuration) {
		this.longestQueueDuration = longestQueueDuration;
	}

	@Column(name = "MEDIA_TYPE_ID", length = 10)
	public String getMediaTypeId() {
		return MediaTypeId;
	}

	public void setMediaTypeId(String mediaTypeId) {
		MediaTypeId = mediaTypeId;
	}

	@Column(name = "MEDIA_TYPE_NAME", length = 50)
	public String getMediaTypeName() {
		return MediaTypeName;
	}

	public void setMediaTypeName(String mediaTypeName) {
		MediaTypeName = mediaTypeName;
	}

	@Column(name = "QUEUE_COUNT", precision = 10, scale = 0)
	public BigDecimal getQueueCount() {
		return QueueCount;
	}

	public void setQueueCount(BigDecimal queueCount) {
		QueueCount = queueCount;
	}

	@Column(name = "SERVICE_LEVEL", precision = 10, scale = 0)
	public BigDecimal getServiceLevel() {
		return ServiceLevel;
	}

	public void setServiceLevel(BigDecimal serviceLevel) {
		ServiceLevel = serviceLevel;
	}

	@Column(name = "SKILL_NAME", precision = 10, scale = 0)
	public String getSkillName() {
		return SkillName;
	}

	public void setSkillName(String skillName) {
		SkillName = skillName;
	}

	@Column(name = "SKILL_ID", precision = 10, scale = 0)
	public BigDecimal getSkillId() {
		return SkillId;
	}

	public void setSkillId(BigDecimal skillId) {
		SkillId = skillId;
	}

	@Column(name = "SERVICE_LEVEL_GOAL", precision = 10, scale = 0)
	public BigDecimal getServiceLevelGoal() {
		return ServiceLevelGoal;
	}

	public void setServiceLevelGoal(BigDecimal serviceLevelGoal) {
		ServiceLevelGoal = serviceLevelGoal;
	}

	@Column(name = "SERVICE_LEVEL_THRESHOLD", precision = 10, scale = 0)
	public BigDecimal getServiceLevelThreshold() {
		return ServiceLevelThreshold;
	}

	public void setServiceLevelThreshold(BigDecimal serviceLevelThreshold) {
		ServiceLevelThreshold = serviceLevelThreshold;
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
		return this.sysSkillSummariesLog;
	}

	public void setSysAgentPerformanceLog(SysAgentMainLog sysAgentIdStatesLog) {
		this.sysSkillSummariesLog = sysAgentIdStatesLog;
	}

}
