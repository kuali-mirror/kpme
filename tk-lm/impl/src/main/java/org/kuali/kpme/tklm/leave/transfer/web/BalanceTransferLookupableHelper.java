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
package org.kuali.kpme.tklm.leave.transfer.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class BalanceTransferLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = -6910172165048825489L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		BalanceTransfer bt = (BalanceTransfer) businessObject;
		String balanceTransferId = bt.getBalanceTransferId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("balanceTransferId", balanceTransferId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String principalId = fieldValues.get("principalId");
        String fromAccrualCategory = fieldValues.get("fromAccrualCategory");
        String transferAmount = fieldValues.get("transferAmount");
        String toAccrualCategory = fieldValues.get("toAccrualCategory");
        String amountTransferred = fieldValues.get("amountTransferred");
        String forfeitedAmount = fieldValues.get("forfeitedAmount");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));

        List<BalanceTransfer> transfers = LmServiceLocator.getBalanceTransferService().getBalanceTransfers(principalId, fromAccrualCategory, transferAmount, toAccrualCategory, 
        		amountTransferred, forfeitedAmount, TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt));
        
        transfers = filterByPrincipalId(transfers);
        
        return transfers;
    }

	private List<BalanceTransfer> filterByPrincipalId(
			List<BalanceTransfer> transfers) {
        //TODO - performance
		if(!transfers.isEmpty()) {
			Iterator<? extends BusinessObject> iter = transfers.iterator();
			while(iter.hasNext()) {
				BalanceTransfer transfer = (BalanceTransfer) iter.next();
				LocalDate effectiveLocalDate = transfer.getEffectiveLocalDate();
				DateTime effectiveDate = effectiveLocalDate.toDateTimeAtStartOfDay();
				List<Job> principalsJobs = (List<Job>) HrServiceLocator.getJobService().getActiveLeaveJobs(transfer.getPrincipalId(), effectiveLocalDate);
				String userPrincipalId = HrContext.getPrincipalId();
				boolean canView = false;
				for(Job job : principalsJobs) {
					
					if(job.isEligibleForLeave()) {
						
						String department = job != null ? job.getDept() : null;
						DepartmentContract departmentObj = job != null ? HrServiceLocator.getDepartmentService().getDepartmentWithoutRoles(department, effectiveLocalDate) : null;
						String location = departmentObj != null ? departmentObj.getLocation() : null;

			        	if (LmServiceLocator.getLMPermissionService().isAuthorizedInDepartment(userPrincipalId, "View Balance Transfer", department, effectiveDate)
							|| LmServiceLocator.getLMPermissionService().isAuthorizedInLocation(userPrincipalId, "View Balance Transfer", location, effectiveDate)) {
								canView = true;
								break;
						}
			        	else {
			        		//do NOT block approvers, processors, delegates from viewing the object.
							List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getActiveAssignmentsForJob(transfer.getPrincipalId(), job.getJobNumber(), effectiveLocalDate);
							for(Assignment assignment : assignments) {
								if(HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))
										|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))
										|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))
										|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))) {
									canView = true;
									break;
								}
							}
			        	}
					}
				}
				if(!canView) {
					iter.remove();
				}
			}
		}
		return transfers;
	}

}
