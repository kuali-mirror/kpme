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
package org.kuali.kpme.pm.positionreportgroup;

import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionReportGroupKeyBo extends HrBusinessObjectKey<PositionReportGroupBo, PositionReportGroupKeyBo> {

	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfPositionReportGroupKey, PositionReportGroupKeyBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<EffectiveKey, PositionReportGroupKeyBo> toBo =
		new ModelObjectUtils.Transformer<EffectiveKey, PositionReportGroupKeyBo>() {
			public PositionReportGroupKeyBo transform(EffectiveKey input) {
				return PositionReportGroupKeyBo.from(input);
			};
		};

	/*
	 * convert bo to immutable
	 *
	 * Can be used with ModelObjectUtils:
	 *
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfPositionReportGroupKeyBo, PositionReportGroupKeyBo.toImmutable);
	 */
	public static final ModelObjectUtils.Transformer<PositionReportGroupKeyBo, EffectiveKey> toImmutable =
		new ModelObjectUtils.Transformer<PositionReportGroupKeyBo, EffectiveKey>() {
			public EffectiveKey transform(PositionReportGroupKeyBo input) {
				return PositionReportGroupKeyBo.to(input);
			};
		};

	@Override
	public PositionReportGroupBo getOwner() {
		return super.getOwner();
	}
	
	@Override
	public void setOwner(PositionReportGroupBo owner) {
		super.setOwner(owner);
	}
	
	public static PositionReportGroupKeyBo from(EffectiveKey im) {
		return commonFromLogic(im, new PositionReportGroupKeyBo());
	}

}