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
package org.kuali.hr.paygrade;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.salgroup.SalGroup;

public class PayGrade extends HrBusinessObject {

	private static final long serialVersionUID = -5736949952127760566L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "PayGrade";

	private String hrPayGradeId;
	private String payGrade;
	private String description;
	private String userPrincipalId;
	private String salGroup;
	private SalGroup salGroupObj;
    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

	public String getHrPayGradeId() {
		return hrPayGradeId;
	}

	public void setHrPayGradeId(String hrPayGradeId) {
		this.hrPayGradeId = hrPayGradeId;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	@Override
	public String getUniqueKey() {
		return payGrade;
	}
	
	@Override
	public String getId() {
		return getHrPayGradeId();
	}

	@Override
	public void setId(String id) {
		setHrPayGradeId(id);
	}
	
	public String getSalGroup() {
		return salGroup;
	}

	public void setSalGroup(String salGroup) {
		this.salGroup = salGroup;
	}

	public SalGroup getSalGroupObj() {
		return salGroupObj;
	}

	public void setSalGroupObj(SalGroup salGroupObj) {
		this.salGroupObj = salGroupObj;
	}

}
