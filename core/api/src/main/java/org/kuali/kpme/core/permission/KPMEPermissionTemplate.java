/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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