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
package org.kuali.hr.tklm.time.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.ModuleService;

/**
 * This is set up in the kr/struts-config.xml, so that our maintenance docs
 * have some modified header information.
 */
public class TimeMaintenanceForm extends KualiMaintenanceForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * This was cut and pasted from the parent, with some modification.
	 */
	@Override
	protected String getPersonInquiryUrlLink(Person user, String linkBody) {
        StringBuffer urlBuffer = new StringBuffer();
                
        if(user != null && StringUtils.isNotEmpty(linkBody) ) {
        	ModuleService moduleService = KRADServiceLocatorWeb.getKualiModuleService().getResponsibleModuleService(Person.class);
        	Map<String, String[]> parameters = new HashMap<String, String[]>();
        	parameters.put(KimConstants.AttributeConstants.PRINCIPAL_ID, new String[] { user.getPrincipalId() });
        	String inquiryUrl = moduleService.getExternalizableBusinessObjectInquiryUrl(Person.class, parameters);
            if(!StringUtils.equals(KimConstants.EntityTypes.SYSTEM, user.getEntityTypeCode())){
	            urlBuffer.append("<a href='");
	            urlBuffer.append(inquiryUrl);
	            urlBuffer.append("' ");
	            urlBuffer.append("target='_blank'");
	            urlBuffer.append("title='Person Inquiry'>");
	            urlBuffer.append(linkBody);
	            urlBuffer.append("</a>");
	            
	            // Added re: KPME-207 (https://jira.kuali.org/browse/KPME-207)
	            urlBuffer.append(" (");
	            urlBuffer.append(user.getName());
	            urlBuffer.append(")");
            } else{
            	urlBuffer.append(linkBody);
            }
        }
        
        return urlBuffer.toString();
	}

}
