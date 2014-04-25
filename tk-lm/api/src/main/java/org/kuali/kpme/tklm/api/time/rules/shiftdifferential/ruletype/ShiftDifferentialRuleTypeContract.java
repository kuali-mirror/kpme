package org.kuali.kpme.tklm.api.time.rules.shiftdifferential.ruletype;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

public interface ShiftDifferentialRuleTypeContract extends HrBusinessObjectContract{

	public String getTkShiftDiffRuleTypeId();

	public String getNamespace();

	public String getName();

	public String getServiceName();


}
