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
package org.kuali.kpme.pm.api.position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.*;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualificationContract;
import org.kuali.kpme.pm.api.position.PositionContract;
import org.kuali.kpme.pm.api.position.funding.PositionFunding;
import org.kuali.kpme.pm.api.position.funding.PositionFundingContract;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartmentContract;
import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibility;
import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibilityContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.w3c.dom.Element;

@XmlRootElement(name = Position.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Position.Constants.TYPE_NAME, propOrder = {
//    Position.Elements.LOCATION,
    Position.Elements.BENEFITS_ELIGIBLE,
    Position.Elements.PERCENT_TIME,
    Position.Elements.CLASSIFICATION_TITLE,
    Position.Elements.SALARY_GROUP,
    Position.Elements.QUALIFICATION_LIST,
    Position.Elements.REPORTS_TO_PRINCIPAL_ID,
    Position.Elements.LEAVE_ELIGIBLE,
    Position.Elements.POSITION_REPORT_GROUP,
    Position.Elements.POSITION_TYPE,
    Position.Elements.POOL_ELIGIBLE,
    Position.Elements.MAX_POOL_HEAD_COUNT,
    Position.Elements.TENURE_ELIGIBLE,
    Position.Elements.DEPARTMENT_LIST,
    Position.Elements.POSITION_STATUS,
    Position.Elements.CONTRACT_TYPE,
    Position.Elements.RENEW_ELIGIBLE,
    Position.Elements.EXPECTED_END_DATE_TIME,
    Position.Elements.REPORTS_TO_POSITION_ID,
    Position.Elements.REQUIRED_QUAL_LIST,
    Position.Elements.POSITION_RESPONSIBILITY_LIST,
    Position.Elements.PM_POSITION_CLASS_ID,
    Position.Elements.FUNDING_LIST,
//    Position.Elements.INSTITUTION,
    Position.Elements.WORK_MONTHS,
    Position.Elements.TEMPORARY,
    Position.Elements.CATEGORY,
    Position.Elements.LEAVE_PLAN,
    Position.Elements.FLAG_LIST,
    Position.Elements.PAY_STEP,
    Position.Elements.PAY_GRADE,
    Position.Elements.DUTY_LIST,
    Position.Elements.CONTRACT,
    Position.Elements.POSITION_CLASS,
    Position.Elements.APPOINTMENT_TYPE,
    Position.Elements.REPORTS_TO_WORKING_TITLE,
    Position.Elements.PRIMARY_DEPARTMENT,
    Position.Elements.PROCESS,
    Position.Elements.POSITION_NUMBER,
    Position.Elements.HR_POSITION_ID,
    Position.Elements.DESCRIPTION,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    Position.Elements.ACTIVE,
    Position.Elements.ID,
    Position.Elements.EFFECTIVE_LOCAL_DATE,
    Position.Elements.CREATE_TIME,
    Position.Elements.USER_PRINCIPAL_ID,
    Position.Elements.GROUP_KEY_CODE,
    Position.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Position extends AbstractDataTransferObject implements PositionContract {

	private static final long serialVersionUID = -3036514710625474302L;
	
//	@XmlElement(name = Elements.LOCATION, required = false)
//    private final String location;
    @XmlElement(name = Elements.BENEFITS_ELIGIBLE, required = false)
    private final String benefitsEligible;
    @XmlElement(name = Elements.PERCENT_TIME, required = false)
    private final BigDecimal percentTime;
    @XmlElement(name = Elements.CLASSIFICATION_TITLE, required = false)
    private final String classificationTitle;
    @XmlElement(name = Elements.SALARY_GROUP, required = false)
    private final String salaryGroup;
    @XmlElementWrapper(name = Elements.QUALIFICATION_LIST, required = false)
    @XmlElement(name = Elements.QUALIFICATION, required = false)
    private final List<PositionQualification> qualificationList;
    @XmlElement(name = Elements.REPORTS_TO_PRINCIPAL_ID, required = false)
    private final String reportsToPrincipalId;
    @XmlElement(name = Elements.LEAVE_ELIGIBLE, required = false)
    private final String leaveEligible;
    @XmlElement(name = Elements.POSITION_REPORT_GROUP, required = false)
    private final String positionReportGroup;
    @XmlElement(name = Elements.POSITION_TYPE, required = false)
    private final String positionType;
    @XmlElement(name = Elements.POOL_ELIGIBLE, required = false)
    private final String poolEligible;
    @XmlElement(name = Elements.MAX_POOL_HEAD_COUNT, required = false)
    private final int maxPoolHeadCount;
    @XmlElement(name = Elements.TENURE_ELIGIBLE, required = false)
    private final String tenureEligible;
    @XmlElementWrapper(name = Elements.DEPARTMENT_LIST, required = false)
    @XmlElement(name = Elements.DEPARTMENT, required = false)
    private final List<PositionDepartment> departmentList;
    @XmlElement(name = Elements.POSITION_STATUS, required = false)
    private final String positionStatus;
    @XmlElement(name = Elements.CONTRACT_TYPE, required = false)
    private final String contractType;
    @XmlElement(name = Elements.RENEW_ELIGIBLE, required = false)
    private final String renewEligible;
    @XmlElement(name = Elements.EXPECTED_END_DATE_TIME, required = false)
    private final DateTime expectedEndDateTime;
    @XmlElement(name = Elements.REPORTS_TO_POSITION_ID, required = false)
    private final String reportsToPositionId;
    @XmlElementWrapper(name = Elements.REQUIRED_QUAL_LIST, required = false)
    @XmlElement(name = Elements.REQUIRED_QUAL, required = false)
    private final List<ClassificationQualification> requiredQualList;
    @XmlElementWrapper(name = Elements.POSITION_RESPONSIBILITY_LIST, required = false)
    @XmlElement(name = Elements.POSITION_RESPONSIBILITY, required = false)
    private final List<PositionResponsibility> positionResponsibilityList;
    @XmlElement(name = Elements.PM_POSITION_CLASS_ID, required = false)
    private final String pmPositionClassId;
    @XmlElementWrapper(name = Elements.FUNDING_LIST, required = false)
    @XmlElement(name = Elements.FUNDING, required = false)
    private final List<PositionFunding> fundingList;
//    @XmlElement(name = Elements.INSTITUTION, required = false)
//    private final String institution;
    @XmlElement(name = Elements.WORK_MONTHS, required = false)
    private final int workMonths;
    @XmlElement(name = Elements.TEMPORARY, required = false)
    private final String temporary;
    @XmlElement(name = Elements.CATEGORY, required = false)
    private final String category;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
    @XmlElementWrapper(name = Elements.FLAG_LIST, required = false)
    @XmlElement(name = Elements.FLAG, required = false)
    private final List<PstnFlag> flagList;
    @XmlElement(name = Elements.PAY_STEP, required = false)
    private final String payStep;
    @XmlElement(name = Elements.PAY_GRADE, required = false)
    private final String payGrade;
    @XmlElementWrapper(name = Elements.DUTY_LIST, required = false)
    @XmlElement(name = Elements.DUTY, required = false)
    private final List<PositionDuty> dutyList;
    @XmlElement(name = Elements.CONTRACT, required = false)
    private final String contract;
    @XmlElement(name = Elements.POSITION_CLASS, required = false)
    private final String positionClass;
    @XmlElement(name = Elements.APPOINTMENT_TYPE, required = false)
    private final String appointmentType;
    @XmlElement(name = Elements.REPORTS_TO_WORKING_TITLE, required = false)
    private final String reportsToWorkingTitle;
    @XmlElement(name = Elements.PRIMARY_DEPARTMENT, required = false)
    private final String primaryDepartment;
    @XmlElement(name = Elements.PROCESS, required = false)
    private final String process;
    @XmlElement(name = Elements.POSITION_NUMBER, required = false)
    private final String positionNumber;
    @XmlElement(name = Elements.HR_POSITION_ID, required = false)
    private final String hrPositionId;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
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
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = false)
    private final HrGroupKey groupKey;
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private Position() {
//        this.location = null;
        this.benefitsEligible = null;
        this.percentTime = null;
        this.classificationTitle = null;
        this.salaryGroup = null;
        this.qualificationList = null;
        this.reportsToPrincipalId = null;
        this.leaveEligible = null;
        this.positionReportGroup = null;
        this.positionType = null;
        this.poolEligible = null;
        this.maxPoolHeadCount = 0;
        this.tenureEligible = null;
        this.departmentList = null;
        this.positionStatus = null;
        this.contractType = null;
        this.renewEligible = null;
        this.expectedEndDateTime = null;
        this.reportsToPositionId = null;
        this.requiredQualList = null;
        this.positionResponsibilityList = null;
        this.pmPositionClassId = null;
        this.fundingList = null;
//        this.institution = null;
        this.workMonths = 0;
        this.temporary = null;
        this.category = null;
        this.leavePlan = null;
        this.flagList = null;
        this.payStep = null;
        this.payGrade = null;
        this.dutyList = null;
        this.contract = null;
        this.positionNumber = null;
        this.positionClass = null;
        this.appointmentType = null;
        this.reportsToWorkingTitle = null;
        this.primaryDepartment = null;
        this.process = null;
        this.hrPositionId = null;
        this.description = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        this.groupKeyCode = null;
        this.groupKey = null;
    }

    private Position(Builder builder) {
//        this.location = builder.getLocation();
        this.benefitsEligible = builder.getBenefitsEligible();
        this.percentTime = builder.getPercentTime();
        this.classificationTitle = builder.getClassificationTitle();
        this.salaryGroup = builder.getSalaryGroup();
        this.qualificationList = ModelObjectUtils.<PositionQualification>buildImmutableCopy(builder.getQualificationList());
        this.reportsToPrincipalId = builder.getReportsToPrincipalId();
        this.leaveEligible = builder.getLeaveEligible();
        this.positionReportGroup = builder.getPositionReportGroup();
        this.positionType = builder.getPositionType();
        this.poolEligible = builder.getPoolEligible();
        this.maxPoolHeadCount = builder.getMaxPoolHeadCount();
        this.tenureEligible = builder.getTenureEligible();
        this.departmentList = ModelObjectUtils.<PositionDepartment>buildImmutableCopy(builder.getDepartmentList());
        this.positionStatus = builder.getPositionStatus();
        this.contractType = builder.getContractType();
        this.renewEligible = builder.getRenewEligible();
        this.expectedEndDateTime = builder.getExpectedEndDateTime();
        this.reportsToPositionId = builder.getReportsToPositionId();
        this.requiredQualList = ModelObjectUtils.<ClassificationQualification>buildImmutableCopy(builder.getRequiredQualList());
        this.positionResponsibilityList = ModelObjectUtils.<PositionResponsibility>buildImmutableCopy(builder.getPositionResponsibilityList());
        this.pmPositionClassId = builder.getPmPositionClassId();
        this.fundingList = ModelObjectUtils.<PositionFunding>buildImmutableCopy(builder.getFundingList());
//        this.institution = builder.getInstitution();
        this.workMonths = builder.getWorkMonths();
        this.temporary = builder.getTemporary();
        this.category = builder.getCategory();
        this.leavePlan = builder.getLeavePlan();
        this.flagList = ModelObjectUtils.<PstnFlag>buildImmutableCopy(builder.getFlagList());
        this.payStep = builder.getPayStep();
        this.payGrade = builder.getPayGrade();
        this.dutyList = ModelObjectUtils.<PositionDuty>buildImmutableCopy(builder.getDutyList());
        this.contract = builder.getContract();
        this.positionNumber = builder.getPositionNumber();
        this.positionClass = builder.getPositionClass();
        this.appointmentType = builder.getAppointmentType();
        this.reportsToWorkingTitle = builder.getReportsToWorkingTitle();
        this.primaryDepartment = builder.getPrimaryDepartment();
        this.process = builder.getProcess();
        this.hrPositionId = builder.getHrPositionId();
        this.description = builder.getDescription();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
    }

//    @Override
//    public String getLocation() {
//        return this.location;
//    }

    @Override
    public String getBenefitsEligible() {
        return this.benefitsEligible;
    }

    @Override
    public BigDecimal getPercentTime() {
        return this.percentTime;
    }

    @Override
    public String getClassificationTitle() {
        return this.classificationTitle;
    }

    @Override
    public String getSalaryGroup() {
        return this.salaryGroup;
    }

    @Override
    public List<PositionQualification> getQualificationList() {
        return this.qualificationList;
    }

    @Override
    public String getReportsToPrincipalId() {
        return this.reportsToPrincipalId;
    }

    @Override
    public String getLeaveEligible() {
        return this.leaveEligible;
    }

    @Override
    public String getPositionReportGroup() {
        return this.positionReportGroup;
    }

    @Override
    public String getPositionType() {
        return this.positionType;
    }

    @Override
    public String getPoolEligible() {
        return this.poolEligible;
    }

    @Override
    public int getMaxPoolHeadCount() {
        return this.maxPoolHeadCount;
    }

    @Override
    public String getTenureEligible() {
        return this.tenureEligible;
    }

    @Override
    public List<PositionDepartment> getDepartmentList() {
        return this.departmentList;
    }

    @Override
    public String getPositionStatus() {
        return this.positionStatus;
    }

    @Override
    public String getContractType() {
        return this.contractType;
    }

    @Override
    public String getRenewEligible() {
        return this.renewEligible;
    }

    @Override
    public DateTime getExpectedEndDateTime() {
        return this.expectedEndDateTime;
    }

    @Override
    public String getReportsToPositionId() {
        return this.reportsToPositionId;
    }

    @Override
    public List<ClassificationQualification> getRequiredQualList() {
        return this.requiredQualList;
    }

    @Override
    public List<PositionResponsibility> getPositionResponsibilityList() {
        return this.positionResponsibilityList;
    }

    @Override
    public String getPmPositionClassId() {
        return this.pmPositionClassId;
    }

    @Override
    public List<PositionFunding> getFundingList() {
        return this.fundingList;
    }

//    @Override
//    public String getInstitution() {
//        return this.institution;
//    }

    @Override
    public int getWorkMonths() {
        return this.workMonths;
    }

    @Override
    public String getTemporary() {
        return this.temporary;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
    }

    @Override
    public List<PstnFlag> getFlagList() {
        return this.flagList;
    }

    @Override
    public String getPayStep() {
        return this.payStep;
    }

    @Override
    public String getPositionClass() {
        return this.positionClass;
    }

    @Override
    public String getAppointmentType() {
        return this.appointmentType;
    }

    @Override
    public String getReportsToWorkingTitle() {
        return this.reportsToWorkingTitle;
    }

    @Override
    public String getPrimaryDepartment() {
        return this.primaryDepartment;
    }

    @Override
    public String getProcess() {
        return this.process;
    }

    @Override
    public String getPayGrade() {
        return this.payGrade;
    }

    @Override
    public List<PositionDuty> getDutyList() {
        return this.dutyList;
    }

    @Override
    public String getContract() {
        return this.contract;
    }

    @Override
    public String getPositionNumber() {
        return this.positionNumber;
    }

    @Override
    public String getHrPositionId() {
        return this.hrPositionId;
    }

    @Override
    public String getDescription() {
        return this.description;
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
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }

    @Override
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }


    /**
     * A builder which can be used to construct {@link Position} instances.  Enforces the constraints of the {@link PositionContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionContract, ModelBuilder
    {

		private static final long serialVersionUID = 1980038289993898352L;

//		private String location;
        private String benefitsEligible;
        private BigDecimal percentTime;
        private String classificationTitle;
        private String salaryGroup;
        private List<PositionQualification.Builder> qualificationList;
        private String reportsToPrincipalId;
        private String leaveEligible;
        private String positionReportGroup;
        private String positionType;
        private String poolEligible;
        private int maxPoolHeadCount;
        private String tenureEligible;
        private List<PositionDepartment.Builder> departmentList;
        private String positionStatus;
        private String contractType;
        private String renewEligible;
        private DateTime expectedEndDateTime;
        private String reportsToPositionId;
        private List<ClassificationQualification.Builder> requiredQualList;
        private List<PositionResponsibility.Builder> positionResponsibilityList;
        private String pmPositionClassId;
        private List<PositionFunding.Builder> fundingList;
//        private String institution;
        private int workMonths;
        private String temporary;
        private String category;
        private String leavePlan;
        private List<PstnFlag.Builder> flagList;
        private String payStep;
        private String positionClass;
        private String appointmentType;
        private String reportsToWorkingTitle;
        private String primaryDepartment;
        private String process;
        private String payGrade;
        private List<PositionDuty.Builder> dutyList;
        private String contract;
        private String positionNumber;
        private String hrPositionId;
        private String description;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;
        
		private static final ModelObjectUtils.Transformer<PositionDepartmentContract, PositionDepartment.Builder> toPositionDepartmentBuilder = new ModelObjectUtils.Transformer<PositionDepartmentContract, PositionDepartment.Builder>() {
			public PositionDepartment.Builder transform(PositionDepartmentContract input) {
				return PositionDepartment.Builder.create(input);
			}
		};
       
		private static final ModelObjectUtils.Transformer<PstnFlagContract, PstnFlag.Builder> toPstnFlagBuilder = new ModelObjectUtils.Transformer<PstnFlagContract, PstnFlag.Builder>() {
			public PstnFlag.Builder transform(PstnFlagContract input) {
				return PstnFlag.Builder.create(input);
			}
		};
		
		private static final ModelObjectUtils.Transformer<PositionFundingContract, PositionFunding.Builder> toPositionFundingBuilder = new ModelObjectUtils.Transformer<PositionFundingContract, PositionFunding.Builder>() {
			public PositionFunding.Builder transform(PositionFundingContract input) {
				return PositionFunding.Builder.create(input);
			}
		};
		
		private static final ModelObjectUtils.Transformer<PositionQualificationContract, PositionQualification.Builder> toPositionQualificationBuilder = new ModelObjectUtils.Transformer<PositionQualificationContract, PositionQualification.Builder>() {
			public PositionQualification.Builder transform(PositionQualificationContract input) {
				return PositionQualification.Builder.create(input);
			}
		};
		
		private static final ModelObjectUtils.Transformer<PositionResponsibilityContract, PositionResponsibility.Builder> toPositionResponsibilityBuilder = new ModelObjectUtils.Transformer<PositionResponsibilityContract, PositionResponsibility.Builder>() {
			public PositionResponsibility.Builder transform(PositionResponsibilityContract input) {
				return PositionResponsibility.Builder.create(input);
			}
		};
		
		private static final ModelObjectUtils.Transformer<PositionDutyContract, PositionDuty.Builder> toPositionDutyBuilder = new ModelObjectUtils.Transformer<PositionDutyContract, PositionDuty.Builder>() {
			public PositionDuty.Builder transform(PositionDutyContract input) {
				return PositionDuty.Builder.create(input);
			}
		};
		
		private static final ModelObjectUtils.Transformer<ClassificationQualificationContract, ClassificationQualification.Builder> toClassificationQualificationBuilder = new ModelObjectUtils.Transformer<ClassificationQualificationContract, ClassificationQualification.Builder>() {
			public ClassificationQualification.Builder transform(ClassificationQualificationContract input) {
				return ClassificationQualification.Builder.create(input);
			}
		};
        
        private Builder(String positionNumber, String groupKeyCode) {
        	setPositionNumber(positionNumber);
            setGroupKeyCode(groupKeyCode);
        }

        public static Builder create(String positionNumber, String groupKeyCode) {
            return new Builder(positionNumber, groupKeyCode);
        }

        public static Builder create(PositionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getPositionNumber(), contract.getGroupKeyCode());
//            builder.setLocation(contract.getLocation());
            builder.setBenefitsEligible(contract.getBenefitsEligible());
            builder.setPercentTime(contract.getPercentTime());
            builder.setClassificationTitle(contract.getClassificationTitle());
            builder.setSalaryGroup(contract.getSalaryGroup());
            builder.setQualificationList(ModelObjectUtils.transform(contract.getQualificationList(), toPositionQualificationBuilder));
            builder.setReportsToPrincipalId(contract.getReportsToPrincipalId());
            builder.setLeaveEligible(contract.getLeaveEligible());
            builder.setPositionReportGroup(contract.getPositionReportGroup());
            builder.setPositionType(contract.getPositionType());
            builder.setPoolEligible(contract.getPoolEligible());
            builder.setMaxPoolHeadCount(contract.getMaxPoolHeadCount());
            builder.setTenureEligible(contract.getTenureEligible());
            builder.setDepartmentList(ModelObjectUtils.transform(contract.getDepartmentList(), toPositionDepartmentBuilder));
            builder.setPositionStatus(contract.getPositionStatus());
            builder.setContractType(contract.getContractType());
            builder.setRenewEligible(contract.getRenewEligible());
            builder.setExpectedEndDateTime(contract.getExpectedEndDateTime());
            builder.setReportsToPositionId(contract.getReportsToPositionId());
            builder.setRequiredQualList(ModelObjectUtils.transform(contract.getRequiredQualList(), toClassificationQualificationBuilder));
            builder.setPositionResponsibilityList(ModelObjectUtils.transform(contract.getPositionResponsibilityList(), toPositionResponsibilityBuilder));
            builder.setPmPositionClassId(contract.getPmPositionClassId());
            builder.setFundingList(ModelObjectUtils.transform(contract.getFundingList(), toPositionFundingBuilder));
//            builder.setInstitution(contract.getInstitution());
            builder.setWorkMonths(contract.getWorkMonths());
            builder.setTemporary(contract.getTemporary());
            builder.setCategory(contract.getCategory());
            builder.setLeavePlan(contract.getLeavePlan());
            builder.setFlagList(ModelObjectUtils.transform(contract.getFlagList(), toPstnFlagBuilder));
            builder.setPayStep(contract.getPayStep());
            builder.setPositionClass(contract.getPositionClass());
            builder.setAppointmentType(contract.getAppointmentType());
            builder.setReportsToWorkingTitle(contract.getReportsToWorkingTitle());
            builder.setPrimaryDepartment(contract.getPrimaryDepartment());
            builder.setProcess(contract.getProcess());
            builder.setPayGrade(contract.getPayGrade());
            builder.setDutyList(ModelObjectUtils.transform(contract.getDutyList(), toPositionDutyBuilder));
            builder.setContract(contract.getContract());
//            builder.setPositionNumber(contract.getPositionNumber());
            builder.setHrPositionId(contract.getHrPositionId());
            builder.setDescription(contract.getDescription());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));

            return builder;
        }

        public Position build() {
            return new Position(this);
        }

//        @Override
//        public String getLocation() {
//            return this.location;
//        }

        @Override
        public String getBenefitsEligible() {
            return this.benefitsEligible;
        }

        @Override
        public BigDecimal getPercentTime() {
            return this.percentTime;
        }

        @Override
        public String getClassificationTitle() {
            return this.classificationTitle;
        }

        @Override
        public String getSalaryGroup() {
            return this.salaryGroup;
        }

        @Override
        public List<PositionQualification.Builder> getQualificationList() {
            return this.qualificationList;
        }

        @Override
        public String getReportsToPrincipalId() {
            return this.reportsToPrincipalId;
        }

        @Override
        public String getLeaveEligible() {
            return this.leaveEligible;
        }

        @Override
        public String getPositionReportGroup() {
            return this.positionReportGroup;
        }

        @Override
        public String getPositionType() {
            return this.positionType;
        }

        @Override
        public String getPoolEligible() {
            return this.poolEligible;
        }

        @Override
        public int getMaxPoolHeadCount() {
            return this.maxPoolHeadCount;
        }

        @Override
        public String getTenureEligible() {
            return this.tenureEligible;
        }

        @Override
        public List<PositionDepartment.Builder> getDepartmentList() {
            return this.departmentList;
        }

        @Override
        public String getPositionStatus() {
            return this.positionStatus;
        }

        @Override
        public String getContractType() {
            return this.contractType;
        }

        @Override
        public String getRenewEligible() {
            return this.renewEligible;
        }

        @Override
        public DateTime getExpectedEndDateTime() {
            return this.expectedEndDateTime;
        }

        @Override
        public String getReportsToPositionId() {
            return this.reportsToPositionId;
        }

        @Override
        public List<ClassificationQualification.Builder> getRequiredQualList() {
            return this.requiredQualList;
        }

        @Override
        public List<PositionResponsibility.Builder> getPositionResponsibilityList() {
            return this.positionResponsibilityList;
        }

        @Override
        public String getPmPositionClassId() {
            return this.pmPositionClassId;
        }

        @Override
        public List<PositionFunding.Builder> getFundingList() {
            return this.fundingList;
        }

//        @Override
//        public String getInstitution() {
//            return this.institution;
//        }

        @Override
        public int getWorkMonths() {
            return this.workMonths;
        }

        @Override
        public String getTemporary() {
            return this.temporary;
        }

        @Override
        public String getCategory() {
            return this.category;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
        }

        @Override
        public List<PstnFlag.Builder> getFlagList() {
            return this.flagList;
        }

        @Override
        public String getPayStep() {
            return this.payStep;
        }

        @Override
        public String getPositionClass() {
            return this.positionClass;
        }

        @Override
        public String getAppointmentType() {
            return this.appointmentType;
        }

        @Override
        public String getReportsToWorkingTitle() {
            return this.reportsToWorkingTitle;
        }

        @Override
        public String getPrimaryDepartment() {
            return this.primaryDepartment;
        }

        @Override
        public String getProcess() {
            return this.process;
        }

        @Override
        public String getPayGrade() {
            return this.payGrade;
        }

        @Override
        public List<PositionDuty.Builder> getDutyList() {
            return this.dutyList;
        }

        @Override
        public String getContract() {
            return this.contract;
        }

        @Override
        public String getPositionNumber() {
            return this.positionNumber;
        }

        @Override
        public String getHrPositionId() {
            return this.hrPositionId;
        }

        @Override
        public String getDescription() {
            return this.description;
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
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

//        public void setLocation(String location) {
//
//            this.location = location;
//        }

        public void setBenefitsEligible(String benefitsEligible) {

            this.benefitsEligible = benefitsEligible;
        }

        public void setPercentTime(BigDecimal percentTime) {

            this.percentTime = percentTime;
        }

        public void setClassificationTitle(String classificationTitle) {

            this.classificationTitle = classificationTitle;
        }

        public void setSalaryGroup(String salaryGroup) {

            this.salaryGroup = salaryGroup;
        }

        public void setQualificationList(List<PositionQualification.Builder> qualificationList) {

            this.qualificationList = qualificationList;
        }

        public void setReportsToPrincipalId(String reportsToPrincipalId) {

            this.reportsToPrincipalId = reportsToPrincipalId;
        }

        public void setLeaveEligible(String leaveEligible) {

            this.leaveEligible = leaveEligible;
        }

        public void setPositionReportGroup(String positionReportGroup) {

            this.positionReportGroup = positionReportGroup;
        }

        public void setPositionType(String positionType) {

            this.positionType = positionType;
        }

        public void setPoolEligible(String poolEligible) {

            this.poolEligible = poolEligible;
        }

        public void setMaxPoolHeadCount(int maxPoolHeadCount) {

            this.maxPoolHeadCount = maxPoolHeadCount;
        }

        public void setTenureEligible(String tenureEligible) {

            this.tenureEligible = tenureEligible;
        }

        public void setDepartmentList(List<PositionDepartment.Builder> departmentList) {

            this.departmentList = departmentList;
        }

        public void setPositionStatus(String positionStatus) {

            this.positionStatus = positionStatus;
        }

        public void setContractType(String contractType) {

            this.contractType = contractType;
        }

        public void setRenewEligible(String renewEligible) {

            this.renewEligible = renewEligible;
        }

        public void setExpectedEndDateTime(DateTime expectedEndDateTime) {

            this.expectedEndDateTime = expectedEndDateTime;
        }

        public void setReportsToPositionId(String reportsToPositionId) {

            this.reportsToPositionId = reportsToPositionId;
        }

        public void setRequiredQualList(List<ClassificationQualification.Builder> requiredQualList) {

            this.requiredQualList = requiredQualList;
        }

        public void setPositionResponsibilityList(List<PositionResponsibility.Builder> positionResponsibilityList) {

            this.positionResponsibilityList = positionResponsibilityList;
        }

        public void setPmPositionClassId(String pmPositionClassId) {

            this.pmPositionClassId = pmPositionClassId;
        }

        public void setFundingList(List<PositionFunding.Builder> fundingList) {

            this.fundingList = fundingList;
        }

//        public void setInstitution(String institution) {
//
//            this.institution = institution;
//        }

        public void setWorkMonths(int workMonths) {

            this.workMonths = workMonths;
        }

        public void setTemporary(String temporary) {

            this.temporary = temporary;
        }

        public void setCategory(String category) {

            this.category = category;
        }

        public void setLeavePlan(String leavePlan) {

            this.leavePlan = leavePlan;
        }

        public void setFlagList(List<PstnFlag.Builder> flagList) {

            this.flagList = flagList;
        }

        public void setPayStep(String payStep) {

            this.payStep = payStep;
        }

        public void setPositionClass(String positionClass) {
            this.positionClass = positionClass;
        }

        public void setAppointmentType(String appointmentType) {
            this.appointmentType = appointmentType;
        }

        public void setReportsToWorkingTitle(String reportsToWorkingTitle) {
            this.reportsToWorkingTitle = reportsToWorkingTitle;
        }

        public void setPrimaryDepartment(String primaryDepartment) {
            this.primaryDepartment = primaryDepartment;
        }

        public void setProcess(String process) {
            this.process = process;
        }


        public void setPayGrade(String payGrade) {

            this.payGrade = payGrade;
        }

        public void setDutyList(List<PositionDuty.Builder> dutyList) {

            this.dutyList = dutyList;
        }

        public void setContract(String contract) {

            this.contract = contract;
        }

        public void setPositionNumber(String positionNumber) {
        	if (StringUtils.isBlank(positionNumber)) {
                throw new IllegalArgumentException("position number is blank");
            }           
            this.positionNumber = positionNumber;
        }

        public void setHrPositionId(String hrPositionId) {

            this.hrPositionId = hrPositionId;
        }

        public void setDescription(String description) {

            this.description = description;
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
        
        public void setGroupKeyCode(String groupKeyCode) {
        	if (StringUtils.isBlank(groupKeyCode)) {
                throw new IllegalArgumentException("group key code is blank");
            }
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            this.groupKey = groupKey;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "position";
        final static String TYPE_NAME = "PositionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

//        final static String LOCATION = "location";
        final static String BENEFITS_ELIGIBLE = "benefitsEligible";
        final static String PERCENT_TIME = "percentTime";
        final static String CLASSIFICATION_TITLE = "classificationTitle";
        final static String SALARY_GROUP = "salaryGroup";
        final static String QUALIFICATION_LIST = "qualificationList";
        final static String QUALIFICATION = "qualification";
        final static String REPORTS_TO_PRINCIPAL_ID = "reportsToPrincipalId";
        final static String LEAVE_ELIGIBLE = "leaveEligible";
        final static String POSITION_REPORT_GROUP = "positionReportGroup";
        final static String POSITION_TYPE = "positionType";
        final static String POOL_ELIGIBLE = "poolEligible";
        final static String MAX_POOL_HEAD_COUNT = "maxPoolHeadCount";
        final static String TENURE_ELIGIBLE = "tenureEligible";
        final static String DEPARTMENT_LIST = "departmentList";
        final static String DEPARTMENT = "department";
        final static String POSITION_STATUS = "positionStatus";
        final static String CONTRACT_TYPE = "contractType";
        final static String RENEW_ELIGIBLE = "renewEligible";
        final static String EXPECTED_END_DATE_TIME = "expectedEndDateTime";
        final static String REPORTS_TO_POSITION_ID = "reportsToPositionId";
        final static String REQUIRED_QUAL_LIST = "requiredQualList";
        final static String REQUIRED_QUAL = "requiredQual";
        final static String POSITION_RESPONSIBILITY_LIST = "positionResponsibilityList";
        final static String POSITION_RESPONSIBILITY = "positionResponsibility";
        final static String PM_POSITION_CLASS_ID = "pmPositionClassId";
        final static String FUNDING_LIST = "fundingList";
        final static String FUNDING = "funding";
//        final static String INSTITUTION = "institution";
        final static String WORK_MONTHS = "workMonths";
        final static String TEMPORARY = "temporary";
        final static String CATEGORY = "category";
        final static String LEAVE_PLAN = "leavePlan";
        final static String FLAG_LIST = "flagList";
        final static String FLAG = "flag";
        final static String PAY_STEP = "payStep";
        final static String POSITION_CLASS = "positionClass";
        final static String APPOINTMENT_TYPE = "appointmentType";
        final static String REPORTS_TO_WORKING_TITLE = "reportsToWorkingTitle";
        final static String PRIMARY_DEPARTMENT = "primaryDepartment";
        final static String PROCESS = "process";
        final static String PAY_GRADE = "payGrade";
        final static String DUTY_LIST = "dutyList";
        final static String DUTY = "duty";
        final static String CONTRACT = "contract";
        final static String POSITION_NUMBER = "positionNumber";
        final static String HR_POSITION_ID = "hrPositionId";
        final static String DESCRIPTION = "description";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}