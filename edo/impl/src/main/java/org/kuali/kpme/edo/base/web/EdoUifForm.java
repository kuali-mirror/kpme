package org.kuali.kpme.edo.base.web;

import org.apache.commons.lang3.StringUtils;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.web.form.UifFormBase;

public class EdoUifForm extends UifFormBase {
	
	private static final long serialVersionUID = 1L;
	
	private String employeeName;
	private String employeeId;
	private String userName;
	private String userDept;
	private String targetEmployeeName;
	private String targetEmployeeId;
	
	private boolean targetFlag;
	private boolean myDossierVisible;
	private boolean candidateDelegatesVisible;
	private boolean guestVisible;
	private boolean adminVisible;
	private boolean eDossiersVisible;
	private boolean chairDelegatesVisible;
	private boolean superExportVisible;
	private Boolean portalLinksVisible;
	
	public EdoUifForm() {
		super();
		
		this.setEmployeeId(HrContext.getPrincipalId());
		this.setEmployeeName(HrContext.getPrincipalName());
		this.setUserName(HrContext.getPrincipalName());
		this.setUserDept("test");
		this.setTargetEmployeeName(HrContext.getTargetName());
		this.setTargetEmployeeId(HrContext.getTargetPrincipalId());
		
		this.setTargetFlag(HrContext.isTargetingUser());
		this.setMyDossierVisible(true);
		this.setCandidateDelegatesVisible(true);
		this.setGuestVisible(true);
		
		this.setAdminVisible(true);
		this.seteDossiersVisible(true);
		this.setChairDelegatesVisible(true);
		this.setSuperExportVisible(true);
	}
	
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public void setTargetFlag(boolean targetFlag) {
		this.targetFlag = targetFlag;
	}


	public String getTargetEmployeeName() {
		return targetEmployeeName;
	}


	public void setTargetEmployeeName(String targetEmployeeName) {
		this.targetEmployeeName = targetEmployeeName;
	}


	public String getTargetEmployeeId() {
		return targetEmployeeId;
	}


	public void setTargetEmployeeId(String targetEmployeeId) {
		this.targetEmployeeId = targetEmployeeId;
	}
	
	public boolean isTargetFlag() {
		return targetFlag;
	}

	public boolean isMyDossierVisible() {
		return myDossierVisible;
	}

	public void setMyDossierVisible(boolean myDossierVisible) {
		this.myDossierVisible = myDossierVisible;
	}

	public boolean isCandidateDelegatesVisible() {
		return candidateDelegatesVisible;
	}

	public void setCandidateDelegatesVisible(boolean candidateDelegatesVisible) {
		this.candidateDelegatesVisible = candidateDelegatesVisible;
	}
	
	public boolean isGuestVisible() {
		return guestVisible;
	}

	public void setGuestVisible(boolean guestVisible) {
		this.guestVisible = guestVisible;
	}

	public Boolean isPortalLinksVisible() {
		if(portalLinksVisible == null) {
			String portalLinkFlag = ConfigContext.getCurrentContextConfig().getProperty(EdoConstants.KHR_EDO_PORTAL_LINK_CONFIG);
			this.setPortalLinksVisible(StringUtils.isNotBlank(portalLinkFlag) && portalLinkFlag.equals("true") ? Boolean.TRUE : Boolean.FALSE);
		}
		return portalLinksVisible;
	}

	public void setPortalLinksVisible(Boolean portalLinksVisible) {
		this.portalLinksVisible = portalLinksVisible;
	}

	public boolean iseDossiersVisible() {
		return eDossiersVisible;
	}

	public void seteDossiersVisible(boolean eDossiersVisible) {
		this.eDossiersVisible = eDossiersVisible;
	}

	public boolean isChairDelegatesVisible() {
		return chairDelegatesVisible;
	}

	public void setChairDelegatesVisible(boolean chairDelegatesVisible) {
		this.chairDelegatesVisible = chairDelegatesVisible;
	}

	public boolean isSuperExportVisible() {
		return superExportVisible;
	}

	public void setSuperExportVisible(boolean superExportVisible) {
		this.superExportVisible = superExportVisible;
	}


	public boolean isAdminVisible() {
		return adminVisible;
	}


	public void setAdminVisible(boolean adminVisible) {
		this.adminVisible = adminVisible;
	}
	
}
