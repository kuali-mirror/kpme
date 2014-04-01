package org.kuali.kpme.core.api.departmentaffiliation;

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

@XmlRootElement(name = DepartmentAffiliation.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = DepartmentAffiliation.Constants.TYPE_NAME, propOrder = {
    DepartmentAffiliation.Elements.DEPT_AFFL_TYPE,
    DepartmentAffiliation.Elements.HR_DEPT_AFFL_ID,
    DepartmentAffiliation.Elements.PRIMARY_INDICATOR,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    DepartmentAffiliation.Elements.ACTIVE,
    DepartmentAffiliation.Elements.ID,
    DepartmentAffiliation.Elements.EFFECTIVE_LOCAL_DATE,
    DepartmentAffiliation.Elements.CREATE_TIME,
    DepartmentAffiliation.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class DepartmentAffiliation extends AbstractDataTransferObject implements DepartmentAffiliationContract {

	private static final long serialVersionUID = -8536403714487676667L;
	
	@XmlElement(name = Elements.DEPT_AFFL_TYPE, required = false)
    private final String deptAfflType;
    @XmlElement(name = Elements.HR_DEPT_AFFL_ID, required = false)
    private final String hrDeptAfflId;
    @XmlElement(name = Elements.PRIMARY_INDICATOR, required = false)
    private final boolean primaryIndicator;
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
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private DepartmentAffiliation() {
        this.deptAfflType = null;
        this.hrDeptAfflId = null;
        this.primaryIndicator = false;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private DepartmentAffiliation(Builder builder) {
        this.deptAfflType = builder.getDeptAfflType();
        this.hrDeptAfflId = builder.getHrDeptAfflId();
        this.primaryIndicator = builder.isPrimaryIndicator();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getDeptAfflType() {
        return this.deptAfflType;
    }

    @Override
    public String getHrDeptAfflId() {
        return this.hrDeptAfflId;
    }

    @Override
    public boolean isPrimaryIndicator() {
        return this.primaryIndicator;
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
     * A builder which can be used to construct {@link DepartmentAffiliation} instances.  Enforces the constraints of the {@link DepartmentAffiliationContract}.
     * 
     */
    public final static class Builder implements Serializable, DepartmentAffiliationContract, ModelBuilder {

		private static final long serialVersionUID = -4886512382767407566L;
		
		private String deptAfflType;
        private String hrDeptAfflId;
        private boolean primaryIndicator;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(DepartmentAffiliationContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setDeptAfflType(contract.getDeptAfflType());
            builder.setHrDeptAfflId(contract.getHrDeptAfflId());
            builder.setPrimaryIndicator(contract.isPrimaryIndicator());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public DepartmentAffiliation build() {
            return new DepartmentAffiliation(this);
        }

        @Override
        public String getDeptAfflType() {
            return this.deptAfflType;
        }

        @Override
        public String getHrDeptAfflId() {
            return this.hrDeptAfflId;
        }

        @Override
        public boolean isPrimaryIndicator() {
            return this.primaryIndicator;
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

        public void setDeptAfflType(String deptAfflType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.deptAfflType = deptAfflType;
        }

        public void setHrDeptAfflId(String hrDeptAfflId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrDeptAfflId = hrDeptAfflId;
        }

        public void setPrimaryIndicator(boolean primaryIndicator) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.primaryIndicator = primaryIndicator;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.active = active;
        }

        public void setId(String id) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.id = id;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "departmentAffiliation";
        final static String TYPE_NAME = "DepartmentAffiliationType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String DEPT_AFFL_TYPE = "deptAfflType";
        final static String HR_DEPT_AFFL_ID = "hrDeptAfflId";
        final static String PRIMARY_INDICATOR = "primaryIndicator";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

