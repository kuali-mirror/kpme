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
package org.kuali.kpme.core.paytype;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.location.impl.campus.CampusBo;

import com.google.common.collect.ImmutableList;

public class PayType extends HrBusinessObject {
    public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PayType";
    private static final String[] PRIVATE_CACHES_FOR_FLUSH = {PayType.CACHE_NAME, Job.CACHE_NAME, Assignment.CACHE_NAME};
	public static final List<String> CACHE_FLUSH = Collections.unmodifiableList(Arrays.asList(PRIVATE_CACHES_FOR_FLUSH));
    //KPME-2273/1965 Primary Business Keys List.	
    public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("payType")
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
    private String institution;
	private String flsaStatus;
	private String payFrequency;
	
	private Location locationObj;
	private Institution institutionObj;

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
	
}
