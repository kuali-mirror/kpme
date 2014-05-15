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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.rules.shiftdifferential.ruletype.ShiftDifferentialRuleTypeContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftTypeService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;

import javax.xml.namespace.QName;

public class ShiftDifferentialRuleType extends HrBusinessObject implements ShiftDifferentialRuleTypeContract{
	
	static class KeyFields {
    	private static final String NAME = "name";
    }
	private static final long serialVersionUID = 1L;
	public static final String CACHE_NAME = TkConstants.Namespace.NAMESPACE_PREFIX + "ShiftDifferentialRuleType";
	
	private String tkShiftDiffRuleTypeId;
	private String namespace;
	private String name;

    private String serviceName;

    private transient ShiftTypeService shiftTypeService;
	
	public String getTkShiftDiffRuleTypeId() {
		return tkShiftDiffRuleTypeId;
	}

	public void setTkShiftDiffRuleTypeId(String tkShiftDiffRuleTypeId) {
		this.tkShiftDiffRuleTypeId = tkShiftDiffRuleTypeId;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	 @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.NAME, this.getName())
				.build();
	}

	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(KeyFields.NAME)
            .build();
	
	@Override
	public String getId() {
		return getTkShiftDiffRuleTypeId();
	}

	@Override
	public void setId(String id) {
		setTkShiftDiffRuleTypeId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getName();
	}

    public void setShiftTypeService(ShiftTypeService shiftTypeService) {
        this.shiftTypeService = shiftTypeService;
    }

    public ShiftTypeService getShiftTypeService() {
        if (shiftTypeService == null) {
            QName serviceQName = QName.valueOf(getServiceName());
            try {
                shiftTypeService = GlobalResourceLoader.getService(serviceQName);
                if (shiftTypeService == null) {
                    shiftTypeService = TkServiceLocator.getService(TkServiceLocator.TK_SHIFT_TYPE_SERVICE_BASE);
                }
            } catch (Exception ex) {
                shiftTypeService = TkServiceLocator.getService(TkServiceLocator.TK_SHIFT_TYPE_SERVICE_BASE);
            }
        }
        return shiftTypeService;

    }

}
