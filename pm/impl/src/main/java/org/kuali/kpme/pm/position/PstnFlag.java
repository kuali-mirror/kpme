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
package org.kuali.kpme.pm.position;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.kuali.kpme.pm.api.position.PstnFlagContract;


public class PstnFlag extends PositionDerived implements PstnFlagContract {
	private static final long serialVersionUID = 1L;
	
	private String pmFlagId;
	private String category;
	//private String names;
	private List<String> names; // KPME-2360/2958
	
	public String getPmFlagId() {
		return pmFlagId;
	}
	public void setPmFlagId(String pmFlagId) {
		this.pmFlagId = pmFlagId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        PstnFlag rhs = (PstnFlag)obj;
        return new EqualsBuilder()
                .append(this.getPmFlagId(), rhs.getPmFlagId())
                .append(this.getCategory(), rhs.getCategory())
                .append(this.getNames(), rhs.getNames())
                .append(hrPositionId, rhs.getHrPositionId())
                .isEquals();

    }
}
