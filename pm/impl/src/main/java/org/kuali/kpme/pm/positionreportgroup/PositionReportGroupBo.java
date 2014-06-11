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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.kuali.kpme.core.bo.HrKeyedSetBusinessObject;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroupContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionReportGroupBo extends HrKeyedSetBusinessObject<PositionReportGroupBo, PositionReportGroupKeyBo> implements PositionReportGroupContract {

	private static final long serialVersionUID = -1562521354198281362L;

	private static final String POSITION_REPORT_GROUP = "positionReportGroup";

	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(POSITION_REPORT_GROUP)
		    .build();

	
	
	private String pmPositionReportGroupId;
	private String positionReportGroup;
	private String description;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(POSITION_REPORT_GROUP, this.getPositionReportGroup())
				.build();
	}

	/*
	 * convert bo to immutable
	 *
	 * Can be used with ModelObjectUtils:
	 *
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionReportGroupBo, PositionReportGroupBo.toImmutable);
	 */
	public static final ModelObjectUtils.Transformer<PositionReportGroupBo, PositionReportGroup> toImmutable =
			new ModelObjectUtils.Transformer<PositionReportGroupBo, PositionReportGroup>() {
		public PositionReportGroup transform(PositionReportGroupBo input) {
			return PositionReportGroupBo.to(input);
		};
	};

	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionReportGroup, PositionReportGroupBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<PositionReportGroup, PositionReportGroupBo> toBo =
			new ModelObjectUtils.Transformer<PositionReportGroup, PositionReportGroupBo>() {
		public PositionReportGroupBo transform(PositionReportGroup input) {
			return PositionReportGroupBo.from(input);
		};
	};

	@Override
	public String getId() {
		return this.getPmPositionReportGroupId();
	}

	@Override
	public void setId(String id) {
		setPmPositionReportGroupId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionReportGroup();
	}

	public String getPmPositionReportGroupId() {
		return pmPositionReportGroupId;
	}

	public void setPmPositionReportGroupId(String pmPositionReportGroupId) {
		this.pmPositionReportGroupId = pmPositionReportGroupId;
	}

	public String getPositionReportGroup() {
		return positionReportGroup;
	}

	public void setPositionReportGroup(String positionReportGroup) {
		this.positionReportGroup = positionReportGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static PositionReportGroupBo from(PositionReportGroup im) {
		if (im == null) {
			return null;
		}
		PositionReportGroupBo prg = new PositionReportGroupBo();
		prg.setPmPositionReportGroupId(im.getPmPositionReportGroupId());
		prg.setPositionReportGroup(im.getPositionReportGroup());
		prg.setDescription(im.getDescription());
		
		Set<PositionReportGroupKeyBo> effectiveKeyBoSet = ModelObjectUtils.transformSet(im.getEffectiveKeySet(), PositionReportGroupKeyBo.toBo);
		// set prg as the owner for each of the derived effective key objects in the set
		PositionReportGroupKeyBo.setOwnerOfDerivedCollection(prg, effectiveKeyBoSet);
		// set the key list, constructed from the key set
		if(effectiveKeyBoSet != null) {
			prg.setEffectiveKeyList(new ArrayList<PositionReportGroupKeyBo>(effectiveKeyBoSet));
		}
		
		// finally copy over the common fields into prg from im
		copyCommonFields(prg, im);

		return prg;
	}

	public static PositionReportGroup to(PositionReportGroupBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionReportGroup.Builder.create(bo).build();
	}

	@Override
	public List<PositionReportGroupKeyBo> getEffectiveKeyList(){
		return super.getEffectiveKeyList();	
	}

	public void setEffectiveKeyList(List<PositionReportGroupKeyBo> effectiveKeyList) {
		super.setEffectiveKeyList(effectiveKeyList);
	}

}