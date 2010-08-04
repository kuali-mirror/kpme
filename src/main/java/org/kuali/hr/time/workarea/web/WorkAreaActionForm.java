package org.kuali.hr.time.workarea.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.role.assign.service.TkRoleAssignService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaMaintenanceDocument;
import org.kuali.hr.time.workarea.service.WorkAreaService;
import org.kuali.rice.kns.web.struts.form.KualiTableRenderFormMetadata;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class WorkAreaActionForm extends KualiTransactionalDocumentFormBase {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected transient KualiTableRenderFormMetadata memberTableMetadata;
    private TkRoleAssign newRoleAssignment;
    

    @Override
    public String getDefaultDocumentTypeName() {
	return "WorkAreaMaintenanceDocument";
    }

    @Override
    public String getDocTypeName() {
	return "WorkAreaMaintenanceDocument";
    }

    @Override
    public void populate(HttpServletRequest request) {
	super.populate(request);
    }

    public KualiTableRenderFormMetadata getMemberTableMetadata() {
	return this.memberTableMetadata;
    }

    public void setMemberTableMetadata(KualiTableRenderFormMetadata memberTableMetadata) {
	this.memberTableMetadata = memberTableMetadata;
    }

    @Override
    public boolean shouldMethodToCallParameterBeUsed(String methodToCallParameterName, String methodToCallParameterValue, HttpServletRequest request) {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public boolean shouldPropertyBePopulatedInForm(String requestParameterName, HttpServletRequest request) {
	// TODO Auto-generated method stub
	return true;
    }

    public TkRoleAssign getNewRoleAssignment() {
        return newRoleAssignment;
    }

    public void setNewRoleAssignment(TkRoleAssign newRoleAssignment) {
        this.newRoleAssignment = newRoleAssignment;
    }
    
}
