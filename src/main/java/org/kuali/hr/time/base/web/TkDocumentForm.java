/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.base.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;

public class TkDocumentForm extends KualiDocumentFormBase {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String documentId;

	private String methodToCall;

	public String getMethodToCall() {
		return methodToCall;
	}

	public void setMethodToCall(String methodToCall) {
		this.methodToCall = methodToCall;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public boolean isPropertyEditable(String propertyName) {
		return true;
	}

	@Override
	public boolean isPropertyNonEditableButRequired(String propertyName) {
		return true;
	}

	@Override
	public void populate(HttpServletRequest request) {
        super.populate(request);
		((ActionFormUtilMap) getActionFormUtilMap()).setCacheValueFinderResults(false);
        
		if (this.getMethodToCall() == null || StringUtils.isEmpty(this.getMethodToCall())) {
			setMethodToCall(WebUtils.parseMethodToCall(this, request));
		}
	}
}
