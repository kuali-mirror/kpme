package org.kuali.hr.time.permission.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.KPMENamespace;
import org.kuali.hr.core.permission.KPMEDocumentStatus;
import org.kuali.hr.core.permission.KPMEPermissionTemplate;
import org.kuali.hr.core.permission.service.KPMEPermissionServiceBase;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.department.service.DepartmentService;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.RoleService;

public class TKPermissionServiceImpl extends KPMEPermissionServiceBase implements TKPermissionService {
	
	private DepartmentService departmentService;
	private PermissionService permissionService;
	private RoleService roleService;
	private TimesheetService timesheetService;
	
	@Override
	public boolean isSystemUser(String principalId) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		List<String> roleIds = new ArrayList<String>();
		roleIds.add(getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_SYSTEM_VIEW_ONLY.getRoleName()));
		roleIds.add(getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_SYSTEM_ADMINISTRATOR.getRoleName()));

		return getRoleService().principalHasRole(principalId, roleIds, qualification);
	}
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return isAuthorized(principalId, permissionName, qualification);
	}
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification) {
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), permissionName, qualification);
	}
	
    @Override
    public boolean canViewTimesheet(String principalId, String documentId) {
    	return canOwnerViewTimesheet(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KPMEPermissionTemplate.VIEW_KPME_DOCUMENT.getPermissionTemplateName(), documentId);
    }
    
    private boolean canOwnerViewTimesheet(String principalId, String documentId) {
    	boolean canOwnerViewTimesheet = false;
    	
    	TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(documentId);
    	
    	if (timesheetDocument != null) {
        	canOwnerViewTimesheet = StringUtils.equals(principalId, timesheetDocument.getPrincipalId());
    	}
    	
    	return canOwnerViewTimesheet;
    }
    
    @Override
    public boolean canEditTimesheet(String principalId, String documentId) {
        return canOwnerEditTimesheet(principalId, documentId) 
        		|| isAuthorizedByTemplate(principalId, KPMEPermissionTemplate.EDIT_KPME_DOCUMENT.getPermissionTemplateName(), documentId);
    }
    
    private boolean canOwnerEditTimesheet(String principalId, String documentId) {
    	boolean canOwnerEditTimesheet = false;
    	
    	TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(documentId);
    	
    	if (timesheetDocument != null) {
        	DocumentStatus documentStatus = DocumentStatus.fromCode(timesheetDocument.getDocumentHeader().getDocumentStatus());
        	KPMEDocumentStatus kpmeDocumentStatus = KPMEDocumentStatus.getKPMEDocumentStatus(documentStatus);

        	if (!KPMEDocumentStatus.FINAL.equals(kpmeDocumentStatus)) {
        		canOwnerEditTimesheet = StringUtils.equals(principalId, timesheetDocument.getPrincipalId());
        	}
    	}
    	
    	return canOwnerEditTimesheet;
    }
    
    @Override
    public boolean canSubmitTimesheet(String principalId, String documentId) {
        return canOwnerSubmitTimesheet(principalId, documentId) 
        		|| isAuthorizedByTemplate(principalId, KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT, documentId);
    }
    
    private boolean canOwnerSubmitTimesheet(String principalId, String documentId) {
    	boolean canOwnerSubmitTimesheet = false;
    	
    	TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(documentId);
    	
    	if (timesheetDocument != null) {
    		canOwnerSubmitTimesheet = StringUtils.equals(principalId, timesheetDocument.getPrincipalId());
    	}
    	
    	return canOwnerSubmitTimesheet;
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
        return isAuthorizedByTemplate(principalId, "Administer Routing for Document", documentId);
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, String documentId) {
    	boolean isAuthorizedByTemplate = false;
    	
    	TimesheetDocument timesheetDocument = getTimesheetService().getTimesheetDocument(documentId);
    	
    	if (timesheetDocument != null) {
    		String documentTypeName = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE;
        	DocumentStatus documentStatus = DocumentStatus.fromCode(timesheetDocument.getDocumentHeader().getDocumentStatus());
        	String ownerPrincipalId = timesheetDocument.getPrincipalId();
        	List<Assignment> assignments = timesheetDocument.getAssignments();
        	
        	isAuthorizedByTemplate = isAuthorizedByTemplate(principalId, permissionTemplateName, documentTypeName, documentStatus, ownerPrincipalId, assignments);
    	}
    	
    	return isAuthorizedByTemplate;
    }
    
    @Override
	public boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, Map<String, String> permissionDetails) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return isAuthorizedByTemplate(principalId, permissionTemplateName, permissionDetails, qualification);
	}
	
    @Override
	public boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification) {
		return getPermissionService().isAuthorizedByTemplate(principalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(), permissionTemplateName, permissionDetails, qualification);
	}
    
    @Override
	public boolean isAuthorizedByTemplateInWorkArea(String principalId, String permissionTemplateName, Long workArea, DocumentStatus documentStatus) {
    	String documentTypeName = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE;

    	return isAuthorizedByTemplateInWorkArea(principalId, permissionTemplateName, workArea, documentTypeName, documentStatus);
    }
    
    @Override
	public boolean isAuthorizedByTemplateInDepartment(String principalId, String permissionTemplateName, String department, DocumentStatus documentStatus) {
    	String documentTypeName = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE;

    	return isAuthorizedByTemplateInDepartment(principalId, permissionTemplateName, department, documentTypeName, documentStatus);
    }
    
    @Override
	public boolean isAuthorizedByTemplateInLocation(String principalId, String permissionTemplateName, String location, DocumentStatus documentStatus) {
    	String documentTypeName = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE;
    	
    	return isAuthorizedByTemplateInLocation(principalId, permissionTemplateName, location, documentTypeName, documentStatus);
    }

    @Override
    public boolean canEditTimeBlock(String principalId, TimeBlock timeBlock) {
        if (principalId != null) {

        	// if the sys admin user is working on his own time block, do not grant edit permission without further checking
            if (TKContext.isSystemAdmin()&& !timeBlock.getPrincipalId().equals(principalId)) {
            	return true;
            }
            Job job = TkServiceLocator.getJobService().getJob(
                    TKContext.getTargetPrincipalId(), timeBlock.getJobNumber(),
                    timeBlock.getEndDate());
            PayType payType = TkServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), timeBlock.getEndDate());
            
            if (TKContext.isAnyApprover()
                    && TKContext.getApproverWorkAreas().contains(timeBlock.getWorkArea())
                    || TKContext.isReviewer()
                    && TKContext.getReviewerWorkAreas().contains(timeBlock.getWorkArea())) {

                if (StringUtils.equals(payType.getRegEarnCode(),
                		timeBlock.getEarnCode())) {
                    return true;
                }

                List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDate());
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

            if (principalId.equals(TKContext.getTargetPrincipalId())) {

                if (StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())) {
                    //If you are a clock user and you have only one assignment you should not be allowed to change the assignment
                    //TODO eventually move this logic to one concise place for editable portions of the timeblock
                    List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(),timeBlock.getBeginDate());
                    if (assignments.size() == 1) {
                    	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(job.getDept(),timeBlock.getWorkArea(),job.getHrPayType(),timeBlock.getBeginDate());
                    	
                    	if (tcr != null && !tcr.isClockUserFl()) {
                    		return true;
                        }  else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                }

                List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDate());
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

            if (TKContext.isSystemAdmin()) {
                return true;
            }

            Job job = TkServiceLocator.getJobService().getJob(
                    TKContext.getTargetPrincipalId(), timeBlock.getJobNumber(),
                    timeBlock.getEndDate());
            PayType payType = TkServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), timeBlock.getEndDate());

            if (TKContext.isAnyApprover()
                    && TKContext.getApproverWorkAreas().contains(timeBlock.getWorkArea())
                    || TKContext.isReviewer()
                    && TKContext.getReviewerWorkAreas().contains(timeBlock.getWorkArea())) {

                if (StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())) {
                    TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(job.getDept(),timeBlock.getWorkArea(),timeBlock.getBeginDate());
                    
                    if (tcr != null && !tcr.isClockUserFl()) {
                    	return true;
                    } else {
                        return false;
                    }
                }

                List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDate());
                for (EarnCodeSecurity dec : deptEarnCodes) {
                    if (dec.isApprover()
                            && StringUtils.equals(dec.getEarnCode(),
                            		timeBlock.getEarnCode())) {
                        return true;
                    }
                }
            }

            if (principalId.equals(TKContext.getTargetPrincipalId())
                    && !timeBlock.getClockLogCreated()) {
                if (StringUtils.equals(payType.getRegEarnCode(),
                		timeBlock.getEarnCode())) {
                    return true;
                }

                List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDate());
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
            if (TKContext.isSystemAdmin()&& !timeBlock.getPrincipalId().equals(principalId)) {
            	return true;
            }
            Job job = TkServiceLocator.getJobService().getJob(
                    TKContext.getTargetPrincipalId(), timeBlock.getJobNumber(),
                    timeBlock.getEndDate());
            PayType payType = TkServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), timeBlock.getEndDate());

            if (TKContext.isAnyApprover()
                    && TKContext.getApproverWorkAreas().contains(timeBlock.getWorkArea())
                    || TKContext.isReviewer()
                    && TKContext.getReviewerWorkAreas().contains(timeBlock.getWorkArea())) {

                if (StringUtils.equals(payType.getRegEarnCode(),
                		timeBlock.getEarnCode())) {
                    return true;
                }

                List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator
                        .getEarnCodeSecurityService().getEarnCodeSecurities(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), timeBlock.getEndDate());
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
//            if (userId.equals(TKContext.getTargetPrincipalId())
//                    && tb.getClockLogCreated()) {
//                return false;
//            // But if the timeblock was created by the employee himeself and is an async timeblock,
//            // the user should be able to delete that timeblock
//            } else if (userId.equals(TKContext.getTargetPrincipalId()) && !tb.getClockLogCreated() ) {
//                return true;
//            } else {
            
            // if the time block is generated by clock actions, do not allow it to be edited/deleted
			if(timeBlock.getClockLogCreated()) {
					return false;
			}

            //if on a regular earncode and the user is a clock user and this is the users timesheet, do not allow to be deleted
            if (StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())) {
            	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(job.getDept(),timeBlock.getWorkArea(),payType.getPayType(),timeBlock.getEndDate());
            	
            	if (tcr == null || tcr.isClockUserFl()) {
            		if (StringUtils.equals(principalId,TKContext.getTargetPrincipalId())) {
	                    return false;
	                }  else {
	                    return true;
	                }
                }
            }

            List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator
                    .getEarnCodeSecurityService().getEarnCodeSecurities(
                            job.getDept(), job.getHrSalGroup(),
                            job.getLocation(), timeBlock.getEndDate());
            for (EarnCodeSecurity dec : deptEarnCodes) {
                if (dec.isEmployee()
                        && StringUtils.equals(dec.getEarnCode(),
                        		timeBlock.getEarnCode())
                        && hasManagerialRolesOnWorkArea(timeBlock)) {
                    return true;
                }
            }

        }

        return false;
    }
    

    public boolean hasManagerialRolesOnWorkArea(TimeBlock tb) {
        return TKContext.getApproverWorkAreas().contains(tb.getWorkArea())
               || TKContext.getReviewerWorkAreas().contains(tb.getWorkArea());
    }
    
    @Override
    public boolean canEditOvertimeEarnCode(TimeBlock tb) {
        WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(tb.getWorkArea(), new java.sql.Date(tb.getEndTimestamp().getTime()));
        if (StringUtils.equals(workArea.getOvertimeEditRole(), "Employee")) {
            return true;
        } else if (StringUtils.equals(workArea.getOvertimeEditRole(), KPMERole.APPROVER.getRoleName()) ||
                StringUtils.equals(workArea.getOvertimeEditRole(), KPMERole.APPROVER_DELEGATE.getRoleName())) {
            return TKContext.getApproverWorkAreas().contains(workArea.getWorkArea());
        } else {
            return TKContext.getDepartmentAdminAreas().contains(workArea.getDepartment());
        }
    }
    
    /*
     * @see org.kuali.hr.time.permissions.TkPermissionsService#canEditRegEarnCode(org.kuali.hr.time.timeblock.TimeBlock)
     * this method is used in calendar.tag
     * it's only used when a user is working on its own timesheet, regular earn code cannot be editable on clock entered time block
     */
    @Override
    public boolean canEditRegEarnCode(TimeBlock tb) {
    	AssignmentDescriptionKey adk = new AssignmentDescriptionKey(tb.getJobNumber().toString(), tb.getWorkArea().toString(), tb.getTask().toString());
        Assignment anAssignment = TkServiceLocator.getAssignmentService().getAssignment(adk, tb.getBeginDate());
        if(anAssignment != null) {
        	// use timesheet's end date to get Time Collection Rule
        	TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(tb.getDocumentId());
        	Date aDate =  tb.getBeginDate();
        	if(tdh != null && tdh.getEndDate() != null) {
        		aDate = new java.sql.Date(tdh.getEndDate().getTime());
        	}
        	
        	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(anAssignment.getDept(), anAssignment.getWorkArea(), anAssignment.getJob().getHrPayType(), aDate);
        	if (tcr == null || tcr.isClockUserFl()) {
        		// use assignment to get the payType object, then check if the regEarnCode of the paytyep matches the earn code of the timeblock
        		// if they do match, then return false
        		PayType pt = TkServiceLocator.getPayTypeService().getPayType(anAssignment.getJob().getHrPayType(), anAssignment.getJob().getEffectiveDate());
        		if(pt != null && pt.getRegEarnCode().equals(tb.getEarnCode())) {
        			return false;
        		}
        	}
        }
    	return true;
    }
    
    @Override
    public boolean canViewTimeTabs() {
    	boolean canViewTimeTabs = false;
    	Date asOfDate = TKUtils.getTimelessDate(null);
    	String flsaStatus = TkConstants.FLSA_STATUS_NON_EXEMPT;
    	// find active assignments as of currentDate
    	String principalId = TKContext.getTargetPrincipalId();
    	if(isActiveAssignmentFoundOnJobFlsaStatus(principalId, flsaStatus, false)) {
    		//find timecalendar defined
    		canViewTimeTabs = isCalendarDefined("payCalendar", principalId, asOfDate, false);
    	}
    	return canViewTimeTabs;
    }
    
    private boolean isActiveAssignmentFoundOnJobFlsaStatus(String principalId, String flsaStatus, boolean chkForLeaveEligible) {
    	boolean isActiveAssFound = false;
    	Date asOfDate = TKUtils.getTimelessDate(null);
     	List<Assignment> activeAssignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
     	if(activeAssignments != null && !activeAssignments.isEmpty()) {
     		for(Assignment assignment : activeAssignments) {
     			if(assignment != null && assignment.getJob() != null && assignment.getJob().getFlsaStatus() != null && assignment.getJob().getFlsaStatus().equalsIgnoreCase(flsaStatus)) {
     				if(chkForLeaveEligible) {
     					isActiveAssFound = assignment.getJob().isEligibleForLeave();
     					if(!isActiveAssFound){
     						continue;
     					}
     				}
     				isActiveAssFound = true;
     				break;
     			}  
     		}
     	}
    	return isActiveAssFound;
    }
    
    private boolean isCalendarDefined(String calendarType, String principalId, Date asOfDate, boolean chkForLeavePlan){
    	boolean calDefined = false;
    	PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
    	if(principalHRAttributes != null) {
    		if(calendarType.equalsIgnoreCase("payCalendar")) {
    			calDefined = principalHRAttributes.getPayCalendar() != null ? true : false;
    		} else if(calendarType.equalsIgnoreCase("leaveCalendar")) {
    			calDefined = principalHRAttributes.getLeaveCalendar() != null ? true : false;
    			if(calDefined && chkForLeavePlan) {
    				calDefined = principalHRAttributes.getLeavePlan() != null ? true : false;
    			}
    		} 
    	}
    	return calDefined;
    }

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	public RoleService getRoleService() {
		return roleService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public TimesheetService getTimesheetService() {
		return timesheetService;
	}

	public void setTimesheetService(TimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}
	
}