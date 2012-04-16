package org.kuali.hr.time.permissions;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.util.KEWConstants;

import java.util.List;

public class TkPermissionsServiceImpl implements TkPermissionsService {
    private static final Logger LOG = Logger
            .getLogger(DepartmentalRuleAuthorizer.class);

    @Override
    public boolean canAddTimeBlock() {
        boolean addTimeBlock = false;

        if (TKContext.getUser().isSystemAdmin()) {
            addTimeBlock = true;
        } else {
            boolean docFinal = TKContext.getCurrentTimesheetDoucment()
                    .getDocumentHeader().getDocumentStatus()
                    .equals(TkConstants.ROUTE_STATUS.FINAL);
            if (!docFinal) {
                if (StringUtils
                        .equals(TKContext.getCurrentTimesheetDoucment()
                                .getPrincipalId(), TKContext.getUser()
                                .getPrincipalId())
                        || TKContext.getUser().isSystemAdmin()
                        || TKContext.getUser().isLocationAdmin()
//                        || TKContext.getUser().isDepartmentAdmin()
                        || TKContext.getUser().isReviewer()
                        || TKContext.getUser().isApprover()) {
                    addTimeBlock = true;
                }
            }
        }
        return addTimeBlock;
    }

    @Override
    public boolean canEditTimeBlockAllFields(TimeBlock tb) {

        UserRoles ur = TKContext.getUser().getCurrentRoles();
        String userId = TKContext.getUser().getPrincipalId();

        if (userId != null && ur != null) {

            if (ur.isSystemAdmin()) {
                return true;
            }

            Job job = TkServiceLocator.getJobSerivce().getJob(
                    TKContext.getTargetPrincipalId(), tb.getJobNumber(),
                    tb.getEndDate());
            PayType payType = TkServiceLocator.getPayTypeSerivce().getPayType(
                    job.getHrPayType(), tb.getEndDate());

            if (ur.isTimesheetApprover()
                    && ur.getApproverWorkAreas().contains(tb.getWorkArea())
                    || ur.isTimesheetReviewer()
                    && ur.getReviewerWorkAreas().contains(tb.getWorkArea())) {

                if (StringUtils.equals(payType.getRegEarnCode(),
                        tb.getEarnCode())) {
                    return true;
                }

                List<DepartmentEarnCode> deptEarnCodes = TkServiceLocator
                        .getDepartmentEarnCodeService().getDepartmentEarnCodes(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), tb.getEndDate());
                for (DepartmentEarnCode dec : deptEarnCodes) {
                    if (dec.isApprover()
                            && StringUtils.equals(dec.getEarnCode(),
                            tb.getEarnCode())) {
                        return true;
                    }
                }
            }

            if (userId.equals(TKContext.getTargetPrincipalId())
                    && !tb.getClockLogCreated()) {
                if (StringUtils.equals(payType.getRegEarnCode(),
                        tb.getEarnCode())) {
                    return true;
                }

                List<DepartmentEarnCode> deptEarnCodes = TkServiceLocator
                        .getDepartmentEarnCodeService().getDepartmentEarnCodes(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), tb.getEndDate());
                for (DepartmentEarnCode dec : deptEarnCodes) {
                    if (dec.isEmployee()
                            && StringUtils.equals(dec.getEarnCode(),
                            tb.getEarnCode())) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    @Override
    public boolean canEditTimeBlock(TimeBlock tb) {

        UserRoles ur = TKContext.getUser().getCurrentRoles();
        String userId = TKContext.getUser().getPrincipalId();

        if (userId != null && ur != null) {

            if (ur.isSystemAdmin()) {
                return true;
            }

            Job job = TkServiceLocator.getJobSerivce().getJob(
                    TKContext.getTargetPrincipalId(), tb.getJobNumber(),
                    tb.getEndDate());
            PayType payType = TkServiceLocator.getPayTypeSerivce().getPayType(
                    job.getHrPayType(), tb.getEndDate());

            if (ur.isTimesheetApprover()
                    && ur.getApproverWorkAreas().contains(tb.getWorkArea())
                    || ur.isTimesheetReviewer()
                    && ur.getReviewerWorkAreas().contains(tb.getWorkArea())) {

                if (StringUtils.equals(payType.getRegEarnCode(),
                        tb.getEarnCode())) {
                    return true;
                }

                List<DepartmentEarnCode> deptEarnCodes = TkServiceLocator
                        .getDepartmentEarnCodeService().getDepartmentEarnCodes(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), tb.getEndDate());
                for (DepartmentEarnCode dec : deptEarnCodes) {
                    if (dec.isApprover()
                            && StringUtils.equals(dec.getEarnCode(),
                            tb.getEarnCode())) {
                        return true;
                    }
                }
            }

            if (userId.equals(TKContext.getTargetPrincipalId())) {

                if (StringUtils.equals(payType.getRegEarnCode(),
                        tb.getEarnCode())) {
                    return true;
                }

                List<DepartmentEarnCode> deptEarnCodes = TkServiceLocator
                        .getDepartmentEarnCodeService().getDepartmentEarnCodes(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), tb.getEndDate());
                for (DepartmentEarnCode dec : deptEarnCodes) {
                    if (dec.isEmployee()
                            && StringUtils.equals(dec.getEarnCode(),
                            tb.getEarnCode())) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    @Override
    public boolean canDeleteTimeBlock(TimeBlock tb) {
        UserRoles ur = TKContext.getUser().getCurrentRoles();
        String userId = TKContext.getUser().getPrincipalId();

        if (userId != null && ur != null) {

            if (ur.isSystemAdmin()) {
                return true;
            }

            Job job = TkServiceLocator.getJobSerivce().getJob(
                    TKContext.getTargetPrincipalId(), tb.getJobNumber(),
                    tb.getEndDate());
            PayType payType = TkServiceLocator.getPayTypeSerivce().getPayType(
                    job.getHrPayType(), tb.getEndDate());

            if (ur.isTimesheetApprover()
                    && ur.getApproverWorkAreas().contains(tb.getWorkArea())
                    || ur.isTimesheetReviewer()
                    && ur.getReviewerWorkAreas().contains(tb.getWorkArea())) {

                if (StringUtils.equals(payType.getRegEarnCode(),
                        tb.getEarnCode())) {
                    return true;
                }

                List<DepartmentEarnCode> deptEarnCodes = TkServiceLocator
                        .getDepartmentEarnCodeService().getDepartmentEarnCodes(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), tb.getEndDate());
                for (DepartmentEarnCode dec : deptEarnCodes) {
                    if (dec.isApprover()
                            && StringUtils.equals(dec.getEarnCode(),
                            tb.getEarnCode())) {
                        return true;
                    }
                }
            }

            // If the timeblock was created by the employee himeself and is a sync timeblock,
            // the user can't delete the timeblock
            if (userId.equals(TKContext.getTargetPrincipalId())
                    && tb.getClockLogCreated()) {
                return false;
            // But if the timeblock was created by the employee himeself and is an async timeblock,
            // the user should be able to delete that timeblock
            } else if (userId.equals(TKContext.getTargetPrincipalId()) && !tb.getClockLogCreated() ) {
                return true;
            } else {
                if (StringUtils.equals(payType.getRegEarnCode(),
                        tb.getEarnCode())) {
                    return true;
                }

                List<DepartmentEarnCode> deptEarnCodes = TkServiceLocator
                        .getDepartmentEarnCodeService().getDepartmentEarnCodes(
                                job.getDept(), job.getHrSalGroup(),
                                job.getLocation(), tb.getEndDate());
                for (DepartmentEarnCode dec : deptEarnCodes) {
                    if (dec.isEmployee()
                            && StringUtils.equals(dec.getEarnCode(),
                            tb.getEarnCode())
                            && hasManagerialRolesOnWorkArea(tb)) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    @Override
    public boolean canViewAdminTab() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        boolean viewAdminTab = ur.isSystemAdmin() || ur.isLocationAdmin()
                || ur.isDepartmentAdmin() || ur.isGlobalViewOnly();

        return viewAdminTab;
    }

    @Override
    public boolean canViewApproverTab() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        boolean viewApproverTab = ur.isSystemAdmin()
                || ur.isTimesheetApprover() || ur.isTimesheetReviewer();

        return viewApproverTab;
    }

    @Override
    public boolean canViewClockTab() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        return ur.isActiveEmployee() && ur.isSynchronous();
    }

    @Override
    public boolean canViewBatchJobsTab() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        return ur.isSystemAdmin();
    }

    @Override
    public boolean canViewPersonInfoTab() {
        return true;
    }

    @Override
    public boolean canViewTimeDetailTab() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        return ur.isActiveEmployee();
    }

    @Override
    public boolean canViewLeaveAccrualTab() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        return ur.isActiveEmployee();
    }

    @Override
    public boolean canViewTimesheet(String documentId) {
        boolean viewTimeSheet = false;

        if (documentId != null) {
            return canViewTimesheet(TkServiceLocator.getTimesheetService()
                    .getTimesheetDocument(documentId));
        }

        return viewTimeSheet;
    }

    @Override
    public boolean canViewTimesheet(TimesheetDocument document) {
        boolean viewTimeSheet = false;
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        if (document == null)
            return viewTimeSheet;

        // Sysadmin
        viewTimeSheet = ur.isSystemAdmin();
        // Owner
        viewTimeSheet |= StringUtils.equals(ur.getPrincipalId(),
                document.getPrincipalId());
        // Global VO
        viewTimeSheet |= ur.isGlobalViewOnly();

        if (!viewTimeSheet) {
            // Departmental View Only? || Reviewer || Org Admin || Approver
            // (document object iteration)
            List<Assignment> assignments = document.getAssignments();
            for (Assignment assignment : assignments) {
                String dept = assignment.getDept();
                Long wa = assignment.getWorkArea();

                viewTimeSheet |= ur.getOrgAdminDepartments().contains(dept);
                viewTimeSheet |= ur.getApproverWorkAreas().contains(wa);
                viewTimeSheet |= ur.getReviewerWorkAreas().contains(wa);
                viewTimeSheet |= ur.getDepartmentViewOnlyDepartments()
                        .contains(dept);
            }
        }

        return viewTimeSheet;
    }

    @Override
    public boolean canEditTimesheet(TimesheetDocument document) {
        boolean editTimeSheet = false;
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        // Quick escape.
        if (document == null)
            return editTimeSheet;

        // Sysadmin
        editTimeSheet = ur.isSystemAdmin();
        // Owner (and not enroute/final)
        editTimeSheet |= (StringUtils.equals(ur.getPrincipalId(),
                document.getPrincipalId()) && (StringUtils.equals(
                TkConstants.ROUTE_STATUS.INITIATED, document
                .getDocumentHeader().getDocumentStatus())
                || StringUtils.equals(TkConstants.ROUTE_STATUS.SAVED, document
                .getDocumentHeader().getDocumentStatus()) || (StringUtils
                .equals(TkConstants.ROUTE_STATUS.ENROUTE, document
                        .getDocumentHeader().getDocumentStatus()))));

        if (!editTimeSheet) {
            // Departmental View Only? || Reviewer || Org Admin || Approver
            // (document object iteration)
            List<Assignment> assignments = document.getAssignments();
            for (Assignment assignment : assignments) {
                String dept = assignment.getDept();
                Long wa = assignment.getWorkArea();

                editTimeSheet |= ur.getOrgAdminDepartments().contains(dept);
                editTimeSheet |= ur.getApproverWorkAreas().contains(wa);
                editTimeSheet |= ur.getReviewerWorkAreas().contains(wa);
            }
        }

        return editTimeSheet;
    }

    @Override
    public boolean canEditTimesheet(String documentId) {
        return canEditTimesheet(TkServiceLocator.getTimesheetService()
                .getTimesheetDocument(documentId));
    }

    @Override
    public boolean canApproveTimesheet(TimesheetDocument doc) {

        TimesheetDocumentHeader docHeader = TkServiceLocator
                .getTimesheetDocumentHeaderService().getDocumentHeader(
                        doc.getDocumentId());
        boolean isEnroute = StringUtils.equals(docHeader.getDocumentStatus(),
                "ENROUTE");

        if (isEnroute) {
            DocumentRouteHeaderValue routeHeader = KEWServiceLocator
                    .getRouteHeaderService().getRouteHeader(
                            Long.parseLong(doc.getDocumentId()));
            boolean authorized = KEWServiceLocator.getDocumentSecurityService()
                    .routeLogAuthorized(TKContext.getUserSession(),
                            routeHeader,
                            new SecuritySession(TKContext.getUserSession()));
            if (authorized) {
                List<String> principalsToApprove = KEWServiceLocator
                        .getActionRequestService()
                        .getPrincipalIdsWithPendingActionRequestByActionRequestedAndDocId(
                                KEWConstants.ACTION_REQUEST_APPROVE_REQ,
                                routeHeader.getRouteHeaderId());
                if (!principalsToApprove.isEmpty()
                        && principalsToApprove.contains(TKContext
                        .getPrincipalId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canSubmitTimesheet(TimesheetDocument doc) {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        if (StringUtils
                .equals(TKContext.getPrincipalId(), doc.getPrincipalId())) {
            return true;
        }

        if (ur.isApproverForTimesheet(doc)) {
            return true;
        }

        // System admins can route the document as well as the employee
        if (ur.isSystemAdmin()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canSubmitTimesheet(String docId) {
        TimesheetDocument doc = TkServiceLocator.getTimesheetService()
                .getTimesheetDocument(docId);
        return canSubmitTimesheet(doc);
    }

    @Override
    public boolean canViewLinkOnMaintPages() {
        return TKContext.getUser().getCurrentRoles().isSystemAdmin()
                || TKContext.getUser().isGlobalViewOnly();
    }

    @Override
    public boolean canViewDeptMaintPages() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();

        return ur.isSystemAdmin() || ur.isGlobalViewOnly()
                || ur.getOrgAdminCharts().size() > 0
                || ur.getOrgAdminDepartments().size() > 0
                || ur.getDepartmentViewOnlyDepartments().size() > 0
                || ur.isAnyApproverActive();
    }

    @Override
    public boolean canViewDeptMaintPages(DepartmentalRule dr) {
        boolean ret = false;
        UserRoles ur = TKContext.getUser().getCurrentRoles();
        if (ur.isSystemAdmin() || ur.isGlobalViewOnly())
            return true;

        if (dr != null) {
            // dept | workArea | meaning
            // ---------|------------|
            // 1: % , -1 , any dept/work area valid roles
            // *2: % , <defined> , must have work area <-- *
            // 3: <defined>, -1 , must have dept, any work area
            // 4: <defined>, <defined> , must have work area or department
            // defined
            //
            // * Not permitted.

            if (StringUtils
                    .equals(dr.getDept(), TkConstants.WILDCARD_CHARACTER)
                    && dr.getWorkArea().equals(TkConstants.WILDCARD_LONG)) {
                // case 1
                ret = ur.getApproverWorkAreas().size() > 0
                        || ur.getOrgAdminCharts().size() > 0
                        || ur.getOrgAdminDepartments().size() > 0;
            } else if (StringUtils.equals(dr.getDept(),
                    TkConstants.WILDCARD_CHARACTER)) {
                // case 2 *
                // Should not encounter this case.
                LOG.error("Invalid case encountered while scanning business objects: Wildcard Department & Defined workArea.");
            } else if (dr.getWorkArea().equals(TkConstants.WILDCARD_LONG)) {
                // case 3
                ret = ur.getOrgAdminDepartments().contains(dr.getDept());
            } else {
                ret = ur.getApproverWorkAreas().contains(dr.getWorkArea())
                        || ur.getOrgAdminDepartments().contains(dr.getDept());
            }
        }

        return ret;
    }

    @Override
    public boolean canEditDeptMaintPages() {
        UserRoles ur = TKContext.getUser().getCurrentTargetRoles();
        return ur.isSystemAdmin() || ur.getOrgAdminCharts().size() > 0
                || ur.getOrgAdminDepartments().size() > 0;
    }

    @Override
    public boolean canEditDeptMaintPages(DepartmentalRule dr) {
        boolean ret = false;
        UserRoles ur = TKContext.getUser().getCurrentRoles();
        if (ur.isSystemAdmin())
            return true;

        if (dr != null && ur.getOrgAdminDepartments().size() > 0) {
            String dept = dr.getDept();
            if (StringUtils.equals(dept, TkConstants.WILDCARD_CHARACTER)) {
                // Must be system administrator
                ret = false;
            } else {
                // Must have parent Department
                ret = ur.getOrgAdminDepartments().contains(dr.getDept());
            }
        }

        return ret;
    }

    @Override
    public boolean canWildcardWorkAreaInDeptRule(DepartmentalRule dr) {
        // Sysadmins and (Departmental OrgAdmins for their Department)
        UserRoles ur = TKContext.getUser().getCurrentRoles();
        if (ur.isSystemAdmin())
            return true;

        String dept = dr.getDept();
        if (StringUtils.equals(dept, TkConstants.WILDCARD_CHARACTER)) {
            // Only system administrators can wildcard the work area if the
            // department also has a wildcard.
            return ur.isSystemAdmin();
        } else {
            return ur.getOrgAdminDepartments().contains(dept);
        }
    }

    @Override
    public boolean canWildcardDeptInDeptRule(DepartmentalRule dr) {
        // Sysadmins only.
        UserRoles ur = TKContext.getUser().getCurrentRoles();
        return ur.isSystemAdmin();
    }

    @Override
    public boolean canEditOvertimeEarnCode(TimeBlock tb) {
        WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(tb.getWorkArea(), new java.sql.Date(tb.getEndTimestamp().getTime()));
        if (StringUtils.equals(workArea.getOvertimeEditRole(), TkConstants.ROLE_TK_EMPLOYEE)) {
            return true;
        } else if (StringUtils.equals(workArea.getOvertimeEditRole(), TkConstants.ROLE_TK_APPROVER) ||
                StringUtils.equals(workArea.getOvertimeEditRole(), TkConstants.ROLE_TK_APPROVER_DELEGATE)) {
            return TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(workArea.getWorkArea());
        } else {
            return TKContext.getUser().getCurrentRoles().getOrgAdminDepartments().contains(workArea.getDepartment());
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
        	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService()
        								.getTimeCollectionRule(anAssignment.getDept(), anAssignment.getWorkArea()
        										, anAssignment.getJob().getHrPayType(), anAssignment.getEffectiveDate());
        	if(tcr != null && tcr.isClockUserFl()) {
        		// use assignment to get the payType object, then check if the regEarnCode of the paytyep matches the earn code of the timeblock
        		// if they do match, then return false
        		PayType pt = TkServiceLocator.getPayTypeSerivce().getPayType(anAssignment.getJob().getHrPayType(), anAssignment.getJob().getEffectiveDate());
        		if(pt != null && pt.getRegEarnCode().equals(tb.getEarnCode())) {
        			return false;
        		}
        	}
        }
    	return true;
    }

    @Override
    public boolean canDeleteDeptLunchDeduction() {
        return TKContext.getUser().getCurrentRoles().isAnyApproverActive();
    }

    @Override
    public boolean canAddSystemLevelRole() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canAddLocationLevelRoles() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canAddDepartmentLevelRoles() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canAddWorkareaLevelRoles() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean hasManagerialRolesOnWorkArea(TimeBlock tb) {
        UserRoles ur = TKContext.getUser().getCurrentRoles();
        return ur.getApproverWorkAreas().contains(tb.getWorkArea())
               || ur.getReviewerWorkAreas().contains(tb.getWorkArea());
    }

}
