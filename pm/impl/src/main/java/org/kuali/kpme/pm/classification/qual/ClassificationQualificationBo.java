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
package org.kuali.kpme.pm.classification.qual;


import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.PMConstants;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualificationContract;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrTypeContract;
import org.kuali.kpme.pm.classification.ClassificationDerived;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.util.ObjectUtils;

public class ClassificationQualificationBo extends ClassificationDerived implements ClassificationQualificationContract {
	private static final long serialVersionUID = 1L;
	
	private String pmClassificationQualificationId;
	private String qualificationType;
	private String typeValue;		// for GUI only
	private String qualifier;
	private String qualificationValue;
	private String displayOrder;
	private transient String qualifierString;
	
	public String getQualifierString() {
		String qualifierString = qualificationValue;
		if (qualifier != null) {
			PstnQlfrTypeContract qualifierType = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeByType(qualificationType);
			
			if (ObjectUtils.isNotNull(qualifierType)
					&& qualifierType.getTypeValue().equals(PMConstants.PSTN_QLFR_NUMBER)) {
				if (qualifier.equals(PMConstants.PSTN_CLSS_QLFR_VALUE.GREATER_EQUAL)) {
					qualifierString = PMConstants.PSTN_CLSS_QLFR_STRING_VALUE.GREATER_EQUAL + " " + qualificationValue;
				} else if (qualifier.equals(PMConstants.PSTN_CLSS_QLFR_VALUE.GREATER_THAN)) {
					qualifierString = PMConstants.PSTN_CLSS_QLFR_STRING_VALUE.GREATER_THAN + " " + qualificationValue;
				} else if (qualifier.equals(PMConstants.PSTN_CLSS_QLFR_VALUE.LESS_EQUAL)) {
					qualifierString = PMConstants.PSTN_CLSS_QLFR_STRING_VALUE.LESS_EQUAL + " " + qualificationValue;
				} else if (qualifier.equals(PMConstants.PSTN_CLSS_QLFR_VALUE.LESS_THAN)) {
					qualifierString = PMConstants.PSTN_CLSS_QLFR_STRING_VALUE.LESS_THAN + " " + qualificationValue;
				} else if (qualifier.equals(PMConstants.PSTN_CLSS_QLFR_VALUE.EQUAL)) {
					qualifierString = PMConstants.PSTN_CLSS_QLFR_STRING_VALUE.EQUAL + " " + qualificationValue;
				} else {
					qualifierString = qualifier + " " + qualificationValue;
				}
			}
		}
		return qualifierString;
	}

	public void setQualifierString(String qualifierString) {
		this.qualifierString = qualifierString;
	}
		
	public String getQualificationType() {
		return qualificationType;
	}

	public void setQualificationType(String qualificationType) {
		this.qualificationType = qualificationType;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getQualificationValue() {
		return qualificationValue;
	}

	public void setQualificationValue(String qualificationValue) {
		this.qualificationValue = qualificationValue;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getPmClassificationQualificationId() {
		return pmClassificationQualificationId;
	}

	public void setPmClassificationQualificationId(
			String pmClassificationQualificationId) {
		this.pmClassificationQualificationId = pmClassificationQualificationId;
	}

	public String getTypeValue() {
		if(StringUtils.isNotEmpty(this.getQualificationType())) {
			PstnQlfrTypeContract aTypeObj = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(this.getQualificationType());
			if(aTypeObj != null) {
				return aTypeObj.getTypeValue();
			}
		}
		return "";
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public static ClassificationQualificationBo from(ClassificationQualification im) {

		if (im == null) {
			return null;
		}

		ClassificationQualificationBo classificationQualificationBo = new ClassificationQualificationBo();

		classificationQualificationBo.setDisplayOrder(im.getDisplayOrder());
		classificationQualificationBo.setObjectId(im.getObjectId());
		classificationQualificationBo.setPmClassificationQualificationId(im.getPmClassificationQualificationId());
		classificationQualificationBo.setPmPositionClassId(im.getPmPositionClassId());
		classificationQualificationBo.setQualificationType(im.getQualificationType());
		classificationQualificationBo.setQualificationValue(im.getQualificationValue());
		classificationQualificationBo.setQualifier(im.getQualifier());
		classificationQualificationBo.setVersionNumber(im.getVersionNumber());
		
		return classificationQualificationBo;

	}

	public static ClassificationQualification to(ClassificationQualificationBo bo) {
		if (bo == null) {
			return null;
		}
		return ClassificationQualification.Builder.create(bo).build();
	}
	
	public static final ModelObjectUtils.Transformer<ClassificationQualificationBo, ClassificationQualification> toImmutable = new ModelObjectUtils.Transformer<ClassificationQualificationBo, ClassificationQualification>() {
		public ClassificationQualification transform(ClassificationQualificationBo input) {
			return ClassificationQualificationBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<ClassificationQualification, ClassificationQualificationBo> toBo = new ModelObjectUtils.Transformer<ClassificationQualification, ClassificationQualificationBo>() {
		public ClassificationQualificationBo transform(ClassificationQualification input) {
			return ClassificationQualificationBo.from(input);
		};
	};

	@Override
	public String getId() {
		return this.getPmClassificationQualificationId();
	}

	@Override
	public void setId(String id) {
		this.setPmClassificationQualificationId(id);
	}
}
