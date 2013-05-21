/**
 * Copyright 2004-2013 The Kuali Foundation
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

import org.kuali.kpme.core.bo.position.PositionBase;

import java.util.LinkedList;
import java.util.List;


public class Position extends PositionBase {
    private List<PositionQualification> qualificationList = new LinkedList<PositionQualification>();
    private List<PositionDuty> dutyList = new LinkedList<PositionDuty>();
    private List<PstnFlag> flagList = new LinkedList<PstnFlag>();

    public List<PositionDuty> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<PositionDuty> dutyList) {
		this.dutyList = dutyList;
	}

	public List<PositionQualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<PositionQualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<PstnFlag> getFlagList() {
		return flagList;
	}

	public void setFlagList(List<PstnFlag> flagList) {
		this.flagList = flagList;
	}
}
