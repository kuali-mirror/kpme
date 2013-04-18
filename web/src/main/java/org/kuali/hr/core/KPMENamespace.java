package org.kuali.hr.core;

public enum KPMENamespace {
	
	KPME_WKFLW ("KPME-WKFLW"),
	KPME_HR ("KPME-HR"),
	KPME_TK ("KPME-TK"),
	KPME_LM ("KPME-LM");
	
	private String namespaceCode;
	
	private KPMENamespace(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

}