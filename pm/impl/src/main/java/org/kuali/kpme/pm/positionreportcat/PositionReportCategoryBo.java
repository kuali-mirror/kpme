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
package org.kuali.kpme.pm.positionreportcat;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategoryContract;
import org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionReportCategoryBo extends HrKeyedBusinessObject implements PositionReportCategoryContract {
	
	private static final String POSITION_REPORT_CAT = "positionReportCat";
		
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(POSITION_REPORT_CAT)
		    .build();

	private static final long serialVersionUID = 1L;
	
	private String pmPositionReportCatId;
	private String positionReportCat;
	private String positionReportType;
	private String description;
	
	private PositionReportTypeBo prtObj;	
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(POSITION_REPORT_CAT, this.getPositionReportCat())
				.build();
	}
	
	/*
	 * convert bo to immutable
	 *
	 * Can be used with ModelObjectUtils:
	 *
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionReportCategoryBo, PositionReportCategoryBo.toImmutable);
	 */
	public static final ModelObjectUtils.Transformer<PositionReportCategoryBo, PositionReportCategory> toImmutable =
			new ModelObjectUtils.Transformer<PositionReportCategoryBo, PositionReportCategory>() {
		public PositionReportCategory transform(PositionReportCategoryBo input) {
			return PositionReportCategoryBo.to(input);
		};
	};

	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionReportCategory, PositionReportCategoryBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<PositionReportCategory, PositionReportCategoryBo> toBo =
			new ModelObjectUtils.Transformer<PositionReportCategory, PositionReportCategoryBo>() {
		public PositionReportCategoryBo transform(PositionReportCategory input) {
			return PositionReportCategoryBo.from(input);
		};
	};
	
	@Override
	public String getId() {
		return getPmPositionReportCatId();
	}

	@Override
	public void setId(String id) {
		setPmPositionReportCatId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionReportCat() + "_" + getPositionReportType() + "_" + getGroupKeyCode();
	}

	public String getPositionReportType() {
		return positionReportType;
	}

	public void setPositionReportType(String positionReportType) {
		this.positionReportType = positionReportType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPmPositionReportCatId() {
		return pmPositionReportCatId;
	}

	public void setPmPositionReportCatId(String pmPositionReportCatId) {
		this.pmPositionReportCatId = pmPositionReportCatId;
	}

	public String getPositionReportCat() {
		return positionReportCat;
	}

	public void setPositionReportCat(String positionReportCat) {
		this.positionReportCat = positionReportCat;
	}

	public PositionReportTypeBo getPrtObj() {
		return prtObj;
	}

	public void setPrtObj(PositionReportTypeBo prtObj) {
		this.prtObj = prtObj;
	}

	public static PositionReportCategoryBo from(PositionReportCategory im) {
		if (im == null) {
			return null;
		}
		PositionReportCategoryBo prc = new PositionReportCategoryBo();
		
		prc.setPmPositionReportCatId(im.getPmPositionReportCatId());
		prc.setPositionReportCat(im.getPositionReportCat());
		prc.setPositionReportType(im.getPositionReportType());
		prc.setDescription(im.getDescription());
		prc.setPrtObj(PositionReportTypeBo.from(im.getPrtObj()));
        
		// finally copy over the common fields into prc from im
		copyCommonFields(prc, im);

		return prc;
	} 

	public static PositionReportCategory to(PositionReportCategoryBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionReportCategory.Builder.create(bo).build();
	}
	
}
