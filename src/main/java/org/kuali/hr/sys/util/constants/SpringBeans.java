package org.kuali.hr.sys.util.constants;

public enum SpringBeans {
	
	//todo this placeholder should be removed on first enumeration
	PLACEHOLDER("placeholder"); 

	private String description;

	private SpringBeans(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}

}
