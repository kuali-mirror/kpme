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
package org.kuali.kpme.pm.classification.duty;

import java.math.BigDecimal;

import org.kuali.kpme.pm.api.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDutyContract;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.ClassificationDerived;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class ClassificationDutyBo extends ClassificationDerived implements ClassificationDutyContract {
	
	private static final long serialVersionUID = -3553603419139534148L;
	
	private String pmDutyId;
	private String name;
	private String description;
	private BigDecimal percentage;
	
	
	public String getPmDutyId() {
		return pmDutyId;
	}
	public void setPmDutyId(String pmDutyId) {
		this.pmDutyId = pmDutyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	@Override
	public String getId() {
		return this.getPmDutyId();
	}
	@Override
	public void setId(String id) {
		this.setPmDutyId(id);
	}
	
		public static ClassificationDutyBo from(ClassificationDuty im) {
				if (im == null) {
					return null;
				}
				ClassificationDutyBo classificationDutyBo = new ClassificationDutyBo();
		
				
				classificationDutyBo.setPmDutyId(im.getPmDutyId());
				classificationDutyBo.setName(im.getName());
				classificationDutyBo.setDescription(im.getDescription());
				classificationDutyBo.setPercentage(im.getPercentage());
				
				classificationDutyBo.setPmPositionClassId(im.getPmPositionClassId());
		        classificationDutyBo.setVersionNumber(im.getVersionNumber());
		        classificationDutyBo.setObjectId(im.getObjectId());
				
				return classificationDutyBo;
			}
		
			public static ClassificationDuty to(ClassificationDutyBo bo) {
				if (bo == null) {
					return null;
				}
				return ClassificationDuty.Builder.create(bo).build();
			}
		
			public static final ModelObjectUtils.Transformer<ClassificationDutyBo, ClassificationDuty> toImmutable = new ModelObjectUtils.Transformer<ClassificationDutyBo, ClassificationDuty>() {
				public ClassificationDuty transform(ClassificationDutyBo input) {
					return ClassificationDutyBo.to(input);
				};
			};
		
			public static final ModelObjectUtils.Transformer<ClassificationDuty, ClassificationDutyBo> toBo = new ModelObjectUtils.Transformer<ClassificationDuty, ClassificationDutyBo>() {
				public ClassificationDutyBo transform(ClassificationDuty input) {
					return ClassificationDutyBo.from(input);
				};
			};
			
}
