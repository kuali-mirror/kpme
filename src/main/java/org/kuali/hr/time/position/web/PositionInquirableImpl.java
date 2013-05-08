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
package org.kuali.hr.time.position.web;


import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.base.web.TkInquirableImpl;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class PositionInquirableImpl extends TkInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		Position pos = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("hrPositionId"))) {
			pos = TkServiceLocator.getPositionService().getPosition((String) fieldValues.get("hrPositionId"));

		} else if(StringUtils.isNotBlank((String)fieldValues.get("positionNumber"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			java.util.Date uDate = null;
			try {
				uDate = new SimpleDateFormat("MM/dd/yyyy").parse(fieldValues.get("effectiveDate").toString());

			    Date effdt = new Date(uDate.getTime());
			    pos = TkServiceLocator.getPositionService().getPosition((String) fieldValues.get("positionNumber"), effdt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
			
		} else {
			pos = (Position) super.getBusinessObject(fieldValues);
		}

		return pos;
	}
}
