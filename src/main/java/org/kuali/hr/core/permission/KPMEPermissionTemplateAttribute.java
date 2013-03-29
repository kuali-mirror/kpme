package org.kuali.hr.core.permission;

public enum KPMEPermissionTemplateAttribute {
	
	KPME_DOCUMENT_STATUS ("kpmeDocumentStatus");
	
	private String permissionTemplateAttributeName;
	
	private KPMEPermissionTemplateAttribute(String permissionTemplateAttributeName) {
		this.permissionTemplateAttributeName = permissionTemplateAttributeName;
	}

	public String getPermissionTemplateAttributeName() {
		return permissionTemplateAttributeName;
	}

	public void setPermissionTemplateAttributeName(String permissionTemplateAttributeName) {
		this.permissionTemplateAttributeName = permissionTemplateAttributeName;
	}

}