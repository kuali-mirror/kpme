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
package org.kuali.kpme.core.lookup;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class KpmeHrBusinessObjectLookupableHelper extends KPMELookupableHelperServiceImpl {

	private static final long serialVersionUID = 5042375373803175373L;
	
	private static final String VIEW = "view";
	private boolean viewLinkEnabled = true; 
	
	public boolean isViewLinkEnabled() {
		return viewLinkEnabled;
	}

	public void setViewLinkEnabled(boolean viewLinkEnabled) {
		this.viewLinkEnabled = viewLinkEnabled;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		// remove edit link for inactive BOs if not authorized
		HrBusinessObjectContract bo = (HrBusinessObjectContract) businessObject;
		if ( (!bo.isActive()) && (!HrContext.canEditInactiveRecords()) ) { // KPME-2699
			for (HtmlData action : customActionUrls) {
				if (StringUtils.equals(action.getMethodToCall(), KRADConstants.MAINTENANCE_EDIT_METHOD_TO_CALL)) {
					customActionUrls.remove(action);
					break;
				}
			}
		}
		
		// add view link unless explicitly disabled
		if(this.isViewLinkEnabled()) {			
			Properties parameters = new Properties();
			parameters.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, businessObject.getClass().getName());
			parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);			
			parameters.putAll(getParametersFromPrimaryKey(businessObject, pkNames));			
			AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, parameters), VIEW);
			viewUrl.setDisplayText(VIEW);
			viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
			customActionUrls.add(viewUrl);
		}
		
		return customActionUrls;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends HrBusinessObjectContract> getSearchResults(Map<String, String> fieldValues) {
		// strengthen the return type
		return (List<? extends HrBusinessObjectContract>) super.getSearchResults(fieldValues);
	}

}
