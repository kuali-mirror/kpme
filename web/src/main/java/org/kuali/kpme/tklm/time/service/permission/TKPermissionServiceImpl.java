package org.kuali.kpme.tklm.time.service.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.permission.HrPermissionServiceBase;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.service.TimesheetService;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class TKPermissionServiceImpl extends HrPermissionServiceBase implements TKPermissionService {
	
	private PermissionService permissionService;
	private TimesheetService timesheetService;

	@Override
	public boolean isAuthorized(String principalId, String permissionName, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
	}

	@Override
	public boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification, DateTime asOfDate) {
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), permissionName, qualification);
	}
	
    @Override
    public boolean canViewTimesheet(String principalId, String documentId) {
    	return canSuperUserAdministerTimesheet(principalId, documentId)
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, documentId);
    }
    
    @Override
    public boolean canViewTimesheetAssignment(String principalId, String documentId, Assignment assignment) {
    	return canSuperUserAdministerTimesheet(principalId, documentId)
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, documentId, assignment);
    }
    
    @Override
    public boolean canEditTimesheet(String principalId, String documentId) {
        return canSuperUserAdministerTimesheet(principalId, documentId)
        		|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.EDIT_DOCUMENT, documentId);
    }
    
    @Override
    public boolean canEditTimesheetAssignment(String principalId, String documentId, Assignment assignment) {
    	return canSuperUserAdministerTimesheet(principalId, documentId)
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.EDIT_DOCUMENT, documentId, assignment);
    }
    
    @Override
    public boolean canSubmitTimesheet(String principalId, String documentId) {
        return canSuperUserAdministerTimesheet(principalId, documentId)
        		|| isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT, documentId);
    }
    
    @Override
    public boolean canApproveTimesheet(String principalId, String documentId) {
    	boolean canApproveTimesheet = false;
    	
    	ValidActions validActions = KewApiServiceLocator.getWorkflowDocumentActionsService().determineValidActions(documentId, principalId);
    	
    	if (validActions.getValidActions() != null) {
    		canApproveTimesheet = validActions.getValidActions().contains(ActionType.APPROVE);
    	}
    	
    	return canApproveTimesheet;
    }
    
    @Override
    public boolean canSuperUserAdministerTimesheet(String principalId, String documentId) {
        return isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, "Administer Routing for Document", documentId);
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentId) {
    	boolean isAuthorizedByTemplate = false;
    	
    	TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(documentId);
    	
    	if (timesheetDocument != null) {
    		String documentTypeName = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE;
        	DocumentStatus documentStatus = DocumentStatus.fromCode(timesheetDocument.getDocumentHeader().getDocumentStatus());
    		List<Assignment> assignments = timesheetDocument.getAssignments();
        	
        	isAuthorizedByTemplate = isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, assignments);
    	}
    	
    	return isAuthorizedByTemplate;
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentId, Assignment assignment) {
    	boolean isAuthorizedByTemplate = false;
    	
    	TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(documentId);
    	
    	if (timesheetDocument != null) {
    		String documentTypeName = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE;
        	DocumentStatus documentStatus = DocumentStatus.fromCode(timesheetDocument.getDocumentHeader().getDocumentStatus());
        	
        	isAuthorizedByTemplate = isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, assignment);
    	}
    	
    	return isAuthorizedByTemplate;
    }
    
    @Override
	public boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
	}
	
    @Override
	public boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification, DateTime asOfDate) {
		return getPermissionService().isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification);
	}

    @Override
    public boolean canEditTimeBlock(String principalId, TimeBlock timeBlock) {
        if (principalId != null) {

        	// if the sys admin user is working on his own time block, do not grant edit permission without further checking
            if (HrContext.isSystemAdmin()&& !timeBlock.getPrincipalId().equals(principalId)) {
            	return true;
            }
            Job job = HrServiceLocator.getJobService().getJob(
                    HrContext.getTargetPrincipalId(), timeBlock.getJobNumber(),
                    timeBlock.getEndDateTime().toLocalDate());
            PayType payType = HrServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), timeBlock.getEndDateTime().toLocalDate());
            
        	if (HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.REVIEWER.getRoleName(), timeBlock.getWorkArea(), new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), timeBlock.getWorkArea(), new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), timeBlock.getWorkArea(), new DateTime())) {
        		
                if (StringUtils.equals(payType.getRegEarnCode(),
                		timeBlock.getEarnCode())) {
                    return true;
                }

                List<EarnCodeSecurity> deptEarnCodes = HrServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
                for (EarnCodeSecurity dec : deptEarnCodes) {
                    if (dec.isApprover()
                            && StringUtils.equals(dec.getEarnCode(),
                            		timeBlock.getEarnCode())) {
                        return true;
                    }
                }
            }
            
            // if the time block is generated by clock actions, do not allow it to be edited/deleted
			if(timeBlock.getClockLogCreated()) {
					return false;
			}

            if (principalId.equals(HrContext.getTargetPrincipalId())) {

                if (StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())) {
                    //If you are a clock user and you have only one assignment you should not be allowed to change the assignment
                    //TODO eventually move this logic to one concise place for editable portions of the timeblock
                    List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(HrContext.getPrincipalId(),timeBlock.getBeginDateTime().toLocalDate());
                    if (assignments.size() == 1) {
                    	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(job.getDept(),timeBlock.getWorkArea(),job.getHrPayType(),timeBlock.getBeginDateTime().toLocalDate());
                    	
                    	if (tcr != null && !tcr.isClockUserFl()) {
                    		return true;
                        }  else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                }

                List<EarnCodeSecurity> deptEarnCodes = HrServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
                for (EarnCodeSecurity dec : deptEarnCodes) {
                    if (dec.isEmployee()
                            && StringUtils.equals(dec.getEarnCode(),
                            		timeBlock.getEarnCode())) {
                        return true;
                    }
                }
            }

        }

        return false;
    }
    
    @Override
    public boolean canEditTimeBlockAllFields(String principalId, TimeBlock timeBlock) {
        if (principalId != null) {

            if (HrContext.isSystemAdmin()) {
                return true;
            }

            Job job = HrServiceLocator.getJobService().getJob(
                    HrContext.getTargetPrincipalId(), timeBlock.getJobNumber(),
                    timeBlock.getEndDateTime().toLocalDate());
            PayType payType = HrServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), timeBlock.getEndDateTime().toLocalDate());

        	if (HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.REVIEWER.getRoleName(), timeBlock.getWorkArea(), new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), timeBlock.getWorkArea(), new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), timeBlock.getWorkArea(), new DateTime())) {
        		
                if (StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())) {
                    TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(job.getDept(),timeBlock.getWorkArea(),timeBlock.getBeginDateTime().toLocalDate());
                    
                    if (tcr != null && !tcr.isClockUserFl()) {
                    	return true;
                    } else {
                        return false;
                    }
                }

                List<EarnCodeSecurity> deptEarnCodes = HrServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
                for (EarnCodeSecurity dec : deptEarnCodes) {
                    if (dec.isApprover()
                            && StringUtils.equals(dec.getEarnCode(),
                            		timeBlock.getEarnCode())) {
                        return true;
                    }
                }
            }

            if (principalId.equals(HrContext.getTargetPrincipalId())
                    && !timeBlock.getClockLogCreated()) {
                if (StringUtils.equals(payType.getRegEarnCode(),
                		timeBlock.getEarnCode())) {
                    return true;
                }

                List<EarnCodeSecurity> deptEarnCodes = HrServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
                for (EarnCodeSecurity dec : deptEarnCodes) {
                    if (dec.isEmployee()
                            && StringUtils.equals(dec.getEarnCode(),
                            		timeBlock.getEarnCode())) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    @Override
    public boolean canDeleteTimeBlock(String principalId, TimeBlock timeBlock) {
        if (principalId != null) {

        	// if the sys admin user is working on his own time block, do not grant delete permission without further checking
            if (HrContext.isSystemAdmin()&& !timeBlock.getPrincipalId().equals(principalId)) {
            	return true;
            }
            Job job = HrServiceLocator.getJobService().getJob(
                    HrContext.getTargetPrincipalId(), timeBlock.getJobNumber(),
                    timeBlock.getEndDateTime().toLocalDate());
            PayType payType = HrServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), timeBlock.getEndDateTime().toLocalDate());

        	if (HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.REVIEWER.getRoleName(), timeBlock.getWorkArea(), new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), timeBlock.getWorkArea(), new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), timeBlock.getWorkArea(), new DateTime())) {

                if (StringUtils.equals(payType.getRegEarnCode(),
                		timeBlock.getEarnCode())) {
                    return true;
                }

                List<EarnCodeSecurity> deptEarnCodes = HrServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
                for (EarnCodeSecurity dec : deptEarnCodes) {
                    if (dec.isApprover()
                            && StringUtils.equals(dec.getEarnCode(),
                            		timeBlock.getEarnCode())) {
                        return true;
                    }
                }
            }

