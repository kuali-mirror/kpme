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

import org.kuali.kpme.core.bo.HrKeyedSetBusinessObject;
import org.kuali.kpme.pm.api.positiontype.PositionType;
import org.kuali.kpme.pm.api.positiontype.PositionTypeContract;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupKeyBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;


public class PositionTypeBo extends HrKeyedSetBusinessObject<PositionTypeBo, PositionTypeGroupKeyBo> implements PositionTypeContract {

    private static final long serialVersionUID = 3022125278353071180L;



    static class KeyFields {
		private static final String POSITION_TYPE = "positionType";
		//private static final String GROUP_KEY_CODE = "groupKeyCode";
	}
	
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

	
	// KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.POSITION_TYPE)
			//.add(GROUP_KEY_CODE)
			.build();


	private String pmPositionTypeId;
	private String positionType;
	private String description;
	private boolean academicFlag;
	

	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.POSITION_TYPE, this.getPositionType())
				//.put(GROUP_KEY_CODE, this.getGroupKeyCode())
				.build();
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
		return getPositionType();
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

		PositionTypeBo pt = new PositionTypeBo();
		pt.setDescription(im.getDescription());
		pt.setPmPositionTypeId(im.getPmPositionTypeId());
		pt.setPositionType(im.getPositionType());
		pt.setAcademicFlag(im.isAcademicFlag());


        Set<PositionTypeGroupKeyBo> effectiveKeyBoSet = ModelObjectUtils.transformSet(im.getEffectiveKeySet(), PositionTypeGroupKeyBo.toBo);
        // set pt as the owner for each of the derived effective key objects in the set
        PositionTypeGroupKeyBo.setOwnerOfDerivedCollection(pt, effectiveKeyBoSet);
        // set the key list, constructed from the key set
        if(effectiveKeyBoSet != null) {
            pt.setEffectiveKeyList(new ArrayList<PositionTypeGroupKeyBo>(effectiveKeyBoSet));
        }

		copyCommonFields(pt, im);

		return pt;

	}

	public static PositionType to(PositionTypeBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionType.Builder.create(bo).build();
	}

    @Override
    public List<PositionTypeGroupKeyBo> getEffectiveKeyList() {
        return super.getEffectiveKeyList();
    }

    public void setEffectiveKeyList(List<PositionTypeGroupKeyBo> effectiveKeyList) {
        super.setEffectiveKeyList(effectiveKeyList);
    }

}