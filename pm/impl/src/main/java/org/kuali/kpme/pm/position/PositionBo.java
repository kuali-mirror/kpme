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
package org.kuali.kpme.pm.position;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.position.PositionBaseContract;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.position.PositionBaseBo;
import org.kuali.kpme.pm.api.classification.ClassificationContract;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDutyContract;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlagContract;
import org.kuali.kpme.pm.api.position.PositionContract;
import org.kuali.kpme.pm.classification.qual.ClassificationQualificationBo;
import org.kuali.kpme.pm.position.funding.PositionFundingBo;
import org.kuali.kpme.pm.positiondepartment.PositionDepartmentBo;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibilityBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;

import com.google.common.collect.ImmutableList;
import org.kuali.rice.core.api.util.Truth;

public class PositionBo extends PositionBaseBo implements PositionContract {
	private static final long serialVersionUID = 1L;
	
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(PositionBaseContract.CACHE_NAME)
            .add(PositionContract.CACHE_NAME)
            .build();
	
	private List<PositionQualificationBo> qualificationList = new LinkedList<PositionQualificationBo>();
    private List<PositionDutyBo> dutyList = new LinkedList<PositionDutyBo>();
    private List<PstnFlagBo> flagList = new LinkedList<PstnFlagBo>();
    private List<PositionResponsibilityBo> positionResponsibilityList = new LinkedList<PositionResponsibilityBo>();
    private List<PositionFundingBo> fundingList = new ArrayList<PositionFundingBo>();
    private List<PositionDepartmentBo> departmentList = new ArrayList<PositionDepartmentBo>();

    private String institution;
    private String salaryGroup;
    private String pmPositionClassId;
    private transient String positionClass;
    private String classificationTitle;

    private BigDecimal percentTime;
    private int workMonths;
    private String benefitsEligible;
    private String leaveEligible;
    private String leavePlan;
    private String positionReportGroup;
    private String positionType;
    private String poolEligible;
    private int maxPoolHeadCount;
    private String tenureEligible;
    
    // KPME-3016
    private String location; 
    private String process;
    private String positionStatus;
    private String primaryDepartment;
    private String appointmentType;
    private String reportsToPositionId;
    private String reportsToPrincipalId;
    private Date expectedEndDate;
    private String renewEligible;
    private String temporary;
    private String contract;
    private String contractType;
    private String payGrade;
    private String payStep;
    
    private String category;		// used to determine what fields should show when editing an existing maint doc
    
    private String reportsToWorkingTitle; // KPME-3269
    
    private List<ClassificationQualificationBo> requiredQualList = new ArrayList<ClassificationQualificationBo>(); 	// read only required qualifications that comes from assiciated Classification
    /*private transient boolean displayQualifications;
    private transient boolean displayDuties;
    private transient boolean displayFlags;
    private transient boolean displayResponsibility;
    private transient boolean displayFunding;
    private transient boolean displayAdHocRecipients;*/
   
    public List<PositionDutyBo> getDutyList() {
    	if(CollectionUtils.isEmpty(dutyList) && StringUtils.isNotEmpty(this.getPmPositionClassId())) {
    		List<? extends ClassificationDutyContract> aList = PmServiceLocator.getClassificationDutyService().getDutyListForClassification(this.getPmPositionClassId());
    		if(CollectionUtils.isNotEmpty(aList)) {
    			List<PositionDutyBo> pDutyList = new ArrayList<PositionDutyBo>();
    			// copy basic information from classificaton duty list
    			for(ClassificationDutyContract aDuty : aList) {
    				PositionDutyBo pDuty = new PositionDutyBo();
    				pDuty.setName(aDuty.getName());
    				pDuty.setDescription(aDuty.getDescription());
    				pDuty.setPercentage(aDuty.getPercentage());
    				pDuty.setPmDutyId(null);
    				pDuty.setHrPositionId(this.getHrPositionId());
    				pDutyList.add(pDuty);
    			}
    			this.setDutyList(pDutyList);
    		}
    	}
		return dutyList;
	}

	public List<PositionResponsibilityBo> getPositionResponsibilityList() {
		return positionResponsibilityList;
	}

