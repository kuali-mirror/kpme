package org.kuali.hr.sys.util.constants;

public enum SpringBeans {
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
