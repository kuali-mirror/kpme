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
package org.kuali.kpme.core.api.paytype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.api.mo.EffectiveKeyContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = PayType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PayType.Constants.TYPE_NAME, propOrder = {
		PayType.Elements.REG_EARN_CODE_OBJ,
		PayType.Elements.PAY_TYPE,
		PayType.Elements.EFFECTIVE_KEY_SET,
		PayType.Elements.DESCR,
		PayType.Elements.REG_EARN_CODE,
		PayType.Elements.HR_PAY_TYPE_ID,
		PayType.Elements.HR_EARN_CODE_ID,
		PayType.Elements.OVT_EARN_CODE,
		PayType.Elements.FLSA_STATUS,
		PayType.Elements.PAY_FREQUENCY,
		PayType.Elements.ACTIVE,
		PayType.Elements.ID,
		PayType.Elements.EFFECTIVE_LOCAL_DATE,
		PayType.Elements.CREATE_TIME,
		PayType.Elements.USER_PRINCIPAL_ID,
		PayType.Elements.GROUP_KEY_CODE_SET,
		PayType.Elements.GROUP_KEY_SET,
		CoreConstants.CommonElements.VERSION_NUMBER,
		CoreConstants.CommonElements.OBJECT_ID,
		CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PayType
extends AbstractDataTransferObject
implements PayTypeContract
{

	@XmlElement(name = Elements.REG_EARN_CODE_OBJ, required = false)
	private final EarnCode regEarnCodeObj;
	@XmlElement(name = Elements.PAY_TYPE, required = false)
	private final String payType;
	@XmlElement(name = Elements.EFFECTIVE_KEY_SET, required = false)
	private final Set<EffectiveKey> effectiveKeySet;
	@XmlElement(name = Elements.DESCR, required = false)
	private final String descr;
	@XmlElement(name = Elements.REG_EARN_CODE, required = false)
	private final String regEarnCode;
	@XmlElement(name = Elements.HR_PAY_TYPE_ID, required = false)
	private final String hrPayTypeId;
	@XmlElement(name = Elements.HR_EARN_CODE_ID, required = false)
	private final String hrEarnCodeId;
	@XmlElement(name = Elements.OVT_EARN_CODE, required = false)
	private final Boolean ovtEarnCode;
	@XmlElement(name = Elements.FLSA_STATUS, required = false)
	private final String flsaStatus;
	@XmlElement(name = Elements.PAY_FREQUENCY, required = false)
	private final String payFrequency;
	@XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
	private final Long versionNumber;
	@XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
	private final String objectId;
	@XmlElement(name = Elements.ACTIVE, required = false)
	private final boolean active;
	@XmlElement(name = Elements.ID, required = false)
	private final String id;
	@XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private final LocalDate effectiveLocalDate;
	@XmlElement(name = Elements.CREATE_TIME, required = false)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private final DateTime createTime;
	@XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
	private final String userPrincipalId;
	@XmlElement(name = Elements.GROUP_KEY_CODE_SET, required = false)
	private final Set<String> groupKeyCodeSet;
	@XmlElement(name = Elements.GROUP_KEY_SET, required = false)
	private final Set<HrGroupKey> groupKeySet;
	@SuppressWarnings("unused")
	@XmlAnyElement
	private final Collection<Element> _futureElements = null;

	/**
	 * Private constructor used only by JAXB.
	 *
	 */
	private PayType() {
		this.regEarnCodeObj = null;
		this.payType = null;
		this.effectiveKeySet = null;
		this.descr = null;
		this.regEarnCode = null;
		this.hrPayTypeId = null;
		this.hrEarnCodeId = null;
		this.ovtEarnCode = null;
		this.flsaStatus = null;
		this.payFrequency = null;
		this.versionNumber = null;
		this.objectId = null;
		this.active = false;
		this.id = null;
		this.effectiveLocalDate = null;
		this.createTime = null;
		this.userPrincipalId = null;
		this.groupKeyCodeSet = null;
		this.groupKeySet = null;
	}

	private PayType(Builder builder) {
		this.regEarnCodeObj = builder.getRegEarnCodeObj() == null ? null : builder.getRegEarnCodeObj().build();
		this.payType = builder.getPayType();
		this.effectiveKeySet = ModelObjectUtils.<EffectiveKey>buildImmutableCopy(builder.getEffectiveKeySet());
		this.descr = builder.getDescr();
		this.regEarnCode = builder.getRegEarnCode();
		this.hrPayTypeId = builder.getHrPayTypeId();
		this.hrEarnCodeId = builder.getHrEarnCodeId();
		this.ovtEarnCode = builder.isOvtEarnCode();
		this.flsaStatus = builder.getFlsaStatus();
		this.payFrequency = builder.getPayFrequency();
		this.versionNumber = builder.getVersionNumber();
		this.objectId = builder.getObjectId();
		this.active = builder.isActive();
		this.id = builder.getId();
		this.effectiveLocalDate = builder.getEffectiveLocalDate();
		this.createTime = builder.getCreateTime();
		this.userPrincipalId = builder.getUserPrincipalId();
		this.groupKeyCodeSet = builder.getGroupKeyCodeSet();
		this.groupKeySet = ModelObjectUtils.<HrGroupKey>buildImmutableCopy(builder.getGroupKeySet());
	}

	@Override
	public EarnCode getRegEarnCodeObj() {
		return this.regEarnCodeObj;
	}

	@Override
	public String getPayType() {
		return this.payType;
	}

	@Override
	public Set<EffectiveKey> getEffectiveKeySet() {
		return this.effectiveKeySet;
	}

	// helper method to convert from key-set to  key-list 
	public List<EffectiveKey> getEffectiveKeyList() {
		if (CollectionUtils.isEmpty(this.effectiveKeySet)) {
			return Collections.emptyList();
		}
		List<EffectiveKey> copy = new ArrayList<EffectiveKey>();
		for (EffectiveKey key : this.effectiveKeySet) {
			copy.add(key);
		}
		return Collections.unmodifiableList(copy);
	}

	@Override
	public String getDescr() {
		return this.descr;
	}

	@Override
	public String getRegEarnCode() {
		return this.regEarnCode;
	}

	@Override
	public String getHrPayTypeId() {
		return this.hrPayTypeId;
	}

	@Override
	public String getHrEarnCodeId() {
		return this.hrEarnCodeId;
	}

	@Override
	public Boolean isOvtEarnCode() {
		return this.ovtEarnCode;
	}

	@Override
	public String getFlsaStatus() {
		return this.flsaStatus;
	}

	@Override
	public String getPayFrequency() {
		return this.payFrequency;
	}

	@Override
	public Long getVersionNumber() {
		return this.versionNumber;
	}

	@Override
	public String getObjectId() {
		return this.objectId;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public LocalDate getEffectiveLocalDate() {
		return this.effectiveLocalDate;
	}

	@Override
	public DateTime getCreateTime() {
		return this.createTime;
	}

	@Override
	public String getUserPrincipalId() {
		return this.userPrincipalId;
	}

	@Override
	public Set<String> getGroupKeyCodeSet() {
		return this.groupKeyCodeSet;
	}

	@Override
	public Set<HrGroupKey> getGroupKeySet() {
		return this.groupKeySet;
	}


	/**
	 * A builder which can be used to construct {@link PayType} instances.  Enforces the constraints of the {@link PayTypeContract}.
	 *
	 */
	public final static class Builder
	implements Serializable, PayTypeContract, ModelBuilder
	{

		private static final long serialVersionUID = 4303332490877015437L;
		private EarnCode.Builder regEarnCodeObj;
		private String payType;
		private Set<EffectiveKey.Builder> effectiveKeySet;
		private String descr;
		private String regEarnCode;
		private String hrPayTypeId;
		private String hrEarnCodeId;
		private Boolean ovtEarnCode;
		private String flsaStatus;
		private String payFrequency;
		private Long versionNumber;
		private String objectId;
		private boolean active;
		private String id;
		private LocalDate effectiveLocalDate;
		private DateTime createTime;
		private String userPrincipalId;
		private Set<String> groupKeyCodeSet;
		private Set<HrGroupKey.Builder> groupKeySet;


		private static final ModelObjectUtils.Transformer<EffectiveKeyContract, EffectiveKey.Builder> toEffectiveKeyBuilder 
		= new ModelObjectUtils.Transformer<EffectiveKeyContract, EffectiveKey.Builder>() {
			public EffectiveKey.Builder transform(EffectiveKeyContract input) {
				return EffectiveKey.Builder.create(input);
			}
		};

		private static final ModelObjectUtils.Transformer<HrGroupKeyContract, HrGroupKey.Builder> toHrGroupKeyBuilder 
		= new ModelObjectUtils.Transformer<HrGroupKeyContract, HrGroupKey.Builder>() {
			public HrGroupKey.Builder transform(HrGroupKeyContract input) {
				return HrGroupKey.Builder.create(input);
			}
		};

		private Builder(String payType) {
			setPayType(payType);
		}

		public static Builder create(String payType) {
			return new Builder(payType);
		}

		public static Builder create(PayTypeContract contract) {
			if (contract == null) {
				throw new IllegalArgumentException("contract was null");
			}
			Builder builder = create(contract.getPayType());
			builder.setEffectiveKeySet(ModelObjectUtils.transformSet(contract.getEffectiveKeySet(), toEffectiveKeyBuilder));
			builder.setRegEarnCodeObj(contract.getRegEarnCodeObj() == null ? null : EarnCode.Builder.create(contract.getRegEarnCodeObj()));
			builder.setDescr(contract.getDescr());
			builder.setRegEarnCode(contract.getRegEarnCode());
			builder.setHrPayTypeId(contract.getHrPayTypeId());
			builder.setHrEarnCodeId(contract.getHrEarnCodeId());
			builder.setOvtEarnCode(contract.isOvtEarnCode());
			builder.setFlsaStatus(contract.getFlsaStatus());
			builder.setPayFrequency(contract.getPayFrequency());
			builder.setVersionNumber(contract.getVersionNumber());
			builder.setObjectId(contract.getObjectId());
			builder.setActive(contract.isActive());
			builder.setId(contract.getId());
			builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
			builder.setCreateTime(contract.getCreateTime());
			builder.setUserPrincipalId(contract.getUserPrincipalId());
			builder.setGroupKeyCodeSet(contract.getGroupKeyCodeSet());
			builder.setGroupKeySet(ModelObjectUtils.transformSet(contract.getGroupKeySet(), toHrGroupKeyBuilder));
			return builder;
		}

		public PayType build() {
			return new PayType(this);
		}

		@Override
		public EarnCode.Builder getRegEarnCodeObj() {
			return this.regEarnCodeObj;
		}

		@Override
		public String getPayType() {
			return this.payType;
		}

		@Override
		public String getDescr() {
			return this.descr;
		}
		
		@Override
		public Set<EffectiveKey.Builder> getEffectiveKeySet() {
			return this.effectiveKeySet;
		}

		@Override
		public String getRegEarnCode() {
			return this.regEarnCode;
		}

		@Override
		public String getHrPayTypeId() {
			return this.hrPayTypeId;
		}

		@Override
		public String getHrEarnCodeId() {
			return this.hrEarnCodeId;
		}

		@Override
		public Boolean isOvtEarnCode() {
			return this.ovtEarnCode;
		}

		@Override
		public String getFlsaStatus() {
			return this.flsaStatus;
		}

		@Override
		public String getPayFrequency() {
			return this.payFrequency;
		}

		@Override
		public Long getVersionNumber() {
			return this.versionNumber;
		}

		@Override
		public String getObjectId() {
			return this.objectId;
		}

		@Override
		public boolean isActive() {
			return this.active;
		}

		@Override
		public String getId() {
			return this.id;
		}

		@Override
		public LocalDate getEffectiveLocalDate() {
			return this.effectiveLocalDate;
		}

		@Override
		public DateTime getCreateTime() {
			return this.createTime;
		}

		@Override
		public String getUserPrincipalId() {
			return this.userPrincipalId;
		}

		@Override
		public Set<String> getGroupKeyCodeSet() {
			return this.groupKeyCodeSet;
		}

		@Override
		public Set<HrGroupKey.Builder> getGroupKeySet() {
			return this.groupKeySet;
		}

		public void setEffectiveKeySet(Set<EffectiveKey.Builder> effectiveKeySet) {

			this.effectiveKeySet = effectiveKeySet;
		}

		public void setRegEarnCodeObj(EarnCode.Builder regEarnCodeObj) {
			this.regEarnCodeObj = regEarnCodeObj;
		}

		public void setPayType(String payType) {
			if (StringUtils.isEmpty(payType)) {
				throw new IllegalArgumentException("payType is blank");
			}
			this.payType = payType;
		}

		public void setDescr(String descr) {
			this.descr = descr;
		}

		public void setRegEarnCode(String regEarnCode) {
			this.regEarnCode = regEarnCode;
		}

		public void setHrPayTypeId(String hrPayTypeId) {
			this.hrPayTypeId = hrPayTypeId;
		}

		public void setHrEarnCodeId(String hrEarnCodeId) {
			this.hrEarnCodeId = hrEarnCodeId;
		}

		public void setOvtEarnCode(Boolean ovtEarnCode) {
			this.ovtEarnCode = ovtEarnCode;
		}

		public void setFlsaStatus(String flsaStatus) {
			this.flsaStatus = flsaStatus;
		}

		public void setPayFrequency(String payFrequency) {
			this.payFrequency = payFrequency;
		}

		public void setVersionNumber(Long versionNumber) {
			this.versionNumber = versionNumber;
		}

		public void setObjectId(String objectId) {
			this.objectId = objectId;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
			this.effectiveLocalDate = effectiveLocalDate;
		}

		public void setCreateTime(DateTime createTime) {
			this.createTime = createTime;
		}

		public void setUserPrincipalId(String userPrincipalId) {
			this.userPrincipalId = userPrincipalId;
		}

		public void setGroupKeyCodeSet(Set<String> groupKeyCodeSet) {

			this.groupKeyCodeSet = groupKeyCodeSet;
		}

		public void setGroupKeySet(Set<HrGroupKey.Builder> groupKeySet) {

			this.groupKeySet = groupKeySet;
		}

	}


	/**
	 * Defines some internal constants used on this class.
	 *
	 */
	static class Constants {

		final static String ROOT_ELEMENT_NAME = "payType";
		final static String TYPE_NAME = "PayTypeType";

	}


	/**
	 * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
	 *
	 */
	static class Elements {
		final static String EFFECTIVE_KEY_SET = "effectiveKeySet";
		final static String REG_EARN_CODE_OBJ = "regEarnCodeObj";
		final static String PAY_TYPE = "payType";
		final static String DESCR = "descr";
		final static String REG_EARN_CODE = "regEarnCode";
		final static String HR_PAY_TYPE_ID = "hrPayTypeId";
		final static String HR_EARN_CODE_ID = "hrEarnCodeId";
		final static String OVT_EARN_CODE = "ovtEarnCode";
		final static String FLSA_STATUS = "flsaStatus";
		final static String PAY_FREQUENCY = "payFrequency";
		final static String ACTIVE = "active";
		final static String ID = "id";
		final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
		final static String CREATE_TIME = "createTime";
		final static String USER_PRINCIPAL_ID = "userPrincipalId";
		final static String GROUP_KEY_CODE_SET = "groupKeyCodeSet";
		final static String GROUP_KEY_SET = "groupKeySet";

	}

}
