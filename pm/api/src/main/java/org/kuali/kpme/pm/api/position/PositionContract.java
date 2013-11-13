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
package org.kuali.kpme.pm.api.position;


import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.core.api.position.PositionBaseContract;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualificationContract;
import org.kuali.kpme.pm.api.position.funding.PositionFundingContract;
import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibilityContract;

public interface PositionContract extends PositionBaseContract {

    /**
     * The Position Duty List
     *
     * <p>
     * dutyList of a Position.
     * <p>
     *
     * @return List dutyList for Position
     */
    public List<? extends PositionDutyContract> getDutyList();


    /**
     * The Position Responsibility List
     *
     * <p>
     * positionResponsibilityList of a Position.
     * <p>
     *
     * @return List positionResponsibilityList for Position
     */
    public List<? extends PositionResponsibilityContract> getPositionResponsibilityList();

    /**
     * The Position Qualification List
     *
     * <p>
     * qualificationList of a Position.
     * <p>
     *
     * @return List qualificationList for Position
     */
    public List<? extends PositionQualificationContract> getQualificationList();


    /**
     * The Position Flag List
     *
     * <p>
     * flagList of a Position.
     * <p>
     *
     * @return List flagList for Position
     */
    public List<? extends PstnFlagContract> getFlagList();


    /**
     * The Position ID
     *
     * <p>
     * pmPositionClassId of a Position.
     * <p>
     *
     * @return pmPositionClassId for Position
     */
    public String getPmPositionClassId();

    /**
     * The Position Required ClassificationQualification List
     *
     * <p>
     * requiredQualList of a Position.
     * <p>
     *
     * @return List requiredQualList for Position
     */
    public List<? extends ClassificationQualificationContract> getRequiredQualList();

    /**
     * The Position PositionFunding List
     *
     * <p>
     * requiredQualList of a Position.
     * <p>
     *
     * @return List fundingList for Position
     */
    public List<? extends PositionFundingContract> getFundingList();



    /**
     * The Position Category
     *
     * <p>
     * category of a Position.
     * <p>
     *
     * @return category for Position
     */
    public String getCategory();

    /**
     * The Position Institution
     *
     * <p>
     * institution of a Position.
     * <p>
     *
     * @return institution for Position
     */
    public String getInstitution();


    /**
     * The Position Location
     *
     * <p>
     * location of a Position.
     * <p>
     *
     * @return location for Position
     */
    public String getLocation();

    /**
     * The Position Salary Group
     *
     * <p>
     * salaryGroup of a Position.
     * <p>
     *
     * @return salaryGroup for Position
     */
    public String getSalaryGroup();

    /**
     * The Position Classification Title
     *
     * <p>
     * classificationTitle of a Position.
     * <p>
     *
     * @return classificationTitle for Position
     */
    public String getClassificationTitle();


    /**
     * The Position Percent Time
     *
     * <p>
     * percentTime of a Position.
     * <p>
     *
     * @return percentTime for Position
     */
    public BigDecimal getPercentTime();


    /**
     * The Position Benefits Eligibility
     *
     * <p>
     * benefitsEligible of a Position.
     * <p>
     *
     * @return benefitsEligible for Position
     */
    public String getBenefitsEligible();


    /**
     * The Position Leave Eligibility
     *
     * <p>
     * leaveEligible of a Position.
     * <p>
     *
     * @return leaveEligible for Position
     */
    public String getLeaveEligible();


    /**
     * The Position Leave Plan
     *
     * <p>
     * leavePlan of a Position.
     * <p>
     *
     * @return leavePlan for Position
     */
    public String getLeavePlan();


    /**
     * The Position Reporting Group
     *
     * <p>
     * positionReportGroup of a Position.
     * <p>
     *
     * @return positionReportGroup for Position
     */
    public String getPositionReportGroup();


    /**
     * The Position Type
     *
     * <p>
     * positionType of a Position.
     * <p>
     *
     * @return positionType for Position
     */
    public String getPositionType();


    /**
     * The Position Pool Eligibility
     *
     * <p>
     * poolEligible of a Position.
     * <p>
     *
     * @return poolEligible for Position
     */
    public String getPoolEligible();



    /**
     * The Position Max Pool Head Count
     *
     * <p>
     * maxPoolHeadCount of a Position.
     * <p>
     *
     * @return maxPoolHeadCount for Position
     */
    public int getMaxPoolHeadCount();


    /**
     * The Position Tenure Eligibility
     *
     * <p>
     * tenureEligible of a Position.
     * <p>
     *
     * @return tenureEligible for Position
     */
    public String getTenureEligible();


    /**
     * The Position Work Months
     *
     * <p>
     * workMonths of a Position.
     * <p>
     *
     * @return workMonths for Position
     */
    public int getWorkMonths();


}
