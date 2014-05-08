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
package org.kuali.kpme.pm.positionflag;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.pm.api.positionflag.PositionFlag;
import org.kuali.kpme.pm.api.positionflag.PositionFlagContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionFlagBo extends HrBusinessObject implements
		PositionFlagContract {

	private static final String POSITION_FLAG_NAME = "positionFlagName";
	private static final String CATEGORY = "category";

	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(CATEGORY).add(POSITION_FLAG_NAME).build();

	private static final long serialVersionUID = 1L;

	private String pmPositionFlagId;
	private String category;
	private String positionFlagName;

	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(CATEGORY, this.getCategory())
				.put(POSITION_FLAG_NAME, this.getPositionFlagName()).build();
	}

	public String getPmPositionFlagId() {
		return pmPositionFlagId;
	}

	public void setPmPositionFlagId(String pmPositionFlagId) {
		this.pmPositionFlagId = pmPositionFlagId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPositionFlagName() {
		return positionFlagName;
	}

	public void setPositionFlagName(String positionFlagName) {
		this.positionFlagName = positionFlagName;
	}

	@Override
	public String getId() {
		return this.getPmPositionFlagId();
	}

	@Override
	public void setId(String id) {
		this.setPmPositionFlagId(id);

	}

	@Override
	protected String getUniqueKey() {
		return this.getCategory() + "_" + this.getPositionFlagName();
	}

	public static PositionFlagBo from(PositionFlag im) {
		if (im == null) {
			return null;
		}
		PositionFlagBo positionFlagBo = new PositionFlagBo();

		positionFlagBo.setPositionFlagName(im.getPositionFlagName());
		positionFlagBo.setCategory(im.getCategory());
		positionFlagBo.setId(im.getId());
		positionFlagBo.setObjectId(im.getObjectId());
		positionFlagBo.setPmPositionFlagId(im.getPmPositionFlagId());

		copyCommonFields(positionFlagBo, im);

		return positionFlagBo;
	}

	public static PositionFlag to(PositionFlagBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionFlag.Builder.create(bo).build();
	}

	public static final ModelObjectUtils.Transformer<PositionFlagBo, PositionFlag> toImmutable = new ModelObjectUtils.Transformer<PositionFlagBo, PositionFlag>() {
		public PositionFlag transform(PositionFlagBo input) {
			return PositionFlagBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<PositionFlag, PositionFlagBo> toBo = new ModelObjectUtils.Transformer<PositionFlag, PositionFlagBo>() {
		public PositionFlagBo transform(PositionFlag input) {
			return PositionFlagBo.from(input);
		};
	};
}
