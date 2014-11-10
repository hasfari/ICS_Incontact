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
 *         InteractionHistory.java
 */

@Entity
@Table(name = "INTERACTION_HISTORY")
public class InteractionHistory implements java.io.Serializable {

	private int tblId;
	private BigDecimal agentId;
	private String agentName;
	private BigDecimal businessUnitId;
	private String campaignName;
	private BigDecimal campaignId;
	private BigDecimal contactId;
	private String fromAddress;
	private Timestamp lastUpdateTime;
	private String mastercontactId;
	private String mediaName;
	private String mediaType;
	private String skillName;
	private BigDecimal skillId;
	private Timestamp startDate;
	private String teamName;
	private BigDecimal teamId;
	private String toAddress;
	private String pointOfContact;
	private Timestamp wsStartTime;
	private Timestamp wsEndTime;
	private Timestamp sysUpdateTime;
	private SysAgentMainLog sysAgentInteractionHistoryLog;

	@Id
	@Column(name = "TBL_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "INTERACTION_HISTORY_ID")
	@SequenceGenerator(name = "INTERACTION_HISTORY_ID", sequenceName = "AGENT_INTERACTION_HISTORY_SEQ")
	public int getTblId() {
		return this.tblId;
	}

	public void setTblId(int tblId) {
		this.tblId = tblId;
	}

	@Column(name = "AGENT_ID", precision = 10, scale = 0)
	public BigDecimal getAgentId() {
		return this.agentId;
	}

	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	@Column(name = "AGENT_NAME", length = 150)
	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Column(name = "BUSINESS_UNIT_ID", precision = 10, scale = 0)
	public BigDecimal getBusinessUnitId() {
		return this.businessUnitId;
	}

	public void setBusinessUnitId(BigDecimal businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	@Column(name = "CAMPAIGN_NAME", length = 150)
	public String getcampaignName() {
		return this.campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@Column(name = "CAMPAIGN_ID", precision = 10, scale = 0)
	public BigDecimal getCampaignId() {
		return this.campaignId;
	}

	public void setCampaignId(BigDecimal campaignId) {
		this.campaignId = campaignId;
	}

	@Column(name = "CONTACT_ID", precision = 10, scale = 0)
	public BigDecimal getContactId() {
		return this.contactId;
	}

	public void setContactId(BigDecimal contactId) {
		this.contactId = contactId;
	}

	@Column(name = "FROM_ADDRESS", length = 250)
	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	@Column(name = "LAST_UPDATE_TIME")
	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "MASTER_CONTACT_ID", length = 150)
	public String getMastercontactId() {
		return this.mastercontactId;
	}

	public void setMastercontactId(String mastercontactId) {
		this.mastercontactId = mastercontactId;
	}

	@Column(name = "MEDIA_NAME", length = 150)
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

	@Column(name = "SKILL_NAME", length = 250)
	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Column(name = "SKILL_ID", precision = 10, scale = 0)
	public BigDecimal getSkillId() {
		return this.skillId;
	}

	public void setSkillId(BigDecimal skillId) {
		this.skillId = skillId;
	}

	@Column(name = "START_DATE", length = 7)
	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Column(name = "TEAM_NAME", length = 250)
	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Column(name = "TEAM_ID", precision = 10, scale = 0)
	public BigDecimal getTeamId() {
		return this.teamId;
	}

	public void setTeamId(BigDecimal teamId) {
		this.teamId = teamId;
	}

	@Column(name = "TO_ADDRESS", length = 250)
	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	@Column(name = "POINT_OF_CONTACT", length = 150)
	public String getPointOfContact() {
		return this.pointOfContact;
	}

	public void setPointOfContact(String pointOfContact) {
		this.pointOfContact = pointOfContact;
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
	@JoinColumn(name = "SYS_LOG_ID", nullable = false)
	public SysAgentMainLog getSysAgentInteractionHistoryLog() {
		return sysAgentInteractionHistoryLog;
	}

	public void setSysAgentInteractionHistoryLog(SysAgentMainLog sysAgentInteractionHistoryLog) {
		this.sysAgentInteractionHistoryLog = sysAgentInteractionHistoryLog;
	}

}
