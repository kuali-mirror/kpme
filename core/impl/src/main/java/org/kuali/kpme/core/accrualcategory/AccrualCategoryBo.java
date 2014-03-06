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
package org.kuali.kpme.core.accrualcategory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRuleBo;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.leaveplan.LeavePlanBo;
import org.kuali.kpme.core.util.HrConstants;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class AccrualCategoryBo extends HrBusinessObject implements AccrualCategoryContract {
    private static final String ACCRUAL_CATEGORY = "accrualCategory";

	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "AccrualCategory";
    
    //KPME-2273/1965 Primary Business Keys List. Will be using this from now on instead.
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(ACCRUAL_CATEGORY)
            .build();	
	private static final long serialVersionUID = 1L;
	private String lmAccrualCategoryId;
	private String leavePlan;
	private String accrualCategory;
	private String descr;
	private String accrualEarnInterval;
	private String proration;
	private String donation;
	private String showOnGrid;
	private String unitOfTime;
	private String hasRules;
    private String earnCode;
    private EarnCodeBo earnCodeObj;
    private BigDecimal minPercentWorked;

	private LeavePlanBo leavePlanObj;
	private List<AccrualCategoryRuleBo> accrualCategoryRules = new LinkedList<AccrualCategoryRuleBo>();

	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(ACCRUAL_CATEGORY, this.getAccrualCategory())		
				.build();
	}

	public String getHasRules() {
		return hasRules;
	}

	public void setHasRules(String hasRules) {
		this.hasRules = hasRules;
	}
	
	public BigDecimal getMinPercentWorked() {
		return minPercentWorked;
	}

	public void setMinPercentWorked(BigDecimal minPercentWorked) {
		this.minPercentWorked = minPercentWorked;
	}

	public LeavePlanBo getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlanBo leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}

	public List<AccrualCategoryRuleBo> getAccrualCategoryRules() {
		return accrualCategoryRules;
	}

	public void setAccrualCategoryRules(
			List<AccrualCategoryRuleBo> accrualCategoryRules) {
		this.accrualCategoryRules = accrualCategoryRules;
	}

	public String getLmAccrualCategoryId() {
		return lmAccrualCategoryId;
	}

	public void setLmAccrualCategoryId(String lmAccrualCategoryId) {
		this.lmAccrualCategoryId = lmAccrualCategoryId;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAccrualEarnInterval() {
		return accrualEarnInterval;
	}

	public void setAccrualEarnInterval(String accrualEarnInterval) {
		this.accrualEarnInterval = accrualEarnInterval;
	}

	public String getProration() {
		return proration;
	}

	public void setProration(String proration) {
		this.proration = proration;
	}

	public String getDonation() {
		return donation;
	}

	public void setDonation(String donation) {
		this.donation = donation;
	}

	public String getShowOnGrid() {
		return showOnGrid;
	}

	public void setShowOnGrid(String showOnGrid) {
		this.showOnGrid = showOnGrid;
	}

	public String getUnitOfTime() {
		return unitOfTime;
	}

	public void setUnitOfTime(String unitOfTime) {
		this.unitOfTime = unitOfTime;
	}

	@Override
	protected String getUniqueKey() {
		return lmAccrualCategoryId;
	}



	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public EarnCodeBo getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCodeBo earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

    @Override
    public DateTime getCreateTime() {
        return getTimestamp() != null ? new DateTime(getTimestamp().getTime()) : null;
    }
	@Override
	public void setId(String id) {
		setLmAccrualCategoryId(id);
	}

	@Override
	public String getId() {
		return getLmAccrualCategoryId();
	}

    public static AccrualCategoryBo from(org.kuali.kpme.core.api.accrualcategory.AccrualCategory im) {
        if (im == null) {
            return null;
        }
        AccrualCategoryBo ac = new AccrualCategoryBo();

        ac.setAccrualCategory(im.getAccrualCategory());
        ac.setAccrualEarnInterval(im.getAccrualEarnInterval());
        ac.setDescr(im.getDescr());
        ac.setDonation(im.getDonation());
        ac.setAccrualEarnInterval(im.getAccrualEarnInterval());
        ac.setEarnCode(im.getEarnCode());
        ac.setHasRules(im.getHasRules());
        ac.setId(ac.getId());

        ac.setLmAccrualCategoryId(im.getLmAccrualCategoryId());
        ac.setLeavePlan(im.getLeavePlan());
        ac.setProration(im.getProration());
        ac.setDonation(im.getDonation());
        ac.setShowOnGrid(im.getShowOnGrid());
        ac.setUnitOfTime(im.getUnitOfTime());
        ac.setMinPercentWorked(im.getMinPercentWorked());
        ac.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        ac.setActive(im.isActive());
        if (im.getCreateTime() != null) {
        ac.setTimestamp(new Timestamp(im.getCreateTime().getMillis()));
        }
        ac.setUserPrincipalId(im.getUserPrincipalId());
        ac.setVersionNumber(im.getVersionNumber());
        ac.setObjectId(im.getObjectId());

        return ac;
    }

    public static AccrualCategory to(AccrualCategoryBo bo) {
        if (bo == null) {
            return null;
        }

        return AccrualCategory.Builder.create(bo).build();
    }
}
