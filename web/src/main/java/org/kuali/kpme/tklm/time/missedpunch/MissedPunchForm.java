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
package org.kuali.kpme.tklm.time.missedpunch;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class MissedPunchForm extends KualiTransactionalDocumentFormBase {

    private static final long serialVersionUID = 1L;
    private Boolean assignmentReadOnly = false;

    @Override
    public String getBackLocation() {
        return "Clock.do?methodToCall=closeMissedPunchDoc";
    }

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        ((ActionFormUtilMap) getActionFormUtilMap()).setCacheValueFinderResults(false);

        if (this.getMethodToCall() == null || StringUtils.isEmpty(this.getMethodToCall())) {
            setMethodToCall(WebUtils.parseMethodToCall(this, request));
        }
    }

	public boolean isAssignmentReadOnly() {
		return assignmentReadOnly;
	}

	public void setAssignmentReadOnly(boolean assignmentReadOnly) {
		this.assignmentReadOnly = assignmentReadOnly;
	}

}
