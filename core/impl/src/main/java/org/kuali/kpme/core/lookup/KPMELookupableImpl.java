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
package org.kuali.kpme.core.lookup;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;

public class KPMELookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = 7098170370881970354L;

	@Override
    protected String getActionUrlHref(LookupForm lookupForm, Object dataObject, String methodToCall, List<String> pkNames) {
		if (!StringUtils.equals(methodToCall, "maintenanceView")) {
			return super.getActionUrlHref(lookupForm, dataObject, methodToCall, pkNames);
		} else {
			Properties urlParameters = new Properties();

	        urlParameters.setProperty(UifParameters.DATA_OBJECT_CLASS_NAME, dataObject.getClass().getName());
	        urlParameters.setProperty(UifParameters.METHOD_TO_CALL, UifConstants.MethodToCallNames.START);
	        
	        Map<String, String> primaryKeyValues = KRADUtils.getPropertyKeyValuesFromDataObject(pkNames, dataObject);
	        for (String primaryKey : primaryKeyValues.keySet()) {
	            String primaryKeyValue = primaryKeyValues.get(primaryKey);

	            urlParameters.put(primaryKey, primaryKeyValue);
	        }
	        
	        return UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, urlParameters);
		}
    }

}