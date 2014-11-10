package com.incontact.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.incontact.service.impl.ScheduleIncontactJob;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * ApplicationContextAwareProvider.java
 */
public class ApplicationContextAwareProvider implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null)
			applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;

		ScheduleIncontactJob scheduleIncontactJob = (ScheduleIncontactJob) this.applicationContext.getBean("scheduleIncontactJob");
		try {
			scheduleIncontactJob.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}