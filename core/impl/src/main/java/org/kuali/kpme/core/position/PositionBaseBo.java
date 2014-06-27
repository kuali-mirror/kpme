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
package org.kuali.kpme.core.position;

import org.kuali.kpme.core.api.position.PositionBase;
import org.kuali.kpme.core.api.position.PositionBaseContract;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionBaseBo extends HrKeyedBusinessObject implements PositionBaseContract {
	
	protected static class KeyFields {
		public static final String POSITION_NUMBER = "positionNumber";
	}

	
	private static final long serialVersionUID = -3258249005786874634L;
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(KeyFields.POSITION_NUMBER)
            .build();

	
	private String hrPositionId;
	private String positionNumber;
	private String description;

	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(KeyFields.POSITION_NUMBER, this.getPositionNumber())
			.build();
	}
	

	@Override
	public String getId() {
		return getHrPositionId();
	}

	@Override
	public void setId(String id) {
		setHrPositionId(id);
	}
	
	@Override
	public String getUniqueKey() {
		return positionNumber;
	}

	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static PositionBaseBo from(PositionBase im) {
		if (im == null) {
            return null;
        }		

		PositionBaseBo retVal = new PositionBaseBo();
		retVal.setHrPositionId(im.getHrPositionId());
		retVal.setPositionNumber(im.getPositionNumber());
		retVal.setGroupKeyCode(im.getGroupKeyCode());
		retVal.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
		retVal.setDescription(im.getDescription());
		copyCommonFields(retVal, im);

		retVal.setId(im.getId());
		retVal.setEffectiveLocalDate(im.getEffectiveLocalDate());

	    return retVal;
	}
	
	public static PositionBase to(PositionBaseBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionBase.Builder.create(bo).build();
	}

	public static final ModelObjectUtils.Transformer<PositionBaseBo, PositionBase> toImmutable = new ModelObjectUtils.Transformer<PositionBaseBo, PositionBase>() {
		public PositionBase transform(PositionBaseBo input) {
			return PositionBaseBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<PositionBase, PositionBaseBo> toBo = new ModelObjectUtils.Transformer<PositionBase, PositionBaseBo>() {
		public PositionBaseBo transform(PositionBase input) {
			return PositionBaseBo.from(input);
		};
	};
	
}