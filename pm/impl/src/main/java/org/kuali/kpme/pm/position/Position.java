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
