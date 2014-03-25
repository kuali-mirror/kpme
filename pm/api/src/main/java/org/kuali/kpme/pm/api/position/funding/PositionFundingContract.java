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
package org.kuali.kpme.pm.api.position.funding;

import org.kuali.kpme.pm.api.position.PositionDerivedContract;
import java.math.BigDecimal;

/**
 * <p>PositionFundingContract interface</p>
 *
 */
public interface PositionFundingContract extends PositionDerivedContract {

    /**
     * The primary key for a PositionFunding entry saved in the database
     *
     * <p>
     * pmPositionFunctionId of a PositionFunding.
     * <p>
     *
     * @return pmPositionFunctionId for PositionFunding
     */
	public String getPmPositionFunctionId();


    /**
     * The source associated with the PositionFunding
     *
     * <p>
     * source of a PositionFunding.
     * <p>
     *
     * @return source for PositionFunding
     */
	public String getSource();

    /**
     * The chart associated with the PositionFunding
     *
     * <p>
     * chart of a PositionFunding.
     * <p>
     *
     * @return chart for PositionFunding
     */
	public String getChart();

    /**
     * The organization associated with the PositionFunding
     *
     * <p>
     * org of a PositionFunding.
     * <p>
     *
     * @return org for PositionFunding
     */
	public String getOrg();

    /**
     * The account associated with the PositionFunding
     *
     * <p>
     * The account component of the chart of accounts to be charged when time recorded against this work area and task
     * <p>
     *
     * @return account for PositionFunding
     */
	public String getAccount();

    /**
     * The subAccount associated with the PositionFunding
     *
     * <p>
     * The sub-account component of the chart of accounts to be charged when time recorded against this work area and task
     * <p>
     *
     * @return subAccount for PositionFunding
     */
	public String getSubAccount();

    /**
     * The object code associated with the PositionFunding
     *
     * <p>
     * The Object code component of the chart of accounts to be charged when time recorded against this work area and task.
     * <p>
     *
     * @return objectCode for PositionFunding
     */
	public String getObjectCode();

    /**
     * The subObjectCode associated with the PositionFunding
     *
     * <p>
     * The sub-object component of the chart of accounts to be charged when time recorded against this work area and task.
     * <p>
     *
     * @return subObjectCode for PositionFunding
     */
	public String getSubObjectCode();

    /**
     * The organization reference code associated with the PositionFunding
     *
     * <p>
     * orgRefCode of a PositionFunding.
     * <p>
     *
     * @return orgRefCode for PositionFunding
     */
	public String getOrgRefCode();

    /**
     * The Percentage that the account will be used to fund the position
     *
     * <p>
     * percent of a PositionFunding.
     * <p>
     *
     * @return percent for PositionFunding
     */
	public BigDecimal getPercent();

    /**
     * The amount to be funded
     *
     * <p>
     * amount of a PositionFunding.
     * <p>
     *
     * @return amount for PositionFunding
     */
	public BigDecimal getAmount();

    /**
     * The flag used to indicate account priority
     *
     * <p>
     * priorityFlag of a PositionFunding.
     * <p>
     *
     * @return priorityFlag for PositionFunding
     */
	public boolean isPriorityFlag();
	
}
