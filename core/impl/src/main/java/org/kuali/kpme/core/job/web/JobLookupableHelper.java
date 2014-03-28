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
package org.kuali.kpme.core.job.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.lookup.KPMELookupableHelperServiceImpl;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class JobLookupableHelper extends KPMELookupableHelperServiceImpl {

	private static final long serialVersionUID = 3233495722838070429L;
    private static final ModelObjectUtils.Transformer<Job, JobBo> toJobBo =
            new ModelObjectUtils.Transformer<Job, JobBo>() {
                public JobBo transform(Job input) {
                    return JobBo.from(input);
                };
            };


    @Override
	@SuppressWarnings("rawtypes")
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
    	List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		JobBo job = (JobBo) businessObject;
        String hrJobId = job.getHrJobId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		
		params.put("hrJobId", hrJobId);
		
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		//	Add copy link - KPME-3059
		customActionUrls.add(getUrlData(job, KRADConstants.MAINTENANCE_COPY_METHOD_TO_CALL, pkNames));
		
		return customActionUrls;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String principalId = fieldValues.get("principalId");
        String firstName = fieldValues.get("firstName");
        String lastName = fieldValues.get("lastName");
        String jobNumber = fieldValues.get("jobNumber");
        String dept = fieldValues.get("dept");
        String positionNumber = fieldValues.get("positionNumber");
        String hrPayType = fieldValues.get("hrPayType");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        return ModelObjectUtils.transform(HrServiceLocator.getJobService().getJobs(GlobalVariables.getUserSession().getPrincipalId(), principalId, firstName, lastName, jobNumber, dept, positionNumber, hrPayType,
                TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist), toJobBo);
    }

}