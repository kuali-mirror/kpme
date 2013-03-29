package org.kuali.hr.lm.permission.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.KPMENamespace;
import org.kuali.hr.core.permission.KPMEDocumentStatus;
import org.kuali.hr.core.permission.KPMEPermissionTemplate;
import org.kuali.hr.core.permission.service.KPMEPermissionServiceBase;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.service.LeaveCalendarService;
import org.kuali.hr.lm.leaverequest.service.LeaveRequestDocumentService;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.department.service.DepartmentService;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.RoleService;

public class LMPermissionServiceImpl extends KPMEPermissionServiceBase implements LMPermissionService {
	
	private DepartmentService departmentService;
	private PermissionService permissionService;
	private RoleService roleService;
	private LeaveCalendarService leaveCalendarService;
	private LeaveRequestDocumentService leaveRequestDocumentService;
	
	@Override
	public boolean isSystemUser(String principalId) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		List<String> roleIds = new ArrayList<String>();
		roleIds.add(getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_SYSTEM_VIEW_ONLY.getRoleName()));
		roleIds.add(getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_SYSTEM_ADMINISTRATOR.getRoleName()));

		return getRoleService().principalHasRole(principalId, roleIds, qualification);
	}
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), permissionName, qualification);
	}
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification) {
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), permissionName, qualification);
	}
	
    @Override
    public boolean canViewLeaveCalendar(String principalId, String documentId) {
    	return canOwnerViewLeaveCalendar(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KPMEPermissionTemplate.VIEW_KPME_DOCUMENT.getPermissionTemplateName(), documentId);
    }
    
    private boolean canOwnerViewLeaveCalendar(String principalId, String documentId) {
    	boolean canOwnerViewLeaveCalendar = false;
    	
    	LeaveCalendarDocument leaveCalendarDocument = getLeaveCalendarService().getLeaveCalendarDocument(documentId);
    	
    	if (leaveCalendarDocument != null) {
    		canOwnerViewLeaveCalendar = StringUtils.equals(principalId, leaveCalendarDocument.getPrincipalId());
    	}
    	
    	return canOwnerViewLeaveCalendar;
    }
    
    @Override
    public boolean canEditLeaveCalendar(String principalId, String documentId) {
    	return canOwnerEditLeaveCalendar(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KPMEPermissionTemplate.EDIT_KPME_DOCUMENT.getPermissionTemplateName(), documentId);
    }
    
    private boolean canOwnerEditLeaveCalendar(String principalId, String documentId) {
    	boolean canOwnerEditLeaveCalendar = false;
    	
    	LeaveCalendarDocument leaveCalendarDocument = getLeaveCalendarService().getLeaveCalendarDocument(documentId);
    	
    	if (leaveCalendarDocument != null) {
    		DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
        	KPMEDocumentStatus kpmeDocumentStatus = KPMEDocumentStatus.getKPMEDocumentStatus(documentStatus);

        	if (!KPMEDocumentStatus.FINAL.equals(kpmeDocumentStatus)) {
        		canOwnerEditLeaveCalendar = StringUtils.equals(principalId, leaveCalendarDocument.getPrincipalId());
        	}
        }
    	
    	return canOwnerEditLeaveCalendar;
    }
    
    @Override
    public boolean canSubmitLeaveCalendar(String principalId, String documentId) {
        return canOwnerSubmitLeaveCalendar(principalId, documentId) 
        		|| isAuthorizedByTemplate(principalId, KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT, documentId);
    }
    
    private boolean canOwnerSubmitLeaveCalendar(String principalId, String documentId) {
    	boolean canOwnerSubmitLeaveCalendar = false;
    	
    	LeaveCalendarDocument leaveCalendarDocument = getLeaveCalendarService().getLeaveCalendarDocument(documentId);
    	
    	if (leaveCalendarDocument != null) {
    		canOwnerSubmitLeaveCalendar = StringUtils.equals(principalId, leaveCalendarDocument.getPrincipalId());
    	}
    	
    	return canOwnerSubmitLeaveCalendar;
    }
    
    @Override
    public boolean canApproveLeaveCalendar(String principalId, String documentId) {
    	boolean canApproveLeaveCalendar = false;
    	
    	ValidActions validActions = KewApiServiceLocator.getWorkflowDocumentActionsService().determineValidActions(documentId, principalId);
    	
    	if (validActions.getValidActions() != null) {
    		canApproveLeaveCalendar = validActions.getValidActions().contains(ActionType.APPROVE);
    	}
    	
    	return canApproveLeaveCalendar;
    }
    
    @Override
    public boolean canSuperUserAdministerLeaveCalendar(String principalId, String documentId) {
        return isAuthorizedByTemplate(principalId, "Administer Routing for Document", documentId);
    }
    
    @Override
    public boolean canViewLeaveRequest(String principalId, String documentId) {
    	return canOwnerViewLeaveRequest(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KPMEPermissionTemplate.VIEW_KPME_DOCUMENT.getPermissionTemplateName(), documentId);
    }
    
    private boolean canOwnerViewLeaveRequest(String principalId, String documentId) {
    	boolean canOwnerViewLeaveRequest = false;
    	
    	LeaveRequestDocument leaveRequestDocument = getLeaveRequestDocumentService().getLeaveRequestDocument(documentId);
    	
    	if (leaveRequestDocument != null) {
    		canOwnerViewLeaveRequest = StringUtils.equals(principalId, leaveRequestDocument.getDocumentHeader().getWorkflowDocument().getPrincipalId());
    	}
    	
    	return canOwnerViewLeaveRequest;
    }
    
    @Override
    public boolean canEditLeaveRequest(String principalId, String documentId) {
    	return canOwnerEditLeaveRequest(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KPMEPermissionTemplate.EDIT_KPME_DOCUMENT.getPermissionTemplateName(), documentId);
    }
    
    private boolean canOwnerEditLeaveRequest(String principalId, String documentId) {
    	boolean canOwnerEditLeaveRequest = false;
    	
    	LeaveRequestDocument leaveRequestDocument = getLeaveRequestDocumentService().getLeaveRequestDocument(documentId);
    	
    	if (leaveRequestDocument != null) {
    		canOwnerEditLeaveRequest = StringUtils.equals(principalId, leaveRequestDocument.getDocumentHeader().getWorkflowDocument().getPrincipalId());
        }
    	
    	return canOwnerEditLeaveRequest;
    }
    
    @Override
    public boolean canSubmitLeaveRequest(String principalId, String documentId) {
        return canOwnerSubmitLeaveRequest(principalId, documentId) 
        		|| isAuthorizedByTemplate(principalId, KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT, documentId);
    }
    
    private boolean canOwnerSubmitLeaveRequest(String principalId, String documentId) {
    	boolean canOwnerSubmitLeaveRequest = false;
    	
    	LeaveRequestDocument leaveRequestDocument = getLeaveRequestDocumentService().getLeaveRequestDocument(documentId);
    	
    	if (leaveRequestDocument != null) {
    		canOwnerSubmitLeaveRequest = StringUtils.equals(principalId, leaveRequestDocument.getDocumentHeader().getWorkflowDocument().getPrincipalId());
    	}
    	
    	return canOwnerSubmitLeaveRequest;
    }
    
    @Override
    public boolean canApproveLeaveRequest(String principalId, String documentId) {
    	boolean canApproveLeaveRequest = false;
    	
    	ValidActions validActions = KewApiServiceLocator.getWorkflowDocumentActionsService().determineValidActions(documentId, principalId);
    	
    	if (validActions.getValidActions() != null) {
    		canApproveLeaveRequest = validActions.getValidActions().contains(ActionType.APPROVE);
    	}
    	
    	return canApproveLeaveRequest;
    }
    
    @Override
    public boolean canSuperUserAdministerLeaveRequest(String principalId, String documentId) {
        return isAuthorizedByTemplate(principalId, "Administer Routing for Document", documentId);
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, String documentId) {
    	boolean isAuthorizedByTemplate = false;
    	
    	LeaveCalendarDocument leaveCalendarDocument = getLeaveCalendarService().getLeaveCalendarDocument(documentId);
    	
    	if (leaveCalendarDocument != null) {
    		String documentType = LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE;
        	DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
        	String ownerPrincipalId = leaveCalendarDocument.getPrincipalId();
        	List<Assignment> assignments = leaveCalendarDocument.getAssignments();
        	
        	isAuthorizedByTemplate = isAuthorizedByTemplate(principalId, permissionTemplateName, documentType, documentStatus, ownerPrincipalId, assignments);
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
    	String documentTypeName = LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE;

    	return isAuthorizedByTemplateInWorkArea(principalId, permissionTemplateName, workArea, documentTypeName, documentStatus);
    }
    
    @Override
	public boolean isAuthorizedByTemplateInDepartment(String principalId, String permissionTemplateName, String department, DocumentStatus documentStatus) {
    	String documentTypeName = LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE;

    	return isAuthorizedByTemplateInDepartment(principalId, permissionTemplateName, department, documentTypeName, documentStatus);
    }
    
    @Override
	public boolean isAuthorizedByTemplateInLocation(String principalId, String permissionTemplateName, String location, DocumentStatus documentStatus) {
    	String documentTypeName = LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE;
    	
    	return isAuthorizedByTemplateInLocation(principalId, permissionTemplateName, location, documentTypeName, documentStatus);
    }
    
    @Override
    public boolean canEditLeaveBlock(String principalId, LeaveBlock leaveBlock) {
        if (principalId != null) {
            String blockType = leaveBlock.getLeaveBlockType();
            String requestStatus = leaveBlock.getRequestStatus();
            if (StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, requestStatus)) {
                return false;
            }
            if (StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, requestStatus)) {
            	List<LeaveRequestDocument> docList= TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(leaveBlock.getLmLeaveBlockId());
            	if(CollectionUtils.isEmpty(docList)) {
            		return false;	// not a leave request. if this is a leave request, do further checking on it
            	}            	
            }
            if (StringUtils.isBlank(blockType)
                    || StringUtils.equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, blockType)
                    || StringUtils.equals(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR, blockType)) {
            	if (!TKContext.isDepartmentAdmin()) {
            		return true;
            	}
            } else if (LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER.equals(blockType)
                    || LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT.equals(blockType)
                    || LMConstants.LEAVE_BLOCK_TYPE.DONATION_MAINT.equals(blockType)
                    || LMConstants.LEAVE_BLOCK_TYPE.LEAVE_ADJUSTMENT_MAINT.equals(blockType)) {
                if (TKContext.isSystemAdmin()) {
                    return true;
                }
            }
            // kpme-1689
            if(StringUtils.equals(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE, blockType)
            		&& StringUtils.isNotEmpty(leaveBlock.getScheduleTimeOffId())
            		&& leaveBlock.getLeaveAmount().compareTo(BigDecimal.ZERO) == -1) {
            	if(TKContext.isSystemAdmin()) {
            		return true;
            	}
            	SystemScheduledTimeOff ssto = TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(leaveBlock.getScheduleTimeOffId());
            	if(ssto != null && !StringUtils.equals(LMConstants.UNUSED_TIME.NO_UNUSED, ssto.getUnusedTime())) {
            		return true;
            	}
            }
        }

        return false;
    }

    @Override
    public boolean canDeleteLeaveBlock(String principalId, LeaveBlock leaveBlock) {
    	if(StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, leaveBlock.getRequestStatus()))  {
            return false;
        }
    	if(canBankOrTransferSSTOUsage(leaveBlock)) {
    		return true;
    	}
        if (StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, leaveBlock.getRequestStatus())) {
        	List<LeaveRequestDocument> docList= TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(leaveBlock.getLmLeaveBlockId());
        	if(CollectionUtils.isEmpty(docList)) {
        		return false;	// not a leave request
        	}
        }
       
        return canEditLeaveBlock(principalId, leaveBlock);
    }
    
    @Override
	public boolean canBankOrTransferSSTOUsage(LeaveBlock lb) {
		// if it's an accrual generated ssto usage leave block which can be banked or transferred, and on a current leave calendar,
	    // it can be deleted so the accrualed amount can be banked
	    return canBankSSTOUsage(lb) || canTransferSSTOUsage(lb);
	}
    
    @Override
	public boolean canBankSSTOUsage(LeaveBlock lb) {
 	   if(lb.getAccrualGenerated() 
			   && StringUtils.isNotEmpty(lb.getScheduleTimeOffId()) 
			   && lb.getLeaveAmount().compareTo(BigDecimal.ZERO) < 0) {
		   SystemScheduledTimeOff ssto = TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(lb.getScheduleTimeOffId());
		   if(ssto != null && StringUtils.equals(ssto.getUnusedTime(), LMConstants.UNUSED_TIME.BANK)) {
			   Date currentDate = TKUtils.getTimelessDate(null);
			   String viewPrincipal = TKContext.getTargetPrincipalId();
			   CalendarEntry ce = TkServiceLocator.getCalendarService()
						.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, currentDate);
			   if(ce != null) {
				   if(!lb.getLeaveDate().before(ce.getBeginPeriodDate()) && !lb.getLeaveDate().after(ce.getEndPeriodDate())) {
					   return true;
				   }
			   }
			  
		   }
	   }
	   return false;
	}
    
    @Override
	public boolean canTransferSSTOUsage(LeaveBlock lb) {
	   if(lb.getAccrualGenerated() 
			   && StringUtils.isNotEmpty(lb.getScheduleTimeOffId()) 
			   && lb.getLeaveAmount().compareTo(BigDecimal.ZERO) < 0) {
		   SystemScheduledTimeOff ssto = TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(lb.getScheduleTimeOffId());
		   if(ssto != null && ssto.getUnusedTime().equals(LMConstants.UNUSED_TIME.TRANSFER)) {
			   Date currentDate = TKUtils.getTimelessDate(null);
			   String viewPrincipal = TKContext.getTargetPrincipalId();
			   CalendarEntry ce = TkServiceLocator.getCalendarService()
						.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, currentDate);
			   if(ce != null) {
				   if(!lb.getLeaveDate().before(ce.getBeginPeriodDate()) && !lb.getLeaveDate().after(ce.getEndPeriodDate())) {
					   return true;
				   }
			   }
			  
		   }
	   }
	   return false;
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
    
    @Override
    public boolean canViewLeaveTabsWithEStatus() {
    	boolean canViewLeaveTabs = false;
    	String principalId = TKContext.getTargetPrincipalId();
    	Date asOfDate = TKUtils.getTimelessDate(null);
    	boolean leaveCalNPlanDefined = isCalendarDefined("leaveCalendar", principalId, asOfDate, true);
    	String flsaStatus = TkConstants.FLSA_STATUS_EXEMPT;
    	boolean activeAss = isActiveAssignmentFoundOnJobFlsaStatus(principalId, flsaStatus, true);
    	canViewLeaveTabs = activeAss && leaveCalNPlanDefined;
    	return canViewLeaveTabs;
    }
    
    @Override
    public boolean canViewLeaveTabsWithNEStatus() {
    	boolean canViewLeaveTabs = false;
    	Date asOfDate = TKUtils.getTimelessDate(null);
    	String flsaStatus = TkConstants.FLSA_STATUS_NON_EXEMPT;
    	// find active assignments as of currentDate
    	String principalId = TKContext.getTargetPrincipalId();
    	boolean activeAss = isActiveAssignmentFoundOnJobFlsaStatus(principalId, flsaStatus, true);
    	// chk leave plan defined
    	boolean leaveCalNPlanDefined = isCalendarDefined("leaveCalendar", principalId, asOfDate, true);
    	boolean timeCalDefined = isCalendarDefined("payCalendar", principalId, asOfDate, false);
    	canViewLeaveTabs = activeAss && leaveCalNPlanDefined && timeCalDefined;
    	return canViewLeaveTabs;
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

	public LeaveCalendarService getLeaveCalendarService() {
		return leaveCalendarService;
	}

	public void setLeaveCalendarService(LeaveCalendarService leaveCalendarService) {
		this.leaveCalendarService = leaveCalendarService;
	}
	
	public LeaveRequestDocumentService getLeaveRequestDocumentService() {
		return leaveRequestDocumentService;
	}

	public void setLeaveRequestDocumentService(LeaveRequestDocumentService leaveRequestDocumentService) {
		this.leaveRequestDocumentService = leaveRequestDocumentService;
	}
	
}