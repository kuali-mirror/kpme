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
package org.kuali.kpme.core.assignment.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.lookup.KpmeHrBusinessObjectLookupableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.DocumentDictionaryService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;

@SuppressWarnings("deprecation")
public class AssignmentLookupableHelper extends KpmeHrBusinessObjectLookupableImpl {

	private static final long serialVersionUID = 774015772672806415L;
	private static final ModelObjectUtils.Transformer<Assignment, AssignmentBo> toAssignmentBo =
            new ModelObjectUtils.Transformer<Assignment, AssignmentBo>() {
                public AssignmentBo transform(Assignment input) {
                    return AssignmentBo.from(input);
                };
            };

	
//	@Override
//	@SuppressWarnings("rawtypes")
//    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
//    	List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
//
//		Assignment assignment = (Assignment) businessObject;
//        String tkAssignmentId = assignment.getTkAssignmentId();
//
//		Properties params = new Properties();
//		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
//		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
//		params.put("tkAssignmentId", tkAssignmentId);
//		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
//		viewUrl.setDisplayText("view");
//		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
//		customActionUrls.add(viewUrl);
//		
//		//	Add copy link - KPME-3060
//		customActionUrls.add(getUrlData(assignment, KRADConstants.MAINTENANCE_COPY_METHOD_TO_CALL, pkNames));
//		
//		return customActionUrls;
//    }


	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
        String fromEffdt = TKUtils.getFromDateString(searchCriteria.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(searchCriteria.get("effectiveDate"));
        String principalId = searchCriteria.get("principalId");
        String jobNumber = searchCriteria.get("jobNumber");
        String dept = searchCriteria.get("dept");
        String workArea = searchCriteria.get("workArea");
        String active = searchCriteria.get("active");
        String showHist = searchCriteria.get("history");

        return ModelObjectUtils.transform(HrServiceLocator.getAssignmentService().searchAssignments(GlobalVariables.getUserSession().getPrincipalId(), TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), principalId, 
        		jobNumber, dept, workArea, active, showHist), toAssignmentBo);
	}
	
}