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
package org.kuali.kpme.pm.positionResponsibilityOption;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.calendar.CalendarBo;
import org.kuali.kpme.core.leaveplan.LeavePlanBo;
import org.kuali.kpme.pm.api.positionresponsibilityoption.PositionResponsibilityOption;
import org.kuali.kpme.pm.api.positionresponsibilityoption.PositionResponsibilityOptionContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionResponsibilityOptionBo extends HrBusinessObject implements PositionResponsibilityOptionContract {
	
	private static final String PR_DESCRIPTION = "prDescription";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5054782543015429750L;
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(PR_DESCRIPTION)
		    .build();
	
	/*
	 * convert bo to immutable
	 *
     * Can be used with ModelObjectUtils:
     *
     * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionResponsibilityOptionBo, PositionResponsibilityOptionBo.toImmutable);
     */
    public static final ModelObjectUtils.Transformer<PositionResponsibilityOptionBo, PositionResponsibilityOption> toImmutable =
            new ModelObjectUtils.Transformer<PositionResponsibilityOptionBo, PositionResponsibilityOption>() {
                public PositionResponsibilityOption transform(PositionResponsibilityOptionBo input) {
                    return PositionResponsibilityOptionBo.to(input);
                };
            };

    /*
     * convert immutable to bo
     *
     * Can be used with ModelObjectUtils:
     *
     * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionResponsibilityOption, PositionResponsibilityOptionBo.toBo);
     */
    public static final ModelObjectUtils.Transformer<PositionResponsibilityOption, PositionResponsibilityOptionBo> toBo =
            new ModelObjectUtils.Transformer<PositionResponsibilityOption, PositionResponsibilityOptionBo>() {
                public PositionResponsibilityOptionBo transform(PositionResponsibilityOption input) {
                    return PositionResponsibilityOptionBo.from(input);
                };
            };
	
	private String prOptionId;
	private String prOptionName;
	private String prDescription;
	
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(PR_DESCRIPTION, this.getPrDescription())
				.build();
	}
	
	public String getPrOptionId() {
		return prOptionId;
	}

	public void setPrOptionId(String prOptionId) {
		this.prOptionId = prOptionId;
	}

	public String getPrOptionName() {
		return prOptionName;
	}

	public void setPrOptionName(String prOptionName) {
		this.prOptionName = prOptionName;
	}

	public String getPrDescription() {
		return prDescription;
	}

	public void setPrDescription(String prDescription) {
		this.prDescription = prDescription;
	}

	@Override
	public String getId() {
		return this.getPrOptionId();
	}

	@Override
	public void setId(String id) {
		this.setPrOptionId(id);
		
	}

	@Override
	protected String getUniqueKey() {
		return this.getPrOptionName();
	}
	

    public static PositionResponsibilityOptionBo from(PositionResponsibilityOption im) {
        if (im == null) {
            return null;
        }
        PositionResponsibilityOptionBo pro = new PositionResponsibilityOptionBo();

        pro.setPrOptionId(im.getPrOptionId());
        pro.setPrOptionName(im.getPrOptionName());
        pro.setPrDescription(im.getPrDescription());
        
        // finally copy over the common fields into pro from im
        copyCommonFields(pro, im);

        return pro;
    }

    public static PositionResponsibilityOption to(PositionResponsibilityOptionBo bo) {
        if (bo == null) {
            return null;
        }

        return PositionResponsibilityOption.Builder.create(bo).build();
    }

}
