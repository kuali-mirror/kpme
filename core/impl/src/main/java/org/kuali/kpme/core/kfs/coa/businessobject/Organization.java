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
package org.kuali.kpme.core.kfs.coa.businessobject;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * 
 */
public class Organization extends PersistableBusinessObjectBase implements Inactivatable {
    private static final Logger LOG = Logger.getLogger(Organization.class);

    private static final long serialVersionUID = 121873645110037203L;

    /**
     * Default no-arg constructor.
     */

    private String organizationCode;
    private String organizationName;
    private Chart chartOfAccounts;
    private boolean active;
    
    // fields for mixed anonymous keys
    private String chartOfAccountsCode;

    /**
     * Gets the organizationCode attribute.
     * 
     * @return Returns the organizationCode
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * Sets the organizationCode attribute.
     * 
     * @param organizationCode The organizationCode to set.
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    /**
     * Gets the organizationName attribute.
     * 
     * @return Returns the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the organizationName attribute.
     * 
     * @param organizationName The organizationName to set.
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    

    /**
     * Gets the chartOfAccounts attribute.
     * 
     * @return Returns the chartOfAccounts
     */
    public Chart getChartOfAccounts() {
        return chartOfAccounts;
    }

    /**
     * Sets the chartOfAccounts attribute.
     * 
     * @param chartOfAccounts The chartOfAccounts to set.
     * @deprecated
     */
    public void setChartOfAccounts(Chart chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
    }

    /**
     * Gets the chartOfAccountsCode attribute.
     * 
     * @return Returns the chartOfAccountsCode.
     */
    public String getChartOfAccountsCode() {
        return chartOfAccountsCode;
    }

    /**
     * Sets the chartOfAccountsCode attribute value.
     * 
     * @param chartOfAccountsCode The chartOfAccountsCode to set.
     */
    public void setChartOfAccountsCode(String chartOfAccountsCode) {
        this.chartOfAccountsCode = chartOfAccountsCode;
    }

    
    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();

        m.put("chartOfAccountsCode", this.chartOfAccountsCode);
        m.put("organizationCode", this.organizationCode);

        return m;
    }

    /**
   

    public String getOrganizationReviewHierarchy() {

        Properties params = new Properties();
        params.put("methodToCall", "start");
        params.put("docFormKey", "");
        params.put("lookupableImplServiceName", "RuleBaseValuesLookupableImplService");
        params.put("fin_coa_cd", this.chartOfAccountsCode);
        params.put("org_cd", this.organizationCode);
        params.put("conversionFields", "");
        params.put("returnLocation", "");
        params.put("active_ind", "true");
        params.put("ruleTemplateName", "KualiOrgReviewTemplate");

        return UrlFactory.parameterizeUrl(SpringContext.getBean(KualiConfigurationService.class).getPropertyString(KFSConstants.WORKFLOW_URL_KEY) + "/Lookup.do", params);
    }

    /**
     * Implementing equals so Org will behave reasonably in a hashed datastructure.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        boolean equal = false;

        LOG.debug("Org equals");

        if (obj != null) {

            if (this == obj)
                return true;

            if (this.getClass().isAssignableFrom(obj.getClass())) {

                Organization other = (Organization) obj;

                LOG.debug("this: " + this);
                LOG.debug("other: " + other);

                if (StringUtils.equals(this.getChartOfAccountsCode(), other.getChartOfAccountsCode())) {
                    if (StringUtils.equals(this.getOrganizationCode(), other.getOrganizationCode())) {
                        equal = true;
                    }
                }
            }
        }

        return equal;
    }

    /**
     * @return Returns the code and description in format: xx - xxxxxxxxxxxxxxxx
     */
    public String getCodeAndDescription() {
        String theString = getOrganizationCode() + "-" + getOrganizationName();
        return theString;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        String hashString = getChartOfAccountsCode() + "|" + getOrganizationCode();
        return hashString.hashCode();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
