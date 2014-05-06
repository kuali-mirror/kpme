package org.kuali.kpme.pm.api.pstnqlfrtype;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PstnQlfrType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PstnQlfrType.Constants.TYPE_NAME, propOrder = {
		PstnQlfrType.Elements.TYPE, PstnQlfrType.Elements.SELECT_VALUES,
		PstnQlfrType.Elements.DESCR, PstnQlfrType.Elements.TYPE_VALUE,
		PstnQlfrType.Elements.CODE, PstnQlfrType.Elements.PM_PSTN_QLFR_TYPE_ID,
		CoreConstants.CommonElements.VERSION_NUMBER,
		CoreConstants.CommonElements.OBJECT_ID, PstnQlfrType.Elements.ACTIVE,
		PstnQlfrType.Elements.ID, PstnQlfrType.Elements.EFFECTIVE_LOCAL_DATE,
		PstnQlfrType.Elements.CREATE_TIME,
		PstnQlfrType.Elements.USER_PRINCIPAL_ID,
		CoreConstants.CommonElements.FUTURE_ELEMENTS })
public final class PstnQlfrType extends AbstractDataTransferObject implements
		PstnQlfrTypeContract {

	@XmlElement(name = Elements.TYPE, required = false)
	private final String type;
	@XmlElement(name = Elements.SELECT_VALUES, required = false)
	private final String selectValues;
	@XmlElement(name = Elements.DESCR, required = false)
	private final String descr;
	@XmlElement(name = Elements.TYPE_VALUE, required = false)
	private final String typeValue;
	@XmlElement(name = Elements.CODE, required = false)
	private final String code;
	@XmlElement(name = Elements.PM_PSTN_QLFR_TYPE_ID, required = false)
	private final String pmPstnQlfrTypeId;
	@XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
	private final Long versionNumber;
	@XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
	private final String objectId;
	@XmlElement(name = Elements.ACTIVE, required = false)
	private final boolean active;
	@XmlElement(name = Elements.ID, required = false)
	private final String id;
	@XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
	private final LocalDate effectiveLocalDate;
	@XmlElement(name = Elements.CREATE_TIME, required = false)
	private final DateTime createTime;
	@XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
	private final String userPrincipalId;
	@SuppressWarnings("unused")
	@XmlAnyElement
	private final Collection<Element> _futureElements = null;

	/**
	 * Private constructor used only by JAXB.
	 * 
	 */
	private PstnQlfrType() {
		this.type = null;
		this.selectValues = null;
		this.descr = null;
		this.typeValue = null;
		this.code = null;
		this.pmPstnQlfrTypeId = null;
		this.versionNumber = null;
		this.objectId = null;
		this.active = false;
		this.id = null;
		this.effectiveLocalDate = null;
		this.createTime = null;
		this.userPrincipalId = null;
	}

	private PstnQlfrType(Builder builder) {
		this.type = builder.getType();
		this.selectValues = builder.getSelectValues();
		this.descr = builder.getDescr();
		this.typeValue = builder.getTypeValue();
		this.code = builder.getCode();
		this.pmPstnQlfrTypeId = builder.getPmPstnQlfrTypeId();
		this.versionNumber = builder.getVersionNumber();
		this.objectId = builder.getObjectId();
		this.active = builder.isActive();
		this.id = builder.getId();
		this.effectiveLocalDate = builder.getEffectiveLocalDate();
		this.createTime = builder.getCreateTime();
		this.userPrincipalId = builder.getUserPrincipalId();
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getSelectValues() {
		return this.selectValues;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}

	@Override
	public String getTypeValue() {
		return this.typeValue;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getPmPstnQlfrTypeId() {
		return this.pmPstnQlfrTypeId;
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

	/**
	 * A builder which can be used to construct {@link PstnQlfrType} instances.
	 * Enforces the constraints of the {@link PstnQlfrTypeContract}.
	 * 
	 */
	public final static class Builder implements Serializable,
			PstnQlfrTypeContract, ModelBuilder {

		private String type;
		private String selectValues;
		private String descr;
		private String typeValue;
		private String code;
		private String pmPstnQlfrTypeId;
		private Long versionNumber;
		private String objectId;
		private boolean active;
		private String id;
		private LocalDate effectiveLocalDate;
		private DateTime createTime;
		private String userPrincipalId;

		private Builder() {
			// TODO modify this constructor as needed to pass any required
			// values and invoke the appropriate 'setter' methods
		}

		public static Builder create() {
			// TODO modify as needed to pass any required values and add them to
			// the signature of the 'create' method
			return new Builder();
		}

		public static Builder create(PstnQlfrTypeContract contract) {
			if (contract == null) {
				throw new IllegalArgumentException("contract was null");
			}
			// TODO if create() is modified to accept required parameters, this
			// will need to be modified
			Builder builder = create();
			builder.setType(contract.getType());
			builder.setSelectValues(contract.getSelectValues());
			builder.setDescr(contract.getDescr());
			builder.setTypeValue(contract.getTypeValue());
			builder.setCode(contract.getCode());
			builder.setPmPstnQlfrTypeId(contract.getPmPstnQlfrTypeId());
			builder.setVersionNumber(contract.getVersionNumber());
			builder.setObjectId(contract.getObjectId());
			builder.setActive(contract.isActive());
			builder.setId(contract.getId());
			builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
			builder.setCreateTime(contract.getCreateTime());
			builder.setUserPrincipalId(contract.getUserPrincipalId());
			return builder;
		}

		public PstnQlfrType build() {
			return new PstnQlfrType(this);
		}

		@Override
		public String getType() {
			return this.type;
		}

		@Override
		public String getSelectValues() {
			return this.selectValues;
		}

		@Override
		public String getDescr() {
			return this.descr;
		}

		@Override
		public String getTypeValue() {
			return this.typeValue;
		}

		@Override
		public String getCode() {
			return this.code;
		}

		@Override
		public String getPmPstnQlfrTypeId() {
			return this.pmPstnQlfrTypeId;
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

		public void setType(String type) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.type = type;
		}

		public void setSelectValues(String selectValues) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.selectValues = selectValues;
		}

		public void setDescr(String descr) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.descr = descr;
		}

		public void setTypeValue(String typeValue) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.typeValue = typeValue;
		}

		public void setCode(String code) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.code = code;
		}

		public void setPmPstnQlfrTypeId(String pmPstnQlfrTypeId) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.pmPstnQlfrTypeId = pmPstnQlfrTypeId;
		}

		public void setVersionNumber(Long versionNumber) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.versionNumber = versionNumber;
		}

		public void setObjectId(String objectId) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.objectId = objectId;
		}

		public void setActive(boolean active) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.active = active;
		}

		public void setId(String id) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.id = id;
		}

		public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.effectiveLocalDate = effectiveLocalDate;
		}

		public void setCreateTime(DateTime createTime) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.createTime = createTime;
		}

		public void setUserPrincipalId(String userPrincipalId) {
			// TODO add validation of input value if required and throw
			// IllegalArgumentException if needed
			this.userPrincipalId = userPrincipalId;
		}

	}

	/**
	 * Defines some internal constants used on this class.
	 * 
	 */
	static class Constants {

		final static String ROOT_ELEMENT_NAME = "pstnQlfrType";
		final static String TYPE_NAME = "PstnQlfrTypeType";

	}

	/**
	 * A private class which exposes constants which define the XML element
	 * names to use when this object is marshalled to XML.
	 * 
	 */
	static class Elements {

		final static String TYPE = "type";
		final static String SELECT_VALUES = "selectValues";
		final static String DESCR = "descr";
		final static String TYPE_VALUE = "typeValue";
		final static String CODE = "code";
		final static String PM_PSTN_QLFR_TYPE_ID = "pmPstnQlfrTypeId";
		final static String ACTIVE = "active";
		final static String ID = "id";
		final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
		final static String CREATE_TIME = "createTime";
		final static String USER_PRINCIPAL_ID = "userPrincipalId";

	}

}
