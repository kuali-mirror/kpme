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
package org.kuali.kpme.tklm.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.HrConstants;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class TagSupport {

    private String principalId;

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public Map<String, String> getDocumentStatus() {
        return HrConstants.DOCUMENT_STATUS;
    }

    private List<String> ipAddresses = new LinkedList<String>();

    public List<String> getIpAddresses() {
        ipAddresses.add("127.0.0.1");
        ipAddresses.add("129.79.23.203");
        ipAddresses.add("129.79.23.59");
        ipAddresses.add("129.79.23.123");

        return ipAddresses;
    }

    public String getPrincipalFullName(){
        EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
        if(person != null
                && person.getDefaultName() != null){
            return person.getDefaultName().getCompositeName();
        }
        return "";
    }
}
