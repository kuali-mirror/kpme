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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.position.PositionBase;
import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.position.funding.PositionFunding;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibility;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

public class Position extends PositionBase {
	private static final long serialVersionUID = 1L;
	
	private List<PositionQualification> qualificationList = new LinkedList<PositionQualification>();
    private List<PositionDuty> dutyList = new LinkedList<PositionDuty>();
    private List<PstnFlag> flagList = new LinkedList<PstnFlag>();
    private List<PositionResponsibility> positionResponsibilityList = new LinkedList<PositionResponsibility>();
    private List<PositionFunding> fundingList = new ArrayList<PositionFunding>();
    
    private String pmPositionClassId;
    
    private List<ClassificationQualification> requiredQualList = new ArrayList<ClassificationQualification>(); 	// read only required qualifications that comes from assiciated Classification

    public List<PositionDuty> getDutyList() {
    	if(CollectionUtils.isEmpty(dutyList) && StringUtils.isNotEmpty(this.getPmPositionClassId())) {
    		List<ClassificationDuty> aList = PmServiceLocator.getClassificationDutyService().getDutyListForClassification(this.getPmPositionClassId());
    		if(CollectionUtils.isNotEmpty(aList)) {
    			List<PositionDuty> pDutyList = new ArrayList<PositionDuty>();
    			// copy basic information from classificaton duty list
    			for(ClassificationDuty aDuty : aList) {
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
    		List<ClassificationFlag> aList = PmServiceLocator.getClassificationFlagService().getFlagListForClassification(this.getPmPositionClassId());
    		if(CollectionUtils.isNotEmpty(aList)) {
    			List<PstnFlag> pFlagList = new ArrayList<PstnFlag>();
    			// copy basic information from classificaton flag list
    			for(ClassificationFlag aFlag : aList) {
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
				List<ClassificationQualification> aList = PmServiceLocator.getClassificationQualService()
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
}
