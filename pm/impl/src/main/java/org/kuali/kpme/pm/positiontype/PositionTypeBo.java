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
package org.kuali.kpme.pm.positiontype;

import java.sql.Timestamp;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.pm.api.positiontype.PositionType;
import org.kuali.kpme.pm.api.positiontype.PositionTypeContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionTypeBo extends HrKeyedBusinessObject implements
		PositionTypeContract {
	
	public static final ModelObjectUtils.Transformer<PositionTypeBo, PositionType> toImmutable = new ModelObjectUtils.Transformer<PositionTypeBo, PositionType>() {
		public PositionType transform(PositionTypeBo input) {
			return PositionTypeBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<PositionType, PositionTypeBo> toBo = new ModelObjectUtils.Transformer<PositionType, PositionTypeBo>() {
		public PositionTypeBo transform(PositionType input) {
			return PositionTypeBo.from(input);
		};
	};

	private static final String POSITION_TYPE = "positionType";
	private static final String GROUP_KEY_CODE = "groupKeyCode";

	// KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(POSITION_TYPE).add(GROUP_KEY_CODE).build();

	private static final long serialVersionUID = 1L;

	private String pmPositionTypeId;
	private String positionType;
	private String description;
	private boolean academicFlag;

	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(POSITION_TYPE, this.getPositionType())
				.put(GROUP_KEY_CODE, this.getGroupKeyCode()).build();
	}

	@Override
	public String getId() {
		return this.getPmPositionTypeId();
	}

	@Override
	public void setId(String id) {
		setPmPositionTypeId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionType() + "_" + this.getGroupKeyCode();
	}

	public String getPmPositionTypeId() {
		return pmPositionTypeId;
	}

	public void setPmPositionTypeId(String pmPositionTypeId) {
		this.pmPositionTypeId = pmPositionTypeId;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String PositionType) {
		this.positionType = PositionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isAcademicFlag() {
		return academicFlag;
	}

	public void setAcademicFlag(boolean academicFlag) {
		this.academicFlag = academicFlag;
	}

	public static PositionTypeBo from(PositionType im) {

		if (im == null) {
			return null;
		}

		PositionTypeBo positionTypeBo = new PositionTypeBo();
		positionTypeBo.setDescription(im.getDescription());
		positionTypeBo.setPmPositionTypeId(im.getPmPositionTypeId());
		positionTypeBo.setPositionType(im.getPositionType());
		positionTypeBo.setGroupKeyCode(im.getGroupKeyCode());     
		positionTypeBo.setAcademicFlag(im.isAcademicFlag());
		positionTypeBo.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));


		copyCommonFields(positionTypeBo, im);

		return positionTypeBo;

	}

	public static void copyCommonFields(HrBusinessObject dest,
			KpmeEffectiveDataTransferObject src) {
		dest.setEffectiveDate(src.getEffectiveLocalDate() == null ? null : src
				.getEffectiveLocalDate().toDate());
		dest.setActive(src.isActive());
		if (src.getCreateTime() != null) {
			dest.setTimestamp(new Timestamp(src.getCreateTime().getMillis()));
		}
		dest.setUserPrincipalId(src.getUserPrincipalId());
		dest.setVersionNumber(src.getVersionNumber());
		dest.setObjectId(src.getObjectId());
	}

	public static PositionType to(PositionTypeBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionType.Builder.create(bo).build();
	}

}