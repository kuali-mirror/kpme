package org.kuali.kpme.core.permission;

public enum KPMEPermissionTemplate {
	
	CREATE_KPME_MAINTENANCE_DOCUMENT ("Create KPME Maintenance Document"),
	EDIT_KPME_MAINTENANCE_DOCUMENT ("Edit KPME Maintenance Document"),
	VIEW_KPME_RECORD ("View KPME Record");
	
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