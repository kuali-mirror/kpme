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
package org.kuali.kpme.pm.position;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.kuali.kpme.core.position.PositionBase;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDutyContract;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlagContract;
import org.kuali.kpme.pm.api.position.PositionContract;
import org.kuali.kpme.pm.api.positiondepartmentaffiliation.PositionDepartmentAffiliationContract;
import org.kuali.kpme.pm.api.positiondepartmentaffiliation.service.PositionDepartmentAffiliationService;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.position.funding.PositionFunding;
import org.kuali.kpme.pm.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibility;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

import com.google.common.collect.ImmutableList;

public class Position extends PositionBase implements PositionContract {
	private static final long serialVersionUID = 1L;
	
    public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Position";
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(PositionBase.CACHE_NAME)
            .add(Position.CACHE_NAME)
            .build();
	
	private List<PositionQualification> qualificationList = new LinkedList<PositionQualification>();
    private List<PositionDuty> dutyList = new LinkedList<PositionDuty>();
    private List<PstnFlag> flagList = new LinkedList<PstnFlag>();
    private List<PositionResponsibility> positionResponsibilityList = new LinkedList<PositionResponsibility>();
    private List<PositionFunding> fundingList = new ArrayList<PositionFunding>();
    private List<PositionDepartment> departmentList = new ArrayList<PositionDepartment>();

    private String institution;
    private String salaryGroup;
    private String pmPositionClassId;
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
    private String reportsTo;
    private Date expectedEndDate;
    private String renewEligible;
    private String temporary;
    private String contract;
    private String contractType;
    
    private String category;		// used to determine what fields should show when editing an existing maint doc
    
    private List<ClassificationQualification> requiredQualList = new ArrayList<ClassificationQualification>(); 	// read only required qualifications that comes from assiciated Classification

    public List<PositionDuty> getDutyList() {
    	if(CollectionUtils.isEmpty(dutyList) && StringUtils.isNotEmpty(this.getPmPositionClassId())) {
    		List<? extends ClassificationDutyContract> aList = PmServiceLocator.getClassificationDutyService().getDutyListForClassification(this.getPmPositionClassId());
    		if(CollectionUtils.isNotEmpty(aList)) {
    			List<PositionDuty> pDutyList = new ArrayList<PositionDuty>();
    			// copy basic information from classificaton duty list
    			for(ClassificationDutyContract aDuty : aList) {
    				PositionDuty pDuty = new PositionDuty();
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

	public List<PositionResponsibility> getPositionResponsibilityList() {
		return positionResponsibilityList;
	}

	public void setPositionResponsibilityList(
			List<PositionResponsibility> positionResponsibilityList) {
		this.positionResponsibilityList = positionResponsibilityList;
	}
	public void setDutyList(List<PositionDuty> dutyList) {
		this.dutyList = dutyList;
	}

	public List<PositionQualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<PositionQualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<PstnFlag> getFlagList() {
		if(CollectionUtils.isEmpty(flagList) && StringUtils.isNotEmpty(this.getPmPositionClassId())) {
    		List<? extends ClassificationFlagContract> aList = PmServiceLocator.getClassificationFlagService().getFlagListForClassification(this.getPmPositionClassId());
    		if(CollectionUtils.isNotEmpty(aList)) {
    			List<PstnFlag> pFlagList = new ArrayList<PstnFlag>();
    			// copy basic information from classificaton flag list
    			for(ClassificationFlagContract aFlag : aList) {
    				PstnFlag pFlag = new PstnFlag();
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

	public void setFlagList(List<PstnFlag> flagList) {
		this.flagList = flagList;
	}
	
	public String getPmPositionClassId() {
		return pmPositionClassId;
	}

	public void setPmPositionClassId(String id) {
		this.pmPositionClassId = id;
	}
	
	public List<ClassificationQualification> getRequiredQualList() {
		if(StringUtils.isNotEmpty(this.getPmPositionClassId())) {
			// when Position Classification Id is changed, change the requiredQualList with it
			if(CollectionUtils.isEmpty(requiredQualList) ||
					(CollectionUtils.isNotEmpty(requiredQualList) 
							&& !requiredQualList.get(0).getPmPositionClassId().equals(this.getPmPositionClassId()))) {
				List<ClassificationQualification> aList = (List<ClassificationQualification>)PmServiceLocator.getClassificationQualService()
						.getQualListForClassification(this.getPmPositionClassId());
				if(CollectionUtils.isNotEmpty(aList))
					this.setRequiredQualList(aList);
			} else {
				
			}
		}
 		return requiredQualList;
	}
	
	public void setRequiredQualList(List<ClassificationQualification> aList) {
			requiredQualList = aList;
	}

	public List<PositionFunding> getFundingList() {
		return fundingList;
	}

	public void setFundingList(List<PositionFunding> fundingList) {
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

    public List<PositionDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<PositionDepartment> departmentList) {
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

	public String getPrimaryDepartment() {

		if (this.departmentList != null && this.departmentList.size() > 0) {

			PositionDepartmentAffiliationService pdaService = PmServiceLocator.getPositionDepartmentAffiliationService();
			for (PositionDepartment department: this.departmentList) {
				
				PositionDepartmentAffiliationContract pda = pdaService.getPositionDepartmentAffiliationByType(department.getPositionDeptAffl());
				if (pda.isPrimaryIndicator()) {
					primaryDepartment = department.getDepartment();
					break;
				} 
			}
		}
		
		return primaryDepartment;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
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
	
}