	public void setPositionResponsibilityList(
			List<PositionResponsibilityBo> positionResponsibilityList) {
		this.positionResponsibilityList = positionResponsibilityList;
	}
	public void setDutyList(List<PositionDutyBo> dutyList) {
		this.dutyList = dutyList;
	}

	public List<PositionQualificationBo> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<PositionQualificationBo> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<PstnFlagBo> getFlagList() {
		if(CollectionUtils.isEmpty(flagList) && StringUtils.isNotEmpty(this.getPmPositionClassId())) {
    		List<? extends ClassificationFlagContract> aList = PmServiceLocator.getClassificationFlagService().getFlagListForClassification(this.getPmPositionClassId());
    		if(CollectionUtils.isNotEmpty(aList)) {
    			List<PstnFlagBo> pFlagList = new ArrayList<PstnFlagBo>();
    			// copy basic information from classificaton flag list
    			for(ClassificationFlagContract aFlag : aList) {
    				PstnFlagBo pFlag = new PstnFlagBo();
    				pFlag.setCategory(aFlag.getCategory());
    				pFlag.setNames(aFlag.getNames());
    				pFlag.setPmFlagId(null);
    				pFlag.setHrPositionId(this.getHrPositionId());
    				pFlagList.add(pFlag);
    			}
    			this.setFlagList(pFlagList);
    		}
		}
		return flagList;
	}

	public void setFlagList(List<PstnFlagBo> flagList) {
		this.flagList = flagList;
	}
	
	public String getPmPositionClassId() {
		return pmPositionClassId;
	}

	public void setPmPositionClassId(String id) {
		this.pmPositionClassId = id;
	}

    public String getPositionClass() {
        if (StringUtils.isBlank(positionClass) && StringUtils.isNotBlank(pmPositionClassId)) {
            ClassificationContract classification = PmServiceLocator.getClassificationService().getClassificationById(this.pmPositionClassId);
            positionClass = classification != null ? classification.getPositionClass() : null;
        }

        return positionClass;
    }

    public void setPositionClass(String positionClass) {
        this.positionClass = positionClass;
    }

    public List<ClassificationQualificationBo> getRequiredQualList() {
		if(StringUtils.isNotEmpty(this.getPmPositionClassId())) {
			// when Position Classification Id is changed, change the requiredQualList with it
			if(CollectionUtils.isEmpty(requiredQualList) ||
					(CollectionUtils.isNotEmpty(requiredQualList) 
							&& !requiredQualList.get(0).getPmPositionClassId().equals(this.getPmPositionClassId()))) {
				List<ClassificationQualificationBo> aList = (List<ClassificationQualificationBo>)PmServiceLocator.getClassificationQualService()
						.getQualListForClassification(this.getPmPositionClassId());
				if(CollectionUtils.isNotEmpty(aList))
					this.setRequiredQualList(aList);
			} else {
				
			}
		}
 		return requiredQualList;
	}
	
	public void setRequiredQualList(List<ClassificationQualificationBo> aList) {
			requiredQualList = aList;
	}

	public List<PositionFundingBo> getFundingList() {
		return fundingList;
	}

