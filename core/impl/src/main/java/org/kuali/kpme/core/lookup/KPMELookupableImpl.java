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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KPMELookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = 7098170370881970354L;

    public void buildViewActionLink(Link actionLink, Object model, String maintenanceMethodToCall) {
        LookupForm lookupForm = (LookupForm) model;

        Map<String, Object> actionLinkContext = actionLink.getContext();
        Object dataObject = actionLinkContext == null ? null : actionLinkContext
                .get(UifConstants.ContextVariableNames.LINE);

        List<String> pkNames = getLegacyDataAdapter().listPrimaryKeyFieldNames(getDataObjectClass());

        Properties urlParameters = new Properties();

        urlParameters.setProperty(UifParameters.DATA_OBJECT_CLASS_NAME, dataObject.getClass().getName());
        urlParameters.setProperty(UifParameters.METHOD_TO_CALL, UifConstants.MethodToCallNames.START);

        Map<String, String> primaryKeyValues = KRADUtils.getPropertyKeyValuesFromDataObject(pkNames, dataObject);
        for (String primaryKey : primaryKeyValues.keySet()) {
            String primaryKeyValue = primaryKeyValues.get(primaryKey);

            urlParameters.put(primaryKey, primaryKeyValue);
        }

        String href = UrlFactory.parameterizeUrl(KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_INQUIRY, urlParameters);
        actionLink.setHref(href);
    }


    /*@Override
    protected String getMaintenanceActionUrl(LookupForm lookupForm, Object dataObject, String methodToCall,
                                             List<String> pkNames) {
        if (!StringUtils.equals(methodToCall, "maintenanceView")) {
            return super.getMaintenanceActionUrl(lookupForm, dataObject, methodToCall, pkNames);
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
    }*/
}