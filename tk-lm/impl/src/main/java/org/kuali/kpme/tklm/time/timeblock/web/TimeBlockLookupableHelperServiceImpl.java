/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.timeblock.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.search.Range;
import org.kuali.rice.core.api.search.SearchExpressionUtils;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.document.DocumentStatusCategory;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.inquiry.Inquirable;
import org.kuali.rice.krad.lookup.LookupUtils;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.uif.widget.Inquiry;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.LookupForm;

public class TimeBlockLookupableHelperServiceImpl extends KPMELookupableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DOC_ID = "documentId";
	private static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	private static final String BEGIN_DATE = "beginDate";
	private static final String BEGIN_TIMESTAMP = "beginTimestamp";
    private static final String BEGIN_DATE_LOWER = KRADConstants.LOOKUP_RANGE_LOWER_BOUND_PROPERTY_PREFIX + "beginDate";
    private static final String BEGIN_DATE_UPPER = KRADConstants.LOOKUP_RANGE_UPPER_BOUND_PROPERTY_PREFIX + "beginDate";

	private static final Logger LOG = Logger.getLogger(TimeBlockLookupableHelperServiceImpl.class);

    public void buildInquiryLink(Object dataObject, String propertyName, Inquiry inquiry) {
        Class inquirableClass = dataObject.getClass();
        if(dataObject instanceof TimeBlock) {
            TimeBlock tb = (TimeBlock) dataObject;
            if (tb.getConcreteBlockType() != null
                    && tb.getConcreteBlockType().equals(LeaveBlock.class.getName())) {
                inquirableClass = LeaveBlock.class;
            }
        }

        Inquirable inquirable = getViewDictionaryService().getInquirable(inquirableClass, inquiry.getViewName());
        if (inquirable != null) {
            if(!inquirableClass.equals(LeaveBlock.class)) {
                inquirable.buildInquirableLink(dataObject, propertyName, inquiry);
            }
        } else {
            // TODO: should we really not render the inquiry just because the top parent doesn't have an inquirable?
            // it is possible the path is nested and there does exist an inquiry for the property
            // inquirable not found, no inquiry link can be set
            inquiry.setRender(false);
        }
    }

    @Override
    protected String getActionUrlHref(LookupForm lookupForm, Object dataObject,
                                      String methodToCall, List<String> pkNames) {


        String actionUrlHref = super.getActionUrlHref(lookupForm, dataObject, methodToCall, pkNames);
        String concreteBlockId = null;
        if(dataObject instanceof TimeBlock) {
            TimeBlock tb = (TimeBlock) dataObject;
            concreteBlockId = tb.getTkTimeBlockId();
            if (tb.getConcreteBlockType() != null
                    && tb.getConcreteBlockType().equals(LeaveBlock.class.getName())) {
                actionUrlHref = actionUrlHref.replace("tkTimeBlockId", "lmLeaveBlockId");
                actionUrlHref = actionUrlHref.replace(TimeBlock.class.getName(), LeaveBlock.class.getName());
            }

        }
        if(concreteBlockId == null) {
            return null;
        }

        return actionUrlHref;
    }

    @Override
    public void initSuppressAction(LookupForm lookupForm) {
        ((LookupView) lookupForm.getView()).setSuppressActions(false);
    }

    @Override
    protected List<?> getSearchResults(LookupForm form,
                                       Map<String, String> searchCriteria, boolean unbounded) {
        List<TimeBlock> results = new ArrayList<TimeBlock>();

        if (searchCriteria.containsKey(BEGIN_DATE)) {
            searchCriteria.put(BEGIN_TIMESTAMP, searchCriteria.get(BEGIN_DATE));
            searchCriteria.remove(BEGIN_DATE);
        }
        if (searchCriteria.containsKey(DOC_STATUS_ID)) {
            searchCriteria.put(DOC_STATUS_ID, resolveDocumentStatus(searchCriteria.get(DOC_STATUS_ID)));
        }
        //List<TimeBlock> searchResults = new ArrayList<TimeBlock>();
        List<TimeBlock> searchResults = (List<TimeBlock>)super.getSearchResults(form, searchCriteria, unbounded);

        //convert lookup criteria for LeaveBlock
        Map<String, String> leaveCriteria = new HashMap<String, String>();
        leaveCriteria.putAll(searchCriteria);
        leaveCriteria.put("accrualGenerated", "N");
        if (leaveCriteria.containsKey(DOC_ID)) {
            TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(leaveCriteria.get(DOC_ID));
            if (tdh != null) {
                leaveCriteria.put(KRADConstants.LOOKUP_RANGE_LOWER_BOUND_PROPERTY_PREFIX + "leaveDate", TKUtils.formatDate(tdh.getBeginDateTime().toLocalDate()));
                leaveCriteria.put(KRADConstants.LOOKUP_RANGE_UPPER_BOUND_PROPERTY_PREFIX +"leaveDate", TKUtils.formatDate(tdh.getEndDateTime().toLocalDate()));
            }
        }
        if (leaveCriteria.containsKey(BEGIN_DATE_LOWER)) {
            leaveCriteria.put(KRADConstants.LOOKUP_RANGE_LOWER_BOUND_PROPERTY_PREFIX + "leaveDate", leaveCriteria.get(BEGIN_DATE_LOWER));
            leaveCriteria.remove(BEGIN_DATE_LOWER);
        }
        if (leaveCriteria.containsKey(BEGIN_DATE_UPPER)) {
            leaveCriteria.put(KRADConstants.LOOKUP_RANGE_UPPER_BOUND_PROPERTY_PREFIX + "leaveDate", leaveCriteria.get(BEGIN_DATE_UPPER));
            leaveCriteria.remove(BEGIN_DATE_UPPER);
        }
        if (leaveCriteria.containsKey(DOC_STATUS_ID)) {
            leaveCriteria.put("leaveCalendarDocumentHeader.documentStatus", leaveCriteria.get(DOC_STATUS_ID));
            leaveCriteria.remove(DOC_STATUS_ID);
        }
        LookupForm leaveBlockForm = (LookupForm) ObjectUtils.deepCopy(form);
        leaveBlockForm.setDataObjectClassName(LeaveBlock.class.getName());
        setDataObjectClass(LeaveBlock.class);
        List<LeaveBlock> leaveBlocks = (List<LeaveBlock>)super.getSearchResults(leaveBlockForm, LookupUtils.forceUppercase(LeaveBlock.class, leaveCriteria), unbounded);
        List<TimeBlock> convertedLeaveBlocks = convertLeaveBlockHistories(leaveBlocks);
        searchResults.addAll(convertedLeaveBlocks);
        for ( TimeBlock searchResult : searchResults) {
            TimeBlock timeBlock = (TimeBlock) searchResult;
            results.add(timeBlock);
        }

        results = filterByPrincipalId(results, GlobalVariables.getUserSession().getPrincipalId());
        sortSearchResults(form, searchResults);

        return results;
    }

    protected List<TimeBlock> convertLeaveBlockHistories(List<LeaveBlock> leaveBlocks) {
        List<TimeBlock> histories = new ArrayList<TimeBlock>();
        for(LeaveBlock leaveBlock : leaveBlocks) {

            TimeBlock tBlock = new TimeBlock();
            tBlock.setAmount(leaveBlock.getLeaveAmount());
            tBlock.setHours(leaveBlock.getHours());

            tBlock.setEarnCode(leaveBlock.getEarnCode());
            tBlock.setPrincipalId(leaveBlock.getPrincipalId());
            tBlock.setUserPrincipalId(leaveBlock.getPrincipalIdModified());
            tBlock.setPrincipalIdModified(leaveBlock.getPrincipalIdModified());
            tBlock.setWorkArea(leaveBlock.getWorkArea());
            AssignmentDescriptionKey assignKey = AssignmentDescriptionKey.get(leaveBlock.getAssignmentKey());
            tBlock.setWorkArea(assignKey.getWorkArea());
            tBlock.setJobNumber(assignKey.getJobNumber());
            tBlock.setTask(assignKey.getTask());
            tBlock.setOvertimePref(leaveBlock.getOvertimePref());
            tBlock.setLunchDeleted(leaveBlock.getLunchDeleted());
            tBlock.setDocumentId(leaveBlock.getDocumentId());
            tBlock.setBeginDate(leaveBlock.getLeaveDate());
            tBlock.setEndDate(leaveBlock.getLeaveDate());
            tBlock.setTimeHourDetails(new ArrayList<TimeHourDetail>());
            tBlock.setTimestamp(leaveBlock.getTimestamp());
            tBlock.setClockLogCreated(false);
            tBlock.setTkTimeBlockId(leaveBlock.getLmLeaveBlockId());
            tBlock.setTkTimeBlockId(leaveBlock.getLmLeaveBlockId());
            tBlock.setConcreteBlockType(leaveBlock.getClass().getName());

            histories.add(tBlock);

        }
        return histories;
    }

    protected String resolveDocumentStatus(String selectedStatus) {
        if (StringUtils.isNotBlank(selectedStatus)) {
            if (StringUtils.contains(selectedStatus, "category:")) {
                String[] category = selectedStatus.split(":");
                DocumentStatusCategory statusCategory = DocumentStatusCategory.fromCode(category[1]);
                Set<DocumentStatus> categoryStatuses = DocumentStatus.getStatusesForCategory(statusCategory);
                StringBuffer status = new StringBuffer();
                for (DocumentStatus docStatus : categoryStatuses) {
                    status.append(docStatus.getCode()).append("|");
                }
                return status.toString();
            } else {
                return selectedStatus;
            }
        }
        return StringUtils.EMPTY;
    }

    private List<TimeBlock> filterByPrincipalId(List<TimeBlock> timeBlocks, String principalId) {
        List<TimeBlock> results = new ArrayList<TimeBlock>();

        //TODO - performance  too many db calls in loop
        for (TimeBlock timeBlock : timeBlocks) {
            JobContract jobObj = HrServiceLocator.getJobService().getJob(timeBlock.getPrincipalId(), timeBlock.getJobNumber(), LocalDate.fromDateFields(timeBlock.getBeginDate()), false);
            String department = jobObj != null ? jobObj.getDept() : null;

            DepartmentContract departmentObj = jobObj != null ? HrServiceLocator.getDepartmentService().getDepartmentWithoutRoles(department, LocalDate.fromDateFields(timeBlock.getBeginDate())) : null;
            String location = departmentObj != null ? departmentObj.getLocation() : null;

            Map<String, String> roleQualification = new HashMap<String, String>();
            roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, GlobalVariables.getUserSession().getPrincipalId());
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
            roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);

            if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                    KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
                    || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(principalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                    KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
                results.add(timeBlock);
            }
        }

        return results;
    }

	/*@Override
	protected String getActionUrlHref(LookupForm lookupForm, Object dataObject,
			String methodToCall, List<String> pkNames) {
		String actionUrlHref = super.getActionUrlHref(lookupForm, dataObject, methodToCall, pkNames);
		TimeBlock tb = null;
		String concreteBlockId = null;
		if(dataObject instanceof TimeBlock) {
			tb = (TimeBlock) dataObject;
			concreteBlockId = tb.getTkTimeBlockId();
		}
		if(concreteBlockId == null) {
			return null;
		}

		return actionUrlHref;
	}*/

	/*@Override
	public void initSuppressAction(LookupForm lookupForm) {
		((LookupView) lookupForm.getView()).setSuppressActions(false);
	}*/

}
