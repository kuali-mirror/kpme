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

import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroupKey;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroupKeyContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionReportGroupKeyBo extends HrBusinessObjectKey<PositionReportGroupBo, PositionReportGroupKeyBo> implements PositionReportGroupKeyContract {

	private static final long serialVersionUID = 3035597915412860604L;
	
	// the foreign key linking back to the owner
	private String pmPositionReportGroupId;

	// the PK for the table corresponding to this BO
	private String positionReportGroupKeyId;
	
	
	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfPositionReportGroupKey, PositionReportGroupKeyBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<PositionReportGroupKey, PositionReportGroupKeyBo> toBo =
		new ModelObjectUtils.Transformer<PositionReportGroupKey, PositionReportGroupKeyBo>() {
			public PositionReportGroupKeyBo transform(PositionReportGroupKey input) {
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
	public static final ModelObjectUtils.Transformer<PositionReportGroupKeyBo, PositionReportGroupKey> toImmutable =
		new ModelObjectUtils.Transformer<PositionReportGroupKeyBo, PositionReportGroupKey>() {
			public PositionReportGroupKey transform(PositionReportGroupKeyBo input) {
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

	@Override
	public String getPmPositionReportGroupId() {
		return pmPositionReportGroupId;
	}

	public void setPmPositionReportGroupId(String pmPositionReportGroupId) {
		this.pmPositionReportGroupId = pmPositionReportGroupId;
	}
	
	public String getId() {
		return this.getPositionReportGroupKeyId();
	}
	
	
	public void setId(String id) {
		this.setPositionReportGroupKeyId(id);
	}
	

	@Override
	public String getPositionReportGroupKeyId() {
		return this.positionReportGroupKeyId;
	}	

	public void setPositionReportGroupKeyId(String positionReportGroupKeyId) {
		this.positionReportGroupKeyId = positionReportGroupKeyId;
	}
	
	public static PositionReportGroupKeyBo from(PositionReportGroupKey im) {
		if (im == null) {
			return null;
		}
		PositionReportGroupKeyBo prgk = new PositionReportGroupKeyBo();
		prgk.setOwner(PositionReportGroupBo.from(im.getOwner()));
		prgk.setPositionReportGroupKeyId(im.getPositionReportGroupKeyId());
		prgk.setPmPositionReportGroupId(im.getPmPositionReportGroupId());
		prgk.setVersionNumber(im.getVersionNumber());
		prgk.setObjectId(im.getObjectId());
		prgk.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
		prgk.setGroupKeyCode(im.getGroupKeyCode());
		return prgk;
	}
	

	public static PositionReportGroupKey to(PositionReportGroupKeyBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionReportGroupKey.Builder.create(bo).build();
	}



}