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
package org.kuali.kpme.core.paytype;

import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
public class PayTypeKeyBo extends HrBusinessObjectKey<PayTypeBo, PayTypeKeyBo> {

	private static final long serialVersionUID = -7331734503368683324L;
	
	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfPayTypeKey, PayTypeKeyBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<EffectiveKey, PayTypeKeyBo> toBo =
		new ModelObjectUtils.Transformer<EffectiveKey, PayTypeKeyBo>() {
			public PayTypeKeyBo transform(EffectiveKey input) {
				return PayTypeKeyBo.from(input);
			};
		};

	/*
	 * convert bo to immutable
	 *
	 * Can be used with ModelObjectUtils:
	 *
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfPayTypeKeyBo, PayTypeKeyBo.toImmutable);
	 */
	public static final ModelObjectUtils.Transformer<PayTypeKeyBo, EffectiveKey> toImmutable =
		new ModelObjectUtils.Transformer<PayTypeKeyBo, EffectiveKey>() {
			public EffectiveKey transform(PayTypeKeyBo input) {
				return PayTypeKeyBo.to(input);
			};
		};

	@Override
	public PayTypeBo getOwner() {
		return super.getOwner();
	}
	
	@Override
	public void setOwner(PayTypeBo owner) {
		super.setOwner(owner);
	}
	
	public static PayTypeKeyBo from(EffectiveKey im) {
		return commonFromLogic(im, new PayTypeKeyBo());
	}

}