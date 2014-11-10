package com.incontact.model;

/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * AgentApiTypeEnum.java
 */

public enum AgentApiTypeEnum {
	
	AGENT_STATES(1),
	AGENTS_ID_STATES(2),
	AGENT_ID_INTERACTION_HISTORY(3),
	AGENT_ID_STATE_HISTORY(4),
	AGENT_PERFORMANCE(5),
	AGENT_ID_PERFORMANCE(6),
	AGENT_CONTACTS_STATES(7),
	AGENT_SKILLS_SUMMARY(8),
	AGENT_ID_SKILLS_SUMMARY(9),
	AGENT_SKILLS_SLA_SUMMARY(10);

	private int value;

    private AgentApiTypeEnum(int value) {
            this.value = value;
    }

    public int getValue() {
        return value;
    }

}
