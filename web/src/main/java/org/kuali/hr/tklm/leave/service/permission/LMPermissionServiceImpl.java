package org.kuali.hr.tklm.leave.service.permission;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.KPMENamespace;
import org.kuali.hr.core.bo.assignment.Assignment;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.service.permission.KPMEPermissionServiceBase;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.leave.calendar.service.LeaveCalendarService;
import org.kuali.hr.tklm.leave.request.service.LeaveRequestDocumentService;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.krad.util.KRADConstants;

public class LMPermissionServiceImpl extends KPMEPermissionServiceBase implements LMPermissionService {
	
	private PermissionService permissionService;
	private LeaveCalendarService leaveCalendarService;
	private LeaveRequestDocumentService leaveRequestDocumentService;
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), permissionName, qualification);
	}
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification, DateTime asOfDate) {
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), permissionName, qualification);
	}
	
    @Override
    public boolean canViewLeaveCalendar(String principalId, String documentId) {
    	return canSuperUserAdministerLeaveCalendar(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, documentId);
    }
    
    @Override
    public boolean canViewLeaveCalendarAssignment(String principalId, String documentId, Assignment assignment) {
    	return canSuperUserAdministerLeaveCalendar(principalId, documentId)
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, documentId, assignment);
    }
    
    @Override
    public boolean canEditLeaveCalendar(String principalId, String documentId) {
    	return canSuperUserAdministerLeaveCalendar(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.EDIT_DOCUMENT, documentId);
    }
    
    @Override
    public boolean canEditLeaveCalendarAssignment(String principalId, String documentId, Assignment assignment) {
    	return canSuperUserAdministerLeaveCalendar(principalId, documentId)
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.EDIT_DOCUMENT, documentId, assignment);
    }
    
    @Override
    public boolean canSubmitLeaveCalendar(String principalId, String documentId) {
        return canSuperUserAdministerLeaveCalendar(principalId, documentId) 
        		|| isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT, documentId);
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
        return isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, "Administer Routing for Document", documentId);
    }
    
    @Override
    public boolean canViewLeaveRequest(String principalId, String documentId) {
    	return canSuperUserAdministerLeaveRequest(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, documentId);
    }
    
    @Override
    public boolean canEditLeaveRequest(String principalId, String documentId) {
    	return canSuperUserAdministerLeaveRequest(principalId, documentId) 
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.EDIT_DOCUMENT, documentId);
    }
    
    @Override
    public boolean canSubmitLeaveRequest(String principalId, String documentId) {
        return canSuperUserAdministerLeaveRequest(principalId, documentId) 
        		|| isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT, documentId);
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
        return isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, "Administer Routing for Document", documentId);
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentId) {
    	boolean isAuthorizedByTemplate = false;
    	
    	LeaveCalendarDocument leaveCalendarDocument = getLeaveCalendarService().getLeaveCalendarDocument(documentId);
    	
    	if (leaveCalendarDocument != null) {
    		String documentTypeName = LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE;
        	DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
    		List<Assignment> assignments = leaveCalendarDocument.getAssignments();
        	
        	isAuthorizedByTemplate = isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, assignments);
    	}
    	
    	return isAuthorizedByTemplate;
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentId, Assignment assignment) {
    	boolean isAuthorizedByTemplate = false;
    	
    	LeaveCalendarDocument leaveCalendarDocument = getLeaveCalendarService().getLeaveCalendarDocument(documentId);
    	
    	if (leaveCalendarDocument != null) {
    		String documentTypeName = LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE;
        	DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
        	
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
    public boolean canEditLeaveBlock(String principalId, LeaveBlock leaveBlock) {
        if (principalId != null) {
            String blockType = leaveBlock.getLeaveBlockType();
            String requestStatus = leaveBlock.getRequestStatus();
            if (StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, requestStatus)) {
                return false;
            }
            if (StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, requestStatus)) {
            	List<LeaveRequestDocument> docList= LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(leaveBlock.getLmLeaveBlockId());
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
            	SystemScheduledTimeOff ssto = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(leaveBlock.getScheduleTimeOffId());
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
        	List<LeaveRequestDocument> docList= LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(leaveBlock.getLmLeaveBlockId());
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
		   SystemScheduledTimeOff ssto = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(lb.getScheduleTimeOffId());
		   if(ssto != null && StringUtils.equals(ssto.getUnusedTime(), LMConstants.UNUSED_TIME.BANK)) {
			   String viewPrincipal = TKContext.getTargetPrincipalId();
			   CalendarEntry ce = HrServiceLocator.getCalendarService()
						.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, new LocalDate().toDateTimeAtStartOfDay());
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
		   SystemScheduledTimeOff ssto = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(lb.getScheduleTimeOffId());
		   if(ssto != null && ssto.getUnusedTime().equals(LMConstants.UNUSED_TIME.TRANSFER)) {
			   String viewPrincipal = TKContext.getTargetPrincipalId();
			   CalendarEntry ce = HrServiceLocator.getCalendarService()
						.getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, new LocalDate().toDateTimeAtStartOfDay());
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
    	LocalDate asOfDate = LocalDate.now();
     	List<Assignment> activeAssignments = HrServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
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
    
    private boolean isCalendarDefined(String calendarType, String principalId, LocalDate asOfDate, boolean chkForLeavePlan){
    	boolean calDefined = false;
    	PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
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
    	LocalDate asOfDate = LocalDate.now();
    	boolean leaveCalNPlanDefined = isCalendarDefined("leaveCalendar", principalId, asOfDate, true);
    	String flsaStatus = TkConstants.FLSA_STATUS_EXEMPT;
    	boolean activeAss = isActiveAssignmentFoundOnJobFlsaStatus(principalId, flsaStatus, true);
    	canViewLeaveTabs = activeAss && leaveCalNPlanDefined;
    	return canViewLeaveTabs;
    }
    
    @Override
    public boolean canViewLeaveTabsWithNEStatus() {
    	boolean canViewLeaveTabs = false;
    	LocalDate asOfDate = LocalDate.now();
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

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
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