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
package org.kuali.rice.kns.web.struts.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kns.inquiry.Inquirable;
import org.kuali.rice.kns.web.struts.form.InquiryForm;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class TkInquiryAction extends KualiInquiryAction {
	
	private static final Logger LOG = Logger.getLogger(TkInquiryAction.class);
	
	protected BusinessObject retrieveBOUsingKeyMap(Map<String, String> keyMap, Inquirable kualiInquirable) {
		BusinessObject bo = kualiInquirable.getBusinessObject(keyMap);
        if (bo == null) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_INQUIRY);
        }
        return bo;
	}
	
	@Override
	public ActionForward continueWithInquiry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	InquiryForm inquiryForm = (InquiryForm) form;
    	
    	if (inquiryForm.getBusinessObjectClassName() == null) {
    		LOG.error("Business object name not given.");
    		throw new RuntimeException("Business object name not given.");
    	}
    	BusinessObject bo;
    	if(TkConstants.CLASS_INQUIRY_KEY_MAP.containsKey(inquiryForm.getBusinessObjectClassName())) {
    		Map<String, String> keyMap = inquiryForm.retrieveInquiryDecryptedPrimaryKeys();
    		for(String aKey : TkConstants.CLASS_INQUIRY_KEY_MAP.get(inquiryForm.getBusinessObjectClassName())) {
    			if(request.getParameter(aKey) != null) {
    				keyMap.put(aKey, request.getParameter(aKey).toString());
    			}
    		}
    		bo = retrieveBOUsingKeyMap(keyMap, inquiryForm.getInquirable());
    	} else {
    		bo = retrieveBOFromInquirable(inquiryForm);
    	}
        checkBO(bo);
        
        populateSections(mapping, request, inquiryForm, bo);
        
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }
	
	private void checkBO(BusinessObject bo) {
        if (bo == null && GlobalVariables.getMessageMap().hasNoMessages()) {
        	throw new UnsupportedOperationException("The record you have inquired on does not exist.");
        }
    }
	
}
