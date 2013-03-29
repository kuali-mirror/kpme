package org.kuali.hr.core.permission;

public enum KPMEPermissionTemplate {
	
	VIEW_KPME_DOCUMENT ("View KPME Document"),
	EDIT_KPME_DOCUMENT ("Edit KPME Document"),
	CREATE_KPME_MAINTENANCE_DOCUMENT ("Create KPME Maintenance Document"),
	EDIT_KPME_MAINTENANCE_DOCUMENT ("Edit KPME Maintenance Document");
	
	private String permissionTemplateName;
	
	private KPMEPermissionTemplate(String permissionTemplateName) {
		this.permissionTemplateName = permissionTemplateName;
	}

	public String getPermissionTemplateName() {
		return permissionTemplateName;
	}

	public void setPermissionTemplateName(String permissionTemplateName) {
		this.permissionTemplateName = permissionTemplateName;
	}

}