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
package org.kuali.kpme.core.salarygroup;

import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class SalaryGroupKeyBo extends HrBusinessObjectKey<SalaryGroupBo, SalaryGroupKeyBo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3035597915412860618L;
	
	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfSalaryGroupKey, SalaryGroupKeyBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<EffectiveKey, SalaryGroupKeyBo> toBo =
		new ModelObjectUtils.Transformer<EffectiveKey, SalaryGroupKeyBo>() {
			public SalaryGroupKeyBo transform(EffectiveKey input) {
				return SalaryGroupKeyBo.from(input);
			};
		};

	/*
	 * convert bo to immutable
	 *
	 * Can be used with ModelObjectUtils:
	 *
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfSalaryGroupKeyBo, SalaryGroupKeyBo.toImmutable);
	 */
	public static final ModelObjectUtils.Transformer<SalaryGroupKeyBo, EffectiveKey> toImmutable =
		new ModelObjectUtils.Transformer<SalaryGroupKeyBo, EffectiveKey>() {
			public EffectiveKey transform(SalaryGroupKeyBo input) {
				return SalaryGroupKeyBo.to(input);
			};
		};

	@Override
	public SalaryGroupBo getOwner() {
		return super.getOwner();
	}
	
	@Override
	public void setOwner(SalaryGroupBo owner) {
		super.setOwner(owner);
	}
	
	public static SalaryGroupKeyBo from(EffectiveKey im) {
		return commonFromLogic(im, new SalaryGroupKeyBo());
	}

}
