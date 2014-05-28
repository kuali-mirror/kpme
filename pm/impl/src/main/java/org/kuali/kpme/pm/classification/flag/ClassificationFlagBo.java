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
package org.kuali.kpme.pm.classification.flag;

import java.util.List;

import org.kuali.kpme.pm.api.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlagContract;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.ClassificationDerived;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class ClassificationFlagBo extends ClassificationDerived implements ClassificationFlagContract {
	
	private static final long serialVersionUID = -6195340711898188423L;
	
	private String pmFlagId;
	private String category;
	//private String names;
	private List<String> names; // KPME-2360/2958
	
	public String getPmFlagId() {
		return pmFlagId;
	}
	public void setPmFlagId(String pmFlagId) {
		this.pmFlagId = pmFlagId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	@Override
	public String getId() {
		return this.getPmFlagId();
	}
	@Override
	public void setId(String id) {
		this.setPmFlagId(id);
	}
	
		public static ClassificationFlagBo from(ClassificationFlag im) {
				if (im == null) {
					return null;
				}
				ClassificationFlagBo classificationFlagBo = new ClassificationFlagBo();
		
				
				classificationFlagBo.setPmFlagId(im.getPmFlagId());
				classificationFlagBo.setCategory(im.getCategory());
				classificationFlagBo.setNames(im.getNames());
				classificationFlagBo.setPmPositionClassId(im.getPmPositionClassId());
		        classificationFlagBo.setVersionNumber(im.getVersionNumber());
		        classificationFlagBo.setObjectId(im.getObjectId());
				
				return classificationFlagBo;
			}
		
			public static ClassificationFlag to(ClassificationFlagBo bo) {
				if (bo == null) {
					return null;
				}
				return ClassificationFlag.Builder.create(bo).build();
			}
		
			public static final ModelObjectUtils.Transformer<ClassificationFlagBo, ClassificationFlag> toImmutable = new ModelObjectUtils.Transformer<ClassificationFlagBo, ClassificationFlag>() {
				public ClassificationFlag transform(ClassificationFlagBo input) {
					return ClassificationFlagBo.to(input);
				};
			};
		
			public static final ModelObjectUtils.Transformer<ClassificationFlag, ClassificationFlagBo> toBo = new ModelObjectUtils.Transformer<ClassificationFlag, ClassificationFlagBo>() {
				public ClassificationFlagBo transform(ClassificationFlag input) {
					return ClassificationFlagBo.from(input);
				};
			};
			
	
}