//            // If the timeblock was created by the employee himeself and is a sync timeblock,
//            // the user can't delete the timeblock
//            if (userId.equals(HrContext.getTargetPrincipalId())
//                    && tb.getClockLogCreated()) {
//                return false;
//            // But if the timeblock was created by the employee himeself and is an async timeblock,
//            // the user should be able to delete that timeblock
//            } else if (userId.equals(HrContext.getTargetPrincipalId()) && !tb.getClockLogCreated() ) {
//                return true;
//            } else {
            
            // if the time block is generated by clock actions, do not allow it to be edited/deleted
			if(timeBlock.getClockLogCreated()) {
					return false;
			}

            //if on a regular earncode and the user is a clock user and this is the users timesheet, do not allow to be deleted
            if (StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())) {
            	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(job.getDept(),timeBlock.getWorkArea(),payType.getPayType(),timeBlock.getEndDateTime().toLocalDate());
            	
            	if (tcr == null || tcr.isClockUserFl()) {
            		if (StringUtils.equals(principalId,HrContext.getTargetPrincipalId())) {
	                    return false;
	                }  else {
	                    return true;
	                }
                }
            }
            
            
            //KPME-2264 -
            // EE's should be able to remove timeblocks added via the time detail calendar only after checking prior conditions,
            if (principalId.equals(HrContext.getTargetPrincipalId())) {
            	return true;
            }      

            List<EarnCodeSecurity> deptEarnCodes = HrServiceLocator
                    .getEarnCodeSecurityService().getEarnCodeSecurities(
                            job.getDept(), job.getHrSalGroup(),
                            job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
            for (EarnCodeSecurity dec : deptEarnCodes) {
            	if (HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.REVIEWER.getRoleName(), timeBlock.getWorkArea(), new DateTime())
            			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), timeBlock.getWorkArea(), new DateTime())
            			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), timeBlock.getWorkArea(), new DateTime())) {
	                
            		if (dec.isEmployee()
	                        && StringUtils.equals(dec.getEarnCode(),
	                        		timeBlock.getEarnCode())) {
	                    return true;
	                }
            	}
            }

        }

        return false;
    }
    
    @Override
    public boolean canEditOvertimeEarnCode(TimeBlock timeBlock) {
        String principalId = GlobalVariables.getUserSession().getPrincipalId();
        Long workArea = timeBlock.getWorkArea();
    	WorkArea workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, timeBlock.getEndDateTime().toLocalDate());
    	String department = workAreaObj.getDept();
    	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
		String location = departmentObj != null ? departmentObj.getLocation() : null;
		
    	if (StringUtils.equals(workAreaObj.getOvertimeEditRole(), "Employee")) {
            return true;
        } else if (StringUtils.equals(workAreaObj.getOvertimeEditRole(), KPMERole.APPROVER.getRoleName()) ||
                StringUtils.equals(workAreaObj.getOvertimeEditRole(), KPMERole.APPROVER_DELEGATE.getRoleName())) {
            return HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, new DateTime())
            		|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), workArea, new DateTime());
        } else {
            return TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
        }
    }
    
    /*
     * @see org.kuali.kpme.tklm.time.permissions.TkPermissionsService#canEditRegEarnCode(org.kuali.kpme.tklm.time.timeblock.TimeBlock)
     * this method is used in calendar.tag
     * it's only used when a user is working on its own timesheet, regular earn code cannot be editable on clock entered time block
     */
    @Override
    public boolean canEditRegEarnCode(TimeBlock tb) {
    	AssignmentDescriptionKey adk = new AssignmentDescriptionKey(tb.getJobNumber().toString(), tb.getWorkArea().toString(), tb.getTask().toString());
        Assignment anAssignment = HrServiceLocator.getAssignmentService().getAssignment(adk, tb.getBeginDateTime().toLocalDate());
        if(anAssignment != null) {
        	// use timesheet's end date to get Time Collection Rule
        	TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(tb.getDocumentId());
        	DateTime aDate =  tb.getBeginDateTime();
        	if(tdh != null && tdh.getEndDate() != null) {
        		aDate = new DateTime(tdh.getEndDate());
        	}
        	
        	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(anAssignment.getDept(), anAssignment.getWorkArea(), anAssignment.getJob().getHrPayType(), aDate.toLocalDate());
        	if (tcr == null || tcr.isClockUserFl()) {
        		// use assignment to get the payType object, then check if the regEarnCode of the paytyep matches the earn code of the timeblock
        		// if they do match, then return false
        		PayType pt = HrServiceLocator.getPayTypeService().getPayType(anAssignment.getJob().getHrPayType(), anAssignment.getJob().getEffectiveLocalDate());
        		if(pt != null && pt.getRegEarnCode().equals(tb.getEarnCode())) {
        			return false;
        		}
        	}
        }
    	return true;
    }

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public TimesheetService getTimesheetService() {
		return timesheetService;
	}

	public void setTimesheetService(TimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}
	
}