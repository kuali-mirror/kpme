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

import java.sql.Timestamp;

import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PayTypeBo extends HrKeyedBusinessObject implements PayTypeContract {
	static class KeyFields {
		private static final String PAY_TYPE = "payType";
		final static String GROUP_KEY_CODE = "groupKeyCode";
	}
    
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PayType";
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(PayTypeBo.CACHE_NAME)
            .add(JobBo.CACHE_NAME)
            .add(AssignmentBo.CACHE_NAME)
            .add(CalendarBlockPermissions.CACHE_NAME)
            .build();
    //KPME-2273/1965 Primary Business Keys List.	
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(KeyFields.PAY_TYPE)
            .add(KeyFields.GROUP_KEY_CODE)
            .build();

	private static final long serialVersionUID = 1L;
	private String hrPayTypeId;
	private String payType;
	private String descr;
	private String regEarnCode;
    /** Used for lookup */
	private String hrEarnCodeId;
    private EarnCodeBo regEarnCodeObj;
    private Boolean ovtEarnCode;
    
	private String flsaStatus;
	private String payFrequency;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(KeyFields.PAY_TYPE, this.getPayType())
			.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
			.build();
	}

    public EarnCodeBo getRegEarnCodeObj() {
        return regEarnCodeObj;
    }

    public void setRegEarnCodeObj(EarnCodeBo regEarnCodeObj) {
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

    @Override
	public Boolean isOvtEarnCode() {
		return ovtEarnCode;
	}

    public Boolean getOvtEarnCode() {
        return isOvtEarnCode();
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

    public static PayTypeBo from(PayType im) {
        if (im == null) {
            return null;
        }
        PayTypeBo pt = new PayTypeBo();

        pt.setHrPayTypeId(im.getHrPayTypeId());
        pt.setPayType(im.getPayType());

        pt.setGroupKeyCode(im.getGroupKeyCode());
        pt.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
        
        pt.setDescr(im.getDescr());
        pt.setRegEarnCode(im.getRegEarnCode());
        pt.setHrEarnCodeId(im.getHrEarnCodeId());
        pt.setRegEarnCodeObj(im.getRegEarnCodeObj() == null ? null : EarnCodeBo.from(im.getRegEarnCodeObj()));
        pt.setOvtEarnCode(im.isOvtEarnCode());
        pt.setFlsaStatus(im.getFlsaStatus());
        pt.setPayFrequency(im.getPayFrequency());

        pt.setHrEarnCodeId(im.getHrEarnCodeId());

        pt.setOvtEarnCode(im.isOvtEarnCode());

        pt.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        pt.setActive(im.isActive());
        if (im.getCreateTime() != null) {
            pt.setTimestamp(new Timestamp(im.getCreateTime().getMillis()));
        }
        pt.setUserPrincipalId(im.getUserPrincipalId());
        pt.setVersionNumber(im.getVersionNumber());
        pt.setObjectId(im.getObjectId());

        return pt;
    }

    public static PayType to(PayTypeBo bo) {
        if (bo == null) {
            return null;
        }

        return PayType.Builder.create(bo).build();
    }
}
