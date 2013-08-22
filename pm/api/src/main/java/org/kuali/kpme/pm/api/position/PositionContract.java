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


import org.kuali.kpme.core.api.position.PositionBaseContract;

import org.kuali.kpme.pm.api.classification.qual.ClassificationQualificationContract;
import org.kuali.kpme.pm.api.position.funding.PositionFundingContract;
import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibilityContract;

//import org.kuali.rice.location.impl.campus.CampusBo;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>PositionContract interface</p>
 *
 */
public interface PositionContract extends PositionBaseContract {

    /**
     * The list of PositionDuty objects associated with the Position
     *
     * <p>
     * dutyList of a Position.
     * <p>
     *
     * @return List dutyList for Position
     */
    public List<? extends PositionDutyContract> getDutyList();

    /**
     * The list of PositionResponsibility objects associated with the Position
     *
     * <p>
     * positionResponsibilityList of a Position.
     * <p>
     *
     * @return List positionResponsibilityList for Position
     */
	public List<? extends PositionResponsibilityContract> getPositionResponsibilityList();

    /**
     * The list of PositionQualification objects associated with the Position
     *
     * <p>
     * qualificationList of a Position.
     * <p>
     *
     * @return List qualificationList for Position
     */
	public List<? extends PositionQualificationContract> getQualificationList();

    /**
     * The list of PositionFlag objects associated with the Position
     *
     * <p>
     * flagList of a Position.
     * <p>
     *
     * @return List flagList for Position
     */
	public List<? extends PstnFlagContract> getFlagList();

    /**
     * The position class id associated with the Position 
     *
     * <p>
     * pmPositionClassId of a Position.
     * <p>
     *
     * @return pmPositionClassId for Position
     */
	public String getPmPositionClassId();

    /**
     * The list of ClassificationQualification objects associated with the Position
     *
     * <p>
     * requiredQualList of a Position.
     * <p>
     *
     * @return List requiredQualList for Position
     */
	public List<? extends ClassificationQualificationContract> getRequiredQualList();

    /**
     * The list of PositionFunding objects associated with the Position
     *
     * <p>
     * requiredQualList of a Position.
     * <p>
     *
     * @return List fundingList for Position
     */
	public List<? extends PositionFundingContract> getFundingList();

    /**
     * The category associated with the Position
     *
     * <p>
     * category of a Position.
     * <p>
     *
     * @return category for Position
     */
	public String getCategory();

    /**
     * The institution associated with the Position
     *
     * <p>
     * institution of a Position.
     * <p>
     *
     * @return institution for Position
     */
    public String getInstitution();

    /**
     * The campus associated with the Position
     *
     * <p>
     * campus of a Position.
     * <p>
     *
     * @return campus for Position
     */
    public String getCampus();

    /**
     * The Campus Business Object associated with the Position
     *
     * <p>
     * campusObj of a Position.
     * <p>
     *
     * @return campusObj for Position
     */
    //TODO find the right import for this
    //public CampusEbo getCampusObj();

    /**
     * The salary group associated with the Position
     *
     * <p>
     * salaryGroup of a Position.
     * <p>
     *
     * @return salaryGroup for Position
     */
    public String getSalaryGroup();

    /**
     * The classification title associated with the Position
     *
     * <p>
     * classificationTitle of a Position.
     * <p>
     *
     * @return classificationTitle for Position
     */
    public String getClassificationTitle();

    /**
     * The descriptive title for the Position
     *
     * <p>
     * workingPositionTitle of a Position.
     * <p>
     *
     * @return workingPositionTitle for Position
     */
    public String getWorkingPositionTitle();

    /**
     * The maximum percentage of time worked for the position
     *
     * <p>
     * percentTime of a Position.
     * <p>
     *
     * @return percentTime for Position
     */
    public BigDecimal getPercentTime();

    /**
     * The flag indicating if the position is eligible for benefits
     *
     * <p>
     * benefitsEligible of a Position.
     * <p>
     *
     * @return benefitsEligible for Position
     */
    public String getBenefitsEligible();

    /**
     * The flag indicating if the position is eligible for leave benefits 
     *
     * <p>
     * leaveEligible of a Position.
     * <p>
     *
     * @return leaveEligible for Position
     */
    public String getLeaveEligible();

    /**
     * The leave plan associated with the Position
     *
     * <p>
     * leavePlan of a Position.
     * <p>
     *
     * @return leavePlan for Position
     */
    public String getLeavePlan();

    /**
     * The position reporting group associated with the Position
     *
     * <p>
     * positionReportGroup of a Position.
     * <p>
     *
     * @return positionReportGroup for Position
     */
    public String getPositionReportGroup();

    /**
     * The grouping of positions to determine routing for position maintenance
     *
     * <p>
     * positionType of a Position.
     * <p>
     *
     * @return positionType for Position
     */
    public String getPositionType();

    /**
     * The flag indicating if the position classification is eligible to be used for pooled position
     *
     * <p>
     * poolEligible of a Position.
     * <p>
     *
     * @return poolEligible for Position
     */
    public String getPoolEligible();

    /**
     * Indicates the maximum number of pooled positions to associate with the Position 
     *
     * <p>
     * maxPoolHeadCount of a Position.
     * <p>
     *
     * @return maxPoolHeadCount for Position
     */
    public int getMaxPoolHeadCount();

    /**
     * The flag indicating if the position classification is eligible for tenure
     *
     * <p>
     * tenureEligible of a Position.
     * <p>
     *
     * @return tenureEligible for Position
     */
    public String getTenureEligible();

    /**
     * The number of months in the year the position for the salary group works
     *
     * <p>
     * workMonths of a Position.
     * <p>
     *
     * @return workMonths for Position
     */
    public int getWorkMonths();

}
