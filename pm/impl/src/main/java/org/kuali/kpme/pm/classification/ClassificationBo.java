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
package org.kuali.kpme.pm.classification;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrKeyedSetBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.kpme.pm.api.classification.ClassificationContract;
import org.kuali.kpme.pm.classification.duty.ClassificationDutyBo;
import org.kuali.kpme.pm.classification.flag.ClassificationFlagBo;
import org.kuali.kpme.pm.classification.qual.ClassificationQualificationBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ClassificationBo extends HrKeyedSetBusinessObject<ClassificationBo, ClassificationGroupKeyBo> implements ClassificationContract {

    private static final long serialVersionUID = 3012823278753071180L;


    private static final String CLASSIFICATION_TITLE = "classificationTitle";
	private static final String POSITION_CLASS = "positionClass";
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
											    .add(POSITION_CLASS)
											    .add(CLASSIFICATION_TITLE)
											    .build();
	private String pmPositionClassId;
	private String positionClass;
	private String classificationTitle;
	// salary group fields
	private String salaryGroup;
    private String payGrade;
	private BigDecimal percentTime;
	private String benefitsEligible;
	private String leaveEligible;
	private String leavePlan;
	private String positionReportGroup;
	private String positionType;
	private String poolEligible;
	private String tenureEligible;
	private String externalReference;
	
	private List<ClassificationQualificationBo> qualificationList = new LinkedList<ClassificationQualificationBo>(); 
	private List<ClassificationDutyBo> dutyList = new LinkedList<ClassificationDutyBo>(); 
	private List<ClassificationFlagBo> flagList = new LinkedList<ClassificationFlagBo>(); 
	
	// list of position flags, need to add flag maint section to Position maint doc
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(POSITION_CLASS, this.getPositionClass())
				.put(CLASSIFICATION_TITLE, this.getClassificationTitle())
				.build();
	}
	
	@Override
	public String getId() {
		return this.getPmPositionClassId();
	}

	@Override
	public void setId(String id) {
		this.setPmPositionClassId(id);
	}

	@Override
	protected String getUniqueKey() {
		return this.getPositionClass();
	}

	public String getPositionClass() {
		return positionClass;
	}

	public void setPositionClass(String positionClass) {
		this.positionClass = positionClass;
	}

	public String getClassificationTitle() {
		return classificationTitle;
	}

	public void setClassificationTitle(String classificationTitle) {
		this.classificationTitle = classificationTitle;
	}

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

	public String getSalaryGroup() {
		return salaryGroup;
	}

	public void setSalaryGroup(String salaryGroup) {
		this.salaryGroup = salaryGroup;
	}

    public String getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
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

	public String getTenureEligible() {
		return tenureEligible;
	}

	public void setTenureEligible(String tenureEligible) {
		this.tenureEligible = tenureEligible;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public List<ClassificationQualificationBo> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(
			List<ClassificationQualificationBo> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public String getPmPositionClassId() {
		return pmPositionClassId;
	}

	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}

	public List<ClassificationDutyBo> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<ClassificationDutyBo> dutyList) {
		this.dutyList = dutyList;
	}

	public List<ClassificationFlagBo> getFlagList() {
		return flagList;
	}

	public void setFlagList(List<ClassificationFlagBo> flagList) {
		this.flagList = flagList;
	}
	
	public static ClassificationBo from(Classification im) {
				if (im == null) {
					return null;
				}
				ClassificationBo classificationBo = new ClassificationBo();
				classificationBo.setPmPositionClassId(im.getPmPositionClassId());
				classificationBo.setVersionNumber(im.getVersionNumber());
				classificationBo.setObjectId(im.getObjectId());
		
				classificationBo.setPoolEligible(im.getPoolEligible());
				classificationBo.setPositionType(im.getPositionType());
				classificationBo.setPositionReportGroup(im.getPositionReportGroup());
				classificationBo.setLeaveEligible(im.getLeaveEligible());
				classificationBo.setBenefitsEligible(im.getBenefitsEligible());
				classificationBo.setClassificationTitle(im.getClassificationTitle());
				
				classificationBo.setPositionClass(im.getPositionClass());
				classificationBo.setPercentTime(im.getPercentTime());
				classificationBo.setSalaryGroup(im.getSalaryGroup());
				classificationBo.setTenureEligible(im.getTenureEligible());
				classificationBo.setExternalReference(im.getExternalReference());

                List<ClassificationQualificationBo> qualificationList = ModelObjectUtils.transform(im.getQualificationList(), ClassificationQualificationBo.toBo);
                ClassificationQualificationBo.setOwnerOfDerivedCollection(classificationBo, qualificationList);
                classificationBo.setQualificationList(qualificationList);


                List<ClassificationFlagBo> flagList = ModelObjectUtils.transform(im.getFlagList(), ClassificationFlagBo.toBo);
                ClassificationFlagBo.setOwnerOfDerivedCollection(classificationBo, flagList);
				classificationBo.setFlagList(flagList);


                List<ClassificationDutyBo> dutyList = ModelObjectUtils.transform(im.getDutyList(), ClassificationDutyBo.toBo);
                ClassificationDutyBo.setOwnerOfDerivedCollection(classificationBo, dutyList);
                classificationBo.setDutyList(dutyList);
        
				classificationBo.setLeavePlan(im.getLeavePlan());
				classificationBo.setPayGrade(im.getPayGrade());


				classificationBo.setId(im.getId());
				classificationBo.setEffectiveLocalDate(im.getEffectiveLocalDate());

                Set<ClassificationGroupKeyBo> effectiveKeyBoSet = ModelObjectUtils.transformSet(im.getEffectiveKeySet(), ClassificationGroupKeyBo.toBo);

                ClassificationGroupKeyBo.setOwnerOfDerivedCollection(classificationBo, effectiveKeyBoSet);
                if (effectiveKeyBoSet != null) {
                    classificationBo.setEffectiveKeyList(new ArrayList<ClassificationGroupKeyBo>(effectiveKeyBoSet));
                }

                copyCommonFields(classificationBo, im);
				return classificationBo;
			}
		
			public static Classification to(ClassificationBo bo) {
				if (bo == null) {
					return null;
				}
				return Classification.Builder.create(bo).build();
			}
		
			public static final ModelObjectUtils.Transformer<ClassificationBo, Classification> toImmutable = new ModelObjectUtils.Transformer<ClassificationBo, Classification>() {
				public Classification transform(ClassificationBo input) {
					return ClassificationBo.to(input);
				};
			};
		
			public static final ModelObjectUtils.Transformer<Classification, ClassificationBo> toBo = new ModelObjectUtils.Transformer<Classification, ClassificationBo>() {
				public ClassificationBo transform(Classification input) {
					return ClassificationBo.from(input);
				};
			};
		

}