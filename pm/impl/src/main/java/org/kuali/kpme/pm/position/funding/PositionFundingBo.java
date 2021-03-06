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
package org.kuali.kpme.pm.position.funding;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.api.position.funding.PositionFunding;
import org.kuali.kpme.pm.api.position.funding.PositionFundingContract;
import org.kuali.kpme.pm.position.PositionDerived;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.LegacyDataAdapter;

public class PositionFundingBo extends PositionDerived implements PositionFundingContract {
	
	private static final long serialVersionUID = 1315658420315845055L;


	private String pmPositionFunctionId;
	
	
	private String chart;
	private String org;
	private String account;
	private String subAccount;
	private String objectCode;
	private String subObjectCode;
	private String orgRefCode;
	private BigDecimal percent;
	private BigDecimal amount;
	private boolean priorityFlag;

	// indicates where the funding information comes from, it could be from
	// maint document, Institution batch imports, etc..
	// we use source on the maint document to determine which funding
	// information is readonly
	private String source;
	private transient BusinessObjectService businessObjectService;

	public String getPmPositionFunctionId() {
		return pmPositionFunctionId;
	}

	public void setPmPositionFunctionId(String pmPositionFunctionId) {
		this.pmPositionFunctionId = pmPositionFunctionId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

    public String getChart() {
        if (StringUtils.isEmpty(this.chart)) {
            Map<String, String> fields = new HashMap<String, String>();
            fields.put("accountNumber", this.account);
            fields.put("active", "true");
            Account account = (Account) KNSServiceLocator.getBusinessObjectService().findByPrimaryKey(Account.class,
                    fields);
            if (account != null && !account.isClosed()) {
                this.setChart(account.getChartOfAccountsCode());
            } else {
                this.setChart(null);
            }
        }
        return chart;
    }

	public void setChart(String chart) {
		this.chart = chart;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getSubObjectCode() {
		return subObjectCode;
	}

	public void setSubObjectCode(String subObjectCode) {
		this.subObjectCode = subObjectCode;
	}

	public String getOrgRefCode() {
		return orgRefCode;
	}

	public void setOrgRefCode(String orgRefCode) {
		this.orgRefCode = orgRefCode;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(boolean priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;

		PositionFundingBo rhs = (PositionFundingBo) obj;
		return new EqualsBuilder()
				.append(pmPositionFunctionId, rhs.getPmPositionFunctionId())
				.append(hrPositionId, rhs.getHrPositionId())
				.append(chart, rhs.getChart()).append(org, rhs.getOrg())
				.append(account, rhs.getAccount())
				.append(subAccount, rhs.getSubAccount())
				.append(objectCode, rhs.getObjectCode())
				.append(subObjectCode, rhs.getSubObjectCode())
				.append(orgRefCode, rhs.getOrgRefCode())
				.append(percent, rhs.getPercent())
				.append(amount, rhs.getAmount())
				.append(priorityFlag, rhs.isPriorityFlag()).isEquals();

	}

	public static PositionFundingBo from(PositionFunding im) {
		if (im == null) {
			return null;
		}

		PositionFundingBo positionFundingBo = new PositionFundingBo();

		positionFundingBo.setAccount(im.getAccount());
		positionFundingBo.setAmount(im.getAmount());
		positionFundingBo.setChart(im.getChart());
		positionFundingBo.setHrPositionId(im.getHrPositionId());
		positionFundingBo.setObjectCode(im.getObjectCode());
		positionFundingBo.setOrg(im.getOrg());
		positionFundingBo.setOrgRefCode(im.getOrgRefCode());
		positionFundingBo.setPercent(im.getPercent());
		positionFundingBo.setPmPositionFunctionId(im.getPmPositionFunctionId());
		positionFundingBo.setPriorityFlag(im.isPriorityFlag());
		positionFundingBo.setSource(im.getSource());
		positionFundingBo.setSubAccount(im.getSubAccount());
		positionFundingBo.setSubObjectCode(im.getSubObjectCode());
		positionFundingBo.setObjectId(im.getObjectId());
		positionFundingBo.setVersionNumber(im.getVersionNumber());

		return positionFundingBo;
	}

	public static PositionFunding to(PositionFundingBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionFunding.Builder.create(bo).build();
	}

	public static final ModelObjectUtils.Transformer<PositionFundingBo, PositionFunding> toImmutable = new ModelObjectUtils.Transformer<PositionFundingBo, PositionFunding>() {
		public PositionFunding transform(PositionFundingBo input) {
			return PositionFundingBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<PositionFunding, PositionFundingBo> toBo = new ModelObjectUtils.Transformer<PositionFunding, PositionFundingBo>() {
		public PositionFundingBo transform(PositionFunding input) {
			return PositionFundingBo.from(input);
		};
	};
	
	@Override
	public String getId() {
		return this.getPmPositionFunctionId();
	}

	@Override
	public void setId(String id) {
		this.setPmPositionFunctionId(id);
	}

}
