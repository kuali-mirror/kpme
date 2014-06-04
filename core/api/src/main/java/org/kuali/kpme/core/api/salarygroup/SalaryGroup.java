
package org.kuali.kpme.core.api.salarygroup;

import java.io.Serializable;
import java.math.BigDecimal;
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

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.api.mo.EffectiveKeyContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.w3c.dom.Element;

@XmlRootElement(name = SalaryGroup.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = SalaryGroup.Constants.TYPE_NAME, propOrder = {
    SalaryGroup.Elements.BENEFITS_ELIGIBLE,
    SalaryGroup.Elements.PERCENT_TIME,
    SalaryGroup.Elements.HR_SAL_GROUP,
    SalaryGroup.Elements.EFFECTIVE_KEY_SET,
    SalaryGroup.Elements.LEAVE_ELIGIBLE,
    SalaryGroup.Elements.HR_SAL_GROUP_ID,
    SalaryGroup.Elements.DESCR,
    SalaryGroup.Elements.INSTITUTION,
    SalaryGroup.Elements.LEAVE_PLAN,
    SalaryGroup.Elements.LOCATION,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    SalaryGroup.Elements.ACTIVE,
    SalaryGroup.Elements.ID,
    SalaryGroup.Elements.EFFECTIVE_LOCAL_DATE,
    SalaryGroup.Elements.CREATE_TIME,
    SalaryGroup.Elements.USER_PRINCIPAL_ID,
    SalaryGroup.Elements.GROUP_KEY_CODE_SET,
    SalaryGroup.Elements.GROUP_KEY_SET,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class SalaryGroup
    extends AbstractDataTransferObject
    implements SalaryGroupContract
{

    @XmlElement(name = Elements.BENEFITS_ELIGIBLE, required = false)
    private final String benefitsEligible;
    @XmlElement(name = Elements.PERCENT_TIME, required = false)
    private final BigDecimal percentTime;
    @XmlElement(name = Elements.HR_SAL_GROUP, required = false)
    private final String hrSalGroup;
    @XmlElement(name = Elements.EFFECTIVE_KEY_SET, required = false)
    private final Set<EffectiveKey> effectiveKeySet;
    @XmlElement(name = Elements.LEAVE_ELIGIBLE, required = false)
    private final String leaveEligible;
    @XmlElement(name = Elements.HR_SAL_GROUP_ID, required = false)
    private final String hrSalGroupId;
    @XmlElement(name = Elements.DESCR, required = false)
    private final String descr;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
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
    private SalaryGroup() {
        this.benefitsEligible = null;
        this.percentTime = null;
        this.hrSalGroup = null;
        this.effectiveKeySet = null;
        this.leaveEligible = null;
        this.hrSalGroupId = null;
        this.descr = null;
        this.institution = null;
        this.leavePlan = null;
        this.location = null;
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

    private SalaryGroup(Builder builder) {
        this.benefitsEligible = builder.getBenefitsEligible();
        this.percentTime = builder.getPercentTime();
        this.hrSalGroup = builder.getHrSalGroup();
        this.effectiveKeySet = ModelObjectUtils.<EffectiveKey>buildImmutableCopy(builder.getEffectiveKeySet());  
        this.leaveEligible = builder.getLeaveEligible();
        this.hrSalGroupId = builder.getHrSalGroupId();
        this.descr = builder.getDescr();
        this.institution = builder.getInstitution();
        this.leavePlan = builder.getLeavePlan();
        this.location = builder.getLocation();
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
    public String getBenefitsEligible() {
        return this.benefitsEligible;
    }

    @Override
    public BigDecimal getPercentTime() {
        return this.percentTime;
    }

    @Override
    public String getHrSalGroup() {
        return this.hrSalGroup;
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
    public String getLeaveEligible() {
        return this.leaveEligible;
    }

    @Override
    public String getHrSalGroupId() {
        return this.hrSalGroupId;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public String getInstitution() {
        return this.institution;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
    }

    @Override
    public String getLocation() {
        return this.location;
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
    public Set getGroupKeyCodeSet() {
        return this.groupKeyCodeSet;
    }

    @Override
    public Set getGroupKeySet() {
        return this.groupKeySet;
    }


    /**
     * A builder which can be used to construct {@link SalaryGroup} instances.  Enforces the constraints of the {@link SalaryGroupContract}.
     * 
     */
    public final static class Builder
        implements Serializable, SalaryGroupContract, ModelBuilder
    {

        private String benefitsEligible;
        private BigDecimal percentTime;
        private String hrSalGroup;
        private Set<EffectiveKey.Builder> effectiveKeySet;
        private String leaveEligible;
        private String hrSalGroupId;
        private String descr;
        private String institution;
        private String leavePlan;
        private String location;
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

				  private Builder(String hrSalaryGroup) {
			            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
			        	setHrSalGroup(hrSalaryGroup);
			        }

			        public static Builder create(String hrSalaryGroup) {
			            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
			            return new Builder(hrSalaryGroup);
			        }

        public static Builder create(SalaryGroupContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getHrSalGroup());
            builder.setBenefitsEligible(contract.getBenefitsEligible());
            builder.setPercentTime(contract.getPercentTime());
            builder.setHrSalGroup(contract.getHrSalGroup());
            builder.setEffectiveKeySet(ModelObjectUtils.transformSet(contract.getEffectiveKeySet(), toEffectiveKeyBuilder));
            builder.setLeaveEligible(contract.getLeaveEligible());
            builder.setHrSalGroupId(contract.getHrSalGroupId());
            builder.setDescr(contract.getDescr());
            builder.setInstitution(contract.getInstitution());
            builder.setLeavePlan(contract.getLeavePlan());
            builder.setLocation(contract.getLocation());
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

        public SalaryGroup build() {
            return new SalaryGroup(this);
        }

        @Override
        public String getBenefitsEligible() {
            return this.benefitsEligible;
        }

        @Override
        public BigDecimal getPercentTime() {
            return this.percentTime;
        }

        @Override
        public String getHrSalGroup() {
            return this.hrSalGroup;
        }

        @Override
        public Set<EffectiveKey.Builder> getEffectiveKeySet() {
            return this.effectiveKeySet;
        }

        @Override
        public String getLeaveEligible() {
            return this.leaveEligible;
        }

        @Override
        public String getHrSalGroupId() {
            return this.hrSalGroupId;
        }

        @Override
        public String getDescr() {
            return this.descr;
        }

        @Override
        public String getInstitution() {
            return this.institution;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
        }

        @Override
        public String getLocation() {
            return this.location;
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


        public void setBenefitsEligible(String benefitsEligible) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.benefitsEligible = benefitsEligible;
        }

        public void setPercentTime(BigDecimal percentTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.percentTime = percentTime;
        }

        public void setHrSalGroup(String hrSalGroup) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrSalGroup = hrSalGroup;
        }

        public void setEffectiveKeySet(Set<EffectiveKey.Builder> effectiveKeySet) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveKeySet = effectiveKeySet;
        }

        public void setLeaveEligible(String leaveEligible) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.leaveEligible = leaveEligible;
        }

        public void setHrSalGroupId(String hrSalGroupId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrSalGroupId = hrSalGroupId;
        }

        public void setDescr(String descr) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.descr = descr;
        }

        public void setInstitution(String institution) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.institution = institution;
        }

        public void setLeavePlan(String leavePlan) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.leavePlan = leavePlan;
        }

        public void setLocation(String location) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.location = location;
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

        public void setGroupKeyCodeSet(Set<String> groupKeyCodeSet) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKeyCodeSet = groupKeyCodeSet;
        }

        public void setGroupKeySet(Set<HrGroupKey.Builder> groupKeySet) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKeySet = groupKeySet;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "salaryGroup";
        final static String TYPE_NAME = "SalaryGroupType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String BENEFITS_ELIGIBLE = "benefitsEligible";
        final static String PERCENT_TIME = "percentTime";
        final static String HR_SAL_GROUP = "hrSalGroup";
        final static String EFFECTIVE_KEY_SET = "effectiveKeySet";
        final static String LEAVE_ELIGIBLE = "leaveEligible";
        final static String HR_SAL_GROUP_ID = "hrSalGroupId";
        final static String DESCR = "descr";
        final static String INSTITUTION = "institution";
        final static String LEAVE_PLAN = "leavePlan";
        final static String LOCATION = "location";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE_SET = "groupKeyCodeSet";
        final static String GROUP_KEY_SET = "groupKeySet";

    }

}

