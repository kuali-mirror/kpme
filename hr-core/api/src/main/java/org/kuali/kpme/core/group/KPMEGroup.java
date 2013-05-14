package org.kuali.kpme.core.group;

public enum KPMEGroup {

	SYSTEM_VIEW_ONLY ("System View Only"),
	SYSTEM_ADMINISTRATOR ("System Administrator");

	private String groupName;
	
	private KPMEGroup(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}