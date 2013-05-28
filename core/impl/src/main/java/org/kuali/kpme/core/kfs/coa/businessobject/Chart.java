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
import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 *
 */
public class Chart extends PersistableBusinessObjectBase implements Inactivatable {

    private static final long serialVersionUID = 4129020803214027609L;

    private String finChartOfAccountDescription;
    private boolean active;
    private String chartOfAccountsCode;

    /**
     * Default no-arg constructor.
     */
    public Chart() {
    }

    /**
     * Gets the finChartOfAccountDescription attribute.
     *
     * @return Returns the finChartOfAccountDescription
     */
    public String getFinChartOfAccountDescription() {
        return finChartOfAccountDescription;
    }

    /**
     * Sets the finChartOfAccountDescription attribute.
     *
     * @param finChartOfAccountDescription The finChartOfAccountDescription to set.
     */
    public void setFinChartOfAccountDescription(String finChartOfAccountDescription) {
        this.finChartOfAccountDescription = finChartOfAccountDescription;
    }

    /**
     * Gets the active attribute.
     *
     * @return Returns the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active attribute.
     *
     * @param active The active to set.
     */
    public void setActive(boolean active) {
        this.active = active;
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
     * @return Returns the code and description in format: xx - xxxxxxxxxxxxxxxx
     */
    public String getCodeAndDescription() {
        if (StringUtils.isNotBlank(getChartOfAccountsCode()) && StringUtils.isNotBlank(getFinChartOfAccountDescription()))
            return getChartOfAccountsCode() + " - " + getFinChartOfAccountDescription();
        else
            return "";
    }

    public String getCode() {
        return this.chartOfAccountsCode;
    }

    public String getName() {
        return this.finChartOfAccountDescription;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();

        m.put("chartOfAccountsCode", this.chartOfAccountsCode);

        return m;
    }
}

