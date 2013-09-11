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
package org.kuali.kpme.tklm.time.missedpunch.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.search.Range;
import org.kuali.rice.core.api.search.SearchExpressionUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;

public class MissedPunchLookupableImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = 6521192698205632171L;
	
	private static final Logger LOG = Logger.getLogger(MissedPunchLookupableImpl.class);
	
	@Override
	public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
		List<MissedPunch> results = new ArrayList<MissedPunch>();
		
		LocalDate fromDate = null;
		LocalDate toDate = null;
		String toDateString = TKUtils.getToDateString(searchCriteria.get("actionDate"));
		String fromDateString = TKUtils.getFromDateString(searchCriteria.get("actionDate"));
		String actionTimeString = searchCriteria.get("actionTime");
		searchCriteria.remove("actionTime");

		if(StringUtils.isNotBlank(searchCriteria.get("actionDate"))) {
			Range range = SearchExpressionUtils.parseRange(searchCriteria.get("actionDate"));
			boolean invalid = false;
			if(range.getLowerBoundValue() != null && range.getUpperBoundValue() != null) {
				fromDate = TKUtils.formatDateString(fromDateString);
				if(fromDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[rangeLowerBoundKeyPrefix_beginDate]", "error.invalidLookupDate", range.getLowerBoundValue());
					invalid = true;
				}

				toDate = TKUtils.formatDateString(toDateString);
				if(toDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[beginDate]", "error.invalidLookupDate", range.getUpperBoundValue());
					invalid = true;
				}
			}
			else if(range.getLowerBoundValue() != null) {
				fromDate = TKUtils.formatDateString(fromDateString);
				if(fromDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[rangeLowerBoundKeyPrefix_beginDate]", "error.invalidLookupDate", range.getLowerBoundValue());
					invalid = true;
				}
			}
			else if(range.getUpperBoundValue() != null) {
				toDate = TKUtils.formatDateString(toDateString);
				if(toDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[beginDate]", "error.invalidLookupDate", range.getUpperBoundValue());
					invalid = true;
				}
			}
			if(invalid) {
				return new ArrayList<TimeBlock>();
			}
		}
		searchCriteria.remove("actionDate");
		List<?> searchResults = super.getSearchResults(form, searchCriteria, unbounded);
		//clear result messages, these will be re-added with the correct number of retrieved objects once filtering has been completed.
		GlobalVariables.getMessageMap().getInfoMessagesForProperty("LookupResultMessages").clear();
		for (Object searchResult : searchResults) {
			MissedPunch missedPunch = (MissedPunch) searchResult;
			results.add(missedPunch);
		}
		
		results = filterByPrincipalId(results, GlobalVariables.getUserSession().getPrincipalId());
		results = filterByActionDateRange(results,fromDate,toDate);
		if(!StringUtils.isBlank(actionTimeString)) {
			try {
				results = filterByActionTime(results,actionTimeString);
			} catch (ParseException e) {
				
				LOG.warn("caught ParseException while filtering results by Missed Action Time. Cause: " + e.getCause());
			}
		}
		//LookupableImpl sets an info message with the number of results it found.
		//locate the position of this specific message and replace it with the number of filtered results.
/*		AutoPopulatingList<ErrorMessage> infos = GlobalVariables.getMessageMap().getInfoMessagesForProperty("LookupResultMessages");
		AutoPopulatingList<ErrorMessage> correctedInfos = new AutoPopulatingList(ErrorMessage.class);*/
		//copy alll but the 
/*		for(ErrorMessage info : infos) {
			if(!info.getErrorKey().equalsIgnoreCase(RiceKeyConstants.INFO_LOOKUP_RESULTS_DISPLAY_ALL)) {
				correctedInfos.add(info);
			}
		}*/
