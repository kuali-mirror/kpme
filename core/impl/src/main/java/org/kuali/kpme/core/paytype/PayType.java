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
package org.kuali.kpme.core.paytype;

import javax.persistence.Transient;

import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PayType extends HrBusinessObject implements PayTypeContract {
    private static final String PAY_TYPE = "payType";
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PayType";
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(PayType.CACHE_NAME)
            .add(Job.CACHE_NAME)
            .add(Assignment.CACHE_NAME)
            .add(CalendarBlockPermissions.CACHE_NAME)
            .build();
    //KPME-2273/1965 Primary Business Keys List.	
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(PAY_TYPE)
            .build();

	private static final long serialVersionUID = 1L;
	private String hrPayTypeId;
	private String payType;
	private String descr;
	private String regEarnCode;
    /** Used for lookup */
	private String hrEarnCodeId;
    private EarnCode regEarnCodeObj;
    private String history;
    private Boolean ovtEarnCode;
    
    // KPME-2252
	private String location;
	@Transient
	private String hrLocationId;
    private String institution;
    @Transient
    private String pmInstitutionId;
	private String flsaStatus;
	private String payFrequency;
	
	private Location locationObj;
	private Institution institutionObj;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(PAY_TYPE, this.getPayType())
			.build();
	}

    public EarnCode getRegEarnCodeObj() {
        return regEarnCodeObj;
    }

    public void setRegEarnCodeObj(EarnCode regEarnCodeObj) {
        this.regEarnCodeObj = regEarnCodeObj;
    }

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getRegEarnCode() {
		return regEarnCode;
	}

	public void setRegEarnCode(String regEarnCode) {
		this.regEarnCode = regEarnCode;
	}
	public String getHrPayTypeId() {
		return hrPayTypeId;
	}


	public void setHrPayTypeId(String hrPayTypeId) {
		this.hrPayTypeId = hrPayTypeId;
	}

	public String getHrEarnCodeId() {
		return hrEarnCodeId;
	}

	public void setHrEarnCodeId(String hrEarnCodeId) {
		this.hrEarnCodeId = hrEarnCodeId;
	}

	@Override
	public String getUniqueKey() {
		return payType;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Boolean getOvtEarnCode() {
		return ovtEarnCode;
	}

	public void setOvtEarnCode(Boolean ovtEarnCode) {
		this.ovtEarnCode = ovtEarnCode;
	}

	@Override
	public String getId() {
		return getHrPayTypeId();
	}

	@Override
	public void setId(String id) {
		setHrPayTypeId(id);
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getFlsaStatus() {
		return flsaStatus;
	}

	public void setFlsaStatus(String flsaStatus) {
		this.flsaStatus = flsaStatus;
	}

	public String getPayFrequency() {
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}

	public Institution getInstitutionObj() {
		return institutionObj;
	}

	public void setInstitutionObj(Institution institutionObj) {
		this.institutionObj = institutionObj;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	public String getHrLocationId() {
		return hrLocationId;
	}

	public void setHrLocationId(String hrLocationId) {
		this.hrLocationId = hrLocationId;
	}

	public String getPmInstitutionId() {
		return pmInstitutionId;
	}

	public void setPmInstitutionId(String pmInstitutionId) {
		this.pmInstitutionId = pmInstitutionId;
	}
	
}
