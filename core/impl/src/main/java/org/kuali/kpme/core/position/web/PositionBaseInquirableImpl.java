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
package org.kuali.kpme.core.position.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.position.PositionBaseContract;
import org.kuali.kpme.core.position.PositionBase;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class PositionBaseInquirableImpl extends KualiInquirableImpl {

	private static final long serialVersionUID = 6232629850941402875L;

	@Override
	@SuppressWarnings("rawtypes")
	public BusinessObject getBusinessObject(Map fieldValues) {
		PositionBaseContract position = null;
		
		if (StringUtils.isNotBlank((String)fieldValues.get("hrPositionId"))) {
            position = HrServiceLocator.getPositionService().getPosition((String) fieldValues.get("hrPositionId"));

		} else if(StringUtils.isNotBlank((String)fieldValues.get("positionNumber")) && StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			String positionNumber = (String) fieldValues.get("positionNumber");
			String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
		    position = HrServiceLocator.getPositionService().getPosition(positionNumber, effectiveDate);
		} else {
			position = (PositionBase) super.getBusinessObject(fieldValues);
		}

		return position;
	}
}