/*		for(ErrorMessage info : correctedInfos) {
			GlobalVariables.getMessageMap().putError("LookupResultMessages", info);			
		}*/
		
		super.generateLookupResultsMessages(form, searchCriteria, results, unbounded);
		return results;
	}
	
	
	
	private List<MissedPunch> filterByActionTime(List<MissedPunch> results,	String actionTimeString) throws ParseException {
		List<MissedPunch> resultList = new LinkedList<MissedPunch>();

		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		Date actionDateTime = null;
		try {
			actionDateTime = sdf.parse(actionTimeString);
		} catch (ParseException e) {
			GlobalVariables.getMessageMap().putError("lookupCriteria[actionTime]", "error.invalidTime", actionTimeString);
			throw e;
		}

		for(MissedPunch mp : results) {
			String mpActionTime = mp.getActionTime();
			Date mpActionTimeDate = sdf.parse(mpActionTime);
			boolean added = actionDateTime.compareTo(mpActionTimeDate) == 0 ? resultList.add(mp) : false;
		}
		return resultList;
	}



	private List<MissedPunch> filterByActionDateRange(List<MissedPunch> results, LocalDate fromDate, LocalDate toDate) {
		
		List<MissedPunch> filteredResults = new LinkedList<MissedPunch>();
		for(MissedPunch mp : results) {
			boolean added;
			if(fromDate != null && toDate != null) {
				Interval actionInterval = new Interval(fromDate.toDate().getTime(),toDate.toDate().getTime());
				added = actionInterval.contains(mp.getActionDate().getTime()) ? filteredResults.add(mp) : false;
			}
			else if(fromDate != null) {
				added = mp.getActionDate().before(fromDate.toDate()) ? false : filteredResults.add(mp);
			}
			else if(toDate != null) {
				added = mp.getActionDate().after(toDate.toDate()) ? false : filteredResults.add(mp);
			}
			else {
				added = filteredResults.add(mp);
			}
		}
		return filteredResults;
		
	}



	private List<MissedPunch> filterByPrincipalId(List<MissedPunch> missedPunches, String principalId) {
		List<MissedPunch> results = new ArrayList<MissedPunch>();
		
		for (MissedPunch missedPunch : missedPunches) {
			Job jobObj = HrServiceLocator.getJobService().getJob(missedPunch.getPrincipalId(), missedPunch.getJobNumber(), LocalDate.fromDateFields(missedPunch.getActionDate()));
			String department = jobObj != null ? jobObj.getDept() : null;
			
			Department departmentObj = jobObj != null ? HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.fromDateFields(missedPunch.getActionDate())) : null;
			String location = departmentObj != null ? departmentObj.getLocation() : null;
			
			Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, GlobalVariables.getUserSession().getPrincipalId());
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(principalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
					results.add(missedPunch);
			}
		}
		
		return results;
	}

	@Override
	public void initSuppressAction(LookupForm lookupForm) {
/*
 * lookupAuthorizer.canInitiateDocument(lookupForm, user) returns false in this instance, because no
 * documentTypeName can be obtained within LookupViewAuthorizeBase.canInitiateDocument(LookupForm, Person).
 * This effectively suppresses view actions.
 * 
 *      LookupViewAuthorizerBase lookupAuthorizer = (LookupViewAuthorizerBase) lookupForm.getView().getAuthorizer();
        Person user = GlobalVariables.getUserSession().getPerson();
        ((LookupView) lookupForm.getView()).setSuppressActions(!lookupAuthorizer.canInitiateDocument(lookupForm, user));*/
        ((LookupView) lookupForm.getView()).setSuppressActions(false);
	}



	@Override
	protected String getActionUrlHref(LookupForm lookupForm, Object dataObject,
			String methodToCall, List<String> pkNames) {
		if (!StringUtils.equals(methodToCall, "maintenanceView")) {
			return super.getActionUrlHref(lookupForm, dataObject, methodToCall, pkNames);
		} else {
			Properties urlParameters = new Properties();

	        MissedPunch mp = (MissedPunch) dataObject;
	        MissedPunchDocument mpDoc = TkServiceLocator.getMissedPunchService().getMissedPunchDocumentByMissedPunchId(mp.getTkMissedPunchId());

	        urlParameters.setProperty("command", "displayDocSearchView");
	        
	        if(mpDoc != null) {
	        	urlParameters.setProperty("docId", mpDoc.getDocumentNumber());
	        }
	        else {
	        	urlParameters.setProperty(UifParameters.DOC_NUM, null);
	        }

	        //return  HrServiceLocator.CONTEXT.getget"/kpme-dev/kew/DocHandler.do?command=displayDocSearchView&docId="+mpDoc.getDocumentNumber();
	        String url = ConfigContext.getCurrentContextConfig().getProperty("kew.url") +
	        		"/" +
	        		UrlFactory.parameterizeUrl(KRADConstants.DOC_HANDLER_ACTION, urlParameters);
	        
	        return url;
	        //KRADConstants.DOCHANDLER_DO_URL;
	        //UifParameters.
		}
	}
	
}