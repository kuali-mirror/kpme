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
package org.kuali.kpme.core.bo;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class HrBusinessObject extends PersistableBusinessObjectBase implements HrBusinessObjectContract {

	private static final long serialVersionUID = -5743717258128864335L;
	
	private Date effectiveDate;
	private boolean active=true;
	private Timestamp timestamp;
    private String userPrincipalId;

    //purely for UI (showHistory)
    private transient Boolean history;

	public abstract String getId();
	
	public abstract void setId(String id);
	
	protected abstract String getUniqueKey();
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

    public Date getRelativeEffectiveDate() {
        return effectiveDate == null ? LocalDate.now().toDate() : effectiveDate;
    }

    public void setRelativeEffectiveDate(Date relativeEffectiveDate) {
        //do nothing
    }
	
	public LocalDate getEffectiveLocalDate() {
		return effectiveDate != null ? LocalDate.fromDateFields(effectiveDate) : null;
	}
	
	public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
		this.effectiveDate = effectiveLocalDate != null ? effectiveLocalDate.toDate() : null;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

    public String getUserPrincipalId() {
        return userPrincipalId;
    }

    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }

	public boolean areAllBusinessKeyValuesAvailable() {
		boolean retVal = true;
		try {
			if(this.getBusinessKeyValuesMap().isEmpty()) {
				retVal = false;
			}
		}
		catch(Exception e) {
			retVal = false;
		}		
		return retVal;
	}

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public DateTime getCreateTime() {
        return getTimestamp() == null ? null : new DateTime(getTimestamp().getTime());
    }
    
    
    /**
     * Utility method to copy over the common fields like timestamp, effective date etc. from the src object 
     * to the dest object.
     * 
     * @param dest
     * @param src
     */
    public static void copyCommonFields(HrBusinessObject dest, KpmeEffectiveDataTransferObject src) {
    	dest.setEffectiveDate(src.getEffectiveLocalDate() == null ? null : src.getEffectiveLocalDate().toDate());
        dest.setActive(src.isActive());
        if (src.getCreateTime() != null) {
            dest.setTimestamp(new Timestamp(src.getCreateTime().getMillis()));
        }
        dest.setUserPrincipalId(src.getUserPrincipalId());
        dest.setVersionNumber(src.getVersionNumber());
        dest.setObjectId(src.getObjectId());
    }
}
