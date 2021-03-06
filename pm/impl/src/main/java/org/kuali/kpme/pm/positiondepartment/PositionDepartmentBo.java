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
package org.kuali.kpme.pm.positiondepartment;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.kuali.kpme.core.api.departmentaffiliation.service.DepartmentAffiliationService;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliationBo;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartmentContract;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.PositionKeyedDerived;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.ImmutableList;

public class PositionDepartmentBo extends PositionKeyedDerived implements PositionDepartmentContract {
	
    static class KeyFields {
    	private static final String DEPARTMENT = "department";
    	final static String GROUP_KEY_CODE = "groupKeyCode";
    }
    
	//TODO reslove the issue with DepartmentAffiliation to implement  PositionDepartmentContract
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(KeyFields.DEPARTMENT)
		    .add(KeyFields.GROUP_KEY_CODE)
		    .build();
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionDeptId;
	private String department;
	private String deptAffl;

	private DepartmentBo departmentObj;
	private DepartmentAffiliationBo deptAfflObj;

	/**
	 * @return the DeptAffl
	 */
	public String getDeptAffl() {
		return deptAffl;
	}

	/**
	 * @param deptAffl the deptAffl to set
	 */
	public void setDeptAffl(String deptAffl) {
		this.deptAffl = deptAffl;
	}

	/**
	 * @return the deptAfflObj
	 */
	public DepartmentAffiliationBo getDeptAfflObj() {
		
		if (deptAfflObj == null) {
			if (!StringUtils.isEmpty(deptAffl)) {
				DepartmentAffiliationService pdaService = HrServiceLocator.getDepartmentAffiliationService();
				deptAfflObj = DepartmentAffiliationBo.from(pdaService.getDepartmentAffiliationByType(deptAffl));
			}
		} 
		
		return deptAfflObj;
	}

	/**
	 * @param deptAfflObj the deptAfflObj to set
	 */
	public void setDeptAfflObj(
			DepartmentAffiliationBo deptAfflObj) {
		this.deptAfflObj = deptAfflObj;
	}

    /**
	 * @return the pmPositionDeptId
	 */
	public String getPmPositionDeptId() {
		return pmPositionDeptId;
	}

	/**
	 * @param pmPositionDeptId the pmPositionDeptId to set
	 */
	public void setPmPositionDeptId(String pmPositionDeptId) {
		this.pmPositionDeptId = pmPositionDeptId;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the departmentObj
	 */
	public DepartmentBo getDepartmentObj() {
		return departmentObj;
	}

	/**
	 * @param departmentObj the departmentObj to set
	 */
	public void setDepartmentObj(DepartmentBo departmentObj) {
		this.departmentObj = departmentObj;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        PositionDepartmentBo rhs = (PositionDepartmentBo)obj;
        return new EqualsBuilder()
                .append(pmPositionDeptId,rhs.getPmPositionDeptId())
                .append(groupKeyCode,rhs.getGroupKeyCode())
                .append(department, rhs.getDepartment())
                .append(deptAffl, rhs.getDeptAffl())
                .append(hrPositionId, rhs.getHrPositionId())
                .isEquals();

    }

	@Override
	public String getId() {
		return this.getPmPositionDeptId();
	}

	@Override
	public void setId(String id) {
		this.setPmPositionDeptId(id);
	}
	
	public static PositionDepartmentBo from(PositionDepartment im) {
				if (im == null) {
					return null;
				}
				PositionDepartmentBo pd = new PositionDepartmentBo();
				pd.setPmPositionDeptId(im.getPmPositionDeptId());
				pd.setDeptAfflObj(DepartmentAffiliationBo.from(im.getDeptAfflObj()));
				pd.setGroupKeyCode(im.getGroupKeyCode());
				pd.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
				pd.setDepartment(im.getDepartment());
				pd.setDeptAffl(im.getDeptAffl());
				pd.setHrPositionId(im.getHrPositionId());
		 
				pd.setVersionNumber(im.getVersionNumber());
				pd.setObjectId(im.getObjectId());
				return pd;
			}
			
			public static PositionDepartment to(PositionDepartmentBo bo) {
				if (bo == null) {
					return null;
				}
				return PositionDepartment.Builder.create(bo).build();
			}
		
			public static final ModelObjectUtils.Transformer<PositionDepartmentBo, PositionDepartment> toImmutable = new ModelObjectUtils.Transformer<PositionDepartmentBo, PositionDepartment>() {
				public PositionDepartment transform(PositionDepartmentBo input) {
					return PositionDepartmentBo.to(input);
				};
			};
		
			public static final ModelObjectUtils.Transformer<PositionDepartment, PositionDepartmentBo> toBo = new ModelObjectUtils.Transformer<PositionDepartment, PositionDepartmentBo>() {
				public PositionDepartmentBo transform(PositionDepartment input) {
					return PositionDepartmentBo.from(input);
				};
			};
}