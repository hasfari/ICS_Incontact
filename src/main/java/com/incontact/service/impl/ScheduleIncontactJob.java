package com.incontact.service.impl;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.incontact.ws.IIncontactPerformanceWS;

/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * ScheduleIncontactJob.java
 */

public class ScheduleIncontactJob {

	private IncontactJob incontactJob;
	private IIncontactPerformanceWS incontactPerformanceWS;

	public void init() throws Exception {

		org.springframework.scheduling.quartz.SchedulerFactoryBean sfb = new SchedulerFactoryBean();
		try {
		JobDetailBean jobPerformanceDetail = new JobDetailBean();
		jobPerformanceDetail.setName("incontactPerformanceJob");
		jobPerformanceDetail.getJobDataMap().put("reschedule", new Boolean(true));
		jobPerformanceDetail.setJobClass(IncontactJob.class);
		jobPerformanceDetail.afterPropertiesSet();

		CronTriggerBean cronPerformanceTrigger = new CronTriggerBean();
		cronPerformanceTrigger.setName("incontactPerformanceTrigger");
		cronPerformanceTrigger.setJobName("incontactPerformanceJob");
		cronPerformanceTrigger.setGroup(Scheduler.DEFAULT_MANUAL_TRIGGERS);
		String expression = "0/10 * * * * ?";
		jobPerformanceDetail.getJobDataMap().put("original.reschedule",expression);
		cronPerformanceTrigger.setCronExpression(expression);
		cronPerformanceTrigger.afterPropertiesSet();
		
		JobDetailBean jobIntractionHistoryDetail = new JobDetailBean();
		jobIntractionHistoryDetail.setName("incontactIntractionHistoryJob");
		jobIntractionHistoryDetail.getJobDataMap().put("reschedule", new Boolean(true));
		jobIntractionHistoryDetail.setJobClass(IncontactJob.class);
		jobIntractionHistoryDetail.afterPropertiesSet();

		CronTriggerBean cronIntractionHistoryTrigger = new CronTriggerBean();
		cronIntractionHistoryTrigger.setName("incontactIntractionHistoryTrigger");
		cronIntractionHistoryTrigger.setJobName("incontactIntractionHistoryJob");
		cronIntractionHistoryTrigger.setGroup(Scheduler.DEFAULT_MANUAL_TRIGGERS);
		expression = "0/10 * * * * ?";
		jobIntractionHistoryDetail.getJobDataMap().put("original.reschedule",expression);
		
		cronIntractionHistoryTrigger.setCronExpression(expression);
		cronIntractionHistoryTrigger.afterPropertiesSet();

		JobDetailBean jobStatesHistoryByAgentDetail = new JobDetailBean();
		jobStatesHistoryByAgentDetail.setName("incontactStatesHistoryByAgentJob");
		jobStatesHistoryByAgentDetail.getJobDataMap().put("reschedule", new Boolean(true));
		jobStatesHistoryByAgentDetail.setJobClass(IncontactJob.class);
		jobStatesHistoryByAgentDetail.afterPropertiesSet();

		CronTriggerBean cronStatesHistoryByAgentTrigger = new CronTriggerBean();
		cronStatesHistoryByAgentTrigger.setName("incontactStatesHistoryByAgentTrigger");
		cronStatesHistoryByAgentTrigger.setJobName("incontactStatesHistoryByAgentJob");
		cronStatesHistoryByAgentTrigger.setGroup(Scheduler.DEFAULT_MANUAL_TRIGGERS);
		expression = "0/10 * * * * ?";
		jobStatesHistoryByAgentDetail.getJobDataMap().put("original.reschedule",expression);
		
		cronStatesHistoryByAgentTrigger.setCronExpression(expression);
		cronStatesHistoryByAgentTrigger.afterPropertiesSet();
		
		JobDetailBean jobSkillSummaryDetail = new JobDetailBean();
		jobSkillSummaryDetail.setName("incontactSkillSummaryJob");
		jobSkillSummaryDetail.getJobDataMap().put("reschedule", new Boolean(true));
		jobSkillSummaryDetail.setJobClass(IncontactJob.class);
		jobSkillSummaryDetail.afterPropertiesSet();

		CronTriggerBean cronSkillSummaryTrigger = new CronTriggerBean();
		cronSkillSummaryTrigger.setName("incontactSkillSummaryTrigger");
		cronSkillSummaryTrigger.setJobName("incontactSkillSummaryJob");
		cronSkillSummaryTrigger.setGroup(Scheduler.DEFAULT_MANUAL_TRIGGERS);
		expression = "0/10 * * * * ?";
		jobSkillSummaryDetail.getJobDataMap().put("original.reschedule",expression);

		cronSkillSummaryTrigger.setCronExpression(expression);
		cronSkillSummaryTrigger.afterPropertiesSet();

		JobDetailBean jobAgentIdStatesDetail = new JobDetailBean();
		jobAgentIdStatesDetail.setName("incontactAgentIdStatesJob");
		jobAgentIdStatesDetail.getJobDataMap().put("reschedule", new Boolean(true));
		jobAgentIdStatesDetail.setJobClass(IncontactJob.class);
		jobAgentIdStatesDetail.afterPropertiesSet();

		CronTriggerBean cronAgentIdStatesTrigger = new CronTriggerBean();
		cronAgentIdStatesTrigger.setName("incontactAgentIdStatesTrigger");
		cronAgentIdStatesTrigger.setJobName("incontactAgentIdStatesJob");
		cronAgentIdStatesTrigger.setGroup(Scheduler.DEFAULT_MANUAL_TRIGGERS);
		expression = "0/10 * * * * ?";
		jobAgentIdStatesDetail.getJobDataMap().put("original.reschedule",expression);

		cronAgentIdStatesTrigger.setCronExpression(expression);
		cronAgentIdStatesTrigger.afterPropertiesSet();
		
		JobDetail[] jobDetails = new JobDetail[5];
		jobDetails[0] = jobAgentIdStatesDetail;
		jobDetails[1] = jobIntractionHistoryDetail;
		jobDetails[2] = jobStatesHistoryByAgentDetail;
		jobDetails[3] = jobPerformanceDetail;
		jobDetails[4] = jobSkillSummaryDetail;
		sfb.setJobDetails(jobDetails);
		CronTriggerBean[] triggers = new CronTriggerBean[5];
		triggers[0] = cronAgentIdStatesTrigger;
		triggers[1] = cronIntractionHistoryTrigger;
		triggers[2] = cronStatesHistoryByAgentTrigger;
		triggers[3] = cronPerformanceTrigger;
		triggers[4] = cronSkillSummaryTrigger;
		sfb.setTriggers(triggers);
		sfb.afterPropertiesSet();
		
		sfb.start();
		
		} catch (Exception e) {
			e.printStackTrace();
			sfb.stop();
			throw e;
		}
	}

	public void setIncontactJob(IncontactJob incontactJob) {
		this.incontactJob = incontactJob;
	}
	
	public void setIncontactPerformanceWS(IIncontactPerformanceWS incontactPerformanceWS) {
		this.incontactPerformanceWS = incontactPerformanceWS;
	}
}