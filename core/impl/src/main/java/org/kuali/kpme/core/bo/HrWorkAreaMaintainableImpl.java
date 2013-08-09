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
package org.kuali.kpme.core.bo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.cache.CacheUtils;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public abstract class HrWorkAreaMaintainableImpl extends HrBusinessObjectMaintainableImpl {
    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HrWorkAreaMaintainableImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String wildCardAndLongPattern = "\\%|((\\+|-)?([0-9]+))";

	@Override
    public Map populateBusinessObject(Map<String, String> fieldValues, MaintenanceDocument maintenanceDocument, String methodToCall) {
         if(fieldValues.containsKey("workArea")){
        	Pattern pattern = Pattern.compile(wildCardAndLongPattern);
	        String input = (String)fieldValues.get("workArea");
	        // create a matcher that will match the given input against this pattern
	        Matcher matcher = pattern.matcher(input);
	        boolean matchFound = matcher.matches();
	        if(!matchFound) {
	        	GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "workArea" , "error.validation.long.or.wildcard", input);
	        	System.out.println("Now field values are >> "+fieldValues);
	        	return fieldValues;
	        }
        } else if (fieldValues.containsKey("workArea") && StringUtils.equals(fieldValues.get("workArea"), "%")) {
            fieldValues.put("workArea", "-1");
        } 
        
        // Validating JobNumber
        if(fieldValues.containsKey("jobNumber")){
         	Pattern pattern = Pattern.compile(wildCardAndLongPattern);
 	        String input = (String)fieldValues.get("jobNumber");
 	        // create a matcher that will match the given input against this pattern
 	        Matcher matcher = pattern.matcher(input);
 	        boolean matchFound = matcher.matches();
 	        if(!matchFound) {
 	        	GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "jobNumber" , "error.validation.long.or.wildcard", input);
 	        	return fieldValues;
 	        }
         } else if (fieldValues.containsKey("jobNumber") && StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
            fieldValues.put("jobNumber", "-1");
        }
        return super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
    }
	
	
}
