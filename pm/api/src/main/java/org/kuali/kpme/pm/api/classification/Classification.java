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
package org.kuali.kpme.pm.api.classification;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDutyContract;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlagContract;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualificationContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.w3c.dom.Element;

@XmlRootElement(name = Classification.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Classification.Constants.TYPE_NAME, propOrder = {
    Classification.Elements.LOCATION,
    Classification.Elements.POOL_ELIGIBLE,
    Classification.Elements.POSITION_TYPE,
    Classification.Elements.POSITION_REPORT_GROUP,
    Classification.Elements.LEAVE_ELIGIBLE,
    Classification.Elements.BENEFITS_ELIGIBLE,
    Classification.Elements.CLASSIFICATION_TITLE,
    Classification.Elements.POSITION_CLASS,
    Classification.Elements.INSTITUTION,
    Classification.Elements.PERCENT_TIME,
    Classification.Elements.SALARY_GROUP,
    Classification.Elements.TENURE_ELIGIBLE,
    Classification.Elements.EXTERNAL_REFERENCE,
    Classification.Elements.QUALIFICATION_LIST,
    Classification.Elements.PM_POSITION_CLASS_ID,
    Classification.Elements.LOCATION_OBJ,
    Classification.Elements.FLAG_LIST,
    Classification.Elements.DUTY_LIST,
    Classification.Elements.LEAVE_PLAN,
    Classification.Elements.PAY_GRADE,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    Classification.Elements.ACTIVE,
    Classification.Elements.ID,
    Classification.Elements.EFFECTIVE_LOCAL_DATE,
    Classification.Elements.CREATE_TIME,
    Classification.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Classification extends AbstractDataTransferObject implements ClassificationContract {

	private static final long serialVersionUID = 3022823678756071188L;
	
	@XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.POOL_ELIGIBLE, required = false)
    private final String poolEligible;
    @XmlElement(name = Elements.POSITION_TYPE, required = false)
    private final String positionType;
    @XmlElement(name = Elements.POSITION_REPORT_GROUP, required = false)
    private final String positionReportGroup;
    @XmlElement(name = Elements.LEAVE_ELIGIBLE, required = false)
    private final String leaveEligible;
    @XmlElement(name = Elements.BENEFITS_ELIGIBLE, required = false)
    private final String benefitsEligible;
    @XmlElement(name = Elements.CLASSIFICATION_TITLE, required = false)
    private final String classificationTitle;
    @XmlElement(name = Elements.POSITION_CLASS, required = false)
    private final String positionClass;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    @XmlElement(name = Elements.PERCENT_TIME, required = false)
    private final BigDecimal percentTime;
    @XmlElement(name = Elements.SALARY_GROUP, required = false)
    private final String salaryGroup;
    @XmlElement(name = Elements.TENURE_ELIGIBLE, required = false)
    private final String tenureEligible;
    @XmlElement(name = Elements.EXTERNAL_REFERENCE, required = false)
    private final String externalReference;
    @XmlElement(name = Elements.QUALIFICATION_LIST, required = false)
    private final List<ClassificationQualification> qualificationList;
    @XmlElement(name = Elements.PM_POSITION_CLASS_ID, required = false)
    private final String pmPositionClassId;
    @XmlElement(name = Elements.LOCATION_OBJ, required = false)
    private final Location locationObj;
    @XmlElement(name = Elements.FLAG_LIST, required = false)
    private final List<ClassificationFlag> flagList;
    @XmlElement(name = Elements.DUTY_LIST, required = false)
    private final List<ClassificationDuty> dutyList;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
    @XmlElement(name = Elements.PAY_GRADE, required = false)
    private final String payGrade;
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
    private Classification() {
        this.location = null;
        this.poolEligible = null;
        this.positionType = null;
        this.positionReportGroup = null;
        this.leaveEligible = null;
        this.benefitsEligible = null;
        this.classificationTitle = null;
        this.positionClass = null;
        this.institution = null;
        this.percentTime = null;
        this.salaryGroup = null;
        this.tenureEligible = null;
        this.externalReference = null;
        this.qualificationList = null;
        this.pmPositionClassId = null;
        this.locationObj = null;
        this.flagList = null;
        this.dutyList = null;
        this.leavePlan = null;
        this.payGrade = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private Classification(Builder builder) {
        this.location = builder.getLocation();
        this.poolEligible = builder.getPoolEligible();
        this.positionType = builder.getPositionType();
        this.positionReportGroup = builder.getPositionReportGroup();
        this.leaveEligible = builder.getLeaveEligible();
        this.benefitsEligible = builder.getBenefitsEligible();
        this.classificationTitle = builder.getClassificationTitle();
        this.positionClass = builder.getPositionClass();
        this.institution = builder.getInstitution();
        this.percentTime = builder.getPercentTime();
        this.salaryGroup = builder.getSalaryGroup();
        this.tenureEligible = builder.getTenureEligible();
        this.externalReference = builder.getExternalReference();
        this.qualificationList = ModelObjectUtils.<ClassificationQualification>buildImmutableCopy(builder.getQualificationList());
        this.pmPositionClassId = builder.getPmPositionClassId();
        this.locationObj =  builder.getLocationObj() == null ? null : builder.getLocationObj().build();
        this.flagList = ModelObjectUtils.<ClassificationFlag>buildImmutableCopy(builder.getFlagList());
        this.dutyList = ModelObjectUtils.<ClassificationDuty>buildImmutableCopy(builder.getDutyList());
        this.leavePlan = builder.getLeavePlan();
        this.payGrade = builder.getPayGrade();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getPoolEligible() {
        return this.poolEligible;
    }

    @Override
    public String getPositionType() {
        return this.positionType;
    }

    @Override
    public String getPositionReportGroup() {
        return this.positionReportGroup;
    }

    @Override
    public String getLeaveEligible() {
        return this.leaveEligible;
    }

    @Override
    public String getBenefitsEligible() {
        return this.benefitsEligible;
    }

    @Override
    public String getClassificationTitle() {
        return this.classificationTitle;
    }

    @Override
    public String getPositionClass() {
        return this.positionClass;
    }

    @Override
    public String getInstitution() {
        return this.institution;
    }

    @Override
    public BigDecimal getPercentTime() {
        return this.percentTime;
    }

    @Override
    public String getSalaryGroup() {
        return this.salaryGroup;
    }

    @Override
    public String getTenureEligible() {
        return this.tenureEligible;
    }

    @Override
    public String getExternalReference() {
        return this.externalReference;
    }

    @Override
    public List<ClassificationQualification> getQualificationList() {
        return this.qualificationList;
    }

    @Override
    public String getPmPositionClassId() {
        return this.pmPositionClassId;
    }

    @Override
    public Location getLocationObj() {
        return this.locationObj;
    }

    @Override
    public List<ClassificationFlag> getFlagList() {
        return this.flagList;
    }

    @Override
    public List<ClassificationDuty> getDutyList() {
        return this.dutyList;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
    }

    @Override
    public String getPayGrade() {
        return this.payGrade;
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
     * A builder which can be used to construct {@link Classification} instances.  Enforces the constraints of the {@link ClassificationContract}.
     * 
     */
    public final static class Builder implements Serializable, ClassificationContract, ModelBuilder {

		private static final long serialVersionUID = -5298550128140145929L;
		
		private String location;
        private String poolEligible;
        private String positionType;
        private String positionReportGroup;
        private String leaveEligible;
        private String benefitsEligible;
        private String classificationTitle;
        private String positionClass;
        private String institution;
        private BigDecimal percentTime;
        private String salaryGroup;
        private String tenureEligible;
        private String externalReference;
        private List<ClassificationQualification.Builder> qualificationList;
        private String pmPositionClassId;
        private Location.Builder locationObj;
        private List<ClassificationFlag.Builder> flagList;
        private List<ClassificationDuty.Builder> dutyList;
        private String leavePlan;
        private String payGrade;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        
        private static final ModelObjectUtils.Transformer<ClassificationQualificationContract, ClassificationQualification.Builder> toClassificationQualificationBuilder = new ModelObjectUtils.Transformer<ClassificationQualificationContract, ClassificationQualification.Builder>() {
			public ClassificationQualification.Builder transform(ClassificationQualificationContract input) {
				return ClassificationQualification.Builder.create(input);
			}
		};
       
		private static final ModelObjectUtils.Transformer<ClassificationFlagContract, ClassificationFlag.Builder> toClassificationFlagBuilder = new ModelObjectUtils.Transformer<ClassificationFlagContract, ClassificationFlag.Builder>() {
			public ClassificationFlag.Builder transform(ClassificationFlagContract input) {
				return ClassificationFlag.Builder.create(input);
			}
		};
		
		private static final ModelObjectUtils.Transformer<ClassificationDutyContract, ClassificationDuty.Builder> toClassificationDutyBuilder = new ModelObjectUtils.Transformer<ClassificationDutyContract, ClassificationDuty.Builder>() {
			public ClassificationDuty.Builder transform(ClassificationDutyContract input) {
				return ClassificationDuty.Builder.create(input);
			}
		};

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(ClassificationContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setLocation(contract.getLocation());
            builder.setPoolEligible(contract.getPoolEligible());
            builder.setPositionType(contract.getPositionType());
            builder.setPositionReportGroup(contract.getPositionReportGroup());
            builder.setLeaveEligible(contract.getLeaveEligible());
            builder.setBenefitsEligible(contract.getBenefitsEligible());
            builder.setClassificationTitle(contract.getClassificationTitle());
            builder.setPositionClass(contract.getPositionClass());
            builder.setInstitution(contract.getInstitution());
            builder.setPercentTime(contract.getPercentTime());
            builder.setSalaryGroup(contract.getSalaryGroup());
            builder.setTenureEligible(contract.getTenureEligible());
            builder.setExternalReference(contract.getExternalReference());
            builder.setQualificationList(ModelObjectUtils.transform(contract.getQualificationList(), toClassificationQualificationBuilder));
            builder.setPmPositionClassId(contract.getPmPositionClassId());
            builder.setLocationObj(contract.getLocationObj() == null ? null : Location.Builder.create(contract.getLocationObj()));
            builder.setFlagList(ModelObjectUtils.transform(contract.getFlagList(), toClassificationFlagBuilder));
            builder.setDutyList(ModelObjectUtils.transform(contract.getDutyList(), toClassificationDutyBuilder));
            builder.setLeavePlan(contract.getLeavePlan());
            builder.setPayGrade(contract.getPayGrade());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public Classification build() {
            return new Classification(this);
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public String getPoolEligible() {
            return this.poolEligible;
        }

        @Override
        public String getPositionType() {
            return this.positionType;
        }

        @Override
        public String getPositionReportGroup() {
            return this.positionReportGroup;
        }

        @Override
        public String getLeaveEligible() {
            return this.leaveEligible;
        }

        @Override
        public String getBenefitsEligible() {
            return this.benefitsEligible;
        }

        @Override
        public String getClassificationTitle() {
            return this.classificationTitle;
        }

        @Override
        public String getPositionClass() {
            return this.positionClass;
        }

        @Override
        public String getInstitution() {
            return this.institution;
        }

        @Override
        public BigDecimal getPercentTime() {
            return this.percentTime;
        }

        @Override
        public String getSalaryGroup() {
            return this.salaryGroup;
        }

        @Override
        public String getTenureEligible() {
            return this.tenureEligible;
        }

        @Override
        public String getExternalReference() {
            return this.externalReference;
        }

        @Override
        public List<ClassificationQualification.Builder> getQualificationList() {
            return this.qualificationList;
        }

        @Override
        public String getPmPositionClassId() {
            return this.pmPositionClassId;
        }

        @Override
        public Location.Builder getLocationObj() {
            return this.locationObj;
        }

        @Override
        public List<ClassificationFlag.Builder> getFlagList() {
            return this.flagList;
        }

        @Override
        public List<ClassificationDuty.Builder> getDutyList() {
            return this.dutyList;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
        }

        @Override
        public String getPayGrade() {
            return this.payGrade;
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

        public void setLocation(String location) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.location = location;
        }

        public void setPoolEligible(String poolEligible) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.poolEligible = poolEligible;
        }

        public void setPositionType(String positionType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionType = positionType;
        }

        public void setPositionReportGroup(String positionReportGroup) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionReportGroup = positionReportGroup;
        }

        public void setLeaveEligible(String leaveEligible) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.leaveEligible = leaveEligible;
        }

        public void setBenefitsEligible(String benefitsEligible) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.benefitsEligible = benefitsEligible;
        }

        public void setClassificationTitle(String classificationTitle) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.classificationTitle = classificationTitle;
        }

        public void setPositionClass(String positionClass) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionClass = positionClass;
        }

        public void setInstitution(String institution) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.institution = institution;
        }

        public void setPercentTime(BigDecimal percentTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.percentTime = percentTime;
        }

        public void setSalaryGroup(String salaryGroup) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.salaryGroup = salaryGroup;
        }

        public void setTenureEligible(String tenureEligible) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.tenureEligible = tenureEligible;
        }

        public void setExternalReference(String externalReference) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.externalReference = externalReference;
        }

        public void setQualificationList(List<ClassificationQualification.Builder> qualificationList) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.qualificationList = qualificationList;
        }

        public void setPmPositionClassId(String pmPositionClassId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionClassId = pmPositionClassId;
        }

        public void setLocationObj(Location.Builder locationObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.locationObj = locationObj;
        }

        public void setFlagList(List<ClassificationFlag.Builder> flagList) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.flagList = flagList;
        }

        public void setDutyList(List<ClassificationDuty.Builder> dutyList) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dutyList = dutyList;
        }

        public void setLeavePlan(String leavePlan) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.leavePlan = leavePlan;
        }

        public void setPayGrade(String payGrade) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.payGrade = payGrade;
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

        final static String ROOT_ELEMENT_NAME = "classification";
        final static String TYPE_NAME = "ClassificationType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String LOCATION = "location";
        final static String POOL_ELIGIBLE = "poolEligible";
        final static String POSITION_TYPE = "positionType";
        final static String POSITION_REPORT_GROUP = "positionReportGroup";
        final static String LEAVE_ELIGIBLE = "leaveEligible";
        final static String BENEFITS_ELIGIBLE = "benefitsEligible";
        final static String CLASSIFICATION_TITLE = "classificationTitle";
        final static String POSITION_CLASS = "positionClass";
        final static String INSTITUTION = "institution";
        final static String PERCENT_TIME = "percentTime";
        final static String SALARY_GROUP = "salaryGroup";
        final static String TENURE_ELIGIBLE = "tenureEligible";
        final static String EXTERNAL_REFERENCE = "externalReference";
        final static String QUALIFICATION_LIST = "qualificationList";
        final static String PM_POSITION_CLASS_ID = "pmPositionClassId";
        final static String LOCATION_OBJ = "locationObj";
        final static String FLAG_LIST = "flagList";
        final static String DUTY_LIST = "dutyList";
        final static String LEAVE_PLAN = "leavePlan";
        final static String PAY_GRADE = "payGrade";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}