	public void setFundingList(List<PositionFundingBo> fundingList) {
		this.fundingList = fundingList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSalaryGroup() {
        return salaryGroup;
    }

    public void setSalaryGroup(String salaryGroup) {
        this.salaryGroup = salaryGroup;
    }

    public String getClassificationTitle() {
        return classificationTitle;
    }

    public void setClassificationTitle(String classificationTitle) {
        this.classificationTitle = classificationTitle;
    }


    public BigDecimal getPercentTime() {
        return percentTime;
    }

    public void setPercentTime(BigDecimal percentTime) {
        this.percentTime = percentTime;
    }

    public String getBenefitsEligible() {
        return benefitsEligible;
    }

    public void setBenefitsEligible(String benefitsEligible) {
        this.benefitsEligible = benefitsEligible;
    }

    public String getLeaveEligible() {
        return leaveEligible;
    }

    public void setLeaveEligible(String leaveEligible) {
        this.leaveEligible = leaveEligible;
    }

    public String getLeavePlan() {
        return leavePlan;
    }

    public void setLeavePlan(String leavePlan) {
        this.leavePlan = leavePlan;
    }

    public String getPositionReportGroup() {
        return positionReportGroup;
    }

    public void setPositionReportGroup(String positionReportGroup) {
        this.positionReportGroup = positionReportGroup;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPoolEligible() {
        return poolEligible;
    }

    public void setPoolEligible(String poolEligible) {
        this.poolEligible = poolEligible;
    }

    public int getMaxPoolHeadCount() {
        return maxPoolHeadCount;
    }

    public void setMaxPoolHeadCount(int maxPoolHeadCount) {
        this.maxPoolHeadCount = maxPoolHeadCount;
    }

    public String getTenureEligible() {
        return tenureEligible;
    }

    public void setTenureEligible(String tenureEligible) {
        this.tenureEligible = tenureEligible;
    }

    public int getWorkMonths() {
        return workMonths;
    }

    public void setWorkMonths(int workMonths) {
        this.workMonths = workMonths;
    }

    public List<PositionDepartmentBo> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<PositionDepartmentBo> departmentList) {
        this.departmentList = departmentList;
    }

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

    public void setPrimaryDepartment () {

    }
	public String getPrimaryDepartment() {

		if (this.primaryDepartment == null && this.departmentList != null && this.departmentList.size() > 0) {
			for (PositionDepartmentBo department: this.departmentList) {
				DepartmentAffiliation pda = department.getDeptAfflObj();
				if (pda.isPrimaryIndicator()) {
					primaryDepartment = department.getDepartment();
					break;
				} 
			}
		}
		
		return primaryDepartment;
	}

    public void setPrimaryDepartment (String primaryDepartment) {
        this.primaryDepartment = primaryDepartment;
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReportsToPositionId() {
		return reportsToPositionId;
	}

	public void setReportsToPositionId(String reportsToPositionId) {
		this.reportsToPositionId = reportsToPositionId;
	}

	public String getReportsToPrincipalId() {
		return reportsToPrincipalId;
	}

	public void setReportsToPrincipalId(String reportsToPrincipalId) {
		this.reportsToPrincipalId = reportsToPrincipalId;
	}

	public Date getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndDate(Date expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	public String getRenewEligible() {
		return renewEligible;
	}

	public void setRenewEligible(String renewEligible) {
		this.renewEligible = renewEligible;
	}

	public String getTemporary() {
		return temporary;
	}

	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public String getPayStep() {
		return payStep;
	}

	public void setPayStep(String payStep) {
		this.payStep = payStep;
	}

	public String getReportsToWorkingTitle() {
		return reportsToWorkingTitle;
	}

	public void setReportsToWorkingTitle(String reportsToWorkingTitle) {
		this.reportsToWorkingTitle = reportsToWorkingTitle;
	}

	public boolean isDisplayQualifications() {
        String status = ConfigContext.getCurrentContextConfig().getProperty("kpme.pm.position.display.qualifications");
        return Truth.strToBooleanIgnoreCase(status, Boolean.FALSE);
	}


	public boolean isDisplayDuties() {
        String status = ConfigContext.getCurrentContextConfig().getProperty("kpme.pm.position.display.duties");
        return Truth.strToBooleanIgnoreCase(status, Boolean.FALSE);
	}

	public boolean isDisplayFlags() {
        String status = ConfigContext.getCurrentContextConfig().getProperty("kpme.pm.position.display.flags");
        return Truth.strToBooleanIgnoreCase(status, Boolean.FALSE);
	}


	public boolean isDisplayResponsibility() {
        String status = ConfigContext.getCurrentContextConfig().getProperty("kpme.pm.position.display.responsibility");
        return Truth.strToBooleanIgnoreCase(status, Boolean.FALSE);
	}

	public boolean isDisplayFunding() {
        String status = ConfigContext.getCurrentContextConfig().getProperty("kpme.pm.position.display.funding");
        return Truth.strToBooleanIgnoreCase(status, Boolean.FALSE);
	}


	public boolean isDisplayAdHocRecipients() {
        String status = ConfigContext.getCurrentContextConfig().getProperty("kpme.pm.position.display.adhocrecipients");
        return Truth.strToBooleanIgnoreCase(status, Boolean.FALSE);
	}

	@Override
	public DateTime getExpectedEndDateTime() {
		DateTime retVal = null;
		if(getExpectedEndDate() != null) {
			retVal = new DateTime(getExpectedEndDate());
		}
		return retVal;
	}
}