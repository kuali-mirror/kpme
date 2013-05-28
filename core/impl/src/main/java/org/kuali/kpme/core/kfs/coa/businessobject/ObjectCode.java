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

import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 *
 */
public class ObjectCode extends PersistableBusinessObjectBase implements Inactivatable {


/*    static {
        PersistenceStructureServiceImpl.referenceConversionMap.put(ObjectCode.class, ObjectCodeCurrent.class);
    }*/

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ObjectCode.class);

    private static final long serialVersionUID = -965833141452795485L;
    private Integer universityFiscalYear;
    private String chartOfAccountsCode;
    private String financialObjectCode;
    private String financialObjectCodeName;
    private String financialObjectCodeShortName;
    private boolean active;
    private String financialObjectLevelCode;
    private transient Chart chartOfAccounts;


    /**
     * This method is only for use by the framework
     */
    public void setUniversityFiscalYear(Integer i) {
        this.universityFiscalYear = i;
    }


    /**
     * Gets the financialObjectCode attribute.
     *
     * @return Returns the financialObjectCode
     */
    public String getFinancialObjectCode() {
        return financialObjectCode;
    }

    /**
     * Sets the financialObjectCode attribute.
     *
     * @param financialObjectCode The financialObjectCode to set.
     */
    public void setFinancialObjectCode(String financialObjectCode) {
        this.financialObjectCode = financialObjectCode;
    }

    /**
     * Gets the financialObjectCodeName attribute.
     *
     * @return Returns the financialObjectCodeName
     */
    public String getFinancialObjectCodeName() {
        return financialObjectCodeName;
    }

    /**
     * Sets the financialObjectCodeName attribute.
     *
     * @param financialObjectCodeName The financialObjectCodeName to set.
     */
    public void setFinancialObjectCodeName(String financialObjectCodeName) {
        this.financialObjectCodeName = financialObjectCodeName;
    }

    /**
     * Gets the financialObjectCodeShortName attribute.
     *
     * @return Returns the financialObjectCodeShortName
     */
    public String getFinancialObjectCodeShortName() {
        return financialObjectCodeShortName;
    }

    /**
     * Sets the financialObjectCodeShortName attribute.
     *
     * @param financialObjectCodeShortName The financialObjectCodeShortName to set.
     */
    public void setFinancialObjectCodeShortName(String financialObjectCodeShortName) {
        this.financialObjectCodeShortName = financialObjectCodeShortName;
    }

    /**
     * Gets the financialObjectActiveCode attribute.
     *
     * @return Returns the financialObjectActiveCode
     */
    public boolean isFinancialObjectActiveCode() {
        return active;
    }

    /**
     * Sets the financialObjectActiveCode attribute.
     *
     */
    public void setFinancialObjectActiveCode(boolean active) {
        this.active = active;
    }

    /**
     * Gets the financialBudgetAggregationCd attribute.
     *
     * @return Returns the financialBudgetAggregationCd
     */
    /*
     * public BudgetAggregationCode getFinancialBudgetAggregation() { return financialBudgetAggregation; }
     */

    /**
     * Sets the financialBudgetAggregationCd attribute.
     *
     * @param financialBudgetAggregationCd The financialBudgetAggregationCd to set.
     * @deprecated
     */
    /*
     * public void setFinancialBudgetAggregation(BudgetAggregationCode financialBudgetAggregationCd) {
     * this.financialBudgetAggregation = financialBudgetAggregationCd; }
     */


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
     */
    public void setChartOfAccountsCode(String string) {
        this.chartOfAccountsCode = string;
    }

    /**
     *
     */
    public String getChartOfAccountsCode() {
        return this.chartOfAccountsCode;
    }

    /**
     *
     */
    public Integer getUniversityFiscalYear() {
        return this.universityFiscalYear;
    }

    /**
     * @return Returns the financialObjectLevelCode.
     */
    public String getFinancialObjectLevelCode() {
        return financialObjectLevelCode;
    }

    /**
     * @param financialObjectLevelCode The financialObjectLevelCode to set.
     */
    public void setFinancialObjectLevelCode(String financialObjectLevelCode) {
        this.financialObjectLevelCode = financialObjectLevelCode;
    }


    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper() {

        LinkedHashMap m = new LinkedHashMap();
        m.put("chartOfAccountsCode", this.chartOfAccountsCode);
        m.put("financialObjectCode", this.financialObjectCode);

        return m;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean a) {
        this.active = a;
    }

    public void setCode(String code) {
        this.chartOfAccountsCode = code;
    }

    public void setName(String name) {
        this.financialObjectCodeName = name;
    }

    public String getCode() {
        return this.financialObjectCode;
    }

    public String getName() {
        return this.financialObjectCodeName;
    }

}
